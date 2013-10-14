package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

import opennlp.tools.sentdetect.SentenceDetectorME;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentBySentence;
import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.file.TxtFile;
import edu.southampton.wais.STPLibrary.model.AnnotatedWord;
import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.OpenNLPTextSentenceProcessor;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.processor.StanfordAnnotationProcessor;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.IOFileUtility;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.STPLibrary.utility.TableDependacyFilter;
import edu.southampton.wais.utility.Logger;
import edu.southampton.wais.utility.UtilitySearialization;

public class SearchSuperSense {

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

		// setting the minimum number of word to consider for each sentence..

		parameter.mengifulTerms = minimumNumberWordForSentence;

		Logger.logFine("loading collection ");

		CollectionTextFile collectionTextFile = IOUtility4STPLibrary
				.loadCollectionTextFile(new File(basefolderIN), "collection");

		Logger.logFine("splitting  collection");

		StanfordCoreNLPService stanfordCoreNLPService = null;

		stanfordCoreNLPService = StanfordCoreNLPService
				.StanfordCoreNLPService(parameter.dirConf);

		// build a collection of metadata
		String nameFileStopList1 = parameter.dirConf + File.separator
				+ "StopWordsLite.txt";

		HashSet<String> listStop1 = IOFileUtility
				.readHashSetStringFromFile(new File(nameFileStopList1));

		SentenceDetectorME modelSentence = OpenNLPTextSentenceProcessor
				.loadModel(parameter);

		OpenNLPTextSentenceProcessor sentenceTokenizerProcessor = new OpenNLPTextSentenceProcessor(
				collectionTextFile, parameter, modelSentence);

		sentenceTokenizerProcessor.run();

		CollectionDocumentBySentence collectionDocumentModelBySentence = sentenceTokenizerProcessor
				.getCollectionSentenceModel();

		StanfordAnnotationProcessor stanfordAnnotationProcessor = new StanfordAnnotationProcessor(
				collectionDocumentModelBySentence, stanfordCoreNLPService,
				parameter.patternSentValidation, parameter, listStop1);

		stanfordAnnotationProcessor.run();

		List<String> bodySetence = new ArrayList<String>();
		
		
		List<String> lXYZYNounGov = new ArrayList<String>();
		
		List<String> lXYZZNounDep = new ArrayList<String>();
		
		
		List<String> lXYZYVerbGov = new ArrayList<String>();
		
		
		List<String> lXYZZVerbDep = new ArrayList<String>();
		
		

		for (DocumentModelBySentence doc : collectionDocumentModelBySentence) {

			try {
				UtilitySearialization.serializeObject(doc, new File(basefolderOut + File.separator+"objectDoc"+File.separator+doc.getTextFile().getReference().toString()+".bin"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			for (Map.Entry<Integer, String> sentence : doc.getEntrySet()) {

				if (doc.isValidSentense(sentence.getKey())) {

					bodySetence.add(sentence.getValue());

					SentenceModel model = doc.getSentenceModel(sentence
							.getKey());

					Table<String, String, String> tableDepeGovern = model
							.getTableDepGovern();

					Table<String, String, String> tableDepeDep = model
							.getTableDepDep();

					lXYZYNounGov.addAll(TableDependacyFilter.fileterXYZYNoun(tableDepeGovern));

					lXYZZNounDep.addAll(TableDependacyFilter.fileterXYZYNoun(tableDepeDep));

					lXYZYVerbGov.addAll(TableDependacyFilter.fileterXYZYVerb(tableDepeGovern));

					lXYZZVerbDep.addAll(TableDependacyFilter.fileterXYZYVerb(tableDepeDep));

				}

			}

		}

		try {

			File fs = new File(basefolderOut + File.separator
					+ "allSentece.txt");

			FileUtils.writeLines(fs, bodySetence);
			

			
			File fs1 = new File(basefolderOut + File.separator
					+ "lXYYNounGov.txt");

			FileUtils.writeLines(fs1, lXYZYNounGov);

			
			
			File fs2 = new File(basefolderOut + File.separator
					+ "lXYZZNounDep.txt");

			FileUtils.writeLines(fs2, lXYZZNounDep);

			
			
			File fs3 = new File(basefolderOut + File.separator
					+ "lXYZXVerbGov.txt");

			FileUtils.writeLines(fs3, lXYZYVerbGov);

			
			File fs4 = new File(basefolderOut + File.separator
					+ "lXYZZVerbDep.txt");

			FileUtils.writeLines(fs4, lXYZZVerbDep);
			
			  stopwatch.stop(); // optional

			   long minute = stopwatch.elapsedTime(TimeUnit.MINUTES);
			   
			   Logger.logFine("that minute: " + minute);
			   
			   Logger.logFine("that took: " + stopwatch); // formatted string like "12.3 ms"


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
