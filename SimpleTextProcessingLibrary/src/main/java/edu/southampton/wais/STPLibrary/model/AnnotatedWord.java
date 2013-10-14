package edu.southampton.wais.STPLibrary.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AnnotatedWord  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> value;
	private Map<String,Integer> typeMap;
	private ArrayList<Boolean> valid;
	
	String sentence;
	
	
	public final static String WORD="word";
	
	public  final static String POS="pos";
	
	public final static String NER="ner";
	
	public final static String NERNORM="nerNorm";
	public final static String LEMMA="lemma";
	public final static String STEM="stem";
	public final static String POSNORM="posNorm";
	
		
	
	
	
	
	public String getSentence() {
		return sentence;
	}



	public void setSentence(String sentence) {
		this.sentence = sentence;
	}



	int index=0;
	
	private String comparedWord;
	
    private String typeForEqual;
	
	public AnnotatedWord(String word,String typeForEqual) {
		// TODO Auto-generated constructor stub
	
    this.comparedWord=word;	
    this.typeForEqual=typeForEqual;
		
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
	
		
		String s="";
		
		try {
		s= "{"+this.getValue(AnnotatedWord.LEMMA)+"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		    
		}
	
	    return s;
	
	}

	
	public String toString2() {
		
		StringBuffer buffer= new StringBuffer();
		
		for(String type: this.typeMap.keySet()){
			
		
		buffer.append("("+type+" : "+this.value.get(this.typeMap.get(type))+" : "+ this.valid.get(this.typeMap.get(type))  + "),");
			
			
		}
		
		return "{"+buffer.toString()+"}";
	}

	public String toStringFormatted(List<String>typeRequested) {
		
		StringBuffer buffer= new StringBuffer();
		
		
		for(String type: this.typeMap.keySet()){
			
		     
			 if(typeRequested.contains(type)){
			
		        buffer.append(this.value.get(this.typeMap.get(type))+"/"+type+"/"+this.valid.get(this.typeMap.get(type))+ ", ");
			 
			 }
			
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



	@Override
	public int hashCode() {
		
		 try {
			this.comparedWord=this.getValue(typeForEqual);
		
			
			
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 final int prime = 31;
		int result = 1;
		result = prime * result + ((comparedWord== null) ? 0 : comparedWord.hashCode());
		
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		
		 try {
				this.comparedWord=this.getValue(typeForEqual);
			
				
				
			 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnnotatedWord other = (AnnotatedWord) obj;
		if (comparedWord == null) {
			if (other.comparedWord != null)
				return false;
		} else if (!comparedWord.equals(other.getComparedWord()))
			return false;
		return true;
	}



	public String getComparedWord() {
		return comparedWord;
	}
		
		
		
		
	
	
	public void setInvalidAllType() throws Exception{
		
		for(String item:this.typeMap.keySet()){
			
			
			setInvalidbyType(item);
			
			
		}
		
		
	}



	public boolean isValid() throws Exception {
		
       
		boolean result=true;
		
		for(String item:this.typeMap.keySet()){
			
			
			if(!isValidbyType(item)){
				
			     result=false; 
			     break;	
			}
			
			
		}
		
		
		// TODO Auto-generated method stub
		return result;
	}
	

   

	
	
}
