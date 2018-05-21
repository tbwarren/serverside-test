package serverside.test.sainsburys.berriescherriescurrents.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlConnection {
	
	private HttpURLConnection httpUrlConnection = null;
	
	/*
	 * This is the constructor that sets up the HttpConnection to access
	 * Parameters: url - URL to connect to
	 * throws: IOException
	 */
	public HttpUrlConnection(String url) throws IOException{
		setUpConnectionToUrl(url);
	}
	
	/*
	 * This method gets the data from the Url the class is connected to
	 */
	public StringBuffer getDataString(){
		try{
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.connect();
			
			int responseCode = httpUrlConnection.getResponseCode();
			
			if(responseCode == 200) {
				String.format("Successfully received data. Response Code: %1$d",  responseCode);
				BufferedReader reader = new BufferedReader(
				        new InputStreamReader(httpUrlConnection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				reader.close();
				return response;

			}
			else {
				String.format("Error Code Received: %1$d", responseCode);
			}
		}
		catch(IOException ioException) {
			System.out.println("Unable to Successfully Connect to Url");
		}
		
		return null;
	}
	
	/*
	 * This is a method to get the response code of a GET request
	 * Throws: IOException
	 */
	public Integer getResponseCode() throws IOException{
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.connect();
			
			return httpUrlConnection.getResponseCode();	
	}
	
	/*
	 * This method connects to the desired Url passed in the arguments
	 * Parameters: urlString - URL to connect to
	 * throws: IOException
	 */
	
	private void setUpConnectionToUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		httpUrlConnection = (HttpURLConnection) url.openConnection();
	}
}
