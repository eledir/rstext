package edu.southampton.wais.STPLibrary.utility;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import edu.stanford.nlp.graph.DirectedMultiGraph;

public class DirectedMultigraphUtility {

	
	
	public static Set<String> getTargetVertexGivenEdge(String edge,String source,DirectedMultiGraph<String, String> g ){
		
		
		Set<String> result=Sets.newHashSet();
		
		Set<String> n=g.getNeighbors(source);
		
		
		for(String item:n){
			
			
			List<String> edgeList= g.getEdges(source, item);
			
			if(edgeList.contains(edge)){
				
				result.add(item);
				
			}
			
			
			
			
			
		}
		
		
		
		return result;
		
		
	}
	
	
	
	
public static Set<String> getSourceVertexGivenEdge(String edge,String target,DirectedMultiGraph<String, String> g ){
		
		
		Set<String> result=Sets.newHashSet();
		
		Set<String> n=g.getNeighbors(target);
		
		
		for(String item:n){
			
			
			List<String> edgeList= g.getEdges(item, target);
			
			if(edgeList.contains(edge)){
				
				result.add(item);
				
			}
			
			
			
			
			
		}
		
		
		
		return result;
		
		
	}
	
	
	
	
}
