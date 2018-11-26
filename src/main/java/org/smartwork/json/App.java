package org.smartwork.json;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.smartwork.json.intf.JsonReader;
import org.smartwork.json.reader.FileReaderImpl;
import org.smartwork.json.reader.UrlReaderImpl;

public class App {

	private static final Logger log = Logger.getLogger(App.class.getName());

	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);
		log.info("--Demo JSON Reader--");
		boolean loop = true;
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
				JsonReader urlReader = new UrlReaderImpl(s);
				urlReader.execute();
				break;
			}
			case "2": {
				log.info(
						"Enter the complete json url:\n for eg:\"sample.json\" or \"F:/home/anand/files/sample.json\"");
				s = in.nextLine();
				JsonReader fileReader = new FileReaderImpl(s);
				fileReader.execute();
				break;
			}
			case "":
			default: {
				log.info(
						"\n Enter 1 for 'Reading JSON using URL'\n Enter 2 for 'Reading JSON using File'\n Enter 0 or exit to exit;\nEnter '' or any character to continue");
				continue;
			}
			}

		}
		in.close();

		log.info("--The End--");

	}

}
