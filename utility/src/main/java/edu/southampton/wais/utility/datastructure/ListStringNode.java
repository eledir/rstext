package edu.southampton.wais.utility.datastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListStringNode  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> value;
	private Map<String,Integer> typeMap;
	private ArrayList<Boolean> valid;
	int index=0;
	

	public ListStringNode() {
		// TODO Auto-generated constructor stub
	
	value= new ArrayList<String>();
	typeMap= new HashMap<String,Integer>();
	valid= new ArrayList<Boolean>();
	
	}

	
	
	public void  addString(String item , String type) throws Exception{
		
		if(!typeMap.keySet().contains(type)){
		
		value.add(index,item);
		typeMap.put(type, index);
		valid.add(index, new Boolean(true));
		index++;}
		else{
			
			throw new Exception("Type alread in the map");
		}
	}
	
	
	public String getValue(String type) throws Exception{
		if(typeMap.keySet().contains(type)){
		return this.value.get(this.typeMap.get(type));
		}
		else{
			
			throw new Exception("Type not in the map");
		}
		
	}




	@Override
	public String toString() {
	
		StringBuffer buffer= new StringBuffer();
		
		for(String type: this.typeMap.keySet()){
			
		
		buffer.append("("+type+" : "+this.value.get(this.typeMap.get(type))+" : "+ this.valid.get(this.typeMap.get(type))  + "),");
			
			
		}
		
		return "{"+buffer.toString()+"}";
	}



	public  Boolean isValid(String type) throws Exception {
		if(typeMap.keySet().contains(type)){
		// TODO Auto-generated method stub
		return this.valid.get(this.typeMap.get(type));
		}
		else{
			
			throw new Exception("Type alread in the map");
		}
		
		}
	

	
	public void setInvalid(String type){
		
		this.valid.set(this.typeMap.get(type), new Boolean(false));
		
	}
	
public boolean setInvalidbyType(String type) throws Exception{
	if(typeMap.keySet().contains(type)){	
	int index=this.typeMap.get(type);
	return this.valid.set(index, new Boolean(false));
	}
	else{
		
		throw new Exception("Type alread in the map");
	}	
	
	
		   
		   
	         
	

		
	}
	
	
	
	


	
	public int    getLastIndex(){
		
		return this.value.size()-1;
	}



	public boolean isValidbyType(String type) throws Exception {
		
		if(typeMap.keySet().contains(type)){	
			   
	  return this.valid.get(this.typeMap.get(type));
		}
		else{
			
			throw new Exception("Type alread in the map");
		}	
		}
		
		
		
		
	
	

   

	
	
}
