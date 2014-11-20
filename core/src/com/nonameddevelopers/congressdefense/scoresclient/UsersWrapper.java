package com.nonameddevelopers.congressdefense.scoresclient;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UsersWrapper {
	
	@SerializedName("users")
	public List<User> users;
	
	public UsersWrapper(){
		
	}

}
