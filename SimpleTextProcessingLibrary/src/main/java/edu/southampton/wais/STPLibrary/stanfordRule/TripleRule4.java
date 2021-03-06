package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;



import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class TripleRule4 extends Rule {

	private enum Triple4_XYZ_XNoun_ZAdj {

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
	protected void extractSubj(Set<String> set) {
		// TODO Auto-generated method stub

		Set<String> setVertex = g.getAllVertices();

		for (String item : setVertex) {

			List<String> edges = g.getEdges(verb, item);

			if (edges.contains(Triple4_XYZ_XNoun_ZAdj.SUB.toString())) {

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

		Set<String> setVertex = g.getAllVertices();

		for (String item : setVertex) {

			List<String> edges = g.getEdges(verb, item);

			if (edges.contains(Triple4_XYZ_XNoun_ZAdj.OBJ.toString())) {

				String[] itemSplit = item.split("-");

				if (POSTagStanford.isAdjective(itemSplit[1])) {

					set.add(item);

				}

			}

		}

	}

	

	

}
