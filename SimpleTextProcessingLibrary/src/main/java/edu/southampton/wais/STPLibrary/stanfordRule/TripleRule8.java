package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;



import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class TripleRule8 extends Rule {

	private enum Triple8_XYZ_XNoun_ZVerb_ZADJ {

		SUB {
			public String toString() {
				return "nsubj";
			}
		},

		OBJ {
			public String toString() {
				return "csubj";
			}
		},

	};



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public TripleRule8() {
		super();
	}

	@Override
	protected void extractSubj(Set<String> set) {
		// TODO Auto-generated method stub

		Set<String> setVertex = g.getAllVertices();

		for (String item : setVertex) {

			List<String> edges = g.getEdges(verb, item);

			if (edges.contains(Triple8_XYZ_XNoun_ZVerb_ZADJ.SUB.toString())) {

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

			List<String> edges = g.getEdges(item,verb);

			if (edges.contains(Triple8_XYZ_XNoun_ZVerb_ZADJ.OBJ.toString())) {

				String[] itemSplit = item.split("-");

				if (POSTagStanford.isVerb(itemSplit[1])||POSTagStanford.isAdjective(itemSplit[1])) {

					set.add(item);

				}

			}

		}

	}

	

	

}
