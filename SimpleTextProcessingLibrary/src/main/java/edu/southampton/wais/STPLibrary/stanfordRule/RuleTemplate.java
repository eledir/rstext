package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import edu.southampton.wais.STPLibrary.stanfordRule.RuleTemplateNode.RuleTemplateNodeEnum;

public class RuleTemplate {

	
	private String id;
	
	private String name;
	
	private int numberNode;
	
	private int updateNumberNode;


	
	private Multimap<String,Integer> mapRole;
	
	
	DirectedGraph<RuleTemplateNode, RuleTemplateEdge> graph;

	
	public RuleTemplate(String id) {
		
		super();
		
		this.id=id;
		
		graph=new DefaultDirectedGraph<RuleTemplateNode, RuleTemplateEdge>(RuleTemplateEdge.class);
		
		mapRole=ArrayListMultimap.create();
		
		
	}

	
	
	
	public void addRole(String role,Integer ...ruleTemplateNodeId){
		
		this.mapRole.putAll(role, Lists.newArrayList(ruleTemplateNodeId));
		
	}
	
	
	
 public void addRole(String role,List<Integer> ruleTemplateNodeIdList){
		
		this.mapRole.putAll(role, ruleTemplateNodeIdList);
		
	}
	
	
	
	public void addRuleNode(RuleTemplateNode node) throws RuleException{
		
		
		if(updateNumberNode==0){throw new RuleException("inserting node not allowed,please update number of nodes");}
		
		this.graph.addVertex(node);
		
		this.updateNumberNode--;
		 
		
		
		
	}
	
	
	public boolean addDirectedEdge(RuleTemplateNode a,RuleTemplateNode b, RuleTemplateEdge c) throws RuleException{
		
		
		if(!this.graph.containsVertex(a)){
			
			throw new RuleException("inserting edge not allowed, there is no rule node");
			
		}
		if(!this.graph.containsVertex(b)){
			
			throw new RuleException("inserting edge not allowed,there is no rule node");
		}
		
		return this.graph.addEdge(a, b, c);
		
	}
	
	
	
	
	
	
	public int getNumberNode() {
		return numberNode;
	}


	public void setNumberNode(int numberNode) {
		this.numberNode = numberNode;
		this.updateNumberNode=numberNode;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	  public String toString() {
	    StringBuilder s = new StringBuilder();
	    s.append("{\n");
	    s.append("Node:\n");
	    for (RuleTemplateNode node : this.graph.vertexSet()) {
	      s.append("  ").append(node.toString()).append('\n');
	    }
	    s.append("Edges:\n");
	    for (RuleTemplateNode source : this.graph.vertexSet()) {
	    	
	    
	    	for (RuleTemplateNode dest : this.graph.vertexSet()) {
	    	    
	    		if(this.graph.containsEdge(source, dest)){
	    			 
	    			 s.append("  ").append(source.getId()).append(" -> ").append(dest.getId()).append(" : ").append(this.graph.getEdge(source, dest)).append('\n');
	    		
	    		     }
	    		}
	    		
	    	
	    }
	    
	    
	   s.append("Role in Rule :\n").append(this.mapRole.toString()).append("\n");
	    
	    s.append('}');
	    return s.toString();
	  }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleTemplate other = (RuleTemplate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


		
		
		
	public boolean searchRuleTemplateNodeById(int id,  RuleTemplateNode nodeOut){
		
		
		for(RuleTemplateNode node : this.graph.vertexSet()){
			
			 if(node.getId()==id){
				 
				 nodeOut=node;
				 return true;
				 
			 }
			
			
		}
		
		return false;
		
	}




	public boolean isRuleTemplateNode(Integer id) {
		
		
		for(RuleTemplateNode node : this.graph.vertexSet()){
			
			 if(node.getId()==id){
				 
				 
				 return true;
				 
			 }
			
			
		}
		
		return false;
		
		
	}




	public List<Integer> getSubjID() {
		// TODO Auto-generated method stub
		
		return Lists.newArrayList(this.mapRole.get(RuleTemplateNodeEnum.SubjRole.toString()));
		
		
	}




	public List<Integer> getObjID() {
		// TODO Auto-generated method stub

		return Lists.newArrayList(this.mapRole.get(RuleTemplateNodeEnum.ObjRole.toString()));
		
	
	}




	public List<Integer> getVerbID() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(this.mapRole.get(RuleTemplateNodeEnum.VerbRole.toString()));
		
	}	
		
	

	
	
	
	
	
}
