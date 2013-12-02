package edu.southampton.wais.STPLibrary.stanfordWrapper;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
<<<<<<< HEAD
import edu.southampton.wais.STPLibrary.processor.ExtractTripleStanfordDependancy;
import edu.southampton.wais.STPLibrary.stanfordRule.Rule;

import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule1;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule10;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule2;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule3;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule5;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule8;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule9;
=======
>>>>>>> cc73574d1b0479907502be711d88813895a2c69e
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.datastructure.IntegerSingleNode;
import edu.southampton.wais.utility.general.IOFileUtility;
import edu.southampton.wais.utility.general.Logger;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
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

		int index = -1;

		for (List<CoreLabel> lcl : classifier.classify(sm.getBody())) {
			for (CoreLabel cl : lcl) {
				index++;
				
				String ne = cl.get(CoreAnnotations.AnswerAnnotation.class);
				
				
				Logger.logFiner(index+" "+cl.word()+" "+ne);
				
				
				

				sm.addNer(index, ne);

			}
		}

		index = -1;

		for (CoreMap sentence : sentencesCoreMap) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods

			SemanticGraph dependencies = sentence
					.get(CollapsedCCProcessedDependenciesAnnotation.class);

			
			
			
			for (SemanticGraphEdge edge :dependencies.edgeIterable() ) {

				/*
				 * System.out.println(edge);
				 * System.out.println(edge.getGovernor().originalText());
				 * System.out.println(edge.getDependent().originalText());
				 */

				sm.addNodeGraph(edge.getGovernor()
						.toString(), edge.getDependent().toString(),edge.toString());

				
				sm.addTuple(edge.toString(), edge.getGovernor().toString(), edge.getDependent().toString());
				
			}

			// System.out.println(table);

			// SemanticGraphFormatter formatter= new
			// SemanticGraphFormatter(5,5,true,true,true,true,true);

			// String s=formatter.formatSemanticGraph(dependencies);

			// System.out.println(s);

			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

				index++;

				// this is the text of the token
				String word = token.get(TextAnnotation.class).toLowerCase();
				// this is the POS tag of the token
				String pos = token.get(PartOfSpeechAnnotation.class);

				String lemma = token.get(LemmaAnnotation.class);

				
				
				
				sm.addLemma(index, lemma);

				sm.addPOSWord(index, pos);

				sm.addWord(new IntegerSingleNode(word, index));

				sm.addWordLemma(new IntegerSingleNode(word, index));

				sm.addValidWord(index, true);
				
				
				
				if (POSTagStanford.isAdjective(pos)) {

					sm.addPOSNormaLized(index, POSTagStanford.ADJ);
					sm.addStackAdj(new IntegerSingleNode(word, index));
					sm.addMultiMapPOS(POSTagStanford.ADJ, index);
				}

				else if (POSTagStanford.isNoun(pos)) {

					sm.addPOSNormaLized(index, POSTagStanford.NOUN);
					sm.addStackNoun(new IntegerSingleNode(word, index));
					sm.addMultiMapPOS(POSTagStanford.NOUN, index);

				}

				else if (POSTagStanford.isVerb(pos)) {

					sm.addPOSNormaLized(index, POSTagStanford.VERB);
					sm.addStackVerb(new IntegerSingleNode(word, index));
					sm.addMultiMapPOS(POSTagStanford.VERB, index);

				}

				else if (POSTagStanford.isAdverb(pos)) {

					sm.addPOSNormaLized(index, POSTagStanford.ADVERB);
					sm.addStackAdverb(new IntegerSingleNode(word, index));
					sm.addMultiMapPOS(POSTagStanford.ADVERB, index);

					if (word.equals("n't") || word.equals("not")) {

						sm.addStackNegation(new IntegerSingleNode("not", index));

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
		
		
		//tex="Jihad remains the life of Islam and the main factor of change";
		
		
		tex="What she said makes sense";
		
		
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

			System.out.println(sm.getStackadj());
			System.out.println(sm.getStackadverb());
			System.out.println(sm.getStacknegation());

			System.out.println(sm.getStacknoun());
			System.out.println(sm.getStackverb());
			System.out.println(sm.getPosWord());
			System.out.println(sm.getPosNormalizeWord());

			System.out.println(sm.getIsValidWord());

			System.out.println(sm.getWordCorrected());

			System.out.println(sm.getWordList());

			System.out.println(sm.getNer());

			System.out.println(sm.getNerMultiMap());

			
			System.out.println(sm.getTableDepGovern());
			
			System.out.println(" ---------------");


			
			System.out.println(sm.getGraph());
			
<<<<<<< HEAD
			Rule rule5= new TripleRule8();
			
			
			List<TripleModel>triple=ExtractTripleStanfordDependancy.extract(sm,rule5);
=======
>>>>>>> cc73574d1b0479907502be711d88813895a2c69e
			

			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
