package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;




import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.STPLibrary.utility.DirectedMultigraphUtility;


public class TripleRule12 extends Rule {

	private enum Sub_Triple12_XYZ_XNoun_ZNoun_ZAdj_Z_Verb {

		SUB {
			public String toString() {
				return "nsubj";
			}
		},

	};
	
	
	
	private Set<String>Obj_Triple12_XYZ_XNoun_ZNoun_ZADj_Z_Verb=Sets.newHashSet("acomp","dobj","acomp","comp");
			
			


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public TripleRule12() {
		super();
	}

	@Override
	protected void extractSubj(Set<String> set) {
		// TODO Auto-generated method stub

       Set<String> setVertex=g.getAllVertices();
		
		
		for(String item :setVertex){
			
			    
			List<String>edges=g.getEdges(verb, item);
			
			if(edges.size()>0){
			Set<String>candidate=DirectedMultigraphUtility.getTargetVertexGivenEdge(Sub_Triple12_XYZ_XNoun_ZNoun_ZAdj_Z_Verb.SUB.toString(), item, g);
			
			
			for(String item2:candidate){
				
				String[] itemSplit = item2.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])) {

					set.add(item);
					
				} 					
				
				
			}
			
			
			}
			
			
			
		}

	}

	@Override
	protected void extractObJ(Set<String> set) {
		// TODO Auto-generated method stub

        Set<String> setVertex=g.getAllVertices();
		
		
		for(String item :setVertex){
			
			    
			List<String>edges=g.getEdges(verb, item);
			
			Set<String>intS=Sets.intersection(ImmutableSet.copyOf(edges), Obj_Triple12_XYZ_XNoun_ZNoun_ZADj_Z_Verb);
			
			
			if(intS.size()>0){
				
				
				String[] itemSplit = item.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])||POSTagStanford.isAdjective(itemSplit[1])||POSTagStanford.isVerb(itemSplit[1])) {

					set.add(item);
					
				} 					
				
				
				
			}
			
			
			
		}

	}

	
	
}
