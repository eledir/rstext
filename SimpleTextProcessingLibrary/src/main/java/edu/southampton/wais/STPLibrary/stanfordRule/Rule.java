package edu.southampton.wais.STPLibrary.stanfordRule;


import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;





import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.utility.datastructure.SingleNode;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public abstract class Rule {

	
	
    
	protected DirectedMultiGraph<String, String> g ;
	
	public SingleNode<Integer,String> verb; 
	
	public Rule(){
		
		
	} 
	
	protected abstract void extractSubj(Set<SingleNode<Integer,String>>setSub);
	
	protected abstract void extractObJ(Set<SingleNode<Integer,String>>setObj);

	public  void exstract(SentenceModel sm,SingleNode<Integer,String> verb,DirectedMultiGraph<String, String> g,List<TripleModel> listModel){
		

		
		
		
		
		
		
		this.verb=verb;

		this.g=g;

		Set<SingleNode<Integer,String>> setSub=Sets.newHashSet();
		
		Set<SingleNode<Integer,String>> setObj=Sets.newHashSet();
		
		
		
		this.extractObJ(setObj);

		this.extractSubj(setSub);

		
		for (SingleNode<Integer,String> itemS :setSub){
			
			for(SingleNode<Integer,String> itemO:setObj)
			
				listModel.add(new TripleModel(sm, itemS,verb,itemO));
				
		}
			
		
		
		
		
		
		
	}
	
	
	
	
	
	
}
