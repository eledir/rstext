package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.processor.ExtractModelsBasedOnStanfordDependancyProcessor;
import edu.southampton.wais.STPLibrary.stanfordRule.RuleException;
import edu.southampton.wais.STPLibrary.stanfordRule.RuleParser;
import edu.southampton.wais.STPLibrary.stanfordRule.RuleParserException;
import edu.southampton.wais.STPLibrary.stanfordRule.RuleTemplate;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.general.IOFileUtility;
import edu.southampton.wais.utility.general.UtilitySearialization;

public class ExtractTriplesStanford {

	
	
	
	public static void main(String[] args){
		
	
Iterator<File>iterator1=FileUtils.iterateFiles(new File("/home/antoniodesktop/Dropbox/dati/sentimentIdea/manifesto/allSentenceBad/"),new String[]{"bin"}, true);
		
		ListMultimap<String,String> myMutlimap = ArrayListMultimap.create();
	
		
		String dir = "/Users/antoniopenta/Documents/workspaceReligionSentiment/nlpdata";

		 dir="/home/antoniodesktop/Documents/data/nlpdata";

		 String nameFileStopList1 = dir + File.separator
					+ "StopWordsLite.txt";
		
			HashSet<String> listStop = IOFileUtility
					.readHashSetStringFromFile(new File(nameFileStopList1));

			List<String> ruleDef;
			Set<RuleTemplate> rules=null;
				try {
					ruleDef = FileUtils.readLines(new File(
							"rules/tripleRulesExtraction.rule"));
					
					RuleParser parser = new RuleParser(ruleDef);

					 rules = parser.parser();
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RuleParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

			

		
		
		while(iterator1.hasNext()){
			
			 try {
				
			SentenceModel sm=(SentenceModel) UtilitySearialization.deserialiseObject(iterator1.next());
			
			
			SentenceModelUtility.invalidatedStopListTerm(sm, listStop);

			boolean valid = SentenceModelUtility
					.countMeaningfulTerms(sm, 3);
			System.out.println(StringUtils.repeat("_", 10));

			System.out.println(sm.getBody());

			System.out.println(valid);

			System.out.println(sm.getGraph());

			System.out.println(StringUtils.repeat("_", 10));
			
			if(valid){
				
			
                 List<TripleModel>listTripleModel=ExtractModelsBasedOnStanfordDependancyProcessor.extract(sm, rules);
				
				List<TripleModel>listTripleModelExtend=ExtractModelsBasedOnStanfordDependancyProcessor.extend(sm, listTripleModel);	
				
				System.out.println(StringUtils.repeat("*", 10));

				for (TripleModel model : listTripleModelExtend) {

					System.out.println(model);

				}

				System.out.println(StringUtils.repeat("*", 10));
			}
			
			
			
			}
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	
		 }
	
	}}
