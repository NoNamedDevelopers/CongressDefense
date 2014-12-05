package com.nonameddevelopers.congressdefense.scoresclient;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("Username")
	public String Username;

	@SerializedName("Score")
	public Integer Score;

	@SerializedName("Level")
	public String Level;
	
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

	public String getLevel() {
		return Level;
	}

	public void setLevel(String level) {
		Level = level;
	}

	@Override
	public String toString() {
		return "User [Username=" + Username + ", Score=" + Score + ", Level="
				+ Level + "]";
	}

	
	
	

}
