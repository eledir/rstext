package edu.southampton.wais.STPLibrary.wordnet;

import org.openjena.atlas.lib.MultiSet;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multisets;

import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;

public class WordnetSuperSenseRetrival {

	public static String getSuperSenseToken(String word,
			String posNoNormalize) throws Exception {

		String ss = null;

		if (POSTagStanford.isNoun(posNoNormalize)) {

			IndexWord iw = Dictionary.getInstance().lookupIndexWord(POS.NOUN,
					word);

			if(iw!=null)
			ss = getSuperSense(iw);

		}

		else if (POSTagStanford.isVerb(posNoNormalize)) {

			IndexWord iw2 = Dictionary.getInstance().lookupIndexWord(POS.VERB,
					word);
			
			if(iw2!=null)
			ss = getSuperSense(iw2);

		}

		if(ss==null){
			throw new Exception();
			}
		
		return ss;
	}

	public static String getSuperSense(IndexWord iw) {

		HashMultiset<String> wordsMultiset = HashMultiset.create();

		for (Synset item : iw.getSenses()) {

			wordsMultiset.add(item.getLexFileName());

		}

		ImmutableMultiset<String> set = Multisets
				.copyHighestCountFirst(wordsMultiset);

		return set.asList().get(0);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMultiset<String> wordsMultiset = HashMultiset.create();

		wordsMultiset.add("a");

		wordsMultiset.add("a");

		wordsMultiset.add("b");

		wordsMultiset.add("c");

		ImmutableMultiset<String> set = Multisets
				.copyHighestCountFirst(wordsMultiset);

		System.out.println(set.asList().get(0));

	}

}
