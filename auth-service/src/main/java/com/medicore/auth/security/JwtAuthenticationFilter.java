package com.medicore.auth.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	 private final JwtUtil jwtUtil;
	    private final UserDetailsService userDetailsService;

	    public JwtAuthenticationFilter(JwtUtil jwtUtil,
	                                   UserDetailsService userDetailsService) {
	        this.jwtUtil = jwtUtil;
	        this.userDetailsService = userDetailsService;
	    }

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
				FilterChain filterChain) throws ServletException, IOException {
			final String authHeader = request.getHeader("Authorization");

	        String email = null;
	        String jwt = null;
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            jwt = authHeader.substring(7);
	            email = jwtUtil.extractEmail(jwt);
	        }

	        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	            var userDetails = userDetailsService.loadUserByUsername(email);

	            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {

	                var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    filterChain.doFilter(request, response);
}
			
		

}
