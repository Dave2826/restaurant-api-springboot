package com.david.restaurantapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro que intercepta cada solicitud HTTP
 * para validar el token JWT y establecer
 * la autenticacion en el contexto de seguridad.
 *
 * @author David Morales Guerrero
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        log.info("=== [JwtFilter] Header Authorization presente: {}, empieza con Bearer: {}",
                authHeader != null,
                authHeader != null && authHeader.startsWith("Bearer "));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader
                .replace("Bearer ", "")
                .replaceAll("\\s+", "")
                .trim();            String username = jwtUtil.extractUsername(token);
            log.info("=== [JwtFilter] Token recibido longitud: {}", token.length());
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.info("=== [JwtFilter] UserDetails cargado - username: {}, authorities: {}",
                        userDetails.getUsername(), userDetails.getAuthorities());
                log.info("=== [JwtFilter] Token limpio longitud: {}", token.length());
                log.info("=== [JwtFilter] Token limpio: {}", token);

                boolean tokenValido = jwtUtil.isTokenValid(token, userDetails.getUsername());
                log.info("=== [JwtFilter] Resultado isTokenValid: {}", tokenValido);

                if (tokenValido) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("=== [JwtFilter] Usuario autenticado correctamente en SecurityContext");
                }
            }
        } catch (Exception e) {
            log.error("=== [JwtFilter] Error procesando JWT", e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
