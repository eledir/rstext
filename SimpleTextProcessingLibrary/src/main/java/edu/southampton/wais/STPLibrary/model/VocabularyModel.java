package edu.southampton.wais.STPLibrary.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentModelByWord;
import edu.southampton.wais.STPLibrary.utility.MathUtility;


public class VocabularyModel implements Iterable<Entry<String,  VocabularyMeasure>>{
	

	
	private HashMap<String, VocabularyMeasure> hash;
	private CollectionDocumentModelByWord list;
	
    private double maxValueGlobalMeasure;
    private double minValueGlobalMeasure;
	private boolean firstSettingMin =true;
	private boolean firstSettingMax=true;

	 

	
	
	
	public VocabularyModel(CollectionDocumentModelByWord list){
		
		this.hash= new HashMap<String, VocabularyMeasure>();
		this.list= list;
		
	}
	
	
	
	

	
	
private void setWord(String word,WordFeatures metadataMeasure, DocumentModelByWord item){

		
		
		 
		
		 if(hash.containsKey(word)){
			 // update the count
			 VocabularyMeasure measure =hash.get(word);
			 measure.setCount(measure.getCount()+metadataMeasure.getCount());
			 measure.addMetaDataFile(item);
			 
		 }
		 else{
			 //adding measures
			 VocabularyMeasure measure = new VocabularyMeasure(metadataMeasure.getCount());
			 measure.addMetaDataFile(item);
			 hash.put(word, measure);
			 
		 }
		 
		
		   
		   } 
		
		
		
public VocabularyMeasure getGlobalWordMeasure(String word){
	
	return this.hash.get(word);
}




	
	
public void settingVoucabolary(){
	
	for(DocumentModelByWord item: list){
		
		for (Map.Entry<String, WordFeatures> item2 : item){
			this.setWord(item2.getKey(), item2.getValue(),item);
		}
		
	}
	
	
	
}







@Override
public Iterator<Entry<String, VocabularyMeasure>> iterator() {
	// TODO Auto-generated method stub
	return this.hash.entrySet().iterator();
}


public int sizeWords(){
	return this.hash.size();
	
	
}


public int sizeMetaDataTextCollection(){
	return this.list.sizeCollection();
	
	
}














public double getMaxValueGlobalMeasure() {
	return maxValueGlobalMeasure;
}







public void setMaxValueGlobalMeasure(double maxValueGlobalMeasure) {
	
	if(firstSettingMax){
	
	this.maxValueGlobalMeasure = maxValueGlobalMeasure;
	firstSettingMax=false;
	}
	else{
	   
		  if(maxValueGlobalMeasure>this.maxValueGlobalMeasure){
			  this.maxValueGlobalMeasure=maxValueGlobalMeasure;
		  }
		
		
	}
	
	
	}



public Set<String> getSetToken(){
	return this.hash.keySet();
	
}



public double getMinValueGlobalMeasure() {
	return minValueGlobalMeasure;
}







public void setMinValueGlobalMeasure(double minValueGlobalMeasure) {
	if(firstSettingMin){
	this.minValueGlobalMeasure = minValueGlobalMeasure;
	firstSettingMin=false;
	}
	else{
		if(minValueGlobalMeasure<this.minValueGlobalMeasure){
			this.minValueGlobalMeasure=minValueGlobalMeasure;
		}
	}
}




public void setRangeGlobalMeasure(double value) {
	this.setMaxValueGlobalMeasure(value);
	this.setMinValueGlobalMeasure(value);
}








public SortedSet<String> getTermDictoniary(){
	
	
	SortedSet<String> set= new TreeSet<String>();
	
	for(Entry<String,VocabularyMeasure> item:this){
		
		set.add(item.getKey());
	}
	
	
	return set;
	
}






} 

