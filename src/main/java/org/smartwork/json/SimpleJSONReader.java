package org.smartwork.json;

import static org.smartwork.json.utils.ConfigLoader.getProperty;
import static org.smartwork.json.utils.ConfigLoader.getPropertyAsInt;
import static org.smartwork.json.utils.Constants.JSON_URL_TIMEOUT;
import static org.smartwork.json.utils.Constants.RESPONSE_BY_FILE;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import org.smartwork.json.model.JsonResponse;

import com.google.gson.Gson;

public class SimpleJSONReader {

	private static final Logger log = Logger.getLogger(SimpleJSONReader.class.getName());

	// Defaulting the json path to json available in src/main/resources/sample.json

	private String jsonPath = getProperty(RESPONSE_BY_FILE);

	public SimpleJSONReader(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	public void execute() {

	}

	public String getJsonByUrl() {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(jsonPath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-length", "0");
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			connection.setConnectTimeout(getPropertyAsInt(JSON_URL_TIMEOUT));
			connection.setReadTimeout(getPropertyAsInt(JSON_URL_TIMEOUT));
			connection.connect();
			int status = connection.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				return sb.toString();
			}

		} catch (MalformedURLException ex) {
			log.severe(ex.getMessage());
		} catch (IOException ex) {
			log.severe(ex.getMessage());
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception ex) {
					log.severe(ex.getMessage());
				}
			}
		}
		return null;
	}

	public String getJsonByFile() {
		String mJsonString = null;
		BufferedInputStream fileInputStream = null;
		try {
			// Loading the file from the current classloaders classpath
			fileInputStream = new BufferedInputStream(new FileInputStream(jsonPath));

			int size = fileInputStream.available();
			byte[] buffer = new byte[size];
			fileInputStream.read(buffer);
			mJsonString = new String(buffer, "UTF-8");

		} catch (UnsupportedEncodingException ex) {
			log.severe(ex.getMessage());
		} catch (IOException ex) {
			log.severe(ex.getMessage());
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception ex) {
					log.severe(ex.getMessage());
				}
			}
		}
		return mJsonString;
	}

	/**
	 * This method uses Gson to parse the JSON message, and process the Message as
	 * below 1) Loops through Array of JsonResponse. 2) Gets Value of Number key as
	 * int Array. 3) Sums all the int values in Array. 4) Add the sum of individual
	 * array to find total of all arrays.
	 * 
	 * @param jsonMessage
	 */
	public static void processJSON(String jsonMessage) {
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

	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);
		log.info("--Demo JSON Reader--");
		boolean loop = true;
		String jsonResponse = null;
		log.info(
				"\n Type 1 for 'Reading JSON using URL' & Enter \n Type 2 for 'Reading JSON using File' & Enter \n Type 0 or exit to exit & Enter;\nEnter '' or any character to continue");
		while (loop) {
			String s = in.nextLine();
			switch (s) {
			case "0":
			case "exit":
				System.exit(0);
				;
			case "1": {
				log.info("Enter the complete json url:\n for eg:\"http://demo3130008.mockable.io/json-service\"");
				s = in.nextLine();
				SimpleJSONReader urlReader = new SimpleJSONReader(s);
				jsonResponse = urlReader.getJsonByUrl();
				break;
			}
			case "2": {
				log.info(
						"Enter the complete json url:\n for eg:\"sample.json\" or \"F:/home/anand/files/sample.json\"");
				s = in.nextLine();
				SimpleJSONReader fileReader = new SimpleJSONReader(s);
				jsonResponse = fileReader.getJsonByFile();
				break;
			}
			case "":
			default: {
				log.info(
						"\n Enter 1 for 'Reading JSON using URL'\n Enter 2 for 'Reading JSON using File'\n Enter 0 or exit to exit;\nEnter '' or any character to continue");
				continue;
			}
			}

			if (jsonResponse != null) {
				processJSON(jsonResponse);
			} else {
				log.severe("Unable to get Valid JSON Response");
			}

		}
		in.close();

		log.info("--The End--");

	}

}
