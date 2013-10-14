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
import java.util.regex.Pattern;

import opennlp.tools.sentdetect.SentenceDetectorME;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Table;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentBySentence;
import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.OpenNLPTextSentenceProcessor;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.processor.StanfordAnnotationProcessor;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.IOFileUtility;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.STPLibrary.utility.TableDependacyFilter;
import edu.southampton.wais.utility.Logger;
import edu.southampton.wais.utility.UtilitySearialization;

public class ObjectSentence {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Stopwatch stopwatch = new Stopwatch().start();

		List<String> setsentences = null;
		try {
			setsentences = FileUtils
					.readLines(new File(
							"/home/antoniodesktop/Documents/data/iestremedata/allSentence/allSentenceClean.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String dir = "/home/antoniodesktop/Documents/data/nlpdata";

		StanfordCoreNLPService services = StanfordCoreNLPService
				.StanfordCoreNLPService(dir);

		String nameFileStopList1 = dir + File.separator + "StopWordsLite.txt";

		HashSet<String> listStop = IOFileUtility
				.readHashSetStringFromFile(new File(nameFileStopList1));

		int index = -1;

		for (String tex : setsentences) {

			index++;

			SentenceModel sm = new SentenceModel(tex);

			try {

				services.buildAnnotation4Sentence(sm);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SentenceModelUtility.invalidatedStopListTerm(sm, listStop);

			SentenceModelUtility.invalidatedShortWord(sm, 2);

			try {
				UtilitySearialization.serializeObject(sm, new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/"
						+ File.separator + "objectSentence" +File.separator+ index + ".bin"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		  stopwatch.stop(); // optional

		   long minute = stopwatch.elapsedTime(TimeUnit.MINUTES);
		   
		  System.out.println("that minute: " + minute);
		   
		  System.out.println("that took: " + stopwatch); // formatted string like "12.3 ms"

	}
}