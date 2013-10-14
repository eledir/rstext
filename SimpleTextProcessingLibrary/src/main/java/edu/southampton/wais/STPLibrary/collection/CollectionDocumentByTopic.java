package edu.southampton.wais.STPLibrary.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.southampton.wais.STPLibrary.model.DocumentModelByTopic;

public class CollectionDocumentByTopic implements Iterable<DocumentModelByTopic> {

	
	
	
	private List<DocumentModelByTopic> list;  
	private String name;

	 
	 
	 
	 
	 
	 
	public CollectionDocumentByTopic(String name) {
		this.list= new ArrayList<DocumentModelByTopic>();
		this.name=name;
	}



	public void addDocumentModel(DocumentModelByTopic item){
		this.list.add(item);
	}





	@Override
	public Iterator<DocumentModelByTopic> iterator() {
		// TODO Auto-generated method stub
		return this.list.iterator();
	}



	@Override
	public String toString() {
		return "ColDocTopic [list=" + list + "]";
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
