package edu.southampton.wais.STPLibrary.processor;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
import edu.southampton.wais.STPLibrary.stanfordRule.CandidateRuleIstanceBuilder;
import edu.southampton.wais.STPLibrary.stanfordRule.MatcherRule;
import edu.southampton.wais.STPLibrary.stanfordRule.Rule;
import edu.southampton.wais.STPLibrary.stanfordRule.RuleException;
import edu.southampton.wais.STPLibrary.stanfordRule.RuleIstance;
import edu.southampton.wais.STPLibrary.stanfordRule.RuleParser;
import edu.southampton.wais.STPLibrary.stanfordRule.RuleTemplate;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.datastructure.SingleNode;
import edu.southampton.wais.utility.general.IOFileUtility;
import edu.southampton.wais.utility.general.Logger;

public class ExtractModelsBasedOnStanfordDependancyProcessor {

	public ExtractModelsBasedOnStanfordDependancyProcessor() {

	}

	public static List<TripleModel> extract(SentenceModel sm,
			RuleTemplate ruleTemplate) throws RuleException {

		List<TripleModel> listTripleModel = Lists.newArrayList();

		List<RuleIstance> listRuleIstance = CandidateRuleIstanceBuilder
				.buildCandidateRuleIstance(sm, ruleTemplate);

		for (RuleIstance ruleIstance : listRuleIstance) {

			boolean b = MatcherRule.matchRuleInstance2RuleTemplate(ruleIstance,
					ruleTemplate);

			if (b) {

				Logger.logFiner((String.format("the rule %s is activated..",
						ruleTemplate.getName())));

				Logger.logFiner(ruleTemplate.toString());

				List<Integer> listIDSub = ruleTemplate.getSubjID();

				List<Integer> listIDObj = ruleTemplate.getObjID();

				List<Integer> listIDVerb = ruleTemplate.getVerbID();
				
				
				
			

				listTripleModel.add(new TripleModel(sm, ruleIstance
						.getNodeValueByID(listIDSub.get(0)), ruleIstance
						.getNodeValueByID(listIDVerb.get(0)), ruleIstance
						.getNodeValueByID(listIDObj.get(0))));

			}

		}

		return listTripleModel;

	}

	public static List<TripleModel> extract(SentenceModel sm,
			Set<RuleTemplate> setRuleTemplate) throws RuleException {

		List<TripleModel> listTripleModel = Lists.newArrayList();

		for (RuleTemplate ruleTemplate : setRuleTemplate) {

			
			listTripleModel.addAll(extract(sm, ruleTemplate));
			
			
		}

		return listTripleModel;

	}

	public static List<TripleModel> extend(SentenceModel sm,
			List<TripleModel> listModel) {

		List<TripleModel> listModelExVerb = Lists.newArrayList();

		// first extend the verb

		for (TripleModel model : listModel) {

			Set<SingleNode<Integer,String>> verbEx = ExtendRuleProcessor.extracVerbByListExtend(
					model, sm.getGraph());

			for (SingleNode<Integer,String> verb : verbEx) {
				listModelExVerb.add(new TripleModel(sm, model.getSubj(), verb,
						model.getObjt()));
			}

		}

		// add previous triple
		listModelExVerb.addAll(listModel);

		// extend the subj

		List<TripleModel> listModelExSubj = Lists.newArrayList();

		for (TripleModel model : listModelExVerb) {

			Set<SingleNode<Integer,String>> subEx = ExtendRuleProcessor.extractSubjByListExtend(
					model, sm.getGraph());

			for (SingleNode<Integer,String> subj : subEx) {

				listModelExSubj.add(new TripleModel(sm, subj, model.getVerb(),
						model.getObjt()));

			}

		}

		// add all the triple
		listModelExSubj.addAll(listModelExVerb);

		// extend obj

		List<TripleModel> listModelExObj = Lists.newArrayList();

		for (TripleModel model : listModelExSubj) {

			Set<SingleNode<Integer,String>> objEx = ExtendRuleProcessor.extractObjByListExtend(
					model, sm.getGraph());

			for (SingleNode<Integer,String> obj : objEx) {

				listModelExObj.add(new TripleModel(sm, model.getSubj(), model
						.getVerb(), obj));

			}

		}

		// add all the triple
		listModelExObj.addAll(listModelExSubj);

		return listModelExObj;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String dir = "/Users/antoniopenta/Documents/workspaceReligionSentiment/nlpdata";

		 dir="/home/antoniodesktop/Documents/data/nlpdata";

		Pattern patternSentValidation = Pattern.compile("[a-zA-Z\\-]+");

		StanfordCoreNLPService services = StanfordCoreNLPService
				.StanfordCoreNLPService(dir);

		try {

			StringProcessor stringProcessor = new StringProcessor();

			String nameFileStopList1 = dir + File.separator
					+ "StopWordsLite.txt";

			HashSet<String> listStop = IOFileUtility
					.readHashSetStringFromFile(new File(nameFileStopList1));

			/*List<String> textVet = FileUtils
					.readLines(new File(
							"/Users/antoniopenta/Documents/DatiRicerca/iextreme/collections/trainFiles/KM.txt"));
*/
			List<String> textVet=Lists.newArrayList();
			
			textVet.clear();
			//textVet.add("it seduced people and prevented that religion is god");
			String texSent="To accept the idea of pluralism means that you do not care much about religion";
			
			textVet.add(texSent);
			
			
			for (String tex : textVet) {

				List<String> texL = stringProcessor.cleanSentence(tex,
						patternSentValidation);

				SentenceModel sm = new SentenceModel(Joiner.on(" ").join(texL));

				services.buildAnnotation4Sentence(sm);

				SentenceModelUtility.invalidatedStopListTerm(sm, listStop);

				boolean valid = SentenceModelUtility
						.countMeaningfulTerms(sm, 3);

				System.out.println(StringUtils.repeat("_", 10));

				System.out.println(sm.getBody());

				System.out.println(valid);

				System.out.println(sm.getGraph());

				System.out.println(StringUtils.repeat("_", 10));

				List<String> ruleDef = FileUtils.readLines(new File(
						"rules/tripleRulesExtraction.rule"));

				RuleParser parser = new RuleParser(ruleDef);

				Set<RuleTemplate> rules = parser.parser();

				List<TripleModel>listTripleModel=ExtractModelsBasedOnStanfordDependancyProcessor.extract(sm, rules);
				
				List<TripleModel>listTripleModel2=ExtractModelsBasedOnStanfordDependancyProcessor.extend(sm, listTripleModel);	
				
				
					System.out.println(StringUtils.repeat("*", 10));

					for (TripleModel model : listTripleModel2) {

						System.out.println(model);

					}

					System.out.println(StringUtils.repeat("*", 10));

				}

			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
