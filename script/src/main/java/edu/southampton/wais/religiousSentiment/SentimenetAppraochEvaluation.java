package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import edu.southampton.wais.STPLibrary.sentiment.SentimentComputing;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class SentimenetAppraochEvaluation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Properties props = new Properties();

		props.put("annotators", "tokenize, ssplit, pos, lemma");

		String dir = "/home/antoniodesktop/Documents/data/nlpdata";

		File fnegative = new File(dir + File.separator+"opinion/negative.txt");

		File fpositive = new File(dir + File.separator+"opinion/positive.txt");

		
		
		props.put("parse.model", dir + File.separator
				+ "englishPCFG.caseless.ser.gz");
		props.put("pos.model", dir + File.separator
				+ "english-left3words-distsim.tagger");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		SentimenetAppraochEvaluation eval = new SentimenetAppraochEvaluation();

		SentimentComputing sentiment = SentimentComputing
				.loadSentiWordnet(new File(dir + File.separator+"SentiWordNet_3.0.0_20130122.txt"));

		try {
			List<String> listPositive = FileUtils.readLines(fpositive);

			List<String> listNegative = FileUtils.readLines(fnegative);

			 DescriptiveStatistics statp = new  DescriptiveStatistics();

			 Multimap<String,Double> myMultimapPsitive = ArrayListMultimap.create();
			
			 Multimap<String,Double> myMultimapNegative = ArrayListMultimap.create();
			 
			for (String itemp : listPositive) {

				String lemma = eval.lemmatizationString(itemp, pipeline);

				String vj = sentiment.extract(lemma, "v");

				String nj = sentiment.extract(lemma, "n");

				if (vj != null) {
				
					Double v=new Double(vj);
					
					statp.addValue(v);
					
					if(v.doubleValue()==0){
					
						myMultimapPsitive.put("neu", v);
					}
					
					else if(v.doubleValue()>0){ 
						
						myMultimapPsitive.put("pos", v);
					}
					
				   else{
					
						myMultimapPsitive.put("neg",v);
						
					   System.out.println(lemma);
					
					}
			       }
			    if (nj != null) {
					
					Double v=new Double(nj);
					
					statp.addValue(v);
				
					if(v.doubleValue()==0){
						
						myMultimapPsitive.put("neu", v);
					}
					
					else if(v.doubleValue()>0){ 
						
						myMultimapPsitive.put("pos", v);
					}
					
				   else{
					
						myMultimapPsitive.put("neg",v);
						
					   System.out.println(lemma);
			    
			    }

		}}

			System.out.println("----------------psitive--------------");
			
			System.out.println(statp);

			System.out.println("----------------negative--------------");
			
			
			SummaryStatistics statn = new SummaryStatistics();

			for (String itemn : listNegative) {

				String lemma = eval.lemmatizationString(itemn, pipeline);

				String vj = sentiment.extract(lemma, "v");

				String nj = sentiment.extract(lemma, "n");

				
				
				
				if (vj != null) {
					
					Double v=new Double(vj);
					
					statp.addValue(v);
					
					if(v.doubleValue()==0){
					
						myMultimapNegative.put("neu", v);
					}
					
					else if(v.doubleValue()>0){ 
						
						myMultimapNegative.put("pos", v);
				
						   System.out.println(lemma);
							
					}
					
				   else{
					
						myMultimapNegative.put("neg",v);
						
					
					}
				}
			    
				if (nj != null) {
					
					Double v=new Double(nj);
					
					statp.addValue(v);
				
					if(v.doubleValue()==0){
						
						myMultimapNegative.put("neu", v);
					}
					
					else if(v.doubleValue()>0){ 
						
						myMultimapNegative.put("pos", v);
				
						   System.out.println(lemma);
							
					}
					
				   else{
					
						myMultimapNegative.put("neg",v);
						
					
					}
			    
			    }
				
				
				
				
				
				
				

			}

				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			System.out.println(statn);
			
			
			System.out.println(myMultimapPsitive.get("neu").size());
			
			System.out.println(myMultimapPsitive.get("pos").size());
			System.out.println(myMultimapPsitive.get("neg").size());
			
			System.out.println(myMultimapPsitive.get("neu").size());
			
			System.out.println(myMultimapNegative.get("pos").size());
			System.out.println(myMultimapNegative.get("neg").size());
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * public List<String>lematization(List<String> list){
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	public String lemmatizationString(String s, StanfordCoreNLP pipeline) {

		Annotation document = new Annotation(s);

		// run all Annotators on this text
		pipeline.annotate(document);

		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and
		// has values with custom types
		List<CoreMap> sentencesCoreMap = document
				.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentencesCoreMap) {

			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

				s = token.get(LemmaAnnotation.class);

			}

		}

		return s;

	}

}
