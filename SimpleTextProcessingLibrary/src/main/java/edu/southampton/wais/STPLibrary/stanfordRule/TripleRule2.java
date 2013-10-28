package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;

import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

import edu.stanford.nlp.graph.DirectedMultiGraph;

public class TripleRule2 extends Rule {

	private enum Triple2_XYZ_XNoun_ZNoun {

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
	protected void extractSubj(Set<String> set) {
		// TODO Auto-generated method stub

		Set<String> setVertex = g.getAllVertices();

		for (String item : setVertex) {

			List<String> edges = g.getEdges(verb, item);

			if (edges.contains(Triple2_XYZ_XNoun_ZNoun.SUB.toString())) {

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

			if (edges.contains(Triple2_XYZ_XNoun_ZNoun.OBJ.toString())) {

				String[] itemSplit = item.split("-");

				if (POSTagStanford.isNoun(itemSplit[1])) {

					set.add(item);

				}

			}

		}

	}
}
