package edu.southampton.wais.STPLibrary.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Objects;

import edu.southampton.wais.STPLibrary.file.TextFile;

public class DocumentModelByTriplesSVOSyntacticBased implements Iterable<SentenceModelbyTripleSVOSyntaticbased>{

	
	
	List<SentenceModelbyTripleSVOSyntaticbased> list;
	
	
	private TextFile textFile;
	
	public DocumentModelByTriplesSVOSyntacticBased(TextFile textFile) {
		super();
		this.list = new ArrayList<SentenceModelbyTripleSVOSyntaticbased>();
		this.textFile=textFile;
	}
	
	
	public DocumentModelByTriplesSVOSyntacticBased(List<SentenceModelbyTripleSVOSyntaticbased> list,TextFile textFile) {
		super();
		this.list = list;
		this.textFile=textFile;
		
	}



	public void settingDocumentModelByTriples(List<SentenceModelbyTripleSVOSyntaticbased> list){
		
		for(SentenceModelbyTripleSVOSyntaticbased item: list){
			
			this.list.add(item);
			
			
		}
		
	}





	@Override
	public Iterator<SentenceModelbyTripleSVOSyntaticbased> iterator() {
		// TODO Auto-generated method stub
		return list.iterator();
	}

	
	
	
	
	

	public int sizeSentenceModelByTriplesSVOSyntacticBased(){
		return this.list.size();
	}


	@Override
	public String toString() {
		return Objects.toStringHelper(this.getClass()).add("Document model by triples..\n", this.list).toString();
	}


	
	public TextFile getTextFile() {
		return textFile;
	}
	
}