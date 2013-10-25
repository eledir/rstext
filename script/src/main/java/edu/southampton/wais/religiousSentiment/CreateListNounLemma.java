package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.utility.datastructure.IntegerSingleNode;
import edu.southampton.wais.utility.general.UtilitySearialization;

public class CreateListNounLemma {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		
		Iterator<File>iterator1=FileUtils.iterateFiles(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/objectDoc"),new String[]{"bin"}, true);
		
		Set<String> setNoun=Sets.newHashSet();
		
		while(iterator1.hasNext()){
			
			try {
			
				DocumentModelBySentence doc=(DocumentModelBySentence)UtilitySearialization.deserialiseObject(iterator1.next());
			
				for (Map.Entry<Integer, String> sentence : doc.getEntrySet()) {

					if (doc.isValidSentense(sentence.getKey())) {
						
						
						SentenceModel sm=doc.getSentenceModel(sentence.getKey());
						
						Stack<IntegerSingleNode> stack=sm.getStacknoun();
						
						for(IntegerSingleNode node: stack){
							
							
							if(sm.getIsValidWord().get(node.values)&&node.name.length()>2){
							  
								if(sm.getLemma().containsKey(node.values)){
								
									String lemma=sm.getLemma().get(node.values);
							       
									setNoun.add(lemma.toLowerCase());
								}
								else{
									
									System.out.println("not lemma for "+node.name);
									
								}
								
								}
							else{
								
								System.out.println("not valid lemma for "+node.name);
								
							}
						}
						
						
					}
					}
				
				
			FileUtils.writeLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/allLemmaNoun.txt"), setNoun);
				
				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
		
		
	}

}
