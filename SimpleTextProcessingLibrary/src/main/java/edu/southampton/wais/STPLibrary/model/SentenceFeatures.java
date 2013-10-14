package edu.southampton.wais.STPLibrary.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;



public class SentenceFeatures implements Iterable<AnnotatedWord> {
	
private double globalValueMeasures;


private List<AnnotatedWord> listWord;









public SentenceFeatures() {
	super();
	this.listWord= new ArrayList<AnnotatedWord>();
}

public double getGlobalValueMeasures() {
	return globalValueMeasures;
}

public void setGlobalValueMeasures(double globalValueMeasures) {
	this.globalValueMeasures = globalValueMeasures;
}

@Override
public Iterator<AnnotatedWord> iterator() {
	// TODO Auto-generated method stub
	return this.listWord.iterator();
}



public void setAnnotatedWord(AnnotatedWord word){
	this.listWord.add(word);
	
}







public int sizeWord(){
	return this.listWord.size();
}






public List<AnnotatedWord> getListString(){
	return this.listWord;
}

}
