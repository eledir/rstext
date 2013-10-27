package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.Set;

import org.jgrapht.DirectedGraph;


import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class TripleRule4 extends Rule {

	public enum Triple4_XYZ_XNoun_ZAdj {

		SUB {
			public String toString() {
				return "nsubj";
			}
		},

		OBJ {
			public String toString() {
				return "acomp";
			}
		},

	};



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public TripleRule4() {
		super();
	}

	@Override
	protected void filterSubj(Set<String> set, DirectedMultiGraph<String, String> g) {
		// TODO Auto-generated method stub

		for (String item : set) {

			if (item.equals(Triple4_XYZ_XNoun_ZAdj.SUB.toString())) {

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

			if (item.equals(Triple4_XYZ_XNoun_ZAdj.OBJ.toString())) {

				String obj = g.getEdgeTarget(item);

				String[] itemSplit = obj.split("-");

				if (POSTagStanford.isAdjective(itemSplit[1])) {

					this.model.setObjt(obj);
					this.obj = true;
					break;
				}

			}

		}

	}

	

	

}
