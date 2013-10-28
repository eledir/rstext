package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

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

	}

	private ArrayListMultimap<String, String> verbsObject;
	private ArrayList<String> subjList;;

	public TripleRule5() {
		super();

	}

	@Override
	public void exstract(SentenceModel sm, String verb,
			DirectedMultiGraph<String, String> g, List<TripleModel> listModel) {

		verbsObject = ArrayListMultimap.create();
		subjList = Lists.newArrayList();

		this.verb = verb;

		this.g = g;

		Set<String> setSub = Sets.newHashSet();

		Set<String> setObj = Sets.newHashSet();

		this.extractObJ(setObj);

		this.extractSubj(setSub);

		for (String subKey : subjList) {

			for (String verbKey : verbsObject.keySet()) {

				Collection<String> listObj = verbsObject.get(verbKey);

				for (String objKey : listObj) {

					listModel.add(new TripleModel(sm, subKey, verbKey, objKey));

				}

			}

		}

	}

	@Override
	protected void extractSubj(Set<String> set) {
		// TODO Auto-generated method stub

		Set<String> setVertex = g.getAllVertices();

		List<String> verbs = Lists.newArrayList();

		for (String item : setVertex) {

			List<String> edges = g.getEdges(verb, item);

			if (edges.contains(Triple5_XYZ_XNoun_ZNoun.R2.toString())) {

				String[] itemSplit = item.split("-");

				if (POSTagStanford.isVerb(itemSplit[1])) {

					verbs.add(item);

				}

			}

		}

		for (String verb : verbs) {

			for (String vertex : setVertex) {

				List<String> edges = g.getEdges(verb, vertex);

				if (edges.contains(Triple5_XYZ_XNoun_ZNoun.Sub1.toString())) {

					String[] itemSplit = vertex.split("-");

					if (POSTagStanford.isNoun(itemSplit[1])) {

						verbsObject.put(verb, vertex);
					}

				} else if (edges.contains(Triple5_XYZ_XNoun_ZNoun.Sub2
						.toString())) {

					String[] itemSplit = vertex.split("-");

					if (POSTagStanford.isNoun(itemSplit[1])) {

						verbsObject.put(verb, vertex);
					}
				}

				else if (edges
						.contains(Triple5_XYZ_XNoun_ZNoun.Sub3.toString())) {

					String[] itemSplit = vertex.split("-");

					if (POSTagStanford.isNoun(itemSplit[1])) {

						verbsObject.put(verb, vertex);
					}
				}
			}

		}

	}

	@Override
	protected void extractObJ(Set<String> set) {
		// TODO Auto-generated method stub

		Set<String> setVertex = g.getAllVertices();

		List<String> verbs = Lists.newArrayList();

		for (String item : setVertex) {

			List<String> edges = g.getEdges(verb, item);

			if (edges.contains(Triple5_XYZ_XNoun_ZNoun.R1.toString())) {

				String[] itemSplit = item.split("-");

				if (POSTagStanford.isVerb(itemSplit[1])) {

					verbs.add(item);

				}

			}

		}

		for (String verb : verbs) {

			for (String vertex : setVertex) {

				List<String> edges = g.getEdges(verb, vertex);

				if (edges.contains(Triple5_XYZ_XNoun_ZNoun.Obj1.toString())) {
					
					String[] itemSplit = vertex.split("-");

					if (POSTagStanford.isNoun(itemSplit[1])) {
						subjList.add(vertex);
					}
				} else if (edges.contains(Triple5_XYZ_XNoun_ZNoun.Obj2
						.toString())) {
					String[] itemSplit = vertex.split("-");

					if (POSTagStanford.isNoun(itemSplit[1])) {
						subjList.add(vertex);
					}
				}

				else if (edges
						.contains(Triple5_XYZ_XNoun_ZNoun.Obj3.toString())) {
					String[] itemSplit = vertex.split("-");

					if (POSTagStanford.isNoun(itemSplit[1])) {
						subjList.add(vertex);
					}
				}
			}

		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
