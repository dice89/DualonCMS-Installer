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
	
	public String[] getForComboBox(boolean isSource){
		ArrayList<String> result = new ArrayList<String>();
		for(EndPoint ep : endPoints){
			if(isSource){
				if(ep.isFinal())
					result.add(ep.getName());
			} else {
				if(!ep.isFinal())
					result.add(ep.getName());
				if(ep instanceof WebEndPoint)
					result.add(ep.getName());
			}
			
		}
		
		if(isSource)
			result.add("Variable Source");
		else
			result.add("Variable Target");
		
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

			@Override
			public boolean isFinal() {
				// TODO Auto-generated method stub
				return false;
			}

			
			
		};
	}
	
	public int getPosition(String name){
		int i = 0;
		for(EndPoint p : endPoints){
			if(p.getName().equals(name))
				return i;
			i++;
		}
		throw new RuntimeException();
	}
	
}
