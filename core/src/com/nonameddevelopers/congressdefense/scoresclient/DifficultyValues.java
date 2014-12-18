package com.nonameddevelopers.congressdefense.scoresclient;

public class DifficultyValues {

	public static String getDifficultyString(int diff) {
		switch (diff) {
		case 0:
			return "EASY";
		case 1:
			return "MEDIUM";
		case 2:
			return "HARD";

		case 3:
			return "CHUCKNORRYS";
		default:
			return "NOLEVEL";
		}
	}

}
