package edu.southampton.wais.STPLibrary.wordMeasureStrategy;


import java.util.Map.Entry;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.DocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.VocabularyMeasure;
import edu.southampton.wais.STPLibrary.model.VocabularyModel;
import edu.southampton.wais.STPLibrary.model.WordFeatures;
import edu.southampton.wais.STPLibrary.utility.MathUtility;





public class EntropyStrategy implements WordModelProcessingStrategy {

    private VocabularyModel vocabulary;
    private CollectionDocumentModelByWord collectionWordModel;
	
    
    public EntropyStrategy(VocabularyModel vocabulary,CollectionDocumentModelByWord collectionWordModel) {
		this.collectionWordModel=collectionWordModel;
		this.vocabulary=vocabulary;
	}

	
	
	//i use this formula for each string
	// 1+ Sum(pi*log_2(pi))/log_2(CollectionSize)
	//p1=pl/pg
	//pl  local probability
	//pg  global probability
	
	@Override
	public void execute() {
		
		for (Entry<String, VocabularyMeasure> item : vocabulary){
			
			   VocabularyMeasure measure= item.getValue();
			   
		      double entropy=0;
		      
		      //global probability
			   double pg=(double)measure.getCount()/(double)vocabulary.sizeWords();
			
			   for(DocumentModelByWord item2 : measure){
				   
				   WordFeatures measure2=item2.getWordFeature(item.getKey());
				   
				   //local probability
				   double pl=(double)measure2.getCount()/(double)item2.sizeWords();
				  
				   double pij=pl/pg+1;
				   
				   entropy=entropy+ pij*MathUtility.log2(pij);
				   
			   }
			   
			   entropy=1+entropy/MathUtility.log2(this.collectionWordModel.sizeCollection()+1);
			   
			   this.vocabulary.setRangeGlobalMeasure(entropy);
			   
			   measure.setGlobalMeasureValue((entropy));
			
			
		}
		
		
		
		

	}



	

	
	
	
	
	

}
