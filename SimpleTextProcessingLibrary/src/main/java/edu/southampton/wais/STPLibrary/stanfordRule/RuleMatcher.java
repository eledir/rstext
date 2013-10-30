package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import opennlp.tools.parser.PosSampleStream;

import org.jgrapht.DirectedGraph;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.utility.general.Logger;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public class RuleMatcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<TripleModel> extractTriples(SentenceModel sm,
			RuleTemplate template) {

		DirectedMultiGraph<String, String> gIstance = sm.getGraph();

		DirectedGraph<RuleTemplateNode, RuleTemplateEdge> gTemplate = template.graph;

		// get all the nodes of the template

		Set<RuleTemplateNode> nodeTemplateSet = gTemplate.vertexSet();

		// get all the nodes of the graph from the sentence

		Set<String> setVertex = gIstance.getAllVertices();

		Multimap<Integer, String> mapFilterNode = ArrayListMultimap.create();

		// filter just the node that match the node constraint

		for (RuleTemplateNode ruleTemplateNode : nodeTemplateSet) {

			Set<String> setVertexFiltered = Sets.filter(setVertex,
					MatcherNode.match(ruleTemplateNode));

			mapFilterNode.putAll(ruleTemplateNode.getId(), setVertexFiltered);

		}

		// filter the map based on the edge constraint

		for (Integer id1 : mapFilterNode.keySet()) {

			for (Integer id2 : mapFilterNode.keySet()) {

				if (id1 != id2) {

					// retrieve constraing 1d1->Id2

					if (gTemplate.containsEdge(new RuleTemplateNode(id1),
							new RuleTemplateNode(id2))) {

						Set<String> filteredIstanceNode1 = Sets.newHashSet();

						Set<String> filteredIstanceNode2 = Sets.newHashSet();

						// remove node before updating
						mapFilterNode.removeAll(id1);
						mapFilterNode.removeAll(id1);

						boolean id1_id2_allowed = ckeckEdgeAllowed(gIstance,
								gTemplate.getEdge(new RuleTemplateNode(id1),
										new RuleTemplateNode(id2)),
								mapFilterNode.get(id1), mapFilterNode.get(id2),
								filteredIstanceNode1, filteredIstanceNode2);

						if (id1_id2_allowed) {

							mapFilterNode.putAll(id1, Sets.intersection(
									filteredIstanceNode1,
									Sets.newHashSet(mapFilterNode.get(id1))));

							mapFilterNode.putAll(id2, Sets.intersection(
									filteredIstanceNode2,
									Sets.newHashSet(mapFilterNode.get(id2))));

						}

					}

					if (gTemplate.containsEdge(new RuleTemplateNode(id2),
							new RuleTemplateNode(id1))) {

						Set<String> filteredIstanceNode1 = Sets.newHashSet();

						Set<String> filteredIstanceNode2 = Sets.newHashSet();

						boolean id2_id1_allowed = ckeckEdgeAllowed(gIstance,
								gTemplate.getEdge(new RuleTemplateNode(id2),
										new RuleTemplateNode(id1)),
								mapFilterNode.get(id1), mapFilterNode.get(id2),
								filteredIstanceNode1, filteredIstanceNode2);

						if (id2_id1_allowed) {

							mapFilterNode.putAll(id1, Sets.intersection(
									filteredIstanceNode1,
									Sets.newHashSet(mapFilterNode.get(id1))));

							mapFilterNode.putAll(id2, Sets.intersection(
									filteredIstanceNode2,
									Sets.newHashSet(mapFilterNode.get(id2))));

						}

					}

				}

			}

		}

		// now you get a clean map of the mach based on edge and nodes
		// constraints

		// check that all the id have some elements

		boolean matchAllConstrain = checkPresenceElmentInMaps(mapFilterNode);

		if (matchAllConstrain) {

			return buildListTripleNode(sm, template, mapFilterNode);

		}

		return Lists.newArrayList();

	}

	private static List<TripleModel> buildListTripleNode(SentenceModel sm,
			RuleTemplate template, Multimap<Integer, String> mapFilterNode) {

		List<Integer> listIDSub = template.getSubjID();

		List<Integer> listIDObj = template.getObjID();

		List<Integer> listIDVerb = template.getVerbID();

		List<String> listSub = Lists.newArrayList();
		List<String> listObj = Lists.newArrayList();
		List<String> listVerb = Lists.newArrayList();

		for (Integer subId : listIDSub) {

			listSub.addAll(mapFilterNode.get(subId));

		}

		for (Integer objId : listIDObj) {

			listObj.addAll(mapFilterNode.get(objId));

		}

		for (Integer verbId : listIDVerb) {

			listVerb.addAll(mapFilterNode.get(verbId));

		}

		List<TripleModel> listTripleModel = Lists.newArrayList();

		for (String itemVerb : listVerb) {

			for (String itemSub : listSub) {

				for (String itemObj : listObj) {

					listTripleModel.add(new TripleModel(sm, itemSub, itemVerb,
							itemObj));

				}
			}

		}

		// TODO Auto-generated method stub
		return listTripleModel;
	}

	private static boolean checkPresenceElmentInMaps(
			Multimap<Integer, String> mapFilterNode) {
		// TODO Auto-generated method stub

		for (Integer id : mapFilterNode.keySet()) {

			if (mapFilterNode.get(id).isEmpty()) {

				return false;
			}

		}

		return true;
	}

	private static boolean ckeckEdgeAllowed(
			DirectedMultiGraph<String, String> gIstance, RuleTemplateEdge edge,
			Collection<String> collection1In, Collection<String> collection2In,
			Collection<String> collection1Out, Collection<String> collection2Out) {

		int size = collection1In.size();

		for (String node1 : collection1In) {
			for (String node2 : collection2In) {

				if (gIstance.isEdge(node1, node2)) {

					List<String> filteredEdge = Lists.newArrayList(Collections2
							.filter(gIstance.getEdges(node1, node2),
									MatcherEdge.match(edge.getDepType())));

					// if the filtered Edge size >0 so the nodes 1 and 2
					// are allowed

					if (!filteredEdge.isEmpty()) {

						collection1Out.add(node1);
						collection2Out.add(node2);

					}

				}

			}
		}

		return collection1In.size() > size ? true : false;

	}

	private static Set<String> filterVerbVertex(Set<String> vertex) {

		Set<String> filtered = Sets.filter(vertex, new Predicate<String>() {
			@Override
			public boolean apply(String p) {

				String[] ps = p.split("-");

				try {

					return POSTagStanford.isVerb(ps[1]) ? true : false;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Logger.logSevere(e.toString());
					return false;
				}
			}
		});

		return filtered;

	}

	private static class MatcherNode implements Predicate<String> {
		private List<String> type;
		private boolean normalizezd;

		private MatcherNode(RuleTemplateNode node) {
			this.type = node.getPosValue();
			this.normalizezd = node.isPosNormalized();
		}

		public boolean apply(String input) {

			String[] inputSplit = input.split("-");

			if (!normalizezd) {

				return type.contains(inputSplit[1]);

			}

			else {

				if (POSTagStanford.isAdjective(inputSplit[1])) {

					return type
							.contains(RuleTemplateNode.RuleTemplateNodeEnum.Adj);
				}

				if (POSTagStanford.isNoun(inputSplit[1])) {

					return type
							.contains(RuleTemplateNode.RuleTemplateNodeEnum.Noun);
				}

				if (POSTagStanford.isVerb(inputSplit[1])) {

					return type
							.contains(RuleTemplateNode.RuleTemplateNodeEnum.Verb);
				}

				return false;
			}

		}

		public static Predicate<String> match(RuleTemplateNode node) {
			return new MatcherNode(node);
		}
	}

	private static class MatcherEdge implements Predicate<String> {

		private List<String> edgeTemplate;

		private MatcherEdge(List<String> edgeTemplate) {
			this.edgeTemplate = edgeTemplate;
		}

		public boolean apply(String input) {

			for (String type : edgeTemplate) {

				if (type.equals(RuleTemplateEdge.EdgeType.Every)) {
					return true;
				} else {

					return input.equals(type) ? true : false;

				}

			}

			return false;

		}

		public static Predicate<String> match(List<String> edge) {
			return new MatcherEdge(edge);
		}
	}

}
