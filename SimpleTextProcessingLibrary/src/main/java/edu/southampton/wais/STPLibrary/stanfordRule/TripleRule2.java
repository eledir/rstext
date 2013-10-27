package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.Set;


import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public class TripleRule2 extends Rule {

	public enum Triple2_XYZ_XNoun_ZNoun {

		SUB {
			public String toString() {
				return "agent";
			}
		},

		OBJ {
			public String toString() {
				return "nsubjpass";
			}
		},

	};

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public TripleRule2() {
		super();
	}

	@Override
	protected void filterSubj(Set<String> set, DirectedMultiGraph<String, String> g) {
		// TODO Auto-generated method stub

		for (String item : set) {

			if (item.equals(Triple2_XYZ_XNoun_ZNoun.SUB.toString())) {

				String subj = g.getEdgeTarget(item);

				String[] itemSplit = subj.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])) {

					this.model.setSubj(subj);
					this.subj = true;
					break;
				}
			}

		}

	}

	@Override
	protected void filterObJ(Set<String> set, DirectedMultiGraph<String, String> g) {
		// TODO Auto-generated method stub

		for (String item : set) {

			if (item.equals(Triple2_XYZ_XNoun_ZNoun.OBJ.toString())) {

				String obj = g.getEdgeTarget(item);

				String[] itemSplit = obj.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])) {

					this.model.setObjt(obj);
					this.obj = true;
					break;
				}

			}

		}

	}

	



}
