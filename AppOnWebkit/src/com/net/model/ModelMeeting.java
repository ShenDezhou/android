package com.net.model;



public class ModelMeeting implements ItemInterface{


	private String id;
	
	private String username;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int getItemType() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	
	
}
