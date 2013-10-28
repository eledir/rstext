package edu.southampton.wais.STPLibrary.processor;

import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;

import com.google.common.base.Predicate;

import com.google.common.collect.Lists;

import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.STPLibrary.stanfordRule.Rule;
import edu.stanford.nlp.graph.DirectedMultiGraph;
public class ExtractTripleStanfordDependancy {


	
	
	
	
	
	
	public ExtractTripleStanfordDependancy(){
		
		
	}
	
	
	
	/*public static List<TripleModel> extract(SentenceModel sm){
		
		
		DirectedGraph<String, String> g=sm.getGraph();
		
		
		Set<String> vertex=g.vertexSet();
		
		Set<String> nounVertex=filterNounVertex(vertex);
		
		
		
		List<TripleModel> triples=Lists.newArrayList();
		
		
		for(String v1: nounVertex){
			
			for(String v2: nounVertex){
				
				
				Logger.logFiner("v1 "+ v1);
				Logger.logFiner("v2 "+ v2);
				
				
				Set<String> amongEdge=g.getAllEdges(v1, v2);
				
				Logger.logFiner("edge "+ amongEdge);
				
				
				Set<String> verb=filterVerbXY(amongEdge,g);
				
				Logger.logFiner("edge filter "+ verb);
				
				
				if(verb.size()==1){
					
					
					
					triples.add(new TripleModel(sm, v1, v2, verb.iterator().next()));
					
					
				}
				
				
			}
		}
		
		
		return triples;
		
	}*/
	
	
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
	
	
/*public static List<TripleModel> extract(SentenceModel sm){
		
	
	    Rule rule1= new TripleRule1();
	
	
	
		
		DirectedGraph<String, String> g=sm.getGraph();
		
		
		Set<String> setVertex=g.vertexSet();
		
		
		Set<String> setVerbVertex=filterVerbVertex(setVertex);
		
		List<TripleModel> triples=Lists.newArrayList();
		
		
		for(String vertex:setVerbVertex){
			
			
		Set<String> setEdge=g.edgesOf(vertex);
			
		
		 if(rule1.exstract(setEdge, new TripleModel(sm, vertex))){
			 
			 triples.add(rule1.getTriple());
		 }
		
		 
		
		 
		 
		 
			
		}
		
		
		for(String v1: nounVertex){
			
			for(String v2: nounVertex){
				
				
				Logger.logFiner("v1 "+ v1);
				Logger.logFiner("v2 "+ v2);
				
				
				Set<String> amongEdge=g.getAllEdges(v1, v2);
				
				Logger.logFiner("edge "+ amongEdge);
				
				
				Set<String> verb=filterVerbXY(amongEdge,g);
				
				Logger.logFiner("edge filter "+ verb);
				
				
				if(verb.size()==1){
					
					
					
					triples.add(new TripleModel(sm, v1, v2, verb.iterator().next()));
					
					
				}
				
				
			}
		}
		
		
		return triples;
		
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
		// TODO Auto-generated method stub

	}

}
