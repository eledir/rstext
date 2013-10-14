package edu.southampton.wais.STPLibrary.wordMeasureStrategy;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.VocabularyModel;

public class TfIdfEntropyStrategy implements WordModelProcessingStrategy {


	
    private EntropyStrategy entropyStrategy;
    private TfIdfStrategy tfIdfStrategy;
	
    
    public TfIdfEntropyStrategy (VocabularyModel vocabulary,CollectionDocumentModelByWord collectionWordModel) {
		
		entropyStrategy= new EntropyStrategy(vocabulary, collectionWordModel);
		tfIdfStrategy= new TfIdfStrategy(vocabulary, collectionWordModel);
	}
	
	@Override
	public void execute() {
		this.entropyStrategy.execute();
		this.tfIdfStrategy.execute();
		
	}

}
