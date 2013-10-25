package edu.southampton.wais.religiousSentiment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.JWNLException;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.wordnet.WordnetSuperSenseRetrival;
import edu.southampton.wais.utility.datastructure.IntegerSingleNode;
import edu.southampton.wais.utility.general.UtilitySearialization;

public class SuperSenseList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


        File f= new File("config/file_properties.xml");
		
		
		FileInputStream fileImFileInputStream;
		try {
			fileImFileInputStream = new FileInputStream(f);
	
			JWNL.initialize(fileImFileInputStream);	
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		Iterator<File>iterator1=FileUtils.iterateFiles(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/objectSentence"),new String[]{"bin"}, true);
		
		Set<String> superSenseList=Sets.newHashSet();
		
		Set<String> superSenseListNull=Sets.newHashSet();
		
		Map<String,Set<String>> myMutlimap = Maps.newHashMap();
		
		
		while(iterator1.hasNext()){
			
				
				try {
					
					SentenceModel sm=(SentenceModel) UtilitySearialization.deserialiseObject(iterator1.next());
					
					
					Stack<IntegerSingleNode>nodes=sm.getStackverb();
					
					for(IntegerSingleNode node :nodes){
						
						int index=node.values;
						
						if(sm.getIsValidWord().get(index)){
							
							String lemma=sm.getLemma().get(index);
							String posNoNormalized=sm.getPosWord().get(index);
							
							
							String supersense;
							try {
								supersense = WordnetSuperSenseRetrival.getSuperSenseToken(lemma,
										posNoNormalized);
								if(supersense!=null){
									
									superSenseList.add(supersense);
									
									if(myMutlimap.containsKey(supersense)){
										
										myMutlimap.get(supersense).add(lemma);
									}
									else{
									Set<String> sets=Sets.newHashSet();
									sets.add(lemma);
										
										myMutlimap.put(supersense, sets);
								
									}
									
								}
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
					
								 superSenseListNull.add(lemma);
									
								
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
		
		
		try {
			FileUtils.writeLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/SuperSenseList.txt"), superSenseList);
			FileUtils.writeLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/SuperSenseNullList.txt"), superSenseListNull);
		
			
			

			try{
				  // Create file 
				  FileWriter fstream = new FileWriter(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/SuperSenseListMap.txt"));
				  BufferedWriter out = new BufferedWriter(fstream);
				  
				  for(String key:myMutlimap.keySet()){
					
					  out.write(key+"\t");
					  out.write(Joiner.on(", ").join(myMutlimap.get(key)));
					  out.write("\n");
				  }
				  
				  
				  //Close the output stream
				  out.close();
				  }catch (Exception e){//Catch exception if any
				  System.err.println("Error: " + e.getMessage());
				  }
			
			
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
