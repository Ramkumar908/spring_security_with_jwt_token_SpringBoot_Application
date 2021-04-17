package com.smart.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.web.model.ResponseHandler;
import com.smart.web.repository.UserRepo;
import com.smart.web.service.VerifyUpdateService;

@Service
public class VerifyUserServiceImpl  implements VerifyUpdateService{

	
	@Autowired
	UserRepo userRepo;
	
	
	@Override
	public ResponseHandler updateVerifyUser(String usermail) {
		
		ResponseHandler handler=new ResponseHandler();
		   try
		   {
		 
			     userRepo.updateVerifyUserStatus(usermail);
			     handler.setResponse_code(200);
				 handler.setResponse_status("User Updated ");
				 handler.setResponse_message("User Status Updated send him for login");
				 return handler;
//			 }
//			 else
//			 {
//				 handler.setResponse_code(502);
//				 handler.setResponse_status("Database Error");
//				 handler.setResponse_message("DataBase Query not Run Properly");
//				 return handler;
//			 }
			
			 
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
