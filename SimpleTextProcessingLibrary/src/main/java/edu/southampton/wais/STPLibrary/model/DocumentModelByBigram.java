package edu.southampton.wais.STPLibrary.model;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import edu.southampton.wais.STPLibrary.file.Reference;
import edu.southampton.wais.STPLibrary.file.TextFile;

public class DocumentModelByBigram  implements Iterable<SentenceModelByBigram>{

	
	
	List<SentenceModelByBigram> list;
	
	
	
	private TextFile textFile;
	
	
	public DocumentModelByBigram(TextFile texfile) {
	
		this.textFile=texfile;

		this.list = new ArrayList<SentenceModelByBigram>();
	}
	
	
	public DocumentModelByBigram(List<SentenceModelByBigram> list,TextFile textFile) {
		
		this.textFile=textFile;
		
		this.list = list;
	
	}



	public void settingDocumentModelByBigramm(List<SentenceModelByBigram> list){
		
		for(SentenceModelByBigram item: list){
			
			this.list.add(item);
			
			
		}
		
	}





	@Override
	public Iterator<SentenceModelByBigram> iterator() {
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
