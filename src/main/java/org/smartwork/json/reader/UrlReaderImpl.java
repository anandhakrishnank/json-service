package org.smartwork.json.reader;

import static org.smartwork.json.utils.ConfigLoader.getProperty;
import static org.smartwork.json.utils.ConfigLoader.getPropertyAsInt;
import static org.smartwork.json.utils.Constants.JSON_URL_TIMEOUT;
import static org.smartwork.json.utils.Constants.RESPONSE_BY_URL;

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

	// Defaulting the json path to json-service configured in resources.properties

	private String jsonPath = getProperty(RESPONSE_BY_URL);

	/**
	 * URL path <jsonPath> will be defaulted to configured value from
	 * resources.property, and will be overridden by constructor argument only if
	 * its is not null or not empty.
	 * 
	 * @param jsonPath
	 */
	public UrlReaderImpl(String jsonPath) {
		if (!"".equals(jsonPath))
			this.jsonPath = jsonPath;
	}

	/**
	 * This method will orchestrate the entire flow Get JSON using getJson Process
	 * the JSON Response using Utils.processJson
	 */
	@Override
	public void execute() {

		String jsonResponse = getJson(jsonPath);

		if (jsonResponse != null) {
			Utils.processJson(jsonResponse);
		} else {
			log.severe("Unable to get Valid JSON Response");
		}
	}

	/**
	 * This method will invoke the service using the URL path, and will return JSON
	 * response as String.
	 * 
	 * @param path
	 * @return JSONResponse
	 */
	private String getJson(String path) {
		HttpURLConnection connection = null;
		try {
			// configure the url path
			URL url = new URL(jsonPath);
			// initializes the connection object.
			connection = (HttpURLConnection) url.openConnection();
			/* connection configurations */
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-length", "0");
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			connection.setConnectTimeout(getPropertyAsInt(JSON_URL_TIMEOUT));
			connection.setReadTimeout(getPropertyAsInt(JSON_URL_TIMEOUT));
			// invokes the json service.
			connection.connect();
			// get the status
			int status = connection.getResponseCode();
			// get the Response if the status is 200
			switch (status) {
			case 200:
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
