package edu.southampton.wais.STPLibrary.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.southampton.wais.STPLibrary.model.DocumentModelByBigram;


public class CollectionDocumentByBigram implements Iterable<DocumentModelByBigram>{

	
	private List<DocumentModelByBigram> list;  
	private String name;

	 
	 
	 
	 
	 
	 
	public CollectionDocumentByBigram(String name) {
		this.list= new ArrayList<DocumentModelByBigram>();
		this.name=name;
	}



	public void addDocumentModel(DocumentModelByBigram item){
		this.list.add(item);
	}





	@Override
	public Iterator<DocumentModelByBigram> iterator() {
		// TODO Auto-generated method stub
		return this.list.iterator();
	}



	@Override
	public String toString() {
		return "ColDocBigram [list=" + list + "]";
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

