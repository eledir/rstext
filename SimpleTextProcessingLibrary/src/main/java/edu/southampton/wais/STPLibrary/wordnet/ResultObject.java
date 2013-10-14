package edu.southampton.wais.STPLibrary.wordnet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.southampton.wais.STPLibrary.wsd.WSDObject;



public class ResultObject implements Iterable<InnerResultObject> {

	
	
 private String sentence;
 
 private List<InnerResultObject> list;
 
 
 public ResultObject(String sentence){
	 
	 
	 this.sentence=sentence;
	 
	 list= new ArrayList<InnerResultObject>();
	 
	 
	 
	 
 }
 
 
 
 
 
 
 
 
 
 
 public String getSentence() {
	return sentence;
}










public void addResult(WSDObject conceptOntology, WSDObject inputConcept,
			double score){
	 
	 
	 this.list.add(new InnerResultObject(conceptOntology, inputConcept, score));
	 
	 
 }
 
 
 
 public void orderResultByScore(){
	 Collections.sort(list);
	 
 }










@Override
public Iterator<InnerResultObject> iterator() {
	// TODO Auto-generated method stub
	return this.list.iterator();
}
 
 
 
 
	
	
	
	
}
