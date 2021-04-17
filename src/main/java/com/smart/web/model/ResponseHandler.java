package com.smart.web.model;

public class ResponseHandler {


	private String response_message;
	private int response_code;
	private String response_status;
	private String user_auth_token;
	public ResponseHandler() {
		
	}
	
	public ResponseHandler(String response_message, int response_code, String response_status, String user_auth_token) {
		this.response_message = response_message;
		this.response_code = response_code;
		this.response_status = response_status;
		this.user_auth_token = user_auth_token;
	}

	public String getResponse_message() {
		return response_message;
	}
	public void setResponse_message(String response_message) {
		this.response_message = response_message;
	}
	public int getResponse_code() {
		return response_code;
	}
	public void setResponse_code(int response_code) {
		this.response_code = response_code;
	}
	public String getResponse_status() {
		return response_status;
	}
	public void setResponse_status(String response_status) {
		this.response_status = response_status;
	}
	
	
	public String getUser_auth_token() {
		return user_auth_token;
	}

	public void setUser_auth_token(String user_auth_token) {
		this.user_auth_token = user_auth_token;
	}

	@Override
	public String toString() {
		return "ResponseHandler [response_message=" + response_message + ", response_code=" + response_code
				+ ", response_status=" + response_status + ", user_auth_token=" + user_auth_token + "]";
	}

	
	
	
}
