package serverside.test.sainsburys.berriescherriescurrents.utility;

import org.json.JSONObject;
import org.json.JSONArray;

public abstract class Helper {
	
	/*
	 * This method converts string buffers to a JSON Object
	 * Parameters: data - the String of the object you wish to convert
	 */
	public static JSONObject convertStringBufferToJson(StringBuffer data){
		return new JSONObject(data.toString());
	}
	
	/*
	 * This method converts string buffers to a JSON Array
	 * Parameters: data - the String of the object you wish to convert
	 */
	public static JSONArray convertStringBufferToJsonArray(StringBuffer data){
		return new JSONArray(data.toString());
	}
	
	/*
	 * This method alters the last part of the url to each products path
	 * Parameters: oldUrl - This is the url
	 */
	
	public static String getLastPartOfUrl(String url) {
		return url.substring(url.lastIndexOf("/"));
	}
}
