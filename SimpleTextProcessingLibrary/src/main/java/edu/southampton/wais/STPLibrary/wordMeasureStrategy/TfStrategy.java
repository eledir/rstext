package edu.southampton.wais.STPLibrary.wordMeasureStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import edu.southampton.wais.STPLibrary.collection.CollectionDocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.DocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.VocabularyMeasure;
import edu.southampton.wais.STPLibrary.model.VocabularyModel;
import edu.southampton.wais.STPLibrary.model.WordFeatures;

public class TfStrategy implements WordModelProcessingStrategy {


	private VocabularyModel vocabulary;
	private CollectionDocumentModelByWord collectionTextFile;
	
	
	public TfStrategy(VocabularyModel vocabulary,
			CollectionDocumentModelByWord collectionMetadataTextFile) {
		this.collectionTextFile=collectionMetadataTextFile;
		this.vocabulary=vocabulary;
	}

	@Override
	public void execute() {
		
		
		for(DocumentModelByWord item: collectionTextFile){
			
			//comopute tfidf
			
			 for (Entry<String, WordFeatures> item2: item){
				 
				 //copmute idf 
				 
				VocabularyMeasure vocabularyMeasure=  this.vocabulary.getGlobalWordMeasure(item2.getKey());
				WordFeatures metaDataMeasure=item2.getValue();
				
				
				double tf=(double)metaDataMeasure.getCount()/(double)item.sizeWords();
				
			    metaDataMeasure.setValue(tf);
				 
			 }
			
			
			
		}
		
		
		

	}

	

	
	
	

}
