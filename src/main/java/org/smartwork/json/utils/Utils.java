package org.smartwork.json.utils;

import java.util.Arrays;
import java.util.logging.Logger;

import org.smartwork.json.model.JsonResponse;

import com.google.gson.Gson;

public class Utils {

	private static final Logger log = Logger.getLogger(Utils.class.getName());

	/**
	 * This method uses Gson to parse the JSON message, and process the Message as
	 * below 
	 * 1) Loops through Array of JsonResponse. 
	 * 2) Gets Value of Number key as int Array. 
	 * 3) Sums all the int values in Array. 
	 * 4) Add the sum of individual array to find total of all arrays.
	 * 
	 * @param jsonMessage
	 */
	public static void processJson(String jsonMessage) {
		try {
			Gson gson = new Gson();
			if (jsonMessage != null) {
				JsonResponse[] response = gson.fromJson(jsonMessage, JsonResponse[].class);
				int total = 0;
				for (JsonResponse JsonResponse : response) {
					int sum = 0;
					sum = Arrays.stream(JsonResponse.getNumbers()).sum();
					log.info("Key: " + JsonResponse.getKey() + ", Sum of Numbers: " + sum);
					total = total + sum;
				}
				log.info("Summation of all numbers: " + total);
			}
		} catch (Exception ex) {
			log.severe(ex.getMessage());
		}

	}

}
