package edu.southampton.wais.STPLibrary.stanfordRule;


import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import com.google.common.base.Objects;


public class RuleIstance {

	
	private DirectedGraph<RuleIstanceNode, RuleIstanceEdge> graph;
	
	private int numberNode;
	private int tempNumberNode;
	
	public RuleIstance(int numberNode){
	
	this.numberNode=numberNode;
	this.tempNumberNode=numberNode;
	
	graph=new DefaultDirectedGraph<RuleIstanceNode, RuleIstanceEdge>(RuleIstanceEdge.class);
	
	
	}
	

	
	public void addNode(RuleIstanceNode node) throws RuleException{
		
		if(tempNumberNode==0){
			
			 throw new RuleException("insert node is not allowed, there are already node :"+this.numberNode);
		}
		this.graph.addVertex(node);
		this.tempNumberNode--;
		
	}
	
   public void addDirectedEdge(RuleIstanceNode nodea,RuleIstanceNode nodeb,RuleIstanceEdge edge) throws RuleException{
		
	   
	   if(!this.graph.containsVertex(nodea)||!this.graph.containsVertex(nodeb)){
		  throw new RuleException("there are not nodes for this edge"); 
	   }
	   
	   this.graph.addEdge(nodea, nodeb,edge);
	  
		
	}
	
   
   public int getNumberNodes(){
	   
	   return this.graph.vertexSet().size();
   }



public Set<RuleIstanceNode> getNodes() {
	// TODO Auto-generated method stub
	return this.graph.vertexSet();
}



public boolean hasEdge(RuleIstanceNode node1, RuleIstanceNode node2) {
	// TODO Auto-generated method stub
	return this.graph.containsEdge(node1, node2);
}



public RuleIstanceEdge getEdge(
		RuleIstanceNode node1, RuleIstanceNode node2) {
	// TODO Auto-generated method stub
	return this.graph.getEdge(node1, node2);
}



public String getNodeValueByID(Integer id) throws RuleException {
	// TODO Auto-generated method stub
	
	
	for(RuleIstanceNode node:this.graph.vertexSet()){
		
		  if(node.getId()==id){
			  return node.getValue();
		  }
		
	}
	
	
	 throw new RuleException("there are not nodes for this edge");  
}
   
public boolean isNodeValueByID(Integer id)  {
	// TODO Auto-generated method stub
	
	
	for(RuleIstanceNode node:this.graph.vertexSet()){
		
		  if(node.getId()==id){
			  return true;
		  }
		
	}
	
	
	return false;
}

@Override
public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("{\n");
    s.append("Node:\n");
    for (RuleIstanceNode node : this.graph.vertexSet()) {
      s.append("  ").append(node.toString()).append('\n');
    }
    s.append("Edges:\n");
    for (RuleIstanceNode source : this.graph.vertexSet()) {
    	
    
    	for (RuleIstanceNode dest : this.graph.vertexSet()) {
    	    
    		if(this.graph.containsEdge(source, dest)){
    			 
    			 s.append("  ").append(source.getId()).append(" -> ").append(dest.getId()).append(" : ").append(this.graph.getEdge(source, dest)).append('\n');
    		
    		     }
    		}
    		
    	
    }
    
     
    s.append('}');
    return s.toString();
  }
	
}
