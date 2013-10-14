package edu.southampton.wais.STPLibrary.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SentenceModelByBigram implements Iterable<BigramModel>{

	
List<BigramModel> listBigramm;



public SentenceModelByBigram(){
	this.listBigramm= new ArrayList<BigramModel>();
	
}



public SentenceModelByBigram(List<BigramModel> listBigramm2) {
	this.listBigramm= new ArrayList<BigramModel>();
	this.addAll(listBigramm2);
}



@Override
public Iterator<BigramModel> iterator() {
	// TODO Auto-generated method stub
	return this.listBigramm.iterator();
}	








public void addBigramm(BigramModel bigrammModel){
	this.listBigramm.add(bigrammModel);
}
	
public void addAll(List<BigramModel>list){
	this.listBigramm=list;
	
}



@Override
public String toString() {
	return "SentenceModelByBigramm [" + listBigramm + "]";
}
	


public int sizeBigram(){
	return this.listBigramm.size();
}
	
}
