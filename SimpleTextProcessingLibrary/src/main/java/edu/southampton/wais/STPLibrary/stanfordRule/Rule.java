package edu.southampton.wais.STPLibrary.stanfordRule;


import java.util.List;
import java.util.Set;


import com.google.common.collect.Sets;



import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public abstract class Rule {

	
	
    
	protected DirectedMultiGraph<String, String> g ;
	
	public String verb; 
	
	public Rule(){
		
		
	} 
	
	protected abstract void extractSubj(Set<String>setSub);
	
	protected abstract void extractObJ(Set<String>setObj);

	public  void exstract(SentenceModel sm,String verb,DirectedMultiGraph<String, String> g,List<TripleModel> listModel){
		

		
		
		
		
		
		
		this.verb=verb;

		this.g=g;

		Set<String> setSub=Sets.newHashSet();
		
		Set<String> setObj=Sets.newHashSet();
		
		
		
		this.extractObJ(setObj);

		this.extractSubj(setSub);

		
		for (String itemS :setSub){
			
			for(String itemO:setObj)
			
				listModel.add(new TripleModel(sm, itemS,verb,itemO));
				
		}
			
		
		
		
		
		
		
	}
	
	
	
	
	
	
}
