package com.smart.web.model;

import java.io.Serializable;


public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	private String usermail;
	private String userpassword;
	
	
	public JwtRequest() {
		
	}


	public JwtRequest(String usermail, String userpassword) {
		
		this.usermail = usermail;
		this.userpassword = userpassword;
	}


	public String getUsermail() {
		return usermail;
	}


	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}


	public String getUserpassword() {
		return userpassword;
	}


	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
