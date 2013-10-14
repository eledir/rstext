package edu.southampton.wais.STPLibrary.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.southampton.wais.STPLibrary.model.DocumentModelByWord;


public class CollectionDocumentModelByWord implements Iterable<DocumentModelByWord> {

	

	private List<DocumentModelByWord> listWordModel;
   
    private String name;
	

	public CollectionDocumentModelByWord(String name) {
		
		this.name=name;
		
		this.listWordModel = new ArrayList<DocumentModelByWord>();
	}

	public void addWordModel(DocumentModelByWord item) {

		this.listWordModel.add(item);
	}

	public void addColllectionWordModel(List<DocumentModelByWord> list) {

		for (DocumentModelByWord item : list)

			this.listWordModel.add(item);
	}

	@Override
	public Iterator<DocumentModelByWord> iterator() {
		// TODO Auto-generated method stub
		return this.listWordModel.iterator();
	}

	
	
	public int sizeCollection(){
		
		return this.listWordModel.size();
	}
	
	
	
	
	
	
	public  double avarageLenghtForDocument(){
		
		
		double count =0;
		for (DocumentModelByWord item :this){
			
			count=count+item.sizeWords();
			
			
		}
		
	  return count/this.listWordModel.size();
	
	}
	
	
	
	
}