package de.beepublished.client.newui;

import java.util.*;

public class EndPointManager {
	private List<EndPoint> endPoints;
	
	public EndPointManager(){
		endPoints = new ArrayList<EndPoint>();
	}
	
	public void addEndPoint(EndPoint ep){
		endPoints.add(ep);
	}
	
	public String[] getForComboBox(){
		ArrayList<String> result = new ArrayList<String>();
		for(EndPoint ep : endPoints)
			result.add(ep.getName());
		return result.toArray(new String[]{});
	}
	
	public EndPoint getEndPoint(String name){
		for(EndPoint p : endPoints)
			if(p.getName().equals(name))
				return p;
		return new EndPoint() {
			
			@Override
			public String getType() {
				return "";
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "";
			}
		};
	}
	
}
