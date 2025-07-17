package com.studentmanagement.common.filter;

import com.studentmanagement.service.JWTService;
import com.studentmanagement.service.UserSecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    JWTService jwtService;
    @Autowired
    ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader != null) {
            if (!authHeader.startsWith("Bearer ")) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "invalid authorized header");
                return;
            }

            String token = authHeader.substring(7);
            if (jwtService.isTokenExpired(token)) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "token is expired");
                return;
            }

            String userEmail;
            try {
                userEmail = jwtService.extractUserEmail(token);
            } catch (Exception e) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "user not found");
                return;
            }

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = context.getBean(UserSecurityService.class).loadUserByUsername(userEmail);
                List<String> roles = jwtService.extractAllRoles(token);
                List<String> rolesFromDb = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                if(!rolesFromDb.equals(roles)){
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "user not allow");
                    return;
                }
                if (!jwtService.validateToken(token, userDetails)) {
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "token is not validate for user");
                    return;
                }
                try {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (Exception e) {
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
                    return;
                }

            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = String.format("{\"status\": %d, \"error\": \"%s\"}", statusCode, message);
        response.getWriter().write(json);
    }

}
