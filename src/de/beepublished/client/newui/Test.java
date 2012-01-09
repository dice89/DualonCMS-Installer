package de.beepublished.client.newui;

import java.io.File;
import java.io.IOException;

import de.beepublished.client.exceptions.ZipVocationException;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			LocalFileEndPoint ep = new LocalFileEndPoint(new File("C:\\Users\\Fabian\\Desktop\\ExampleFileEndPoint.zip"));
			
			System.out.println("AD");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
