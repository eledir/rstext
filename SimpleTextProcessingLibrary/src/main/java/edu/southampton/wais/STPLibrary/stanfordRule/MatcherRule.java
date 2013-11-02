package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;

import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class MatcherRule {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static boolean matchRuleInstance2RuleTemplate(
			RuleIstance ruleInIstance, RuleTemplate template) {

		if (ruleInIstance.getNumberNodes() != template.graph.vertexSet().size()) {
			return false;
		}

		else {

			Set<RuleIstanceNode> setNodesIstance = ruleInIstance.getNodes();

			boolean mathcNode = matchNodeType(setNodesIstance,
					template.graph.vertexSet());

			if (!mathcNode) {
				return false;
			}

			for (RuleIstanceNode node1 : setNodesIstance) {

				for (RuleIstanceNode node2 : setNodesIstance) {

					if (template.graph.containsEdge(
							new RuleTemplateNode(node1.getId()),
							new RuleTemplateNode(node2.getId()))) {

						RuleTemplateEdge edgeTemplate = template.graph.getEdge(
								new RuleTemplateNode(node1.getId()),
								new RuleTemplateNode(node2.getId()));

						if (ruleInIstance.hasEdge(node1, node2)) {

							RuleIstanceEdge edgeInstanceRetrieved = ruleInIstance
									.getEdge(node1, node2);

							boolean matchEdge = mathEdgeType(
									edgeInstanceRetrieved, edgeTemplate);

							if (!matchEdge) {
								return false;
							}

						} else {

							return false;
						}

					}

					if (template.graph.containsEdge(
							new RuleTemplateNode(node2.getId()),
							new RuleTemplateNode(node1.getId()))) {

						RuleTemplateEdge edgeTemplate = template.graph.getEdge(
								new RuleTemplateNode(node2.getId()),
								new RuleTemplateNode(node1.getId()));

						if (ruleInIstance.hasEdge(node2, node1)) {

							RuleIstanceEdge edgeInstanceRetrieved = ruleInIstance
									.getEdge(node2, node1);

							boolean matchEdge = mathEdgeType(
									edgeInstanceRetrieved, edgeTemplate);

							if (!matchEdge) {
								return false;
							}
						} else {
							return false;
						}

					}
				}
			}

			return true;
		}
	}

	private static boolean mathEdgeType(RuleIstanceEdge edgeInstanceRetrieved,
			RuleTemplateEdge edgeTemplate) {
		// TODO Auto-generated method stub

		if (edgeTemplate.getDepType().contains(
				RuleTemplateEdge.EdgeType.Every.toString())) {
			return true;
		}

		List<String> edgeTypeTemplate = edgeTemplate.getDepType();
		List<String> edgeTypeIstance = edgeInstanceRetrieved.getDepType();
		for (String item : edgeTypeIstance) {

			if (!edgeTypeTemplate.contains(item)) {
				return false;
			}
		}

		return true;
	}

	private static boolean matchNodeType(Set<RuleIstanceNode> setNodesIstance,
			Set<RuleTemplateNode> setNodeTemplate) {
		// TODO Auto-generated method stub

		boolean[] resultMatch = new boolean[setNodesIstance.size()];

		for (int i = 0; i < resultMatch.length; i++) {
			resultMatch[i] = false;
		}

		int indexVetMatch = 0;

		for (RuleIstanceNode nodeIstance : setNodesIstance) {

			for (RuleTemplateNode nodeTemplate : setNodeTemplate) {

				if (nodeTemplate.getId() == nodeIstance.getId()) {

					if (nodeTemplate.getPosValue().contains(
							RuleTemplateNode.RuleTemplateNodeEnum.Every.toString())) {
						resultMatch[indexVetMatch] = true;
					}

					else {

						String typeIstance = nodeIstance.getType();

						boolean normalizezd = nodeTemplate.isPosNormalized();

						if (!normalizezd) {

							resultMatch[indexVetMatch] = nodeTemplate
									.getPosValue().contains(typeIstance);

						}

						else {

							if (POSTagStanford.isAdjective(typeIstance)) {

								resultMatch[indexVetMatch] = nodeTemplate
										.getPosValue()
										.contains(
												RuleTemplateNode.RuleTemplateNodeEnum.Adj
														.toString());
							}

							else if (POSTagStanford.isNoun(typeIstance)) {

								resultMatch[indexVetMatch] = nodeTemplate
										.getPosValue()
										.contains(
												RuleTemplateNode.RuleTemplateNodeEnum.Noun
														.toString());
							}

							else if (POSTagStanford.isVerb(typeIstance)) {

								resultMatch[indexVetMatch] = nodeTemplate
										.getPosValue()
										.contains(
												RuleTemplateNode.RuleTemplateNodeEnum.Verb
														.toString());
							} else {
								resultMatch[indexVetMatch] = false;
							}
						}

					}
				}
			}

			indexVetMatch++;

		}

		// check all true
		for (boolean b : resultMatch)
			if (!b)
				return false;
		return true;

	}

}
