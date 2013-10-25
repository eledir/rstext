package edu.southampton.wais.STPLibrary.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.southampton.wais.STPLibrary.file.TextFile;

public class DocumentModelByTopic implements  Iterable<Entry<Integer,  Double>>{

	
Map<Integer,Double> topic;

Map<String,Integer> body;


private TextFile textFile;


public DocumentModelByTopic(TextFile file){
	

	this.topic=new HashMap<Integer, Double>();
	this.body=new HashMap<String, Integer>();
	
	
	textFile=file;
	
	
}


@Override
public Iterator<Entry<Integer, Double>> iterator() {
	// TODO Auto-generated method stub
	return this.topic.entrySet().iterator();
}





public void parseFile(){
	
	
	String[] lines=this.textFile.getBody().split("\n");
	
	boolean topic=true;
	for(String item :lines){
		
		
		if(item.equals("@-@")){
			topic=false;
		}
		
		else{
		String [] subitem=item.split("\t");
		
		if(topic){
			
			
			this.topic.put(new Integer(subitem[0]), new Double(subitem[1]));
			
		}
		
		else{
			
			this.body.put(subitem[0], new Integer(subitem[1]));
			
			
			
		}
		
	}
		
	}
	
	
	
}


public TextFile getTextFile() {
	return textFile;
}



	
}
