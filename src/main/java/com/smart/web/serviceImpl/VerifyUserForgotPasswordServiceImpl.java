package com.smart.web.serviceImpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smart.web.model.ResponseHandler;
import com.smart.web.model.SmartUser;
import com.smart.web.repository.UserRepo;
import com.smart.web.service.VerifyUserForgotPassword;

@Service
public class VerifyUserForgotPasswordServiceImpl  implements VerifyUserForgotPassword{

	
	@Autowired
	UserRepo repository;
	
	
	@Autowired
	private JavaMailSender mailSender;
	@Override
	public ResponseHandler verifyUserFOrForgotPassword(String usermail) {
		
		ResponseHandler handler = new ResponseHandler();
		Random rand = new Random();
		int randno = rand.nextInt(9900) + 10;
		String mailSubject = "forgot password Verification otp";
		String uniquecode = Integer.toString(randno);
		SmartUser us = repository.findByUsermail(usermail);
		
		try
		{
			if (us == null) {

				handler.setResponse_code(414);
				handler.setResponse_message("Given Mail is not Registered");
				handler.setResponse_status("Mail is Incorrect");
				return handler;
			}
			else
			{
				
				
				boolean mailsendStatus = sendMail(usermail, mailSubject, uniquecode);
				if (mailsendStatus == true) {
					handler.setResponse_code(200);
					handler.setResponse_message(
							"Code send to your Registered Mail Please veriy it " + usermail);
					handler.setResponse_status(uniquecode);
					return handler;
				} else {
					handler.setResponse_code(406);
					handler.setResponse_message("Mail Send Failed  please send again ");
					handler.setResponse_status("Email send Failed ");
					return handler;
				}

			}

			
		}catch(Exception e)
		{
			 e.printStackTrace();
		}
		
		return null;
	}
	
	
	private boolean sendMail(String to, String subject, String body) throws MailException {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		try {
			mailSender.send(message);
			return true;
		} catch (MailException e) {
			e.printStackTrace();
			return false;
		}
	}

}
