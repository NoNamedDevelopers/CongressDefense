package com.nonameddevelopers.congressdefense.scoresclient;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("Username")
	public String Username;

	@SerializedName("Score")
	public Integer Score;

	public User() {

	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public Integer getScore() {
		return Score;
	}

	public void setScore(Integer score) {
		Score = score;
	}

}
