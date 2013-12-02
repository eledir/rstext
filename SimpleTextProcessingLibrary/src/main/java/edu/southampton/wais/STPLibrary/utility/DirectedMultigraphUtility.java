package edu.southampton.wais.STPLibrary.utility;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import edu.southampton.wais.utility.datastructure.SingleNode;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public class DirectedMultigraphUtility {

	
	
	public static Set<SingleNode<Integer, String>> getTargetVertexGivenEdge(String edge,SingleNode<Integer, String> source,DirectedMultiGraph<SingleNode<Integer, String>, String> g ){
		
		
		Set<SingleNode<Integer, String>> result=Sets.newHashSet();
		
		Set<SingleNode<Integer, String>> n=g.getNeighbors(source);
		
		
		for(SingleNode<Integer, String> item:n){
			
			
			List<String> edgeList= g.getEdges(source, item);
			
			if(edgeList.contains(edge)){
				
				result.add(item);
				
			}
			
			
			
			
			
		}
		
		
		
		return result;
		
		
	}
	
	
	
	
public static Set<SingleNode<Integer, String>> getSourceVertexGivenEdge(String edge,SingleNode<Integer, String> target,DirectedMultiGraph<SingleNode<Integer, String>, String> g ){
		
		
		Set<SingleNode<Integer, String>> result=Sets.newHashSet();
		
		Set<SingleNode<Integer, String>> n=g.getNeighbors(target);
		
		
		for(SingleNode<Integer, String> item:n){
			
			
			List<String> edgeList= g.getEdges(item, target);
			
			if(edgeList.contains(edge)){
				
				result.add(item);
				
			}
			
			
			
			
			
		}
		
		
		
		return result;
		
		
	}
	
	
	
	
}
