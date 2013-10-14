package edu.southampton.wais.religiousSentiment;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class ProvaTokenizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		  Pattern patternSentValidation = Pattern.compile("[a-zA-Z-[\\-\\'\\’\\\"\\”\\“\\”\\t\\n\\s\\-]]+");
		
		
		String sent="لكافر هو مصطلح فريد أنتجته الثقافة العربية الإسلامية منذ العصر الطباشيري ، فالإسلام كما نعلم جميعا لم يأت به محمد (صلى الله عليه وسلم) بل هو دين جميع أنبياء الله ، ابتداءً من آدم عليه";
		 PTBTokenizer ptbt = new PTBTokenizer(new StringReader(sent),
	              new CoreLabelTokenFactory(), "");
		
		
		// Get the tokens (and delimiters)
		
		
		CoreLabel label;
		
		for (; ptbt.hasNext(); ) {
	        label = (CoreLabel) ptbt.next();
		
	        System.out.println(label.originalText());
	        
	        System.out.println(ProvaTokenizer.isEnable(patternSentValidation,label.originalText()));
	        
	        
	        
		
		}
		
		
	}

	
	public static boolean isEnable( Pattern p, String word){


		   

		   
		

		Matcher m =p.matcher(word);    
		if(m.matches()){
		return true;}
		else{
		return false;
		}  	
	

		}
	
	
}
