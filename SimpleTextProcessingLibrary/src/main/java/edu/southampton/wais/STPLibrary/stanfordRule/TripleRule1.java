package edu.southampton.wais.STPLibrary.stanfordRule;

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
	protected void filterSubj(Set<String> set, DirectedMultiGraph<String, String> g) {
		// TODO Auto-generated method stub

		for (String item : set) {

			if (item.equals(Triple1_XYZ_XNoun_ZNoun.SUB.toString())) {

				String subj = g.getEdgeTarget(item);

				String[] itemSplit = subj.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])) {

					super.model.setSubj(subj);
					super.subj = true;
					break;
				}
			}

		}

	}

	@Override
	protected void filterObJ(Set<String> set, DirectedMultiGraph<String, String> g) {
		// TODO Auto-generated method stub

		for (String item : set) {

			if (item.equals(Triple1_XYZ_XNoun_ZNoun.OBJ.toString())) {

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
