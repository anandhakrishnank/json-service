package org.smartwork.json.reader;

import static org.smartwork.json.utils.ConfigLoader.getProperty;
import static org.smartwork.json.utils.Constants.RESPONSE_BY_FILE;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.smartwork.json.intf.JsonReader;
import org.smartwork.json.utils.Utils;

public class FileReaderImpl implements JsonReader {

	private static final Logger log = Logger.getLogger(FileReaderImpl.class.getName());

	// Defaulting the json path to json available in src/main/resources/sample.json

	private String jsonPath = getProperty(RESPONSE_BY_FILE);

	public FileReaderImpl(String jsonPath) {
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

}
