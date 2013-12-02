package edu.southampton.wais.STPLibrary.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import cc.mallet.util.FileUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
import edu.southampton.wais.STPLibrary.stanfordRule.*;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.DirectedMultigraphUtility;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.datastructure.SingleNode;
import edu.southampton.wais.utility.general.IOFileUtility;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public class ExtendRuleProcessor {

	private static final List<String> conj = Lists.newArrayList("conj_or",
			"conj_and", "conj_but", "prep_of", "prep_about");

	public static Set<SingleNode<Integer, String>> extractSubjByListExtend(TripleModel model,
			DirectedMultiGraph<SingleNode<Integer,String>, String> g) {

		Set<SingleNode<Integer, String>> candidate = Sets.newHashSet();

		Set<SingleNode<Integer, String>> result = Sets.newHashSet();

		// take all the edge
		List<String> listEdge = g.getIncomingEdges(model.getSubj());

		listEdge.addAll(g.getOutgoingEdges(model.getSubj()));

		// check if the edge are the ones that we are looking for
		List<String> listEdgeFiltered = Lists.newArrayList(Collections2.filter(
				listEdge, CheckEdge.match(conj)));

		if (listEdgeFiltered.size() > 0) {

			// add each vertex to the candidate

			for (String edge : listEdgeFiltered) {

				Set<SingleNode<Integer, String>> expantion = DirectedMultigraphUtility
						.getSourceVertexGivenEdge(edge, model.getSubj(), g);

				expantion.addAll(DirectedMultigraphUtility
						.getTargetVertexGivenEdge(edge, model.getSubj(), g));

				for (SingleNode<Integer, String> item : expantion) {

					String itemString=item.getObject();
					
					String[] itemSplit = itemString.split("-");

					if (POSTagStanford.isNoun(itemSplit[1])
							&& !item.equals(model.getObjt())) {

						candidate.add(item);

					}

				}

			}

			// check if the candidate is in relation with a verb different from
			// the one belonging to the triples.

			for (SingleNode<Integer, String> item : candidate) {

				// for earch candidate select the neiborhoood

				Set<SingleNode<Integer, String>> setN = g.getNeighbors(item);

				// cheack if the neiborrhod is in realation with a different
				// verb;
				boolean accepeted = true;

				for (SingleNode<Integer, String> nSingle : setN) {

					String n=nSingle.getObject();
					
					if (n.equals(model.getVerb())) {
						accepeted = false;
						break;
					}

				}

				// add the candidate
				if (accepeted) {
					result.add(item);
				}

			}

		}

		return result;

	}

	public static Set<SingleNode<Integer, String>> extractObjByListExtend(TripleModel model,
			DirectedMultiGraph<SingleNode<Integer, String>, String> g) {

		Set<SingleNode<Integer, String>> candidate = Sets.newHashSet();

		Set<SingleNode<Integer, String>> result = Sets.newHashSet();

		// take all the edge
		List<String> listEdge = g.getIncomingEdges(model.getObjt());

		listEdge.addAll(g.getOutgoingEdges(model.getObjt()));

		// check if the edge are the ones that we are looking for
		List<String> listEdgeFiltered = Lists.newArrayList(Collections2.filter(
				listEdge, CheckEdge.match(conj)));

		if (listEdgeFiltered.size() > 0) {

			// add each vertex to the candidate

			for (String edge : listEdgeFiltered) {

				Set<SingleNode<Integer, String>> expantion = DirectedMultigraphUtility
						.getSourceVertexGivenEdge(edge, model.getObjt(), g);

				expantion.addAll(DirectedMultigraphUtility
						.getTargetVertexGivenEdge(edge, model.getObjt(), g));

				for (SingleNode<Integer, String> item : expantion) {

					String itemString=item.getObject();
					
					String[] itemSplit = itemString.split("-");

					if ((POSTagStanford.isNoun(itemSplit[1])
							|| POSTagStanford.isAdjective(itemSplit[1]) || POSTagStanford
								.isVerb(itemSplit[1]))
							&& !item.equals(model.getObjt())&&!item.equals(model.getVerb())) {

						candidate.add(item);

					}

				}

			}

			// check if the candidate is in relation with a verb different from
			// the one belonging to the triples.

			for (SingleNode<Integer, String> item : candidate) {

				// for earch candidate select the neiborhoood

				Set<SingleNode<Integer, String>> setN = g.getNeighbors(item);

				// cheack if the neiborrhod is in realation with a different
				// verb;
				boolean accepeted = true;

				for (SingleNode<Integer, String> nToken : setN) {

					String n=nToken.getObject();
					
					if (n.equals(model.getVerb())) {
						accepeted = false;
						break;
					}

				}

				// add the candidate
				if (accepeted) {
					result.add(item);
				}

			}

		}

		return result;

	}

	public static Set<SingleNode<Integer, String>> extracVerbByListExtend(TripleModel model,
			DirectedMultiGraph<SingleNode<Integer, String>, String> g) {

		Set<SingleNode<Integer, String>> candidate = Sets.newHashSet();

		// take all the edge

		List<String> listEdge = g.getIncomingEdges(model.getVerb());

		listEdge.addAll(g.getOutgoingEdges(model.getVerb()));

		// check if the edge are the ones that we are looking for

		List<String> listEdgeFiltered = Lists.newArrayList(Collections2.filter(
				listEdge, CheckEdge.match(conj)));

		if (listEdgeFiltered.size() > 0) {

			// add each vertex to the candidate
			for (String edge : listEdgeFiltered) {

				Set<SingleNode<Integer, String>> expantion = DirectedMultigraphUtility
						.getSourceVertexGivenEdge(edge, model.getVerb(), g);

				expantion.addAll(DirectedMultigraphUtility
						.getTargetVertexGivenEdge(edge, model.getVerb(), g));

				for (SingleNode<Integer, String> item : expantion) {

					String itemString=item.getObject();
					
					String[] itemSplit = itemString.split("-");

					if ( POSTagStanford
								.isVerb(itemSplit[1])
							&& !item.equals(model.getObjt())) {

						candidate.add(item);

					}

				}

			}
		}

		return candidate;

	}

	static class CheckEdge implements Predicate<String> {
		private List<String> edge;

		private CheckEdge(List<String> edge) {
			this.edge = edge;
		}

		public boolean apply(String input) {
			return edge.contains(input);
		}

		public static Predicate<String> match(List<String> edge) {
			return new CheckEdge(edge);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
