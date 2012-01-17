package de.beepublished.client;

import java.util.ArrayList;
import java.util.List;

import de.beepublished.client.EndPoint;

public class EndPointManager {
	private List<EndPoint> endPoints;
	private String addition;
	
	public EndPointManager(String addition){
		endPoints = new ArrayList<EndPoint>();
		this.addition = addition;
	}
	
	public void addEndPoint(EndPoint ep){
		endPoints.add(ep);
	}
	
	public String[] getForComboBox(){
		ArrayList<String> result = new ArrayList<String>();
		for(EndPoint ep : endPoints){
			result.add(ep.getName());
		}
		result.add(addition);
		
		return result.toArray(new String[]{});
	}
	
	public EndPoint getAtIndex(int index){
		return endPoints.get(index);
	}
	
	public int getCount(){
		return endPoints.size();
	}
}
