package edu.southampton.wais.STPLibrary.nlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.processor.SentenceTokenizerProcessor;
import edu.southampton.wais.STPLibrary.utility.CollectionSentenceModelUtility;


public class SpaceSentenceTokenizer extends SentenceTokenizerProcessor {

	public SpaceSentenceTokenizer(CollectionTextFile collectionTextFile,
			Parameter o) {
		super(collectionTextFile, o);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getSentence(String body) {
		
		
		
		String []x=body.split("\\n");
		
		//delete the sentences  empty
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
		//clean the vector for the minimmub number of token
		return Arrays.asList(x);
	}

	
	
	
}
