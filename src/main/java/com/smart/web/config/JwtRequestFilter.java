package com.smart.web.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.ExpiredJwtException;

import com.smart.web.model.ResponseHandler;
import com.smart.web.service.JwtUserDetailService;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUserDetailService jwtUserDetailService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		// Jwt token =Header+payload+signature
		// In header=contentType ,algorithm Used
		// payload=Actual Data Of User
		ResponseHandler handler=new ResponseHandler();
		final String requestTokenHeader=request.getHeader("Authorization");
		String username=null;
		String jwtToken=null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
				// only the Token
		
		if(requestTokenHeader !=null && requestTokenHeader.startsWith("Bearer"))
		{
			jwtToken=requestTokenHeader.substring(7);
			try
			{
				
				username=jwtTokenUtil.getUsernameFromToken(jwtToken);
				System.out.println("I am called at first JwtRequestFilter ");
			}catch(IllegalArgumentException e)
			{
				   handler.setResponse_code(311);
		  		   handler.setResponse_status("illegal argument exception");
		  		   handler.setResponse_message("Please enter right argument");
		  		   handler.setUser_auth_token(jwtToken);
		  		  // return handler;
				
			}catch(ExpiredJwtException e)
			{
				System.out.println("Jwt token expired please login again");
				   handler.setResponse_code(310);
		  		   handler.setResponse_status("token not valid ");
		  		   handler.setResponse_message("Token Expire please login again ");
		  		   handler.setUser_auth_token(jwtToken);
		  		  // return handler;
				
			}
			
		}
		else
		{
			logger.warn("JwtToken not start with Bearer String");
			   handler.setResponse_code(309);
	  		   handler.setResponse_status("Token Not found/start withBeare");
	  		   handler.setResponse_message("Token Not present in header ");
	  		   //return handler;
		}
		// Once we get the token validate it.
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails =this.jwtUserDetailService.loadUserByUsername(username);
			
			// if token is valid configure Spring Security to manually set
						// authentication
			
			if(jwtTokenUtil.validateToken(jwtToken,userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		chain.doFilter(request, response);
		// TODO Auto-generated method stub
		
	}
	

}
