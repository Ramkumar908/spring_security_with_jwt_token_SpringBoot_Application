package com.smart.web.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart.web.model.ResponseHandler;
import com.smart.web.model.SmartUser;
import com.smart.web.model.UserDao;
import com.smart.web.repository.UserRepo;
@Service
public class JwtUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
   PasswordEncoder passwordEncoder;	
	
	@Autowired
    UserRegisterService registerservice;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		SmartUser user=userRepo.findByUsermail(username);
		
		if (user==null) {
			throw new UsernameNotFoundException("User Mail found with username: " + username);

//		   System.out.println("i am in if condition and the username"+username+"is matched");
//			return new User("rambabu","$2a$10$MujJMpue1KLGW4Dr1jmr0.Auzx3Hpk5joSFVZFIu.WclcUikV1X5i",
//					new ArrayList<>());
		}
		return new org.springframework.security.core.userdetails.User(user.getUsermail(), user.getuserpassword(),
				new ArrayList<>());
	
	}
	
	
	public ResponseHandler save(UserDao user)
	{
//		SmartUser newuser=new SmartUser();
//		newuser.setUsername(user.getUsername());
//		newuser.setuserpassword(passwordEncoder.encode(user.getPassword()));
//		newuser.setUsercontact(user.getUsercontact());
//		newuser.setUsermail(user.getUsermail());
//		newuser.setUserstatus(user.getUserstatus());
		
		return registerservice.registerUser(user);
		
	}

}
