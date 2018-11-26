package org.smartwork.json.reader;

import static org.smartwork.json.utils.ConfigLoader.getProperty;
import static org.smartwork.json.utils.ConfigLoader.getPropertyAsInt;
import static org.smartwork.json.utils.Constants.JSON_URL_TIMEOUT;
import static org.smartwork.json.utils.Constants.RESPONSE_BY_FILE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.smartwork.json.intf.JsonReader;
import org.smartwork.json.utils.Utils;

public class UrlReaderImpl implements JsonReader {

	private static final Logger log = Logger.getLogger(UrlReaderImpl.class.getName());

	// Defaulting the json path to json available in src/main/resources/sample.json

	private String jsonPath = getProperty(RESPONSE_BY_FILE);

	public UrlReaderImpl(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	@Override
	public void execute() {

		String jsonResponse = getJson(jsonPath);

		if (jsonResponse != null) {
			Utils.processJson(jsonResponse);
		} else {
			log.severe("Unable to get Valid JSON Response");
		}
	}

	@Override
	public String getJson(String path) {
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

}
