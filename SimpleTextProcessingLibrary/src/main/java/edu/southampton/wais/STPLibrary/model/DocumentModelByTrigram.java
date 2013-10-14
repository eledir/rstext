package edu.southampton.wais.STPLibrary.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.southampton.wais.STPLibrary.file.Reference;
import edu.southampton.wais.STPLibrary.file.TextFile;


public class DocumentModelByTrigram implements Iterable<SentenceModelByTrigram>{

	
	
	List<SentenceModelByTrigram> list;
	
	
	private TextFile textFile;
	
	public DocumentModelByTrigram(TextFile textFile) {
		super();
		this.list = new ArrayList<SentenceModelByTrigram>();
		this.textFile=textFile;
	}
	
	
	public DocumentModelByTrigram(List<SentenceModelByTrigram> list,TextFile textFile) {
		super();
		this.list = list;
		this.textFile=textFile;
		
	}



	public void settingDocumentModelByTrigram(List<SentenceModelByTrigram> list){
		
		for(SentenceModelByTrigram item: list){
			
			this.list.add(item);
			
			
		}
		
	}





	@Override
	public Iterator<SentenceModelByTrigram> iterator() {
		// TODO Auto-generated method stub
		return list.iterator();
	}

	
	
	
	
	

	public int sizeSentenceModelByBigramm(){
		return this.list.size();
	}


	@Override
	public String toString() {
		return "DocumentModelByBigramm [" + list + "]";
	}


	
	public TextFile getTextFile() {
		return textFile;
	}






	
	
	
}
