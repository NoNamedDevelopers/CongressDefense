package com.nonameddevelopers.congressdefense.scoresclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.nonameddevelopers.congressdefense.scoresclient.petitions.UpdateOrInsertScore;

public class RESTConnector {

	public static List<User> getScores() throws Exception {
		String url = "http://156.35.94.103:8080/SEV/rest/api/scores";
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", "CongressDefenseCore");

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			String json = response.toString();
			// print result
			System.out.println(json);
			Gson gs = new Gson();

			return gs.fromJson(json, UsersWrapper.class).users;
		} catch (Exception e) {
			throw e;
		}
		
		
	}

	public static void updateUserScore(String username, Integer score) {
		UpdateOrInsertScore updateOrInsertScore = new UpdateOrInsertScore(
				username, score);
		Thread t = new Thread(updateOrInsertScore);
		t.start();
	}

}
