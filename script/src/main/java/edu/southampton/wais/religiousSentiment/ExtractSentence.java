package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import opennlp.tools.sentdetect.SentenceDetectorME;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentBySentence;
import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.OpenNLPTextSentenceProcessor;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.general.IOFileUtility;
import edu.southampton.wais.utility.general.Logger;
import edu.southampton.wais.utility.general.UtilitySearialization;

public class ExtractSentence {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Stopwatch stopwatch = new Stopwatch().start();
		
		Parameter parameter = null;
		try {
			parameter = new Parameter(new File(args[0]));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

			Logger.logInfo("error load propoerties file...");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

			Logger.logInfo("error load propoerties file...");

		}

		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(new File(args[0])));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String basefolderIN = properties.getProperty("basefolderIN");

		// "/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate/20news-bydate-train";

		String basefolderOut = properties.getProperty("basefolderOut");



		int minimumNumberWordForSentence = new Integer(
				properties.getProperty("minimumNumberWordForSentence"));


		//setting the minimum number of word to consider for each sentence..
		
		parameter.mengifulTerms=minimumNumberWordForSentence;

		
		StanfordCoreNLPService services = StanfordCoreNLPService
				.StanfordCoreNLPService(parameter.dirConf);

		String nameFileStopList1 = parameter.dirConf + File.separator + "StopWordsLite.txt";

		HashSet<String> listStop = IOFileUtility
				.readHashSetStringFromFile(new File(nameFileStopList1));
		

		
		CollectionTextFile collectionTextFile = IOUtility4STPLibrary
				.loadCollectionTextFile(new File(basefolderIN), "collection");

		SentenceDetectorME modelSentence=OpenNLPTextSentenceProcessor.loadModel(parameter);
		
		
		
		
		OpenNLPTextSentenceProcessor sentenceTokenizerProcessor = new OpenNLPTextSentenceProcessor(
				collectionTextFile,parameter,modelSentence
				);

	
		
		  sentenceTokenizerProcessor.run();

		

		CollectionDocumentBySentence collectionDocumentModelBySentence = sentenceTokenizerProcessor
				.getCollectionSentenceModel();
		
		
		
		
		
		
		for(DocumentModelBySentence doc:collectionDocumentModelBySentence){
			
			for(int i=0;i<doc.sizeSentence();i++){
				
				if(doc.isValidSentense(i)){
					
					
					String sentence=doc.getSentence(i);

					
					StringProcessor processor= new StringProcessor();
					
					
					List<String>tokens=processor.cleanSentence(sentence, parameter.patternSentValidation);
					
					
					
					if(tokens.size()<Parameter.maxNumTokenSentence){
					
					
					SentenceModel sm = new SentenceModel(sentence);

					try {

						
						
						//Logger.logSevere(sm.getBody());
						
						services.buildAnnotation4Sentence(sm);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					SentenceModelUtility.invalidatedStopListTerm(sm, listStop);

					SentenceModelUtility.invalidatedShortWord(sm, 2);

					try {
						UtilitySearialization.serializeObject(sm, new File(basefolderOut
								 +File.separator+doc.getTextFile().getReference().toString()+"_"+ i + ".bin"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
				
				 

			}
					
			
			
					
				}
				
				
		  stopwatch.stop(); // optional

		  long minute = stopwatch.elapsedTime(TimeUnit.MINUTES);
		   
		  System.out.println("that minute: " + minute);
		   
		  System.out.println("that took: " + stopwatch); // formatted string like "12.3 ms"
			
		
		
		
		
		
	}

}
