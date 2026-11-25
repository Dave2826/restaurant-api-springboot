package com.david.restaurantapi.exception;

import com.david.restaurantapi.auth.FirebaseLoginService;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.Reason;
import com.david.restaurantapi.auth.FirebaseTokenVerifier.TokenRejectedException;
import com.david.restaurantapi.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Manejador global de excepciones para la API REST.
 * Captura excepciones y genera respuestas uniformes ({@link ErrorResponse})
 * sin exponer detalles internos ni stack traces al cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String NOT_FOUND_MESSAGE = "Recurso no encontrado";
    private static final String INVALID_REQUEST_MESSAGE = "Solicitud inválida";
    private static final String INVALID_PARAMETER_MESSAGE = "Parámetro con formato inválido";
    private static final String UNAUTHORIZED_MESSAGE = "Credenciales de acceso inválidas";
    private static final String FORBIDDEN_MESSAGE = "No tiene permisos para acceder a este recurso";
    private static final String CONFLICT_MESSAGE = "La operación entra en conflicto con datos existentes";
    private static final String METHOD_NOT_ALLOWED_MESSAGE = "Método HTTP no soportado para este recurso";
    private static final String UNSUPPORTED_MEDIA_TYPE_MESSAGE = "Tipo de contenido no soportado";
    private static final String AUTHENTICATION_INFRASTRUCTURE_MESSAGE =
            "No fue posible completar la autenticación por un problema interno";

    /**
     * Maneja errores de validación de argumentos en DTOs (@Valid).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErrorResponse.ValidationErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorResponse.ValidationErrorDetail(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return build(HttpStatus.BAD_REQUEST, "Error de validación en los campos enviados", request, details);
    }

    /**
     * Maneja peticiones con cuerpo JSON malformado o tipos de datos incompatibles.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        return build(HttpStatus.BAD_REQUEST, "Cuerpo de solicitud malformado o JSON inválido", request);
    }

    /**
     * Maneja peticiones con un Content-Type no soportado por el recurso.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {

        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, UNSUPPORTED_MEDIA_TYPE_MESSAGE, request);
    }

    /**
     * Maneja parámetros de ruta o query con un tipo distinto al esperado.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        return build(HttpStatus.BAD_REQUEST, INVALID_PARAMETER_MESSAGE, request);
    }

    /**
     * Maneja argumentos ilegales o parámetros inválidos.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {

        return build(HttpStatus.BAD_REQUEST, INVALID_REQUEST_MESSAGE, request);
    }

    /**
     * Maneja recursos inexistentes.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        return build(HttpStatus.NOT_FOUND, messageOrDefault(ex, NOT_FOUND_MESSAGE), request);
    }

    /**
     * Red de seguridad para entidades JPA inexistentes.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex, HttpServletRequest request) {

        return build(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE, request);
    }

    /**
     * Maneja rutas o recursos estáticos inexistentes.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(
            NoResourceFoundException ex, HttpServletRequest request) {

        return build(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE, request);
    }

    /**
     * Maneja fallos de infraestructura de autenticación (por ejemplo, base de
     * datos no disponible). No son errores de credenciales del usuario, por lo
     * que no deben responder 401.
     */
    @ExceptionHandler({AuthenticationServiceException.class, ProviderNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationInfrastructure(
            AuthenticationException ex, HttpServletRequest request) {

        log.error("Fallo de infraestructura de autenticación en la ruta {}",
                request.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, AUTHENTICATION_INFRASTRUCTURE_MESSAGE, request);
    }

    /**
     * Maneja fallos de autenticación (credenciales inválidas o usuario no encontrado).
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {

        return build(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_MESSAGE, request);
    }

    /**
     * Maneja denegaciones de acceso producidas por reglas de autorización.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {

        return build(HttpStatus.FORBIDDEN, FORBIDDEN_MESSAGE, request);
    }

    /**
     * Maneja rechazos de ID Tokens de Firebase.
     * Un token inválido responde 401; un dominio o correo no permitido responde 403.
     */
    @ExceptionHandler(TokenRejectedException.class)
    public ResponseEntity<ErrorResponse> handleTokenRejectedException(
            TokenRejectedException ex, HttpServletRequest request) {

        HttpStatus status = ex.getReason() == Reason.INVALID_TOKEN
                ? HttpStatus.UNAUTHORIZED
                : HttpStatus.FORBIDDEN;

        return build(status, ex.getMessage(), request);
    }

    /**
     * Maneja el caso en el que la integración con Firebase Admin no está
     * habilitada o no tiene credenciales. No es un fallo del usuario, por lo
     * que no debe responder 401 ni 500 genérico.
     */
    @ExceptionHandler(FirebaseLoginService.FirebaseUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleFirebaseUnavailable(
            FirebaseLoginService.FirebaseUnavailableException ex, HttpServletRequest request) {

        log.warn("Integración con Firebase no disponible en la ruta {}", request.getRequestURI());
        return build(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), request);
    }

    /**
     * Maneja violaciones de integridad de datos (claves foráneas, valores únicos, etc.).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        log.warn("Conflicto de integridad de datos en la ruta {}", request.getRequestURI(), ex);
        return build(HttpStatus.CONFLICT, CONFLICT_MESSAGE, request);
    }

    /**
     * Maneja el uso de un método HTTP no soportado por el recurso.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (supportedMethods != null && !supportedMethods.isEmpty()) {
            headers.setAllow(supportedMethods);
        }

        return build(HttpStatus.METHOD_NOT_ALLOWED, METHOD_NOT_ALLOWED_MESSAGE, request, headers);
    }

    /**
     * Manejador de respaldo para cualquier excepción no controlada.
     * Registra el error internamente con logger pero no expone stack traces al cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(
            Exception ex, HttpServletRequest request) {

        log.error("Excepción no controlada en la ruta {}: ", request.getRequestURI(), ex);

        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error interno en el servidor", request);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status,
                                                String message,
                                                HttpServletRequest request,
                                                HttpHeaders headers) {
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).headers(headers).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status,
                                                String message,
                                                HttpServletRequest request,
                                                List<ErrorResponse.ValidationErrorDetail> details) {
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                details
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    private String messageOrDefault(RuntimeException ex, String defaultMessage) {
        String message = ex.getMessage();
        return message != null && !message.isBlank() ? message : defaultMessage;
    }
}
