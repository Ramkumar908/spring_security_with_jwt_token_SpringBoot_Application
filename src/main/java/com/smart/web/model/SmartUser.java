package com.smart.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SmartUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	private String username;
	private String usermail;
	private String usercontact;
	private String userpassword;
	private String userstatus;
	private String registerat;
	private String usertoken;
	
	public SmartUser() {
		
	}

	public SmartUser(int userId, String username, String usermail, String usercontact, String userpassword,
			String userstatus, String registerat, String usertoken) {
		super();
		this.userId = userId;
		this.username = username;
		this.usermail = usermail;
		this.usercontact = usercontact;
		this.userpassword = userpassword;
		this.userstatus = userstatus;
		this.registerat = registerat;
		this.usertoken = usertoken;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public String getUsercontact() {
		return usercontact;
	}

	public void setUsercontact(String usercontact) {
		this.usercontact = usercontact;
	}

	public String getuserpassword() {
		return userpassword;
	}

	public void setuserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}

	public String getRegisterat() {
		return registerat;
	}

	public void setRegisterat(String registerat) {
		this.registerat = registerat;
	}

	public String getUsertoken() {
		return usertoken;
	}

	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}

	@Override
	public String toString() {
		return "DaoUser [userId=" + userId + ", username=" + username + ", usermail=" + usermail + ", usercontact="
				+ usercontact + ", userpassword=" + userpassword + ", userstatus=" + userstatus + ", registerat=" + registerat
				+ ", usertoken=" + usertoken + "]";
	}
	
	

}
