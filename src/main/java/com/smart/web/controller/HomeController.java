package com.smart.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.web.config.JwtTokenUtil;
import com.smart.web.model.JwtRequest;
import com.smart.web.model.ResponseHandler;
import com.smart.web.model.UserDao;
import com.smart.web.repository.UserRepo;
import com.smart.web.service.JwtUserDetailService;
import com.smart.web.service.UpdateUserAuhenticateToken;
import com.smart.web.service.VerifyUpdateService;
import com.smart.web.service.VerifyUserForgotPassword;

@Controller
public class HomeController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUserDetailService jwtUserDetailService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired 
	UserRepo userrepo;
	
	@Autowired
	private VerifyUserForgotPassword  forgotPasService;
	
	
	@Autowired
	VerifyUpdateService verifyUsrService;
	
	
	@Autowired
	UpdateUserAuhenticateToken authService;
	@GetMapping("/hello/{name}")
	public ResponseEntity<String> getCallDefault(@PathVariable String name)
	{
		
		System.out.println("Hello the controller is working fine ");
		
		return new ResponseEntity<String>("hello  :"+name,HttpStatus.OK);
	}
	
	@RequestMapping(value="/authenticate",method= {RequestMethod.POST})
	public ResponseEntity<ResponseHandler> createAuthenticationToken(@RequestBody JwtRequest jwtAuthenticationRequest) throws Exception
	{
		ResponseHandler handler=new ResponseHandler();
		 String token=null;
		try
		{
		authenticate(jwtAuthenticationRequest.getUsermail(),jwtAuthenticationRequest.getUserpassword());
		final UserDetails userDetails=jwtUserDetailService.loadUserByUsername(jwtAuthenticationRequest.getUsermail());
		token=jwtTokenUtil.generateToken(userDetails);
		}catch(Exception e)
		{
		   handler.setResponse_code(401);
  		   handler.setResponse_status("Not Authorised ");
  		   handler.setResponse_message("User Not authorized to authenticate ");
  		   return new ResponseEntity<ResponseHandler>(handler,HttpStatus.UNAUTHORIZED);
			
		}
		 
		handler=authService.updateUserAuthenticate(jwtAuthenticationRequest.getUsermail(), token);
		handler.setUser_auth_token(token);
		return new ResponseEntity<ResponseHandler>(handler,HttpStatus.OK);
		
	}

	
	private void authenticate(String usermail, String password) throws Exception {
		try
		{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usermail, password));
		}catch(DisabledException e)
		{
			throw new Exception("User Disabled ",e);
		}
		catch(BadCredentialsException e)
		{
			throw new Exception("Invalid Credentials",e);
		}
		
	}
	
	/*
	 * 
	 *  Register to new User With username,usermail,usercontact,password,
	 */
	@RequestMapping(value="/register",method={RequestMethod.POST})
	public ResponseEntity<ResponseHandler> saveUser(@RequestBody UserDao userdto) throws Exception
	{
		ResponseHandler handler=new ResponseHandler();

	    String username=userdto.getUsername();
	    String usermail=userdto.getUsermail();
	    String userpassword=userdto.getPassword();
	    String usercontact=userdto.getUsercontact();
	    if(username==null || usermail==null || userpassword ==null || usercontact==null)
	    {
	       handler.setResponse_code(400);
  		   handler.setResponse_status("Parameter Missing ");
  		   handler.setResponse_message("Parameter is missing check it once ");
  		   return new ResponseEntity<ResponseHandler>(handler,HttpStatus.BAD_REQUEST);
	    }
 	     handler=jwtUserDetailService.save(userdto);
 	    if(handler==null)
 	   {
 		   
 		   return new ResponseEntity<ResponseHandler>(handler,HttpStatus.INTERNAL_SERVER_ERROR);
 	   }
 	   return new ResponseEntity<ResponseHandler>(handler,HttpStatus.OK);
		
	}
	
	/*
	 * Otp Verify
	 * @Param(usermail)
	 * @param(otpResponseCode)
	 * @Param(ResponseMessage)
	 * testing url:http://localhost:8080/web/user/api/otp/response
	 * 
	 */
	   
	   
		@RequestMapping(value="/otp/response",method={RequestMethod.GET,RequestMethod.POST},consumes ="application/json",produces = "application/json")
		public ResponseEntity<ResponseHandler> updateVerifyUser(@RequestBody Map<String, Object> map,HttpServletRequest request)
		{
	    	
			System.out.println("the json body we get is"+map);
	    	ResponseHandler handler=new ResponseHandler();
	    	String usermail="";
	    	String otpResponsecode="";
	    	String otpResponseStatus="";
	    	
	    	
	    	   usermail=map.get("usermail").toString();
	    	   otpResponsecode=map.get("otpResponsecode").toString();
	    	   otpResponseStatus=map.get("otpResponseStatus").toString();
	    	   System.out.println("we get usermail is"+map.get("usermail"));
	    	   System.out.println("we get otpResponsecode is "+map.get("otpResponsecode"));
	    	   System.out.println("we get  otpResponseStatus is"+map.get("otpResponseStatus"));
	    	   
	    	   if(otpResponsecode!="200")
	    	   {
	    		   handler.setResponse_code(412);
	    		   handler.setResponse_status("Failed");
	    		   handler.setResponse_message("Otp not match send it again ");
	    		   return new ResponseEntity<ResponseHandler>(handler,HttpStatus.NOT_ACCEPTABLE);
	    	   }
	    	   if(usermail.equals(null)||otpResponsecode.equals(null)||otpResponseStatus.equals(null))
	    	   {
	    		   handler.setResponse_code(400);
	    		   handler.setResponse_status("Parameter Missing ");
	    		   handler.setResponse_message("Parameter is missing check it once ");
	    		   return new ResponseEntity<ResponseHandler>(handler,HttpStatus.BAD_REQUEST);
	    	   }
	    	   
	    	   handler=verifyUsrService.updateVerifyUser(usermail);
	    	   if(handler==null)
	    	   {
	    		   
	    		   return new ResponseEntity<ResponseHandler>(handler,HttpStatus.INTERNAL_SERVER_ERROR);
	    	   }
	    	   return new ResponseEntity<ResponseHandler>(handler,HttpStatus.OK);
		}
		
		
		/*
		 * User Forgot Password Authentication 
		 */
		
		@RequestMapping(value="/user/forgotPassword/{registerEmail}",method={RequestMethod.POST})
		public ResponseEntity<ResponseHandler> userForgotPassword(@PathVariable String registerEmail )
		{
			ResponseHandler handler=new ResponseHandler();
		
			System.out.println("the registered mail we get is "+registerEmail);
			if(registerEmail.equals(null)||registerEmail.isEmpty())
			{
				   handler.setResponse_code(400);
	    		   handler.setResponse_status("Failed");
	    		   handler.setResponse_message("Parameter Missing or Empty parameter ");
	    		   return new ResponseEntity<ResponseHandler>(handler,HttpStatus.BAD_REQUEST);
				
			}
			 handler=forgotPasService.verifyUserFOrForgotPassword(registerEmail);
			 if(handler.toString().equals(null))
			 {
				 return new ResponseEntity<ResponseHandler>(HttpStatus.INTERNAL_SERVER_ERROR);
			 }
			
			return new ResponseEntity<ResponseHandler>(handler,HttpStatus.OK);
			
		}
		
		
		
		
	

}
