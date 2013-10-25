package edu.southampton.wais.STPLibrary.matrix;

import java.util.HashMap;
import java.util.Map;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import edu.southampton.wais.STPLibrary.collection.CollectionDocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.DocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.VocabularyMeasure;
import edu.southampton.wais.STPLibrary.model.VocabularyModel;
import edu.southampton.wais.STPLibrary.model.WordFeatures;
import edu.southampton.wais.utility.general.ColtUtility;

public  class WordModelMatrix {

protected SparseDoubleMatrix2D matrix;

protected  VocabularyModel vocabulary;

private CollectionDocumentModelByWord collectionWordModel;

private boolean iscomputed=false;

private HashMap<Integer, String> mapTerm;

private HashMap<Integer, DocumentModelByWord> mapDocument;

private WordModelMatrixStrategy strategyWordModelMarix;


public  WordModelMatrix(CollectionDocumentModelByWord collectionWordModel, VocabularyModel vocabulary,HashMap<Integer, String> mapTerm,
		HashMap<Integer, DocumentModelByWord> mapDocument, WordModelMatrixStrategy strategyWordModelMatrix){
	this.vocabulary=vocabulary;
	this.collectionWordModel=collectionWordModel;
	this.mapDocument=mapDocument;
	this.mapTerm=mapTerm;
	this.strategyWordModelMarix=strategyWordModelMatrix;
	
	
}


public   void computeTermByDocumentMatrix(){
	
	
	
	matrix= new SparseDoubleMatrix2D(vocabulary.sizeWords(), collectionWordModel.sizeCollection());
	
	
	//the values is computed as local/global
	int i=0,j=0;
	for(Map.Entry<String, VocabularyMeasure> item1: vocabulary){
		j=0;
		for(DocumentModelByWord item2: collectionWordModel){
			WordFeatures metaDataMeasure=item2.getWordFeature(item1.getKey());
			if(metaDataMeasure!=null){
			
				double value= this.strategyWordModelMarix.getValueWordModelMatrix(metaDataMeasure,item1.getValue());
					
				matrix.set(i, j, value);
			}
			else{
				matrix.set(i, j,0);
			}
			
			mapDocument.put(j, item2);
			j++;
			
		}
		mapTerm.put(i, item1.getKey());
		i++;
	
	}
	iscomputed=true;
	
}

	





public  SparseDoubleMatrix2D getTermByDocumentMatrix(){
	
	if(!iscomputed){
		computeTermByDocumentMatrix();
	    return matrix; 
	}
	else{
		return matrix;
	}   

};

public SparseDoubleMatrix2D getDocumentMatrixByTerm(){
	
	
	return ColtUtility.transpose(matrix);
	
};
	
	



	
}
