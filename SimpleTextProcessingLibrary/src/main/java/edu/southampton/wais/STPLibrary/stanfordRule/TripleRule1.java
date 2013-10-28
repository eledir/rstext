package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;

import edu.stanford.nlp.graph.DirectedMultiGraph;

import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class TripleRule1 extends Rule {

	private enum Triple1_XYZ_XNoun_ZNoun {

		SUB {
			public String toString() {
				return "nsubj";
			}
		},

		OBJ {
			public String toString() {
				return "dobj";
			}
		},

	};


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public TripleRule1() {
		super();
	}

	@Override
	protected void extractSubj(Set<String>setSubj) {
		// TODO Auto-generated method stub


		Set<String> setVertex=g.getAllVertices();
		
		
		for(String item :setVertex){
			
			    
			List<String>edges=g.getEdges(verb, item);
			
			
			if(edges.contains(Triple1_XYZ_XNoun_ZNoun.SUB.toString())){
				
				
				String[] itemSplit = item.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])) {

					setSubj.add(item);
					
				} 					
				
				
				
			}
			
			
			
		}
		
		
		
	}


	@Override
	protected void extractObJ(Set<String>setObj) {
		// TODO Auto-generated method stub


Set<String> setVertex=g.getAllVertices();


for(String item :setVertex){
	
	    
	List<String>edges=g.getEdges(verb, item);
	
	
	if(edges.contains(Triple1_XYZ_XNoun_ZNoun.OBJ.toString())){
		
		
		String[] itemSplit = item.split("-");

		if (POSTagStanford.isNoun(itemSplit[1])) {

			setObj.add(item);
			
		} 					
		
		
		
	}
	
	
	
}

	}
	
}	


