package edu.southampton.wais.STPLibrary.stanfordRule;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class RuleParser {

	private List<String> body;

	private String ruleActivation = "R";

	private String sep = "=";
	private String sepValue = ",";
	private String ruleDef = "@R";
	private String suffixStart = "\\-start";
	private String suffixEnd = "\\-end";
	private String suffixNameRule = "name";
	private String suffixNameNode = "node";
	private String suffixNumberNode = "numberNode";
	private String sepNodeKey = "\\.";
	private String suffixPosNormalized = "N";
	private String suffixPosNotNormalized = "!N";

	private Multimap<String, String> ruleDefMap;

	private String suffixNameEdge = "edge";

	private String sepEdgeKey = "\\.";

	private String sepEdgeNode = "->";

	public RuleParser(List<String> body) {

		this.ruleDefMap = ArrayListMultimap.create();
		this.body = body;
	}

	public Set<RuleTemplate> parser() throws RuleParserException,
			RuleException {

		// extract number of rule

		List<String> numberRule = Lists.newArrayList(Collections2.filter(
				this.body, new Predicate<String>() {

					@Override
					public boolean apply(String item) {

						String itemVariable = item.split(sep)[0];

						return itemVariable.equals(ruleActivation) ? true
								: false;

					}
				}));

		if (numberRule.size() > 1) {

			throw new RuleParserException(
					"number of rules to activate defined nultiple times");
		}

		String[] ruleIdVet = numberRule.get(0).split(sep)[1].split(sepValue);

		for (String ruleId : ruleIdVet) {

			// extract the rule definition

			Pattern startRule = Pattern.compile(ruleDef + ruleId + suffixStart,
					Pattern.CASE_INSENSITIVE);

			Pattern endRule = Pattern.compile(ruleDef + ruleId + suffixEnd,
					Pattern.CASE_INSENSITIVE);

			List<String> dataRule = Lists.newArrayList(Collections2.filter(
					this.body,
					RuleExtractPredicate.predicate(startRule, endRule)));

			if (dataRule.isEmpty()) {

				throw new RuleParserException("not definition for rule : "
						+ ruleId);
			}

			this.ruleDefMap.putAll(ruleId, dataRule);

		}

		return createRules();

	}

	private Set<RuleTemplate> createRules() throws RuleParserException,
			RuleException {

		Set<RuleTemplate> set = Sets.newHashSet();

		for (String idRule : this.ruleDefMap.keySet()) {

			RuleTemplate rule = new RuleTemplate(idRule);

			Collection<String> data = this.ruleDefMap.get(idRule);

			// get number node

			Pattern patternNumberNode = Pattern.compile(this.suffixNumberNode
					 + sep+".+",Pattern.CASE_INSENSITIVE);

			Collection<String> numerberNode = Collections2.filter(data,
					RuleFileterDataByPattern.predicate(patternNumberNode));

			if (numerberNode.size() > 1 || numerberNode.isEmpty()) {

				throw new RuleParserException(
						"empty or multiple number nodes definition for for  : "
								+ idRule);
			}

			// set number of nome

			Integer numerNodeValue = new Integer(numerberNode.iterator().next().split(sep)[1]);
			rule.setNumberNode(numerNodeValue);

			// get name rule

			Pattern patternName = Pattern.compile(this.suffixNameRule 
					+sep + ".+",Pattern.CASE_INSENSITIVE);

			Collection<String> name = Collections2.filter(data,
					RuleFileterDataByPattern.predicate(patternName));

			if (name.size() > 1 || name.isEmpty()) {

				throw new RuleParserException("empty or multiple name for  : "
						+ idRule);
			}

			// set name
			rule.setName(name.iterator().next().split(sep)[1]);

			// get node definition

			Pattern patternNode = Pattern.compile(this.suffixNameNode + ".+"
					+ sep+".+",Pattern.CASE_INSENSITIVE);

			Collection<String> nodeDataSet = Collections2.filter(data,
					RuleFileterDataByPattern.predicate(patternNode));

			if (nodeDataSet.size() > numerNodeValue || nodeDataSet.isEmpty()) {

				throw new RuleParserException(
						"there are no nodes def or  more nodes def that the ones defined for  : "
								+ idRule);
			}

			for (String nodeDef : nodeDataSet) {

				String[] nodeDefSplit = nodeDef.split(sep);

				String[] nodeKey = nodeDefSplit[0].split(sepNodeKey);

				if (nodeKey.length > 3) {

					throw new RuleParserException(
							"node def is not correct you need just to specifay node.id.TypePos=Pos1,Pos2, where TypePos={N,!N}, normalized or not normalized  : "
									+ idRule);

				}

				// create a node with id
				
				RuleTemplateNode node = new RuleTemplateNode(new Integer(nodeKey[1]));

				//get posType
				String posType = nodeKey[2];

				// very that the pos tyep is correctly defined
				if (posType.equals(suffixPosNormalized)) {

					node.setPosNormalized(true);

				} else if (posType.equals(suffixPosNotNormalized)) {

					node.setPosNormalized(false);

				} else {

					throw new RuleParserException(
							"you need to defined the type of pos : normalized or not {N,!N} that specifay the node  : "
									+ idRule);

				}

				//getting values
				String[] nodeValue = nodeDefSplit[1].split(sepValue);

				if (nodeValue.length == 0) {

					throw new RuleParserException(
							"you need to specify the pos values for  : "
									+ idRule);

				}

				// set the pos value
				node.setPosValue(Lists.newArrayList(nodeValue));

				// add node to the rule
				rule.addRuleNode(node);

			}

			// get edge definion

			Pattern patternEdge = Pattern.compile(this.suffixNameEdge + ".+"
					+ sep+".+",Pattern.CASE_INSENSITIVE);

			Collection<String> edgeDataSet = Collections2.filter(data,
					RuleFileterDataByPattern.predicate(patternEdge));

			if (edgeDataSet.isEmpty()) {

				throw new RuleParserException(
						"there are no edges defined for  : " + idRule);
			}

			for (String edgeDef : edgeDataSet) {

				String[] edgeDefSplit = edgeDef.split(sep);

				String[] edgeKey = edgeDefSplit[0].split(sepEdgeKey);

				if (edgeKey.length > 2) {

					throw new RuleParserException(
							"edge is not properly defined"
									+ " you need just to specifay edge.NodeId1->NodeId2 : "
									+ idRule);

				}

				String[] edgeNodePointer = edgeKey[1].split(sepEdgeNode);

				if (edgeNodePointer.length != 2) {

					throw new RuleParserException(
							"edge is not properly defined"
									+ " you need just to specifay edge.NodeId1->NodeId2 : "
									+ idRule);

				}

				RuleTemplateNode roleNode1 = new RuleTemplateNode(new Integer(
						edgeNodePointer[0]));
				RuleTemplateNode roleNode2 = new RuleTemplateNode(new Integer(
						edgeNodePointer[1]));

				String[] nodeValue = edgeDefSplit[1].split(sepValue);

				if (nodeValue.length == 0) {

					throw new RuleParserException(
							"you need to specify the dep values for the edge related to  : "
									+ idRule);

				}

				// create a edge
				RuleTemplateEdge edge = new RuleTemplateEdge(Lists.newArrayList(nodeValue));

				rule.addDirectedEdge(roleNode1, roleNode2, edge);

			}

			// add rule tot the set
			set.add(rule);

		}

		// TODO Auto-generated method stub
		return set;
	}

	private static class RuleExtractPredicate implements Predicate<String> {

		private boolean start = false;
		private Pattern startRule;
		private Pattern endRule;

		private RuleExtractPredicate(Pattern startRule, Pattern endRule) {

			this.startRule = startRule;
			this.endRule = endRule;

		}

		public boolean apply(String input) {

			if (startRule.matcher(input).matches()) {
				start = true;
			}

			if (endRule.matcher(input).matches()) {
				start = false;
			}

			return start ? true : false;

		}

		public static Predicate<String> predicate(Pattern startRule,
				Pattern endRule) {
			return new RuleExtractPredicate(startRule, endRule);
		}
	}

	private static class RuleFileterDataByPattern implements Predicate<String> {

		private Pattern pattern;

		private RuleFileterDataByPattern(Pattern pattern) {

			this.pattern = pattern;

		}

		public boolean apply(String input) {

			return pattern.matcher(input).matches() ? true : false;

		}

		public static Predicate<String> predicate(Pattern pattern) {
			return new RuleFileterDataByPattern(pattern);
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// test parser
		
		try {
			List<String> ruleDef=FileUtils.readLines(new File("rules/confRules.txt"));
			
			
			RuleParser parser= new RuleParser(ruleDef);
			
			Set<RuleTemplate>rules=parser.parser();
			
			for(RuleTemplate rule:rules){
				
				
				System.out.println(StringUtils.repeat("-", 30));
				System.out.println(rule);
				System.out.println(StringUtils.repeat("-", 30));
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println(e);
			
		} catch (RuleParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			
			
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		
		
		
		
		
		
		
	}

}
