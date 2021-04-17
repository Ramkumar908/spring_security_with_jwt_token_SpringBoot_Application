package com.smart.web.serviceImpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart.web.model.ResponseHandler;
import com.smart.web.model.SmartUser;
import com.smart.web.model.UserDao;
import com.smart.web.repository.UserRepo;
import com.smart.web.service.UserRegisterService;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public ResponseHandler registerUser(UserDao user) {

		ResponseHandler handler = new ResponseHandler();
		Random rand = new Random();
		int randno = rand.nextInt(9900) + 10;
		String mailSubject = "Smart Email Verification otp";
		String uniquecode = Integer.toString(randno);
		SmartUser us = userRepo.findByUsermail(user.getUsermail());
		SmartUser existUserContact = userRepo.findByUsercontact(user.getUsercontact());
		try {

			if (us == null) {

				if (existUserContact != null
						&& existUserContact.getUsercontact().equalsIgnoreCase(user.getUsercontact())) {
					// System.out.println("User No already register and the no is
					// "+existUsercontact);
					handler.setResponse_code(208);
					handler.setResponse_status("Already Register Number");
					handler.setResponse_message("No already register Please use onother Account");
					return handler;
				}
				SmartUser newuser = new SmartUser();
				newuser.setUsername(user.getUsername());
				newuser.setuserpassword(passwordEncoder.encode(user.getPassword()));
				newuser.setUsercontact(user.getUsercontact());
				newuser.setUsermail(user.getUsermail());
				newuser.setUserstatus("0");
				userRepo.save(newuser);
				boolean mailsendStatus = sendMail(user.getUsermail(), mailSubject, uniquecode);
				if (mailsendStatus == true) {
					handler.setResponse_code(200);
					handler.setResponse_message(
							"User Successfully saved and mail send to the usermail" + user.getUsermail());
					handler.setResponse_status(uniquecode);
					return handler;
				} else {
					handler.setResponse_code(406);
					handler.setResponse_message("Mail Send Failed  please send again ");
					handler.setResponse_status("Email send Failed ");
					return handler;
				}

			}

			// if existmail is true means user already exist then check status and define
			// condition

			else {
				if (us.getUsermail() != null && us.getUserstatus().equalsIgnoreCase("0")) {
					System.out.println("user already exist but not verified with usermail " + user.getUsermail()
							+ "and its status is " + us.getUserstatus());
					boolean usermailstatus = sendMail(user.getUsermail(), mailSubject, uniquecode);
					System.out.println("usermail status for register user mail sending time is" + usermailstatus);
					if (usermailstatus == true) {

						handler.setResponse_code(200);
						handler.setResponse_message(
								"User not verified please verify for login Mail successfully send ");
						handler.setResponse_status("Sucess");
					} else {
						handler.setResponse_code(406);
						handler.setResponse_message("User not verified please verify for login Mail Send Fail ");
						handler.setResponse_status("Mail Send Failed ");
					}
				} else {
					System.out.println("User mail and userstatus  of existing user Mail " + user.getUsermail()
							+ "and staus is  :" + us.getUserstatus());
					handler.setResponse_code(409);
					handler.setResponse_message("User Already exist please register with other account or login");
					handler.setResponse_status("Already exist");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			handler.setResponse_code(501);
			handler.setResponse_status("Internal Server Error");
			handler.setResponse_message("Something went wrong please register again");
			return handler;
		}
		return handler;
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
