package edu.southampton.wais.STPLibrary.stanfordWrapper;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.google.common.collect.ListMultimap;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
import edu.southampton.wais.STPLibrary.stanfordRule.Rule;

import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.datastructure.IntegerSingleNode;
import edu.southampton.wais.utility.datastructure.SingleNode;
import edu.southampton.wais.utility.general.IOFileUtility;
import edu.southampton.wais.utility.general.Logger;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.IndexAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

public class StanfordCoreNLPService {

	
	
	private static final String relativePathModel="coreNlp-3.2.0"+File.separator+"models";
	
	private StanfordCoreNLP pipeline;

	private AbstractSequenceClassifier<CoreLabel> classifier;

	private StanfordCoreNLPService(StanfordCoreNLP pipeline,
			AbstractSequenceClassifier<CoreLabel> classifier) {

		this.pipeline = pipeline;
		this.classifier = classifier;

	}

	
	
	
	/**
	 * 
	 * 
	 * @param args
	 */

	
	
	
	
	public static StanfordCoreNLPService StanfordCoreNLPService(String dir) {

		Properties props = new Properties();

		props.put("annotators", "tokenize, ssplit, pos, lemma, parse");

		props.put("parse.model", dir + File.separator+relativePathModel+File.separator
				+ "englishPCFG.ser.gz");

		props.put("pos.model", dir + File.separator+relativePathModel+File.separator
				+ "english-bidirectional-distsim.tagger");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier
				.getClassifierNoExceptions(dir + File.separator+relativePathModel+File.separator
						+ "english.all.3class.distsim.crf.ser.gz");

		return new StanfordCoreNLPService(pipeline, classifier);

	}

	
	
	
	
	public void buildAnnotation4Sentence(SentenceModel sm) throws Exception {

		// create an empty Annotation just with the given text
		Annotation document = new Annotation(sm.getBody());

		// run all Annotators on this text
		pipeline.annotate(document);

		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and
		// has values with custom types
		List<CoreMap> sentencesCoreMap = document
				.get(SentencesAnnotation.class);
		
		
		this.processTags(sentencesCoreMap, sm);
		this.processNerTag(sentencesCoreMap, sm);
		this.processDependacies(sentencesCoreMap, sm);
		

	
	}
	
	
	
	
	
	
	
