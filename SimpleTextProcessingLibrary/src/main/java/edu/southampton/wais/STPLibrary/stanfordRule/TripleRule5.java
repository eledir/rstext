package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.Set;



import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule3.Triple3_XYZ_XNoun_ZNoun;
import edu.southampton.wais.STPLibrary.stanfordRule.TripleRule4.Triple4_XYZ_XNoun_ZAdj;
import edu.southampton.wais.utility.general.Logger;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public class TripleRule5 extends Rule {

	private enum Triple5_XYZ_XNoun_ZNoun {

		Sub1 {
			public String toString() {
				return "dobj";
			}
		},
		
		Sub2 {
			public String toString() {
				return "prep_of";
			}
		},
		

		Sub3 {
			public String toString() {
				return "prep_about";
			}
		},

		
		Obj1 {
			public String toString() {
				return "dobj";
			}
		},
		
		Obj2 {
			public String toString() {
				return "prep_of";
			}
		},
		

		Obj3 {
			public String toString() {
				return "prep_about";
			}
		},
		
				
		R1 {
			public String toString() {
				return "csubj";
			}
		},

		R2 {
			public String toString() {
				return "ccomp";
			}
		},

	};

	@Override
	protected void filterSubj(Set<String> setEdge,
			DirectedMultiGraph<String, String> g) {
		// TODO Auto-generated method stub

		for (String item : setEdge) {

			if (item.equals(Triple5_XYZ_XNoun_ZNoun.R1.toString())) {

				
				String r1 = g.getEdgeTarget(item);
		
				
				
				Set<String> edger1 = g.edgesOf(r1);

				boolean go1 = searchSubj(edger1,Triple5_XYZ_XNoun_ZNoun.Sub1.toString());
				boolean go2 = searchSubj(edger1,Triple5_XYZ_XNoun_ZNoun.Sub2.toString());
				boolean go3 = searchSubj(edger1,Triple5_XYZ_XNoun_ZNoun.Sub3.toString());
				

				if (go1 ||go2||go3) {
					break;
				}

			}
		}

	}

	private boolean searchSubj(Set<String> set,String edgeName) {

		for (String item : set) {

			
			
			
			if (item.equals(edgeName)) {

				String subj = g.getEdgeTarget(item);

				Logger.logFiner(subj);
				
				String[] itemSplit = subj.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])) {

					this.model.setSubj(item);
					this.subj = true;
					return true;
				}

			}
		}

		return false;
	}

	@Override
	protected void filterObJ(Set<String> setEdge,
			DirectedMultiGraph<String, String> g) {
		// TODO Auto-generated method stub

		
		
		
		for (String item : setEdge) {

			
			
			
			if (item.equals(Triple5_XYZ_XNoun_ZNoun.R2.toString())) {

				String r2 = g.getEdgeTarget(item);
				
				
				
				String newverb=r2.split("-")[0];
				
				this.model.setVerb(newverb);

				Set<String> edger2 = g.edgesOf(r2);

				boolean go1 = searchObj(edger2,Triple5_XYZ_XNoun_ZNoun.Obj1.toString());
				boolean go2 = searchObj(edger2,Triple5_XYZ_XNoun_ZNoun.Obj2.toString());
				boolean go3 = searchObj(edger2,Triple5_XYZ_XNoun_ZNoun.Obj3.toString());

		
				if (go1||go2||go3) {
					break;
				}

			}
		}

	}
		
		
		
		
		
	private boolean searchObj(Set<String> set,String edgeName) {
		// TODO Auto-generated method stub
		for (String item : set) {

			if (item.equals(edgeName)) {

				String obj = g.getEdgeTarget(item);

				String[] itemSplit = obj.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])) {

					this.model.setObjt(item);
					this.obj = true;
					return true;
				}

			}
		}
	return false;
	
	}





	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
