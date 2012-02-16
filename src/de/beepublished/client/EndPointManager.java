package de.beepublished.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Combo;

import de.beepublished.client.EndPoint;

public class EndPointManager {
	private List<EndPoint> endPoints;
	
	public EndPointManager(){
		endPoints = new ArrayList<EndPoint>();
	}
	
	public void addEndPoint(EndPoint ep){
		endPoints.add(ep);
	}
	
	public void removeEndPoint(EndPoint ep){
		endPoints.remove(ep);
		System.out.println("removed");
	}
	
	public String[] getForComboBox(){
		ArrayList<String> result = new ArrayList<String>();
		for(EndPoint ep : endPoints){
			result.add(ep.getName());
		}
		
		return result.toArray(new String[]{});
	}
	
	public EndPoint getAtIndex(int index){
		return endPoints.get(index);
	}
	
	public int getCount(){
		return endPoints.size();
	}
	
	public List<EndPoint> getEndPoints(){
		return endPoints;
	}
	
	public WebServer getSelectedServer(Combo c){
		return (WebServer) endPoints.get(c.getSelectionIndex());
	}
	
	public void exportSettings(String filename){
		try{
			File f = new File(filename);
			PrintWriter inputStream = new PrintWriter(f);
	
			for(EndPoint server : this.getEndPoints()){
				String serialization = server.serialize();
				System.out.println(serialization);
				inputStream.println(serialization);
			}
					
			inputStream.flush();
			inputStream.close();
		} catch (Exception e) {
			System.err.println("Could not export settings:");
			e.printStackTrace();
		}
	}
	
	public void importSettings(String filename){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			String line = null;
			while((line = reader.readLine()) != null){
				this.addEndPoint(WebServer.deserialize(line));
			}
    	} catch (Exception e1) {
    		System.err.println("Could not import settings");
			e1.printStackTrace();
		}
	}
}
