package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.utility.UtilitySearialization;

public class Deserialize {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	Object o;
	try {
		o = UtilitySearialization.deserialiseObject(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/objectDoc/A01-01.docx.bin"));
	
	
	
		DocumentModelBySentence doc= (DocumentModelBySentence)o;
		
		for (Map.Entry<Integer, String> sentence : doc.getEntrySet()) {

			if (doc.isValidSentense(sentence.getKey())) {
				
				System.out.println(sentence.getValue());
				
				System.out.println(doc.getSentenceModel(sentence.getKey()).getNer());
				
			}
			}

	
	
	
	
	
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
}
	
}
