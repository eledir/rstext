package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.sentiment.SentimentComputing;
import edu.southampton.wais.utility.datastructure.IntegerSingleNode;
import edu.southampton.wais.utility.general.UtilitySearialization;

import java.io.*;
public class SentimentList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dir = "/home/antoniodesktop/Documents/data/nlpdata";

		File fnegative = new File(dir + File.separator + "opinion/negative.txt");

		File fpositive = new File(dir + File.separator + "opinion/positive.txt");

		try {
			
			
			List<String> listPositive = FileUtils.readLines(fpositive);
			
			List<String> listNegative = FileUtils.readLines(fnegative);

			SentimentComputing sentiment = SentimentComputing
					.loadSentiWordnet(new File(dir + File.separator
							+ "SentiWordNet_3.0.0_20130122.txt"));

			Iterator<File> iterator1 = FileUtils
					.iterateFiles(
							new File(
									"/home/antoniodesktop/Documents/data/iestremedata/allSentence/objectSentence"),
							new String[] { "bin" }, true);

			ListMultimap<String, String> myMutlimap = ArrayListMultimap
					.create();

			Map<String, List<String>> mapNoun = Maps.newHashMap();

			Map<String, List<String>> mapAll = Maps.newHashMap();

			
			Map<String, List<String>> mapVerb = Maps.newHashMap();

			Map<String, List<String>> mapAdj = Maps.newHashMap();

			while (iterator1.hasNext()) {

				try {

					SentenceModel sm = (SentenceModel) UtilitySearialization
							.deserialiseObject(iterator1.next());

					Stack<IntegerSingleNode> stackNoun = sm.getStacknoun();

					Stack<IntegerSingleNode> stackVerb = sm.getStackverb();

					Stack<IntegerSingleNode> stackAdj = sm.getStackadj();

					for (IntegerSingleNode node : stackNoun) {

						int index = node.values;

						if (sm.getIsValidWord().get(index)) {

							String lemma = sm.getLemma().get(index).toLowerCase();

							
							if(!mapAll.containsKey(lemma)){

								
								String value1 = sentiment.extract(lemma, "n");
								String value2 = "#";
								if (listPositive.contains(lemma)) {
									value2 = "5";
								}
								if (listNegative.contains(lemma)) {

									value2 = "-5";

								}
								List<String> value = Lists.newArrayList();
								value.add(value1);
								value.add(value2);

								mapAll.put(lemma, value);
								
							}
							
							if (!mapNoun.containsKey(lemma)) {
								String value1 = sentiment.extract(lemma, "n");
								String value2 = "#";
								if (listPositive.contains(lemma)) {
									value2 = "5";
								}
								if (listNegative.contains(lemma)) {

									value2 = "-5";

								}
								List<String> value = Lists.newArrayList();
								value.add(value1);
								value.add(value2);

								mapNoun.put(lemma.toLowerCase(), value);

							}

						}

					}
					for (IntegerSingleNode node : stackAdj) {

						int index = node.values;

						if (sm.getIsValidWord().get(index)) {

							String lemma = sm.getLemma().get(index).toLowerCase();

							if(!mapAll.containsKey(lemma)){

								
								String value1 = sentiment.extract(lemma, "a");
								String value2 = "#";
								if (listPositive.contains(lemma)) {
									value2 = "5";
								}
								if (listNegative.contains(lemma)) {

									value2 = "-5";

								}
								List<String> value = Lists.newArrayList();
								value.add(value1);
								value.add(value2);

								mapAll.put(lemma, value);
										
							}

							
							
							
							if (!mapAdj.containsKey(lemma)) {
								String value1 = sentiment.extract(lemma, "a");
								String value2 = "#";
								if (listPositive.contains(lemma)) {
									value2 = "5";
								}
								if (listNegative.contains(lemma)) {

									value2 = "-5";

								}
								List<String> value = Lists.newArrayList();
								value.add(value1);
								value.add(value2);

								mapAdj.put(lemma.toLowerCase(), value);

							}

						}

					}

					for (IntegerSingleNode node : stackVerb) {

						int index = node.values;

						if (sm.getIsValidWord().get(index)) {

							String lemma = sm.getLemma().get(index).toLowerCase();

							
	                           if(!mapAll.containsKey(lemma)){

								
								String value1 = sentiment.extract(lemma, "v");
								String value2 = "#";
								if (listPositive.contains(lemma)) {
									value2 = "5";
								}
								if (listNegative.contains(lemma)) {

									value2 = "-5";

								}
								List<String> value = Lists.newArrayList();
								value.add(value1);
								value.add(value2);

								mapAll.put(lemma, value);
										
							}

							
							
							if (!mapVerb.containsKey(lemma)) {
								String value1 = sentiment.extract(lemma, "a");
								String value2 = "#";
								if (listPositive.contains(lemma)) {
									value2 = "5";
								}
								if (listNegative.contains(lemma)) {

									value2 = "-5";

								}
								List<String> value = Lists.newArrayList();
								value.add(value1);
								value.add(value2);

								mapVerb.put(lemma.toLowerCase(), value);

							}

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

			
			
	
	   UtilitySearialization.serializeObject(mapNoun, new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/mapSentiment/mapNoun.bin"));
	   
		
	   UtilitySearialization.serializeObject(mapAdj, new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/mapSentiment/mapAdj.bin"));
	   
	   UtilitySearialization.serializeObject(mapVerb, new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/mapSentiment/mapVerb.bin"));
	   
	 
	   UtilitySearialization.serializeObject(mapAll, new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/mapSentiment/mapAll.bin"));
		 
	   
			
		SentimentList.write(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/listSentimentNoun.txt"),mapNoun);
			
		SentimentList.write(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/listSentimentAdj.txt"),mapAdj);
			
		SentimentList.write(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/listSentimentVerb.txt"),mapVerb);
			
			
			
			
			
			
			
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private static void write(File file, Map<String, List<String>> mapNoun) {
		// TODO Auto-generated method stub
	
		try{
			  // Create file 
			  FileWriter fstream = new FileWriter(file);
			  BufferedWriter out = new BufferedWriter(fstream);
			  
			  for(String key:mapNoun.keySet()){
				
				  out.write(key+"\t");
				  out.write(Joiner.on("\t").join(mapNoun.get(key)));
				  out.write("\n");
			  }
			  
			  
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		
		
	}

}
