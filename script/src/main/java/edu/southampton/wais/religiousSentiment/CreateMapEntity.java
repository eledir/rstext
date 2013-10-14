package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;
import edu.southampton.wais.utility.UtilitySearialization;

public class CreateMapEntity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		
		
		Iterator<File>iterator1=FileUtils.iterateFiles(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/objectSentence"),new String[]{"bin"}, true);
		
		ListMultimap<String,String> myMutlimap = ArrayListMultimap.create();
		
		while(iterator1.hasNext()){
			
			try {
				
				SentenceModel sm=(SentenceModel) UtilitySearialization.deserialiseObject(iterator1.next());
				
				
				Multimap<String,Integer>map=sm.getNerMultiMap();
				
				
				
				for(String item:map.keySet()){
				
					for(int index :map.get(item)){
					
					  if(sm.getIsValidWord().get(index)&&POSTagStanford.isNoun(sm.getPosWord().get(index))){
						
						String word=sm.getLemma().get(index);
				  
					
					       myMutlimap.put(item,word.toLowerCase());
					}
				}
				
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			
		}
		
		
		for(String item :myMutlimap.keySet()){
			
			
			try {
				FileUtils.writeLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/"+item+".txt"), myMutlimap.get(item));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
