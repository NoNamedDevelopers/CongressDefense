package com.nonameddevelopers.congressdefense.scoresclient.petitions;

import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateOrInsertScore implements Runnable {

	private String username;
	private Integer score;
	private String difficult;

	public UpdateOrInsertScore(String username, Integer score,String difficult) {
		this.username = username;
		this.score = score;
		this.difficult = difficult;
	}

	@Override
	public void run() {
		String url = String.format(
				"http://156.35.94.103:8080/SEV/rest/api/update/%s/%d/%s", username,
				score,difficult);

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
		} catch (Exception e) {
			// do nothing
		}
	}

}
