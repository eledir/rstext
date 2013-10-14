package edu.southampton.wais.STPLibrary.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VocabularyMeasure implements Iterable<DocumentModelByWord>{

	
private int count;

private double globalMeasureValue;

List<DocumentModelByWord> listFile;











public VocabularyMeasure(int count) {
	super();
	this.count = count;
	 listFile = new ArrayList<DocumentModelByWord>();
    	
 

}


public VocabularyMeasure() {
	super();
	 listFile = new ArrayList<DocumentModelByWord>();
	 
}






public Iterator<DocumentModelByWord> iterator() {
	// TODO Auto-generated method stub
	return listFile.iterator();
}


public int getCount() {
	return count;
}


public void setCount(int count) {
	this.count = count;
}


public double getGlobalMeasureValue() {
	return globalMeasureValue;
}


public void setGlobalMeasureValue(double globalMeasureValue) {
	this.globalMeasureValue = globalMeasureValue;
}
	


public int getSizeMetaDataDocument(){
	return this.listFile.size();
}


@Override
public String toString() {
	StringBuffer buf =  new StringBuffer("\n\n-------------------------------------------------\n\n");
	buf.append("\n N=" + count + ", GM="
			+ globalMeasureValue + ", LF=" + listFile + "\n");
     return buf.append("\n\n-------------------------------------------------\n\n").toString();
}




public boolean addMetaDataFile(DocumentModelByWord item){
	return this.listFile.add(item);
}

}
