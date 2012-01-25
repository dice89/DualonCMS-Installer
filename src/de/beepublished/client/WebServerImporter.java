package de.beepublished.client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Imports WebServer from Client setting file (.bps.txt)s
 * @author fabiankajzar
 *
 */
public class WebServerImporter {
	public static List<WebServer> importWebserver(String exportFileName){
		try {
			ArrayList<WebServer> result = new ArrayList<WebServer>();
			BufferedReader reader = new BufferedReader(new FileReader(new File(exportFileName)));
			String line = null;
			while((line = reader.readLine()) != null){
				System.out.println(line);
				result.add(WebServer.deserialize(line));
			}
			return result;
    	} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException();
		}
	}
}
