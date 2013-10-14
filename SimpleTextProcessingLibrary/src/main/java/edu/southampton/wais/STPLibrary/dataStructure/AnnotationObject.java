package edu.southampton.wais.STPLibrary.dataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.model.AnnotatedWord;





public class AnnotationObject implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	List<AnnotatedWord> list;
	
	List<String> listDependacy;
	
	String string;

	
	DocumentModelBySentence documentModelBySentences;
	

	public DocumentModelBySentence getDocumentModelBySentences() {
		return documentModelBySentences;
	}


	public void setDocumentModelBySentences(
			DocumentModelBySentence documentModelBySentences) {
		this.documentModelBySentences = documentModelBySentences;
	}


	public AnnotationObject(List<AnnotatedWord> list, String string) {
		super();
		this.list = list;
		this.string = string;
	}


	public List<AnnotatedWord> getList() {
		return list;
	}


	public String getString() {
		return string;
	}


	


	


	public List<String> getListDependacy() {
		return listDependacy;
	}


	public void setListDependacy(List<String> listDependacy) {
		this.listDependacy = listDependacy;
	}

	
	@Override
	public String toString() {
		
		List<String>typeRequested=new ArrayList<String>();
		typeRequested.add("word");
		typeRequested.add("norPOS");
		typeRequested.add("stem");
		typeRequested.add("lemma");
		
		
		String re=this.string+"\n";
		
		for(AnnotatedWord listStringNode :this.list){
			
			re=re+listStringNode.toStringFormatted(typeRequested)+"\n";
			
		}
		
		return re;
	}
	
	
}