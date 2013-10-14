package edu.southampton.wais.STPLibrary.sentiment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.dictionary.Dictionary;
import edu.southampton.wais.STPLibrary.dataStructure.IntegerSingleNode;
import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.utility.TableDependacyFilter;
import edu.southampton.wais.STPLibrary.wordnet.WordnetSuperSenseRetrival;

public class PolaritySuperSense {

	private SentenceModel sentenceModel;

	
	private SentimentComputing sentimentComputing;

	private HashMap<String, Boolean> mapNegation;

	private HashMap<Integer, String> hashIndexVerb;

	private ListMultimap<Integer, String> hashIndexContexNoun;
	
	private ListMultimap<Integer, String> hashIndexContexAdj;
	

	private ListMultimap<Integer, String> hashIndexContexAll;
	
	
	private HashMap<Integer, Integer> hashPolarityVerb;

	private HashMap<Integer, String> hashSuperSenseVerb;

	private int indexVerb = -1;

	public PolaritySuperSense(SentenceModel sm,
			SentimentComputing sentimentComputing) {

		this.sentenceModel = sm;
		this.sentimentComputing = sentimentComputing;
		hashIndexVerb = Maps.newHashMap();
		hashIndexContexAll = ArrayListMultimap.create();
		hashIndexContexNoun = ArrayListMultimap.create();
		hashIndexContexAdj = ArrayListMultimap.create();
		
		
		
		hashPolarityVerb = Maps.newHashMap();
		hashSuperSenseVerb = Maps.newHashMap();
	}

	public void computePolarity() throws JWNLException {

		// get verb

		Table<String, String, String> tableGove = this.sentenceModel
				.getTableDepGovern();

		computeNegationDepend(tableGove);

		Table<String, String, String> tableGovVerb1 = TableDependacyFilter
				.fileterXYZYVerb(this.sentenceModel.getTableDepGovern());

		Table<String, String, String> tableGovVerb2 = TableDependacyFilter
				.fileterXYZYVerb(this.sentenceModel.getTableDepDep());

		
		
		Set<String> setVerb = Sets.union(tableGovVerb1.columnKeySet(),tableGovVerb2.columnKeySet());

		for (String verb : setVerb) {

			indexVerb++;

			this.hashIndexVerb.put(indexVerb, verb.split("-")[0]);

			Set<String> verbNounContext1 = Sets.newHashSet();

			Set<String> verbAdjContext1 = Sets.newHashSet();

			computeVerbAdjContext(verb, verbAdjContext1, verbNounContext1);
			

			Set<String> verbNounContext2 = Sets.newHashSet();

			Set<String> verbAdjContext2 = Sets.newHashSet();

			computeVerbNounContext(verb, verbAdjContext2, verbNounContext2);

			
			Set<String> verbNounContext=Sets.union(verbNounContext1,verbNounContext2);
			
			Set<String> verbAdjContext=Sets.union(verbAdjContext1,verbAdjContext2);
			
			
			
			int polarityContext = 0;

			for (String itemNC : verbNounContext) {

				String[] itemNCS = itemNC.split("-");

				int index = searchIndex(itemNCS[0].toLowerCase());

				if (index >= 0) {

					if (this.sentenceModel.getIsValidWord().get(index)) {

						String posn = this.sentenceModel.getPosNormalizeWord()
								.get(index);
						String lemma = this.sentenceModel.getLemma().get(index);
						
						String valueSenti=sentimentComputing.extract(
								lemma, posn);
						Integer value;
						if(valueSenti!=null){
						 value= new Integer(valueSenti);
						}else{
							value= new Integer(0);
						}
						this.hashIndexContexAll.put(indexVerb, lemma);
						this.hashIndexContexNoun.put(indexVerb, lemma);

						if (this.mapNegation.containsKey(itemNCS[0])) {
							value = -value;
						}

						polarityContext = polarityContext + value;

					}

				} else {

					System.out.println("Problem with index ");

				}
			}

			for (String itemAC : verbAdjContext) {

				String[] itemACS = itemAC.split("-");

				int index = searchIndex(itemACS[0].toLowerCase());

				if (index >= 0) {
					if (this.sentenceModel.getIsValidWord().get(index)) {

						String posn = this.sentenceModel.getPosNormalizeWord()
								.get(index);
						String lemma = this.sentenceModel.getLemma().get(index);
						String valueSenti=sentimentComputing.extract(
								lemma, posn);

						this.hashIndexContexAll.put(indexVerb, lemma);
						this.hashIndexContexAdj.put(indexVerb, lemma);

						
						Integer value;
						if(valueSenti!=null){
						 value= new Integer(valueSenti);
						}else{
							value= new Integer(0);
						}
						
						if (this.mapNegation.containsKey(itemACS[0])) {
							value = -value;
						}

						polarityContext = polarityContext + value;

					}
				} else {
					System.out.println("Problem with index ");

				}

			}

			int index = searchIndex(verb.split("-")[0].toLowerCase());
			
			String lemma = this.sentenceModel.getLemma().get(index);

			String valueSenti=sentimentComputing.extract(lemma, "v");

			Integer value;
			if(valueSenti!=null){
			 value= new Integer(valueSenti);
			}else{
				value= new Integer(0);
			}
			
			
			int polarityverb = value;

			int polarity = polarityverb + polarityContext;

			if (this.mapNegation.containsKey(verb)) {

				polarity = -polarityverb;

			}

			this.hashPolarityVerb.put(indexVerb, polarity);

			// search suppersense

			String superSense="all";
			try {
			
				superSense = serachSuperSense(lemma,this.sentenceModel.getPosWord().get(index));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}

			this.hashSuperSenseVerb.put(indexVerb, superSense);
			
		}

	}

