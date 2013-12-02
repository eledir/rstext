package edu.southampton.wais.STPLibrary.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.rits.cloning.Cloner;

import edu.southampton.wais.STPLibrary.model.AnnotatedWord;
import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagLingPipe;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.STPLibrary.nlp.RegularExpressionUtility;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.utility.datastructure.IntegerSingleNode;
import edu.southampton.wais.utility.datastructure.SingleNode;
import edu.southampton.wais.utility.general.Logger;

public class SentenceModelUtility {

	public static void invalidatedStopListTerm(SentenceModel smodel,
			HashSet<String> listsStopWords) {

		for (SingleNode<Integer, String> node : smodel) {

			
			int index = node.getNumber();

			// the stop list is sepcified in terms of lemma;
			
			String value=smodel.getLemma().get(index);
			
			
			if (listsStopWords.contains(value)) {

				smodel.getIsValidWord().put(index, false);

			}

		}

	}

	
	public static boolean countMeaningfulTerms(SentenceModel smodel,
			int numMinTokenSentence) {
		// TODO Auto-generated method stub

		HashMap<Integer, Boolean> map = smodel.getIsValidWord();

		Collection<Boolean> valueSet = map.values();

		Iterable<Boolean> filtered = Iterables.filter(valueSet,
				new Predicate<Boolean>() {
					@Override
					public boolean apply(Boolean p) {
						return p;
					}
				});

		return Lists.newArrayList(filtered).size() >= numMinTokenSentence ? true
				: false;

	}

	

	public static void visualizeGraph(Graph<String, String> g) {

		JFrame frame = new JFrame();
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JGraph jgraph = new JGraph(new JGraphModelAdapter(g));

		frame.getContentPane().add(jgraph);

		frame.pack();
		frame.setVisible(true);

	}


	public static void invalidatedShortWord(SentenceModel sm, int len) {
		// TODO Auto-generated method stub
	
		
		List<SingleNode<Integer, String>>list=sm.getWordList();
		
		for(SingleNode<Integer, String> node:list){
			
			if(node.getObject().length()<=len){
				
				sm.addValidWord(node.getNumber(), false);
				
			}
			
		}
	}


	public static List<String> getWordFromString(String gloss,
			HashSet<String> stopList) {
		// TODO Auto-generated method stub
		
		Logger.logSevere("getWordFromString not implemented");
		
		
		return null;
	}

}
