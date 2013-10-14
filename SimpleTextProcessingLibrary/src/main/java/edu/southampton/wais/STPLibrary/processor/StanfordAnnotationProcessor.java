package edu.southampton.wais.STPLibrary.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.google.common.collect.ListMultimap;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentBySentence;
import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.collection.CollectionDocumentModelByWord;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.model.AnnotatedWord;
import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.model.DocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.SentenceFeatures;
import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.RegularExpressionUtility;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.Logger;
import edu.washington.cs.knowitall.commonlib.MultiMap;

public class StanfordAnnotationProcessor implements Runnable {

	private CollectionDocumentBySentence collectionSentenceModel;

	private RegularExpressionUtility regularExpressionUtility;

	private StanfordCoreNLPService stanfordCoreNLPSercvices;

	HashSet<String> listsStopWords;

	private Pattern pattern;

	private Parameter parameter;
	
	private StringProcessor stringProcessor;

	public StanfordAnnotationProcessor(CollectionDocumentBySentence collection,
			StanfordCoreNLPService stanfordCoreNLPService,
			Pattern senteceValidation, Parameter parameter,
			HashSet<String>... list) {

		regularExpressionUtility = new RegularExpressionUtility();

		collectionSentenceModel = collection;

		this.stanfordCoreNLPSercvices = stanfordCoreNLPService;

		listsStopWords = new HashSet<String>();

		for (HashSet<String> item : list) {

			listsStopWords.addAll(item);
		}

		this.pattern = senteceValidation;

		this.parameter = parameter;
		
		this.stringProcessor=new StringProcessor();

	}

	private void processCollectionSentence() throws Exception {

		for (DocumentModelBySentence item : this.collectionSentenceModel) {

			Logger.logFiner("Stanford annotation process for the file "
					+ item.getTextFile().getReference().toString());

			List<Integer> listCancell = new ArrayList<Integer>();

			
			for (Map.Entry<Integer, String> entry: item.getEntrySet()) {

				
				List<String> sc = this.stringProcessor.cleanSentence(entry.getValue(), pattern);

				
				System.out.println("---------------");
				
				System.out.println(sc);
				
				
				System.out.println("---------------");
				
				
				if (!sc.isEmpty()&&sc.size()>=this.parameter.numMinTokenSentence&&sc.size()<=this.parameter.maxNumTokenSentence) {

					
				String scbody=Joiner.on(" ").join(sc);	
					
					SentenceModel smodel = new SentenceModel(scbody);

					stanfordCoreNLPSercvices.buildAnnotation4Sentence(smodel);

					
					SentenceModelUtility.invalidatedStopListTerm(smodel, listsStopWords);
					
					
					boolean valid = SentenceModelUtility.countMeaningfulTerms(smodel,this.parameter.numMinTokenSentence);
							
							
				
					if (valid) {

						item.settingSetenceModel(entry.getKey(), smodel);
						
						item.settingSetence(entry.getKey(), scbody);

					}

					else {

						Logger.logFiner("not valid " + sc);

						listCancell.add(entry.getKey());
						// System.err.print("delete sentences "+index);

					}

				}
					else{
						
						listCancell.add(entry.getKey());
						
					}
					
					
				
			}

			// clean the Document by empty sentences;

			item.invalidateSetences(listCancell);

			Logger.logFiner("Stanford annotation process  done for the file "
					+ item.getTextFile().getReference().toString());

		}

	}

	@Override
	public void run() {

		try {
			this.processCollectionSentence();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public RegularExpressionUtility getRegularExpressionUtility() {
		return regularExpressionUtility;
	}

}