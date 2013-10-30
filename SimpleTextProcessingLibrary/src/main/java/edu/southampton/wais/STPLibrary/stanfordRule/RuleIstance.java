package edu.southampton.wais.STPLibrary.stanfordRule;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class RuleIstance {

	
	private DirectedGraph<String, RuleEdge> graph;
	
	private int numberNode;
	private int tempNumberNode;
	
	public RuleIstance(int numberNode){
	
	this.numberNode=numberNode;
	this.tempNumberNode=numberNode;
	 graph=new DefaultDirectedGraph<String, RuleEdge>(RuleEdge.class);
	
	
	}
	

	
	public void addNode(String node) throws RuleException{
		
		if(tempNumberNode==0){
			
			 throw new RuleException("insert node is not allowed, there are already node :"+this.numberNode);
		}
		this.graph.addVertex(node);
		this.tempNumberNode--;
		
	}
	
   public void addDirectedEdge(String nodea,String nodeb,RuleEdge edge) throws RuleException{
		
	   
	   if(!this.graph.containsVertex(nodea)||!this.graph.containsVertex(nodeb)){
		  throw new RuleException("there are not nodes for this edge"); 
	   }
	   
	   this.graph.addEdge(nodea, nodeb,edge);
		
		
		
	}
	
   
   public int getNumberNodes(){
	   
	   return this.graph.vertexSet().size();
   }
   
	
}
