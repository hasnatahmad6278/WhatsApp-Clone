package com.whattsApp.whatsappClone.config;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;


public class JwtTokenValidator extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jwt= request.getHeader(JwtConstant.JWT_HEADER);
		
		if(jwt!=null) {
			try {
				String email= TokenProvider.getEmailFromToken(jwt);
				List<GrantedAuthority>authorities=new ArrayList<>();
				Authentication authentication=new UsernamePasswordAuthenticationToken(email,null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
	
				
			} catch (Exception e) {
				// TODO: handle exception
				throw new BadCredentialsException("Invalid Token Recived");
			}
		}
		
		
		filterChain.doFilter(request, response);
		
		
		
		
	}
	
	
	
	
	
	
	

	/*@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jwt= request.getHeader("Authorization");
		
		if(jwt!=null) {
			try {
				jwt=jwt.substring(7);
				SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				Claims claim= Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				String username=String.valueOf(claim.get("email"));
				String authorities=String.valueOf(claim.get("authorities"));
				
				List<GrantedAuthority> auths=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				Authentication authentication= new UsernamePasswordAuthenticationToken(username,null, auths);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
	
				
			} catch (Exception e) {
				// TODO: handle exception
				throw new BadCredentialsException("Invalid Token Recived");
			}
		}
		
		
		filterChain.doFilter(request, response);
		
		
		
		
	}
	*/

}
