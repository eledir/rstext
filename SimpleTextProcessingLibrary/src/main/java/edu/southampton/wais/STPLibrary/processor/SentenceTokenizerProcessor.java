package edu.southampton.wais.STPLibrary.processor;
import java.util.List;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentBySentence;
import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.utility.Logger;




public abstract class SentenceTokenizerProcessor implements Runnable{

	
	

	protected CollectionTextFile collectionTextFile;
	CollectionDocumentBySentence collectionSentenceModel;
	protected Parameter param;
	
	
	
	public SentenceTokenizerProcessor(CollectionTextFile collectionTextFile,Parameter o){
	
		this.collectionTextFile=collectionTextFile;
		this.collectionSentenceModel= new CollectionDocumentBySentence(collectionTextFile.getName());
		this.param=o;
	}
	
	
	
	
	


	
	
	


	public abstract List<String>getSentence(String body);
	
	
	@Override
	public void run() {
		
		for(TextFile item : this.collectionTextFile){
			      
			
			   Logger.logFiner("Sentence detector for file "+item.getReference().toString());
			
			   List<String>  listString=this.getSentence(item.getBody());
			   
			   if(!listString.isEmpty()){
			   
			   DocumentModelBySentence sentenceModel = new DocumentModelBySentence(item);
			   
			   
			   for (int i = 0; i < listString.size(); i++) {
			
				   sentenceModel.settingSetence(i, listString.get(i));
				}
			   
			   
			   
			   this.collectionSentenceModel.addSentenceModel(sentenceModel);
			  
			   Logger.logFiner("Sentence detector for file done "+item.getReference().toString());
				
			   
			   }
			   
			   
		}
		
	}




	public CollectionDocumentBySentence getCollectionSentenceModel() {
		return collectionSentenceModel;
	}







	



}
