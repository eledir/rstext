package edu.southampton.wais.STPLibrary.processor;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.jgrapht.DirectedGraph;

import cc.mallet.util.FileUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;

import com.google.common.collect.Lists;

import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
import edu.southampton.wais.STPLibrary.stanfordRule.Rule;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule1;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule2;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule3;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule4;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule5;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule6;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule7;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule8;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule9;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule10;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule11;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule12;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule13;


import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.general.IOFileUtility;
import edu.stanford.nlp.graph.DirectedMultiGraph;
public class ExtractTripleStanfordDependancy {


	
	
	
	
	
	
	public ExtractTripleStanfordDependancy(){
		
		
	}
	
	
	
	
	
	
	public static List<TripleModel> extract(SentenceModel sm,Rule r){
		
		
         DirectedMultiGraph<String, String> g=sm.getGraph();
		
		
		Set<String> setVertex=g.getAllVertices();
		
		
		Set<String> setVerbVertex=filterVerbVertex(setVertex);
		
		List<TripleModel> triples=Lists.newArrayList();
		
		
		for(String vertex:setVerbVertex){
			
			r.exstract(sm,vertex,g,triples);
			
			
		}
		
		return triples;
		
	}
	
	

	
	public static List<TripleModel> extract(SentenceModel sm,Set<Rule> setRule){
		
		
        DirectedMultiGraph<String, String> g=sm.getGraph();
		
		
		Set<String> setVertex=g.getAllVertices();
		
		
		Set<String> setVerbVertex=filterVerbVertex(setVertex);
		
		List<TripleModel> triples=Lists.newArrayList();
		
		
		for(String vertex:setVerbVertex){
			
			for (Rule r:setRule){
			    r.exstract(sm,vertex,g,triples);
			}
			
		}
		
		return triples;
		
	}
	
	
	
	
	public static  List<TripleModel> extend(SentenceModel sm,List<TripleModel> listModel){
		
		
		List<TripleModel>listModelExVerb=Lists.newArrayList();
		
		
		
		
		// first extend the verb
		
		for(TripleModel model:listModel){
			
			
			Set<String>verbEx=ExtendRuleProcessor.extracVerbByListExtend(model, sm.getGraph());
			
			for(String verb:verbEx){
			listModelExVerb.add(new TripleModel(sm, model.getSubj(),verb,model.getObjt()));
			}
			
		}
		
	
		// add previous triple
		listModelExVerb.addAll(listModel);
		
		
	
		// extend the subj
		
		List<TripleModel>listModelExSubj=Lists.newArrayList();
		
		
		for(TripleModel model:listModelExVerb){

		  
		  Set<String>subEx=ExtendRuleProcessor.extractSubjByListExtend(model, sm.getGraph());
		
		  
		  for(String subj:subEx){
			  
			  
			  
			  listModelExSubj.add(new TripleModel(sm,subj,model.getVerb(),model.getObjt()));
			  
			  
		  }
		
		
		
		}
		
		// add all the triple 
		listModelExSubj.addAll(listModelExVerb);
		
		
		
		// extend obj
		
		List<TripleModel>listModelExObj=Lists.newArrayList();
		
		for(TripleModel model:listModelExSubj){

			  
			 Set<String>objEx=ExtendRuleProcessor.extractObjByListExtend(model, sm.getGraph());
			
			  
			  for(String obj:objEx){
				  
				  
				  
				  listModelExObj.add(new TripleModel(sm,model.getSubj(),model.getVerb(),obj));
				  
				  
			  }
			
			
			
			}
			
		// add all the triple	
		listModelExObj.addAll(listModelExSubj);
			
		
		
		return listModelExObj;
		
		
		
					
					
					 
		
		
	}
	
	
	
	
	
	
	
	
	
	
	private static Set<String> filterVerbXY(Set<String> amongEdge,
			DirectedGraph<String, String> g) {
		
		Set<String> vertex=Sets.newHashSet();
		
		  for(String edge :amongEdge){
			  
			  String source=g.getEdgeSource(edge);
			  
			  String target=g.getEdgeTarget(edge);
			  
			  
			  String[] vSource=source.split("-");
			  
			  String[] vTarget=target.split("-");
			  
			  
			  try {
				
				  if(POSTagStanford.isVerb(vSource[1])){vertex.add(source);}
				  if(POSTagStanford.isVerb(vTarget[1])){vertex.add(source);}
					
				  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			  
			  
		  }
			  
			  
		  
		
		
		
		return vertex;
	}



	private static Set<String> filterNounVertex(Set<String> vertex){
		
		
		Set<String> filtered = Sets.filter(vertex, new Predicate<String>() {
		    @Override
		    public boolean apply(String p) {
		        
		    	String [] ps=p.split("-");
		    	
		    	try {
				
		    		return POSTagStanford.isNoun(ps[1]) ? true: false;
				
		    	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
		    }
		});
		
		
		return filtered;
		
	}
	
	
	
	
	
private static Set<String> filterVerbVertex(Set<String> vertex){
		
		
		Set<String> filtered = Sets.filter(vertex, new Predicate<String>() {
		    @Override
		    public boolean apply(String p) {
		        
		    	String [] ps=p.split("-");
		    	
		    	try {
				
		    		return POSTagStanford.isVerb(ps[1]) ? true: false;
				
		    	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
		    }
		});
		
		
		return filtered;
		
	}
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		 String dir = "/Users/antoniopenta/Documents/workspaceReligionSentiment/nlpdata";

			
			//dir="/home/antoniodesktop/Documents/data/nlpdata";

			Pattern patternSentValidation = Pattern.compile("[a-zA-Z\\-]+");

			
			StanfordCoreNLPService services = StanfordCoreNLPService
					.StanfordCoreNLPService(dir);

			
			try {


				
				StringProcessor stringProcessor= new StringProcessor();
				
				String nameFileStopList1 = dir + File.separator
						+ "StopWordsLite.txt";

				HashSet<String> listStop = IOFileUtility
						.readHashSetStringFromFile(new File(nameFileStopList1));

				
				String[] textVet=FileUtils.readFile(new File("/Users/antoniopenta/Documents/DatiRicerca/iextreme/collections/trainFiles/KM.txt"));
				
				for(String tex:textVet){
				
				List<String>texL=stringProcessor.cleanSentence(tex, patternSentValidation);
				
				
				
				SentenceModel sm = new SentenceModel(Joiner.on(" ").join(texL));

				
				services.buildAnnotation4Sentence(sm);

			
				
	            SentenceModelUtility.invalidatedStopListTerm(sm, listStop);
	            
	            boolean valid=SentenceModelUtility.countMeaningfulTerms(sm, 3);
				
				System.out.println(" ---------------");

	            
	            System.out.println(sm.getBody());
	            
	            System.out.println(valid);
				
	            System.out.println(sm.getGraph());
				

				System.out.println(" ---------------");


				
				Set<Rule>listRule=Sets.newHashSet();
				
				listRule.add(new TripleRule1());
				listRule.add(new TripleRule2());
				listRule.add(new TripleRule3());
				listRule.add(new TripleRule4());
				listRule.add(new TripleRule5());
				listRule.add(new TripleRule6());
				listRule.add(new TripleRule7());
				listRule.add(new TripleRule8());
				listRule.add(new TripleRule9());
				listRule.add(new TripleRule10());
				listRule.add(new TripleRule11());
				listRule.add(new TripleRule12());
				listRule.add(new TripleRule13());
				
				
				
				
				List<TripleModel>triple=ExtractTripleStanfordDependancy.extract(sm,listRule);
				
				List<TripleModel>tripleE=ExtractTripleStanfordDependancy.extend(sm, triple);
				
				System.out.println("######################");
				
				System.out.println(triple);

				System.out.println("00000000000000000000000000");
				
				
				System.out.println(tripleE);

				System.out.println("######################");
				
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		
		
		
		
		
		

	}

}
