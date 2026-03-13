package com.medicore.auth.security;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	 private final JwtUtil jwtUtil;
	   

	    public JwtAuthenticationFilter(JwtUtil jwtUtil ) {
	        this.jwtUtil = jwtUtil;
	       
	    }

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
				FilterChain filterChain) throws ServletException, IOException {
			final String authHeader = request.getHeader("Authorization");

	        
	        String jwt = null;
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        	
	        	
	            jwt = authHeader.substring(7);
	            
	        }

	        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	            Claims claims = jwtUtil.extractAllClaims(jwt);

	            String email = claims.getSubject();

	            List<String> roles = claims.get("roles", List.class);

	            var authorities = roles.stream()
	                    .map(SimpleGrantedAuthority::new)
	                    .collect(Collectors.toList());

	          
	            	var authenticationToken =
	                        new UsernamePasswordAuthenticationToken(
	                                email,
	                                null,
	                                authorities
	                        );

	            	 authenticationToken.setDetails(
	                         new WebAuthenticationDetailsSource().buildDetails(request)
	                 );

	                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	             }

	             filterChain.doFilter(request, response);
}
			
		

}
