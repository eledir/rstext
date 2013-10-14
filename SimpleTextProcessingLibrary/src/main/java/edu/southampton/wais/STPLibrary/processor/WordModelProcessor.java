package edu.southampton.wais.STPLibrary.processor;

import java.util.HashMap;


import edu.southampton.wais.STPLibrary.collection.CollectionDocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.VocabularyModel;
import edu.southampton.wais.STPLibrary.wordMeasureStrategy.EntropyStrategy;
import edu.southampton.wais.STPLibrary.wordMeasureStrategy.TfIdfEntropyStrategy;
import edu.southampton.wais.STPLibrary.wordMeasureStrategy.TfIdfStrategy;
import edu.southampton.wais.STPLibrary.wordMeasureStrategy.TfStrategy;
import edu.southampton.wais.STPLibrary.wordMeasureStrategy.WordModelProcessingStrategy;

public class WordModelProcessor {

	
	private WordModelProcessingStrategy wordModelProcessingStrategy;
	private VocabularyModel vocabulary;
	private CollectionDocumentModelByWord collectionWordModel;
	
	

	
	
	
	
	public WordModelProcessor(VocabularyModel vocabulary,CollectionDocumentModelByWord collectionMetadataTextFile) {
		this.vocabulary=vocabulary;
		this.collectionWordModel=collectionMetadataTextFile;
		
	}

	public void executeStrategy() {
		this.wordModelProcessingStrategy.execute();
		
	}
	
	
	public void setTfIdfStrategy(){
		
		this.wordModelProcessingStrategy= new TfIdfStrategy(this.vocabulary,this.collectionWordModel);
		
		
	}

  public void setEntropyStrategy(){
		
		this.wordModelProcessingStrategy= new EntropyStrategy(this.vocabulary,this.collectionWordModel);
		
		
	}
  

  public void setTfIdfEntropyStrategy(){
		
		this.wordModelProcessingStrategy= new TfIdfEntropyStrategy(this.vocabulary,this.collectionWordModel);
		
		
	}

  
  public void setTfStrategy(){
		
		this.wordModelProcessingStrategy= new TfStrategy(this.vocabulary,this.collectionWordModel);
		
		
	}

  
  

}