	private void processDependacies(List<CoreMap> sentencesCoreMap, SentenceModel sm){
		
		
		
		
		for (CoreMap sentence : sentencesCoreMap) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods

			SemanticGraph dependencies = sentence
					.get(CollapsedCCProcessedDependenciesAnnotation.class);

			
			
			
			for (SemanticGraphEdge edge :dependencies.edgeIterable() ) {

				
				
				SingleNode<Integer,String> gov= new SingleNode<Integer,String>(edge.getGovernor().index(),edge.getGovernor().toString());
				
				SingleNode<Integer,String> dep= new SingleNode<Integer,String>(edge.getDependent().index(),edge.getDependent().toString());
				
				sm.addNodeGraph(gov,dep,edge.toString());
				
				sm.addTuple(edge.toString(), gov, dep);
				
								
				
			}

		
		}
		
		
	}
	
	
	
	
	
	
	private  void processNerTag(List<CoreMap> sentencesCoreMap,SentenceModel sm){
		
		int index=0;
		
		for (List<CoreLabel> lcl : classifier.classify(sm.getBody())) {
			
			
			
			for (CoreLabel cl : lcl) {
			
				
				index++;
				
				String ne = cl.get(CoreAnnotations.AnswerAnnotation.class);
				
				
				
				sm.addNer(index,ne);
				
				
				
				
				

				

			}
		}

		
		
		
		
	}
	
	
	
	private  void processTags(List<CoreMap> sentencesCoreMap,SentenceModel sm){
		
		int index=0;
		for (CoreMap sentence : sentencesCoreMap) {
		
			// traversing the words in the current sentence
		
			
		 for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

			index++;
			// this is the text of the token
			String word = token.get(TextAnnotation.class).toLowerCase();
			// this is the POS tag of the token
			String pos = token.get(PartOfSpeechAnnotation.class);

			String lemma = token.get(LemmaAnnotation.class);

			
			
			
			
			
			
			
			
			sm.addLemma(index, lemma);

			sm.addPOSWord(index, pos);

			sm.addValidWord(index, true);

			
			sm.addIndex2String(index, word);
			
			sm.addString2Index(word, index);
			
			
			
			if (POSTagStanford.isAdjective(pos)) {

				sm.addPOSNormaLized(index, POSTagStanford.ADJ);
		
				sm.addMultiMapPOS(POSTagStanford.ADJ, index);
			}

			else if (POSTagStanford.isNoun(pos)) {

				sm.addPOSNormaLized(index, POSTagStanford.NOUN);
				sm.addMultiMapPOS(POSTagStanford.NOUN, index);

			}

			else if (POSTagStanford.isVerb(pos)) {

				sm.addPOSNormaLized(index, POSTagStanford.VERB);
				sm.addMultiMapPOS(POSTagStanford.VERB, index);

			}

			else if (POSTagStanford.isAdverb(pos)) {

				sm.addPOSNormaLized(index, POSTagStanford.ADVERB);
				sm.addMultiMapPOS(POSTagStanford.ADVERB, index);

				if (word.equals("n't") || word.equals("not")) {

					sm.addNegation(index, true);

				}

			}
			
			else {

				sm.addPOSNormaLized(index, POSTagStanford.OTHER);
				sm.addMultiMapPOS(POSTagStanford.OTHER, index);

			}
		 }
	}
	
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String tex = "Nobody  doesn't 2,800 years of the existence of a city that grew from a small Italian village in the 9th century BCE into the centre of a vast civilisation that dominated the Mediterranean region for centuries";
		
		tex="Bell, based in Los Angeles, makes and distributes electronic, computer and building products";
		
		//tex="It can expand each surah as a coherent discourse, arranging surahs into pairs, and establishing seven major surah divisions - the entire Quran thus emerges as a well-connected and systematic book";
		
		tex="To accept the idea of pluralism means that you do not care much about religion";
		
//		try {
//	    tex=FileUtils.readFileToString(new File("prova.txt"));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		

		tex="Jihad  change remains the life of Islam and the main factor of change";
		//tex="Jihad remains the life of Islam and the main factor of change";
		
		
		
		String dir = "/Users/antoniopenta/Documents/workspaceReligionSentiment/nlpdata";

		
		dir="/home/antoniodesktop/Documents/data/nlpdata";

		Pattern patternSentValidation = Pattern.compile("[a-zA-Z\\-]+");

		
		StanfordCoreNLPService services = StanfordCoreNLPService
				.StanfordCoreNLPService(dir);

		
		try {


			
			StringProcessor stringProcessor= new StringProcessor();
			
			String nameFileStopList1 = dir + File.separator
					+ "StopWordsLite.txt";

			HashSet<String> listStop = IOFileUtility
					.readHashSetStringFromFile(new File(nameFileStopList1));

			
			
			List<String>texL=stringProcessor.cleanSentence(tex, patternSentValidation);
			
			
			
			SentenceModel sm = new SentenceModel(Joiner.on(" ").join(texL));

			
			services.buildAnnotation4Sentence(sm);

		
			
            SentenceModelUtility.invalidatedStopListTerm(sm, listStop);
            
           
            boolean valid=SentenceModelUtility.countMeaningfulTerms(sm, 3);
			

            
            System.out.println(sm.getBody());
            
            System.out.println(valid);
			

			System.out.println(sm.getLemma());
			System.out.println(sm.getPosNormMultiMap());

			System.out.println(sm.getPosWord());
			System.out.println(sm.getPosNormalizeWord());

			System.out.println(sm.getIsValidWord());

			System.out.println(sm.getWordCorrected());

			System.out.println(sm.getWordList());

			System.out.println(sm.getNer());

			System.out.println(sm.getNerMultiMap());

			
			System.out.println(sm.getTableDepGovern());
			
			
			System.out.println(sm.getIndex2StringMap());
			System.out.println(sm.getString2IndexMultimap());
			
			System.out.println(" ---------------");


			
			System.out.println(sm.getGraph());
			
			
			

			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
