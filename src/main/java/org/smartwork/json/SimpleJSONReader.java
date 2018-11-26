package org.smartwork.json;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Logger;

import org.smartwork.json.model.JsonResponse;

import com.google.gson.Gson;
import static org.smartwork.json.utils.ConfigLoader.*;
import static org.smartwork.json.utils.Constants.*;

public class SimpleJSONReader {

	private static final Logger log = Logger.getLogger(SimpleJSONReader.class.getName());

	public static String getJsonByUrl(String path) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(path);
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

	public static String getJsonByFile(String path) {
		String mJsonString = null;
		BufferedInputStream fileInputStream = null;
		try {
			// Loading the file from the current classloaders classpath
			fileInputStream = (BufferedInputStream) (SimpleJSONReader.class).getClassLoader().getResourceAsStream(path);

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

	public static void main(String[] args) throws IOException {

		String mJsonString = getJsonByFile(getProperty(RESPONSE_BY_FILE));

		String jsonResponseFromURL = getJsonByUrl(getProperty(RESPONSE_BY_URL));

		if (jsonResponseFromURL != null) {
			processJSON(jsonResponseFromURL);
		}

		if (mJsonString != null) {
			processJSON(mJsonString);
		}

		log.info("--The End--");

	}

	/**
	 * This method uses Gson to parse the JSON message, and process the Message as below
	 * 1) Loops through Array of JsonResponse.
	 * 2) Gets Value of Number key as int Array.
	 * 3) Sums all the int values in Array.
	 * 4) Add the sum of individual array to find total of all arrays.
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

}
