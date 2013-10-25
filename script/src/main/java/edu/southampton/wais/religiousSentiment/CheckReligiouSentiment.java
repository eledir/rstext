package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.JWNLException;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;
import edu.southampton.wais.STPLibrary.sentiment.PolaritySuperSense;
import edu.southampton.wais.STPLibrary.sentiment.PolaritySuperSense2;
import edu.southampton.wais.STPLibrary.sentiment.SentimentComputing;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.general.IOFileUtility;
import edu.southampton.wais.utility.general.Logger;
import edu.southampton.wais.utility.general.UtilitySearialization;

public class CheckReligiouSentiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
        String dir = "/Users/antoniopenta/Documents/workspaceReligionSentiment/nlpdata";

		
		//dir="/home/antoniodesktop/Documents/data/nlpdata";

		Pattern patternSentValidation = Pattern.compile("[a-zA-Z\\-]+");

		
		
		
		
		StanfordCoreNLPService services = StanfordCoreNLPService
				.StanfordCoreNLPService(dir);

		
		try {

			Map<String,List<String>> mapAll=(Map<String, List<String>>) UtilitySearialization.deserialiseObject(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/mapSentiment/mapAll.bin"));
			
			
			StringProcessor stringProcessor= new StringProcessor();
			
			String nameFileStopList1 = dir + File.separator
					+ "StopWordsLite.txt";

			HashSet<String> listStop = IOFileUtility
					.readHashSetStringFromFile(new File(nameFileStopList1));

              File f= new File("config/file_properties.xml");
			
			
			FileInputStream fileImFileInputStream = new FileInputStream(f);			
			JWNL.initialize(fileImFileInputStream);	
			
			
		
			List<String> list=FileUtils.readLines(new File("/home/antoniodesktop/Dropbox/IEXTREME/data/category/CoherenceConsistency.txt"));
			
			for(String sentence :list){
				
			
				List<String>texL=stringProcessor.cleanSentence(sentence, patternSentValidation);
				
				
				
				SentenceModel sm = new SentenceModel(Joiner.on(" ").join(texL));

				
				services.buildAnnotation4Sentence(sm);

			
				
	            SentenceModelUtility.invalidatedStopListTerm(sm, listStop);
	            
	            SentenceModelUtility.invalidatedShortWord(sm, 2);
	            
	            boolean valid=SentenceModelUtility.countMeaningfulTerms(sm, 3);
			
	            System.out.println(sm.getBody());

	            
                if(valid){
                
                	
                	PolaritySuperSense2 polarity= new PolaritySuperSense2(sm,mapAll);
                	polarity.computePolarity();
                	
                	System.out.println(sm.getPosNormalizeWord());
                	System.out.println(sm.getLemma());
                	
                	System.out.println(sm.getTableDepGovern());
                	System.out.println(sm.getNerMultiMap());
                	
                	System.out.println(polarity.getHashIndexContexAll());
                	
                	System.out.println(polarity.getHashIndexContexAdj());
                    
                	System.out.println(polarity.getHashIndexContexNoun());
                    
                	
                	System.out.println(polarity.getHashIndexVerb());
                	
                	System.out.println(polarity.getHashSuperSenseVerb());
                	
                	System.out.println(polarity.getHashPolarityVerb());
                	
                }  
                else{
                	System.out.println("not valid..");
                }
			}
	            
	            
				
				
				
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
