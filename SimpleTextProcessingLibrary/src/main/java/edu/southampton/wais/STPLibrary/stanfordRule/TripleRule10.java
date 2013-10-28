package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;




import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;


public class TripleRule10 extends Rule {

	private enum Sub_Triple10_XYZ_XNoun_ZNoun_ZAdj {

		SUB {
			public String toString() {
				return "nsubj";
			}
		},

	};
	
	
	
	private Set<String>Obj_Triple10_XYZ_XNoun_ZNoun_ZA=Sets.newHashSet("prep_in","prep_of","prep_about",
			"prep_for","prep_than","prep_as","prep_far_from","prep_away_from","prep_around");
			
			


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public TripleRule10() {
		super();
	}

	@Override
	protected void extractSubj(Set<String> set) {
		// TODO Auto-generated method stub

       Set<String> setVertex=g.getAllVertices();
		
		
		for(String item :setVertex){
			
			    
			List<String>edges=g.getEdges(verb, item);
			
			
			if(edges.contains(Sub_Triple10_XYZ_XNoun_ZNoun_ZAdj.SUB.toString())){
				
				
				String[] itemSplit = item.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])) {

					set.add(item);
					
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
			
			Set<String>intS=Sets.intersection(ImmutableSet.copyOf(edges), this.Obj_Triple10_XYZ_XNoun_ZNoun_ZA);
			
			
			if(intS.size()>0){
				
				
				String[] itemSplit = item.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])||POSTagStanford.isAdjective(itemSplit[1])||POSTagStanford.isVerb(itemSplit[1])) {

					set.add(item);
					
				} 					
				
				
				
			}
			
			
			
		}

	}

	
	
}
