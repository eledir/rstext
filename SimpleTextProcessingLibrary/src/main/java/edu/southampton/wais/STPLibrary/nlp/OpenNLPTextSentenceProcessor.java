package edu.southampton.wais.STPLibrary.nlp;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentBySentence;
import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.processor.SentenceTokenizerProcessor;
import edu.southampton.wais.STPLibrary.utility.CollectionSentenceModelUtility;
import edu.southampton.wais.utility.Logger;


import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;





public class OpenNLPTextSentenceProcessor extends SentenceTokenizerProcessor {

	
	
	
	
	private SentenceDetectorME sentenceDetector;



	
	

	private boolean euristicSplittter=false;

	
	public OpenNLPTextSentenceProcessor(CollectionTextFile collectionTextFile,Parameter param,int limit,SentenceDetectorME sentenceDetectorME) {
		super(collectionTextFile, param);
		
		this.sentenceDetector=sentenceDetectorME;
		
		
		// TODO Auto-generated constructor stub
	}
	

	public OpenNLPTextSentenceProcessor(CollectionTextFile collectionTextFile,Parameter param,SentenceDetectorME sentenceDetectorME) {
		super(collectionTextFile, param);
		this.sentenceDetector=sentenceDetectorME;
		
	
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
	
	
	
	
	@Override
	public  List<String> getSentence(String body) {
		
		
		List<String> listSentence= new ArrayList<String>();
		
		try{
		String [] sentence=this.sentenceDetector.sentDetect(body);
		
		
		
		  for(String item : sentence){	
				
				
			  
				  
				  listSentence.add(item);
				  
			  }
			  
		  
		
		  
		
		
		}
		catch (Exception e){
			Logger.logFiner("Error for the body "+e.getMessage());
			
			//Logger.logFiner("Error for the body "+body);
			
		
		   
		}
	  
		
		
		// TODO Auto-generated method stub
					//clean the vector for the minimmub number of token
					return listSentence;
				 
		
		
		
		
	
	
	}

	
	
	
	
	 
	 

	public static SentenceDetectorME loadModel(Parameter param){
		
		
		 InputStream modelIn=null;
		 SentenceDetectorME sentenceDetectorME=null;
		 try {
		 modelIn = new FileInputStream(param.filePathSentenceModelOpenNlp);
		SentenceModel model = new SentenceModel(modelIn);
       sentenceDetectorME = new SentenceDetectorME(model);
	
		 
		 }
		catch (IOException e) {
		  e.printStackTrace();
		
		Logger.logInfo("Error in loading thte model for sentence tokenizer");
		}
		
		finally {
		  
			if (modelIn != null) {
		    try {
		      
		      modelIn.close();
		
		     
		    
		    }
		    catch (IOException e) {
		   
		   Logger.logInfo("Error in loading thte model for sentence tokenizer");
				
		    
				
		    }
		  
			}
		
		   
		}
	
		return sentenceDetectorME;
	}

	

}
