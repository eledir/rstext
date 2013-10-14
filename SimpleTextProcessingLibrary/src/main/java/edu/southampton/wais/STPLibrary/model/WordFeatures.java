package edu.southampton.wais.STPLibrary.model;

import java.util.ArrayList;
import java.util.List;





public class WordFeatures {

	


private int count;

private double value;


public List<Integer> postion;



public List<AnnotatedWord> listannotatedWord;





public WordFeatures() {
	super();
	listannotatedWord= new ArrayList<AnnotatedWord>();
}

public WordFeatures(int count) {
	super();
	this.count = count;
	postion= new ArrayList<Integer>();
	listannotatedWord= new ArrayList<AnnotatedWord>();
}

public WordFeatures(int count,int integer) {
	super();
	this.count = count;
	postion= new ArrayList<Integer>();
	postion.add(integer);
	listannotatedWord= new ArrayList<AnnotatedWord>();
}


public int getCount() {
	return count;
}

public void setCount(int count) {
	this.count = count;
}

public double getValue() {
	return value;
}

public void setValue(double d) {
	this.value = d;
}









public void addPosition(int pos){
	
	this.postion.add(pos);
}



public List<Integer> getPostion() {
	return postion;
}

public void addPosition(Integer postion) {
	this.postion.add(postion);
}



public void addAnnotationWord(AnnotatedWord a ){
	
	this.listannotatedWord.add(a);
	
}




	
	
}
