package edu.southampton.wais.STPLibrary.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import edu.southampton.wais.utility.datastructure.IntegerSingleNode;
import edu.southampton.wais.utility.datastructure.SingleNode;
import edu.stanford.nlp.graph.DirectedMultiGraph;


public class SentenceModel implements Iterable<SingleNode<Integer,String>>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String body;

	
	DirectedMultiGraph<SingleNode<Integer,String>, String> graph;
	
	HashMap<Integer, Boolean> isValidWord;

	HashMap<Integer, String> posNormalizeWord;

	HashMap<Integer, String> posWord;

	HashMap<Integer, String> wordCorrected;

	HashMap<Integer, String> lemma;

	HashMap<Integer, String> ner;

	HashMap<Integer, Boolean> negation;

	HashMap<Integer, Boolean> notEnglishWord;

	
	ListMultimap<String, Integer> posNormMultiMap;
	ListMultimap<String, Integer> nerMultiMap;

	
	ListMultimap<String, Integer> string2IndexMultimap;
	
	HashMap<Integer, String> index2StringMap;
	
	
	List<SingleNode<Integer,String>> wordList;
    List<SingleNode<Integer,String>> wordLemmaList;
	
	
	Table<String,SingleNode<Integer,String>,SingleNode<Integer,String>> tableDepGovern;
	
	
	Table<String,SingleNode<Integer,String>,SingleNode<Integer,String>> tableDepDep;
	
	
	
	

	public SentenceModel(String body) {

		this.body = body;

		posNormMultiMap = ArrayListMultimap.create();

		isValidWord = Maps.newHashMap();
		posWord =Maps.newHashMap();
		posNormalizeWord = Maps.newHashMap();
		wordCorrected = Maps.newHashMap();
		lemma = Maps.newHashMap();
		ner =Maps.newHashMap();
		notEnglishWord=Maps.newHashMap();
		negation=Maps.newHashMap();
	
		
		wordList = Lists.newArrayList();
		wordLemmaList = Lists.newArrayList();
		
		nerMultiMap = ArrayListMultimap.create();
	
		string2IndexMultimap=ArrayListMultimap.create();
		index2StringMap=Maps.newHashMap();
		
		graph=new DirectedMultiGraph<SingleNode<Integer,String>, String>();
		tableDepGovern=HashBasedTable.create();
		tableDepDep=HashBasedTable.create();
		
		

	}

	
	
	public void addTuple(String type,SingleNode<Integer,String> gov,SingleNode<Integer,String> dep){
		
		this.tableDepGovern.put(type, gov, dep);
		
		this.tableDepDep.put(type, dep, gov);
		
		
	}
	
	
	public void addNodeGraph(SingleNode<Integer,String> v1,SingleNode<Integer,String> v2,String edge){
		
		this.graph.addVertex(v1);
		this.graph.addVertex(v2);
		
		this.graph.add(v1, v2, edge);
		
		
	};
	
	
	
	public void addIndex2String(int index, String string){
		this.index2StringMap.put(index,string);
	}
	
	
	
	public void addString2Index(String string,int index){
		this.string2IndexMultimap.put(string, index);
	}
	
	public void addNer(int index, String ner) {

		this.ner.put(index, ner);
		nerMultiMap.put(ner, index);
	}

	public void addMultiMapPOS(String type, int index) {

		this.posNormMultiMap.put(type, index);

	};

	
	public void addValidWord(int index, boolean b) {

		this.isValidWord.put(index, b);

	}

	public void addPOSWord(int index, String pos) {

		this.posWord.put(index, pos);

	}

	public void addWordCorrected(int index, String word) {

		this.wordCorrected.put(index, word);

	}

	public void addLemma(int index, String lemma) {

		this.lemma.put(index, lemma);

	}

	public void addPOSNormaLized(int index, String pos) {

		this.posNormalizeWord.put(index, pos);

	}


	public void addNegation(int index, boolean b) {

		this.negation.put(index, b);

	}

	public void addNotEnglishWord(int index, boolean b) {

		this.notEnglishWord.put(index, b);

	}

	
	
	public void addWord(SingleNode<Integer,String> node) {

		this.wordList.add(node);

	};

	public void addWordLemma(SingleNode<Integer,String> node) {

		this.wordLemmaList.add(node);

	};
	
	
	

	@Override
	public String toString() {
		return "[body=" + body + "]";
	}

	@Override
	public Iterator<SingleNode<Integer,String>> iterator() {
		// TODO Auto-generated method stub
		return this.wordLemmaList.iterator();
	}

	public String getBody() {
		return body;
	}

	public ListMultimap<String, Integer> getPosNormMultiMap() {
		return posNormMultiMap;
	}

	
	public HashMap<Integer, Boolean> getIsValidWord() {
		return isValidWord;
	}

	public HashMap<Integer, String> getPosNormalizeWord() {
		return posNormalizeWord;
	}

	public HashMap<Integer, String> getPosWord() {
		return posWord;
	}

	public HashMap<Integer, String> getWordCorrected() {
		return wordCorrected;
	}

	public HashMap<Integer, String> getLemma() {
		return lemma;
	}

	public HashMap<Integer, String> getNer() {
		return ner;
	}

	
	
	public ListMultimap<String, Integer> getNerMultiMap() {
		return nerMultiMap;
	}

	
	
	public List<SingleNode<Integer,String>> getWordList() {
		return wordList;
	}

	public List<SingleNode<Integer,String>> getWordLemmaList() {
		return wordLemmaList;
	}




	public DirectedMultiGraph<SingleNode<Integer,String>, String> getGraph() {
		return graph;
	}




	public void setGraph(DirectedMultiGraph<SingleNode<Integer,String>, String> graph) {
		this.graph = graph;
	}

	
	
	public int getNumberToken(){
		
		return this.wordList.size();
	}
	
	
	

	
	
	

	public Table<String, SingleNode<Integer,String>,SingleNode<Integer,String>> getTableDepGovern() {
		return tableDepGovern;
	}



	public Table<String, SingleNode<Integer,String>, SingleNode<Integer,String>> getTableDepDep() {
		return tableDepDep;
	}



	public HashMap<Integer, Boolean> getNegation() {
		return negation;
	}



	public HashMap<Integer, Boolean> getNotEnglishWord() {
		return notEnglishWord;
	}



	public ListMultimap<String, Integer> getString2IndexMultimap() {
		return string2IndexMultimap;
	}



	public HashMap<Integer, String> getIndex2StringMap() {
		return index2StringMap;
	}



	


	
	
	
}
