package edu.southampton.wais.STPLibrary.collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;





public class CollectionDocumentBySentence   implements   Iterable<DocumentModelBySentence>{

 private List<DocumentModelBySentence> list;  
 
 
 private String name;

 
 
 
 
 
 
public CollectionDocumentBySentence(String name) {
	this.list= new ArrayList<DocumentModelBySentence>();
	
	this.name=name;
}



public void addSentenceModel(DocumentModelBySentence item){
	this.list.add(item);
}





@Override
public Iterator<DocumentModelBySentence> iterator() {
	// TODO Auto-generated method stub
	return this.list.iterator();
}



@Override
public String toString() {
	return "ColSentModel [list=" + list + "]";
}





public int size(){
	
	return this.list.size();
}



public String getName() {
	return name;
}



public void setName(String name) {
	this.name = name;
}


	
	
	
}
