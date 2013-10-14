


package edu.southampton.wais.STPLibrary.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.model.DocumentModelByTrigram;

public class CollectionDocumentByTrigram implements
		Iterable<DocumentModelByTrigram> {

	private List<DocumentModelByTrigram> list;  
	 
	 
	 private String name;

	 
	 
	 
	 
	 
	 
	public CollectionDocumentByTrigram(String name) {
		this.list= new ArrayList<DocumentModelByTrigram>();
		
		this.name=name;
	}



	public void addDocumentModel(DocumentModelByTrigram item){
		this.list.add(item);
	}





	@Override
	public Iterator<DocumentModelByTrigram> iterator() {
		// TODO Auto-generated method stub
		return this.list.iterator();
	}



	@Override
	public String toString() {
		return "ColDocTrig [list=" + list + "]";
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
