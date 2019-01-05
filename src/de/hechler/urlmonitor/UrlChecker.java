package de.hechler.urlmonitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlChecker {

	private String httpUrl;
	
	private int timeoutMillis;
	
	private int lastStatus;
	private String lastContent;
	
	public UrlChecker(String httpUrl, int timeoutMillis) {
		this.httpUrl = httpUrl;
		this.timeoutMillis = timeoutMillis;
		this.lastStatus = 0;
		this.lastContent = "";
	}
	
	public boolean check() {
		try {
			lastStatus = 0;
			lastContent = "";
			
			URL url = new URL(httpUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(timeoutMillis);
			con.setReadTimeout(timeoutMillis);
			con.setInstanceFollowRedirects(false);
			
			int status = con.getResponseCode();
			lastStatus = status;
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			lastContent = content.toString();

			con.disconnect();
			return status == 200;
		}
		catch (Exception e) {
			lastContent = e.toString();
		}
		return false;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public int getLastStatus() {
		return lastStatus;
	}

	public String getLastContent() {
		return lastContent;
	}
	
	public String getResult() {
		return "STATUS="+getLastStatus()+" "+getLastContent();
	}
	
	
	
}
