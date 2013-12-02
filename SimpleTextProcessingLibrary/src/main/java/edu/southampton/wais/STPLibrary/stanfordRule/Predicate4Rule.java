package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class Predicate4Rule {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private static class MatcherNode implements Predicate<String> {
		private List<String> type;
		private boolean normalizezd;

		private MatcherNode(RuleTemplateNode node) {
			this.type = node.getPosValue();
			this.normalizezd = node.isPosNormalized();
		}

		public boolean apply(String input) {

			if (type.contains(RuleTemplateNode.RuleTemplateNodeEnum.Every
					.toString())) {
				return true;

			} else {

				String[] inputSplit = input.split("-");

				if (!normalizezd) {

					return type.contains(inputSplit[1]);

				}

				else {

					if (POSTagStanford.isAdjective(inputSplit[1])) {

						return type
								.contains(RuleTemplateNode.RuleTemplateNodeEnum.Adj
										.toString());
					}

					if (POSTagStanford.isNoun(inputSplit[1])) {

						return type
								.contains(RuleTemplateNode.RuleTemplateNodeEnum.Noun
										.toString());
					}

					if (POSTagStanford.isVerb(inputSplit[1])) {

						return type
								.contains(RuleTemplateNode.RuleTemplateNodeEnum.Verb
										.toString());
					}

					return false;
				}

			}
		}

	}

	public static Predicate<String> filterRuleNodePredicate(
			RuleTemplateNode node) {
		return new MatcherNode(node);
	}

	private static class MatcherEdge implements Predicate<String> {

		private List<String> edgeTemplate;

		private MatcherEdge(List<String> edgeTemplate) {
			this.edgeTemplate = edgeTemplate;
		}

		public boolean apply(String input) {

			if (edgeTemplate.contains(RuleTemplateEdge.EdgeType.Every
					.toString())) {
				return true;

			} else {

				for (String type : edgeTemplate) {

					if (input.equals(type)) {
						return true;
					}

				}

			}

			return false;

		}

	}

	public static Predicate<String> filterRuleEdgePredicate(List<String> edge) {
		return new MatcherEdge(edge);
	}

	private static class FilterRuleTemplateNodebyId implements
			Predicate<RuleTemplateNode> {

		private int id;

		private FilterRuleTemplateNodebyId(int id) {
			this.id = id;
		}

		public boolean apply(RuleTemplateNode input) {

			return input.getId() == id ? true : false;

		}

	}

	public static Predicate<RuleTemplateNode> FilterRuleTemplateNodebyIdPredicate(
			int id) {
		return new FilterRuleTemplateNodebyId(id);
	}

	public static Set<String> filterNounVertex(Set<String> vertex) {

		Set<String> filtered = Sets.filter(vertex, new Predicate<String>() {
			@Override
			public boolean apply(String p) {

				String[] ps = p.split("-");

				try {

					return POSTagStanford.isNoun(ps[1]) ? true : false;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		});

		return filtered;

	}

	public static Set<String> filterVerbVertex(Set<String> vertex) {

		Set<String> filtered = Sets.filter(vertex, new Predicate<String>() {
			@Override
			public boolean apply(String p) {

				String[] ps = p.split("-");

				try {

					return POSTagStanford.isVerb(ps[1]) ? true : false;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		});

		return filtered;

	}

}
