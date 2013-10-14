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
public class ExtractTripleStanfordDependancy {


	
	
	
	
	
	
	public ExtractTripleStanfordDependancy(){
		
		
	}
	
	
	
	public static List<TripleModel> extract(SentenceModel sm){
		
		
		DirectedGraph<String, String> g=sm.getGraph();
		
		
		Set<String> vertex=g.vertexSet();
		
		Set<String> nounVertex=filterNounVertex(vertex);
		
		
		
		List<TripleModel> triples=Lists.newArrayList();
		
		
		for(String v1: nounVertex){
			
			for(String v2: nounVertex){
				
				
				System.out.println("v1 "+ v1);
				System.out.println("v2 "+ v2);
				
				
				Set<String> amongEdge=g.getAllEdges(v1, v2);
				
				System.out.println("edge "+ amongEdge);
				
				
				Set<String> verb=filterVerbXY(amongEdge,g);
				
				System.out.println("edge filter "+ verb);
				
				
				if(verb.size()==1){
					
					
					
					triples.add(new TripleModel(sm, v1, v2, verb.iterator().next()));
					
					
				}
				
				
			}
		}
		
		
		return triples;
		
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
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
