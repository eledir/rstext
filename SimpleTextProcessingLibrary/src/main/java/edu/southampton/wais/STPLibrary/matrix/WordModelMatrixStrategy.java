package edu.southampton.wais.STPLibrary.matrix;

import edu.southampton.wais.STPLibrary.model.VocabularyMeasure;
import edu.southampton.wais.STPLibrary.model.WordFeatures;

public class WordModelMatrixStrategy {


	public static final int EntropyStrategy=0;

	public static  final int TFIdfStrategy=1;

	public static final int TfIdfEntropyStrategy=2;
	
	public static final int CountStrategy=3;


	private int strategy;

	public WordModelMatrixStrategy(int strategy){

		this.strategy=strategy;

	}






	public  double getValueWordModelMatrix(WordFeatures metaDataMeasure, VocabularyMeasure vocabularyMeasure){

		double value;          
		switch (this.strategy) {

		case WordModelMatrixStrategy.EntropyStrategy:

			value=vocabularyMeasure.getGlobalMeasureValue();
			break;

		case WordModelMatrixStrategy.TFIdfStrategy:

			value=metaDataMeasure.getValue();
			break;

		case WordModelMatrixStrategy.TfIdfEntropyStrategy:

			value=metaDataMeasure.getValue()/vocabularyMeasure.getGlobalMeasureValue();
			break;
		
		case WordModelMatrixStrategy.CountStrategy:

			value=metaDataMeasure.getCount();
			break;

		default:
			value=0;
			break;

		}

		return value;

	};
}