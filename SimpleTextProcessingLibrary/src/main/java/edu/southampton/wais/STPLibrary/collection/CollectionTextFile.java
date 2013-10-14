package edu.southampton.wais.STPLibrary.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.southampton.wais.STPLibrary.file.TextFile;


public class CollectionTextFile implements Iterable<TextFile>{

	
	List<TextFile> listTextFile;
	private String name;
	
	public CollectionTextFile(String name){
		
		this.listTextFile=new ArrayList<TextFile>();
		
		this.name=name;
	}
	
	
	
	
	
	
	
	public boolean add(TextFile e) {
		
	return	this.listTextFile.add(e) ? true :  false;
		
		
	}



public int sizeTexts(){
	return this.listTextFile.size();
}



	@Override
	public Iterator<TextFile> iterator() {
		return this.listTextFile.iterator();
	}







	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}







	@Override
	public String toString() {
		return "CollectionTextFile [listTextFile=" + listTextFile + ", name="
				+ name + "]";
	}

	
	public TextFile getDocByIndex(int i){
		
		return this.listTextFile.get(i);
		
	}

}