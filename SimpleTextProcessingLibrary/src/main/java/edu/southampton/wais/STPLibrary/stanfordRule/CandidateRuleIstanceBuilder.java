package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.utility.PermutationBuilder;
import edu.southampton.wais.utility.datastructure.SingleNode;
import edu.southampton.wais.utility.general.Logger;
import edu.stanford.nlp.graph.DirectedMultiGraph;

public class CandidateRuleIstanceBuilder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<RuleIstance> buildCandidateRuleIstance(SentenceModel sm,
			RuleTemplate template) {

		DirectedMultiGraph<SingleNode<Integer,String>, String> gIstance = sm.getGraph();

		DirectedGraph<RuleTemplateNode, RuleTemplateEdge> gTemplate = template.graph;

		// get all the nodes of the template

		Set<RuleTemplateNode> nodeTemplateSet = gTemplate.vertexSet();

		// get all the nodes of the graph from the sentence

		Set<SingleNode<Integer,String>> setVertex = gIstance.getAllVertices();

		Multimap<Integer, SingleNode<Integer,String>> mapFilterNode = ArrayListMultimap.create();

		// filter just the node that match the node constraint

		for (RuleTemplateNode ruleTemplateNode : nodeTemplateSet) {

			Set<SingleNode<Integer,String>> setVertexFiltered = Sets.filter(setVertex,
					Predicate4Rule
							.filterRuleNodePredicate(ruleTemplateNode));

			mapFilterNode.putAll(ruleTemplateNode.getId(), setVertexFiltered);

		}

		//check if each node in the template has a mapping
		if(mapFilterNode.keySet().size()!=template.getNumberNode()){
			return Lists.newArrayList();
			
		}
		
		
		
		
		
		// create vector with size

		int[] vetSize = new int[mapFilterNode.keySet().size()];

		for (int i = 0; i < vetSize.length; i++) {
			vetSize[i] = mapFilterNode.get(i).size();
		}

		DoubleMatrix2D matrixPermutationIndex = PermutationBuilder
				.buildIndexMatrix(vetSize);

		List<RuleIstance> listRuleIstanceReturn = Lists.newArrayList();
		try {

			for (int i = 0; i < matrixPermutationIndex.rows(); i++) {

				DoubleMatrix1D vet = matrixPermutationIndex.viewRow(i);

				RuleIstance ruleIstance = new RuleIstance(vet.size());

				List<RuleIstanceNode> nodeAdded = Lists.newArrayList();

				for (int j = 0; j < vet.size(); j++) {

					SingleNode<Integer,String> token = Lists.newArrayList(mapFilterNode.get(j)).get(
							(int) vet.get(j));

					String tokenString=token.getObject();
					
					String[] nodeSplitted=tokenString.split("-");
					
					RuleIstanceNode node=new RuleIstanceNode(j, nodeSplitted[1],nodeSplitted[0],token);
					
					ruleIstance.addNode(node);

					nodeAdded.add(node);

				}

				
				boolean checkPresenceEdge=false;
				
				for (RuleIstanceNode node1 : nodeAdded) {

					for (RuleIstanceNode node2 : nodeAdded) {

						if (gIstance.isEdge(node1.getToken(), node2.getToken())) {

							RuleIstanceEdge edge = new RuleIstanceEdge(gIstance.getEdges(
									node1.getToken(), node2.getToken()));

							ruleIstance.addDirectedEdge(node1, node2, edge);

							checkPresenceEdge=true;
							
						}

						if (gIstance.isEdge(node2.getToken(), node1.getToken())) {

							RuleIstanceEdge edge = new RuleIstanceEdge(gIstance.getEdges(
									node2.getToken(), node1.getToken()));

							ruleIstance.addDirectedEdge(node2, node1, edge);

							checkPresenceEdge=true;
							
						}

					}
				}

				// add rule Istance

			   if(checkPresenceEdge){
				listRuleIstanceReturn.add(ruleIstance);
			   }
			}

		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.logSevere("Problem create rule instance you would add node not allowed");
			Logger.logSevere(e.getMessage());
		}

		return listRuleIstanceReturn;

	}

}