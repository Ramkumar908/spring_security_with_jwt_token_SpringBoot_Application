package com.smart.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.web.model.ResponseHandler;
import com.smart.web.repository.UserRepo;
import com.smart.web.service.UpdateUserAuhenticateToken;

@Service
public class UpdateUserAuhenticateServiceImpl  implements UpdateUserAuhenticateToken{

	@Autowired
	UserRepo repo;
	@Override
	public ResponseHandler updateUserAuthenticate(String usermail,String authenticateToken) {
		
		ResponseHandler handler=new ResponseHandler();
		try
		{
			if(usermail.equals(null)|| authenticateToken.equals(null))
			{
				    handler.setResponse_code(401);
		  		   handler.setResponse_status("Not Authenticate  ");
		  		   handler.setResponse_message("Usermail or authenticated token missing ");
		  		   return handler;
			}
			
			repo.updateUserAuthenticateToken(authenticateToken, usermail);
			
//			if(tokenupdatestatus!=-1)
//			 {
//				 handler.setResponse_code(200);
//				 handler.setResponse_status("User Updated ");
//				 handler.setResponse_message("User Toen  Updated send him for login");
//				 handler.setUser_auth_token(authenticateToken);
//				 return handler;
//			 }
//			 else
//			 {
				 handler.setResponse_code(200);
				 handler.setResponse_status("User Authentication Sucess");
				 handler.setResponse_message("Authentiation Update please keep the token for your reference");
				 return handler;
			 //}
			
			 
		 }
	   catch(Exception e)
		
		 {
			 e.printStackTrace();
			 handler.setResponse_code(502);
			 handler.setResponse_status("Database Error");
			 handler.setResponse_message("DataBase Query not Run Properly");
			 return handler;
		 }
	}

}
