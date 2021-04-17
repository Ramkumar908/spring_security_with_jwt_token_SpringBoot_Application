package com.smart.web.model;

public class UserDao {
	
	private String username;
	private String usermail;
	private String usercontact;
	private String password;
	private String userstatus;
	
	public UserDao() {
		
	}

	public UserDao(String username, String usermail, String usercontact, String password, String userstatus) {
		super();
		this.username = username;
		this.usermail = usermail;
		this.usercontact = usercontact;
		this.password = password;
		this.userstatus = userstatus;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}

	@Override
	public String toString() {
		return "UserDao [username=" + username + ", usermail=" + usermail + ", usercontact=" + usercontact
				+ ", password=" + password + ", userstatus=" + userstatus + "]";
	}

	
	
	
	
	
}
