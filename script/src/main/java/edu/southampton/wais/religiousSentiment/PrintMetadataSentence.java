package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.JWNLException;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.wordnet.WordnetSuperSenseRetrival;
import edu.southampton.wais.utility.datastructure.IntegerSingleNode;
import edu.southampton.wais.utility.general.PrintUtility;
import edu.southampton.wais.utility.general.UtilitySearialization;

public class PrintMetadataSentence {

	public static void main(String[] args) {

		String dirIn = "/Users/antoniopenta/Dropbox/dati/sentimentIdea/manifesto/allSentenceGood/";
		
		String dirOut="/Users/antoniopenta/Dropbox/dati/sentimentIdea/manifesto/goodAnalysis/";

		File f = new File("config/file_properties.xml");

		FileInputStream fileImFileInputStream;
		try {
			fileImFileInputStream = new FileInputStream(f);

			JWNL.initialize(fileImFileInputStream);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterator<File> iterator1 = FileUtils.iterateFiles(new File(dirIn),
				new String[] { "bin" }, true);

		Multiset<String> multisetSuperSense = HashMultiset.create();
		Multiset<String> multisetSuperSenseNull = HashMultiset.create();

		Multimap<String, String> multimapSuperSense = ArrayListMultimap
				.create();

		Multiset<String> multisetNoun = HashMultiset.create();
		Multiset<String> multisetVerb = HashMultiset.create();
		Multiset<String> multisetAdj = HashMultiset.create();

		Multiset<String> multisetEntity = HashMultiset.create();

		Multimap<String, String> multimapEntity = ArrayListMultimap.create();

		while (iterator1.hasNext()) {

			try {

				SentenceModel sm = (SentenceModel) UtilitySearialization
						.deserialiseObject(iterator1.next());

				buildSuperSenseList(sm, multisetSuperSense,
						multisetSuperSenseNull, multimapSuperSense);

				buildSetAnnotation(sm, multisetNoun, multisetVerb, multisetAdj,
						multisetEntity, multimapEntity);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			
			
			
			PrintUtility.printOrderingMultiSet(new File(dirOut+File.separator+"multisetSuperSense.txt"), multisetSuperSense, ":");
			
			
			PrintUtility.printOrderingMultiSet(new File(dirOut+File.separator+"multisetSuperSenseNull.txt"), multisetSuperSenseNull, ":");
			
			
			PrintUtility.printOrderingMultiSet(new File(dirOut+File.separator+"multisetNoun.txt"), multisetNoun, ":");
			
			
			PrintUtility.printOrderingMultiSet(new File(dirOut+File.separator+"multisetVerb.txt"), multisetVerb, ":");
			
			
			PrintUtility.printOrderingMultiSet(new File(dirOut+File.separator+"multisetAdj.txt"), multisetAdj, ":");
			
			
			
			PrintUtility.printMultiSet(new File(dirOut+File.separator+"multisetEntity.txt"), multisetEntity, ":");
			
			
			
			
			PrintUtility.printMultiMap(new File(dirOut+File.separator+"multiMapSuperSense.txt"),multimapSuperSense, "->", ";",":");
			
			PrintUtility.printMultiMap(new File(dirOut+File.separator+"multiMapEntity.txt"),multimapEntity, "->", ";",":");
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void buildSetAnnotation(SentenceModel sm,
			Multiset<String> setNoun, Multiset<String> setVerb,
			Multiset<String> setAdjecti, Multiset<String> mutlisetEntity,
			Multimap<String, String> multimapEntity) {

		buildSetNoun(sm, setNoun);
		buildSetVerb(sm, setNoun);
		buildSetAdj(sm, setAdjecti);
		buildMapEntity(sm, mutlisetEntity, multimapEntity);

	}

	private static void buildMapEntity(SentenceModel sm,
			Multiset<String> mutlisetEntity,
			Multimap<String, String> multimapEntity) {
		// TODO Auto-generated method stub

		ListMultimap<String, Integer> list = sm.getNerMultiMap();

		for (String item : list.keySet()) {

			List<Integer> word = list.get(item);

			for (Integer idx : word) {

				if (sm.getIsValidWord().get(idx)) {

					String token = sm.getLemma().get(idx);

					mutlisetEntity.add(item);
					multimapEntity.put(item, token);

				}

			}

		}

	}

	private static void buildSetAdj(SentenceModel sm,
			Multiset<String> setAdjecti) {

		Stack<IntegerSingleNode> verb = sm.getStackadj();

		for (IntegerSingleNode node : verb) {

			int index = node.values;

			if (sm.getIsValidWord().get(index)) {

				setAdjecti.add(node.name);

			}

		}

	}

	private static void buildSetVerb(SentenceModel sm, Multiset<String> setVerb) {
		// TODO Auto-generated method stub

		Stack<IntegerSingleNode> verb = sm.getStackverb();

		for (IntegerSingleNode node : verb) {

			int index = node.values;

			if (sm.getIsValidWord().get(index)) {

				setVerb.add(node.name);

			}

		}

	}

	private static void buildSetNoun(SentenceModel sm, Multiset<String> setNoun) {

		Stack<IntegerSingleNode> stack = sm.getStacknoun();

		for (IntegerSingleNode node : stack) {

			int index = node.values;

			if (sm.getIsValidWord().get(index)) {

				setNoun.add(node.name);

			}

		}

	}

	private static void buildSuperSenseList(SentenceModel sm,
			Multiset<String> setSuperSense, Multiset<String> setSuperSenseNull,
			Multimap<String, String> mutlimapSuperSense) {

		Stack<IntegerSingleNode> nodes = sm.getStackverb();

		for (IntegerSingleNode node : nodes) {

			int index = node.values;

			if (sm.getIsValidWord().get(index)) {

				String lemma = sm.getLemma().get(index);

				String posNoNormalized = sm.getPosWord().get(index);

				String supersense;
				try {
					supersense = WordnetSuperSenseRetrival.getSuperSenseToken(
							lemma, posNoNormalized);

					if (supersense != null) {

						setSuperSense.add(supersense);

						mutlimapSuperSense.put(supersense, lemma);

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block

					setSuperSenseNull.add(lemma);

				}

			}

		}

	}

}