	private String serachSuperSense(String lemma,String posNoNormalized) throws Exception {

		return WordnetSuperSenseRetrival.getSuperSenseToken(lemma,
				posNoNormalized);

	}

	private int searchIndex(String string) {

		// TODO Auto-generated method stub

		List<IntegerSingleNode> list = this.sentenceModel.getWordList();

		int index = -1;

		for (IntegerSingleNode node : list) {

			if (node.getName().equals(string)) {
				index = node.values;
				break;
			}
		}

		return index;
	}

	private void computeVerbAdjContext(String verb,
			Set<String> verbAdjContext,Set<String>verbNounContext) {

		
		//all adj one hop
		
		Table<String, String, String> tableGovContextAdj1 = TableDependacyFilter
				.fileterXYZYInputZAdj(this.sentenceModel.getTableDepGovern(), verb);

		verbAdjContext.addAll(tableGovContextAdj1.values());


		Table<String, String, String> tableGovContextAdj2 = TableDependacyFilter
				.fileterXYZYInputZAdj(this.sentenceModel.getTableDepDep(), verb);

		verbAdjContext.addAll(tableGovContextAdj2.values());

		
		Set<String>verbAdjContext2=Sets.newHashSet();
		
		
		for (String adjContext : verbAdjContext) {

		
			//all adj at one hope from the previous adj
			
			Table<String, String, String> tableGovContextAdj3 = TableDependacyFilter
					.fileterXYZYInputZAdj(this.sentenceModel.getTableDepGovern(), adjContext);

			
			verbAdjContext2.addAll(tableGovContextAdj3.values());

			Table<String, String, String> tableGovContextAdj4 = TableDependacyFilter
					.fileterXYZYInputZAdj(this.sentenceModel.getTableDepDep(), adjContext);

			verbAdjContext2.addAll(tableGovContextAdj4.values());

			
		}	
		
		verbAdjContext.addAll(verbAdjContext2);
		

		for (String adjContext : verbAdjContext) {

			//all noun from the previous adg

		
			Table<String, String, String> tableGovContextNoun1 = TableDependacyFilter
					.fileterXYZYInputZNoun(this.sentenceModel.getTableDepGovern(), adjContext);

			
			verbNounContext.addAll(tableGovContextNoun1.values());

			Table<String, String, String> tableGovContextNoun2 = TableDependacyFilter
					.fileterXYZYInputZNoun(this.sentenceModel.getTableDepDep(), adjContext);

			verbNounContext.addAll(tableGovContextNoun2.values());
		
		}
			
			//noun at one hope from the previous noun
			
		Set<String>verbNounContext2=Sets.newHashSet();
		
			for(String nounContex:verbNounContext){

				Table<String, String, String> tableGovContextNoun3 = TableDependacyFilter
						.fileterXYZYInputZNoun(this.sentenceModel.getTableDepGovern(), nounContex);

				verbNounContext2.addAll(tableGovContextNoun3.values());

				Table<String, String, String> tableGovContextNoun4 = TableDependacyFilter
						.fileterXYZYInputZNoun(this.sentenceModel.getTableDepDep(), nounContex);

				
				verbNounContext2.addAll(tableGovContextNoun4.values());
				
			}
			
			verbNounContext.addAll(verbNounContext2);
			
		

	}
	
	
	
	
	private void computeVerbNounContext(String verb,
			Set<String> verbAdjContext,Set<String>verbNounContext) {

		
		//noun at one hope
		
		
		Table<String, String, String> tableGovContextNoun1 = TableDependacyFilter
				.fileterXYZYInputZNoun(this.sentenceModel.getTableDepGovern(), verb);

		verbNounContext.addAll(tableGovContextNoun1.values());

		Table<String, String, String> tableGovContextNoun2 = TableDependacyFilter
				.fileterXYZYInputZNoun(this.sentenceModel.getTableDepDep(), verb);

		
		verbNounContext.addAll(tableGovContextNoun2.values());
		
		
		
		
		//noun at two hope
		
		Set<String>verbNounContext2=Sets.newHashSet();
		
		for(String nounContex:verbNounContext){

			Table<String, String, String> tableGovContextNoun3 = TableDependacyFilter
					.fileterXYZYInputZNoun(this.sentenceModel.getTableDepGovern(), nounContex);

			verbNounContext2.addAll(tableGovContextNoun3.values());

			Table<String, String, String> tableGovContextNoun4 = TableDependacyFilter
					.fileterXYZYInputZNoun(this.sentenceModel.getTableDepDep(), nounContex);

			
			verbNounContext2.addAll(tableGovContextNoun4.values());
			
		}
		
		
		verbNounContext.addAll(verbNounContext2);
		
		//all adj one hop from the noun
		
		
		for (String noun : verbNounContext){
		
		Table<String, String, String> tableGovContextAdj1 = TableDependacyFilter
				.fileterXYZYInputZAdj(this.sentenceModel.getTableDepGovern(), noun);

		verbAdjContext.addAll(tableGovContextAdj1.values());


		Table<String, String, String> tableGovContextAdj2 = TableDependacyFilter
				.fileterXYZYInputZAdj(this.sentenceModel.getTableDepDep(), noun);

		verbAdjContext.addAll(tableGovContextAdj2.values());

		}
		
		
		//all adj two hope from the adj
		Set<String>verbAdjContext2=Sets.newHashSet();
		
		for (String adjContext : verbAdjContext) {

		
			
			
			Table<String, String, String> tableGovContextAdj3 = TableDependacyFilter
					.fileterXYZYInputZAdj(this.sentenceModel.getTableDepGovern(), adjContext);

			
			verbAdjContext2.addAll(tableGovContextAdj3.values());

			Table<String, String, String> tableGovContextAdj4 = TableDependacyFilter
					.fileterXYZYInputZAdj(this.sentenceModel.getTableDepDep(), adjContext);

			verbAdjContext2.addAll(tableGovContextAdj4.values());

			
			
		}

		
		verbAdjContext.addAll(verbAdjContext2);
		
	}
	

	private void computeNegationDepend(Table<String, String, String> tableGove) {

		// check negation on verb and noun

		Table<String, String, String> tableGovNegation = TableDependacyFilter
				.fileterXYZYXNeg(tableGove);

		// put all context in the map

		mapNegation = Maps.newHashMap();

		for (String term : tableGovNegation.columnKeySet()) {

			mapNegation.put(term, true);

		}

	}

	
	
	
	
	

	public HashMap<String, Boolean> getMapNegation() {
		return mapNegation;
	}

	public HashMap<Integer, String> getHashIndexVerb() {
		return hashIndexVerb;
	}

	public ListMultimap<Integer, String> getHashIndexContexAll() {
		return hashIndexContexAll;
	}

	
	public ListMultimap<Integer, String> getHashIndexContexNoun() {
		return hashIndexContexNoun;
	}

	public ListMultimap<Integer, String> getHashIndexContexAdj() {
		return hashIndexContexAdj;
	}

	public HashMap<Integer, Integer> getHashPolarityVerb() {
		return hashPolarityVerb;
	}

	public HashMap<Integer, String> getHashSuperSenseVerb() {
		return hashSuperSenseVerb;
	}

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
