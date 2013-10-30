package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.utility.general.Logger;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public class ExtractRuleIstance {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<RuleIstance> extractTreeNodeRules(SentenceModel sm) {

		DirectedMultiGraph<String, String> g = sm.getGraph();

		Set<String> setVertex = g.getAllVertices();

		Set<String> setVerbVertex = filterVerbVertex(setVertex);

		List<RuleIstance> ruleIstanceList = Lists.newArrayList();

		for (String vertexverb : setVerbVertex) {

			Set<String> vertexOutGoing = Sets.newHashSet();

			Set<String> vertexInGoing = Sets.newHashSet();

			for (String vertex : setVertex) {

				if (g.isEdge(vertex, vertexverb)) {

					vertexInGoing.add(vertex);

				}
				if (g.isEdge(vertexverb, vertex)) {

					vertexOutGoing.add(vertex);
				}

			}

			
			try {
				
			RuleIstance istance = new RuleIstance(3);

				istance.addNode(vertexverb);
			
			for (String nodeOut : vertexOutGoing) {

				istance.addNode(nodeOut);
				istance.addDirectedEdge(vertexverb, nodeOut,
						new RuleEdge(g.getEdges(vertexverb, nodeOut)));
			}

			for (String nodeIn : vertexInGoing) {

				istance.addNode(nodeIn);
				istance.addDirectedEdge(nodeIn, vertexverb,
						new RuleEdge(g.getEdges(nodeIn, vertexverb)));
			}

			if (vertexOutGoing.size() > 0 || vertexInGoing.size() > 0) {

				ruleIstanceList.add(istance);
			}

			} catch (RuleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				Logger.logSevere(e.toString());
			}

			
		}

		return ruleIstanceList;

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

}
