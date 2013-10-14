package edu.southampton.wais.STPLibrary.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;

import org.apache.lucene.analysis.WordlistLoader;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgraph.JGraph;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import edu.southampton.wais.STPLibrary.dataStructure.DoubleStringNode;
import edu.southampton.wais.STPLibrary.dataStructure.IntegerSingleNode;

public class SentenceModel implements Iterable<IntegerSingleNode>,Serializable {

	private String body;

	ListMultimap<String, Integer> posNormMultiMap;

	DirectedGraph<String, String> graph;
	
	HashMap<Integer, Boolean> isValidWord;

	HashMap<Integer, String> posNormalizeWord;

	HashMap<Integer, String> posWord;

	HashMap<Integer, String> wordCorrected;

	HashMap<Integer, String> lemma;

	HashMap<Integer, String> ner;

	
	ListMultimap<String, Integer> nerMultiMap;

	Stack<IntegerSingleNode> stackadj;

	Stack<IntegerSingleNode> stacknoun;

	Stack<IntegerSingleNode> stackverb;

	Stack<IntegerSingleNode> stackadverb;

	Stack<IntegerSingleNode> stacknegation;

	List<IntegerSingleNode> wordList;

	List<IntegerSingleNode> wordLemmaList;
	
	
	Table<String,String,String> tableDepGovern;
	
	
	Table<String,String,String> tableDepDep;
	
	
	Stack<IntegerSingleNode> notEnglishWord;
	

	public SentenceModel(String body) {

		this.body = body;

		posNormMultiMap = ArrayListMultimap.create();

		
		isValidWord = new HashMap<Integer, Boolean>();

		posWord = new HashMap<Integer, String>();

		posNormalizeWord = new HashMap<Integer, String>();

		wordCorrected = new HashMap<Integer, String>();

		lemma = new HashMap<Integer, String>();

		stackadj = new Stack<IntegerSingleNode>();
		stacknoun = new Stack<IntegerSingleNode>();
		stackverb = new Stack<IntegerSingleNode>();
		stackadverb = new Stack<IntegerSingleNode>();
		stacknegation = new Stack<IntegerSingleNode>();

		wordList = Lists.newArrayList();

		wordLemmaList = Lists.newArrayList();

		nerMultiMap = ArrayListMultimap.create();

		ner = new HashMap<Integer, String>();

		
		
		graph=new DefaultDirectedGraph<String, String>(String.class);
		
		tableDepGovern=HashBasedTable.create();
		
		tableDepDep=HashBasedTable.create();
		
		notEnglishWord=new Stack<IntegerSingleNode>();

	}

	
	
	public void addTuple(String type,String gov,String dep){
		
		this.tableDepGovern.put(type, gov, dep);
		
		this.tableDepDep.put(type, dep, gov);
		
		
	}
	
	
	public void addNodeGraph(String v1,String v2,String edge){
		
		this.graph.addVertex(v1);
		this.graph.addVertex(v2);
		
		this.graph.addEdge(v1, v2, edge);
		
		
	};
	
	
	public void addNer(int index, String ner) {

		this.ner.put(index, ner);
		nerMultiMap.put(ner, index);
	}

	public void addMultiMapPOS(String type, int index) {

		this.posNormMultiMap.put(type, index);

	};

	public void addStackAdj(IntegerSingleNode node) {

		this.stackadj.push(node);

	}

	public void addStackVerb(IntegerSingleNode node) {

		this.stackverb.push(node);

	}

	public void addStackNoun(IntegerSingleNode node) {

		this.stacknoun.push(node);

	}

	public void addStackNegation(IntegerSingleNode node) {

		this.stacknegation.push(node);

	}

	public void addStackAdverb(IntegerSingleNode node) {

		this.stackadverb.push(node);

	}

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

	public void addWord(IntegerSingleNode node) {

		this.wordList.add(node);

	};

	public void addWordLemma(IntegerSingleNode node) {

		this.wordLemmaList.add(node);

	};

	@Override
	public String toString() {
		return "[body=" + body + "]";
	}

	@Override
	public Iterator<IntegerSingleNode> iterator() {
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

	public Stack<IntegerSingleNode> getStackadj() {
		return stackadj;
	}

	public Stack<IntegerSingleNode> getStacknoun() {
		return stacknoun;
	}

	public Stack<IntegerSingleNode> getStackverb() {
		return stackverb;
	}

	public Stack<IntegerSingleNode> getStackadverb() {
		return stackadverb;
	}

	public Stack<IntegerSingleNode> getStacknegation() {
		return stacknegation;
	}

	public List<IntegerSingleNode> getWordList() {
		return wordList;
	}

	public List<IntegerSingleNode> getWordLemmaList() {
		return wordLemmaList;
	}




	public DirectedGraph<String, String> getGraph() {
		return graph;
	}




	public void setGraph(DirectedGraph<String, String> graph) {
		this.graph = graph;
	}

	
	
	
	
	
	

	
	
	

	public Table<String, String, String> getTableDepGovern() {
		return tableDepGovern;
	}



	public Table<String, String, String> getTableDepDep() {
		return tableDepDep;
	}



	public Stack<IntegerSingleNode> getNotEnglishWord() {
		return notEnglishWord;
	}



	
	
	
}
