package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;



import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public abstract class Rule {

	protected boolean subj = false;
	
	protected boolean obj = false;
	
	protected TripleModel model = null;
    
	protected DirectedMultiGraph<String, String> g ;
	
	public String verb; 
	
	public Rule(){
		
		
	} 
	
	protected abstract void filterSubj(Set<String> setEdge,DirectedMultiGraph<String, String> g);
	
	protected abstract void filterObJ(Set<String> setEdge,DirectedMultiGraph<String, String> g);

	public  boolean exstract(String verb,DirectedMultiGraph<String, String> g,TripleModel model){
		
		this.obj=false;
		
		this.subj=false;
		
		
		List<String> listEdge1=g.getIncomingEdges(verb);
		
		List<String> listEdge2=g.getOutgoingEdges(verb);
		
		listEdge1.addAll(listEdge2);
		
		Set<String>setEdge=ImmutableSet.copyOf(listEdge1);
		
		
		this.model = model;
		
		this.verb=verb;
		this.g=g;

		this.filterObJ(setEdge, g);

		this.filterSubj(setEdge, g);

		return this.obj && this.subj ? true : false;
		
		
	}
	
	public TripleModel getTriple(){return model;};
	
}
