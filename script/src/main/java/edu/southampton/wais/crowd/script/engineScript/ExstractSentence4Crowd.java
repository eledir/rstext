package edu.southampton.wais.crowd.script.engineScript;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Joiner;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentBySentence;
import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.file.TxtFile;
import edu.southampton.wais.STPLibrary.model.AnnotatedWord;
import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.nlp.*;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.processor.SentenceTokenizerProcessor;
import edu.southampton.wais.STPLibrary.processor.StanfordAnnotationProcessor;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.utility.general.Logger;


public class ExstractSentence4Crowd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Parameter parameter=null;
		try {
			parameter = new Parameter(new File(args[0]));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		Logger.logInfo("error load propoerties file...");
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
			Logger.logInfo("error load propoerties file...");
			
		}
		

		 Properties properties=new Properties();
			
			try {
				properties.load(new FileInputStream(new File(args[0])));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			ExstractSentence4Crowd exstractSentence4Crowd= new ExstractSentence4Crowd(); 
			
			
			String basefolderIN = properties.getProperty("basefolderIN");
			
			String basefolderOut = properties.getProperty("basefolderOut");
			

			
			  
			
			
			
			
			
			
			
			
				Logger.logFiner("loading map key word topic trec..");
				
				 
				 String nameTopicIndexString=properties.getProperty("topicsSelection");
				
				 
				 List<String>topicIndex=exstractSentence4Crowd.splitList(nameTopicIndexString, ":");
				
				
				 Map<String,List<String>> mapTopicKeyWordTrec= new HashMap<String, List<String>>(); 
					
				 
				 for(String item:topicIndex){
					 
					 
					 String keyWordTopic=properties.getProperty(item);
							 
					 mapTopicKeyWordTrec.put(item, exstractSentence4Crowd.splitList(keyWordTopic, ":"));
					 
					 
				 }
				

				 Logger.logFiner("loading map key word topic trec.. done");
			
			
			
			
			
			
			
			
			
			
			
			
			Logger.logFine("loading collection ");
			
			

			
			
			
			CollectionTextFile collectionTextFile = IOUtility4STPLibrary
					.loadCollectionTextFile(new File(basefolderIN), "collection");
			
			
			
			
			
		
			
		    
			
			
			
		    
		    
		    

			for(TextFile document: collectionTextFile){
				
			
				
				
				
					
					
						

					boolean returnDelims = true;

					// Create the tokenizer
					Iterator tokenizer = new RETokenizer(document.getBody(), parameter.patternSentValidation, returnDelims);

					// Get the tokens (and delimiters)
					
					List<String> listWord = new ArrayList<String>();
					
					for (; tokenizer.hasNext(); ) {
					
						String tokenOrDelim = (String)tokenizer.next();
					    
							
						listWord.add(tokenOrDelim);
					    	
					    
					
					}
			          
			          
			         
					
					
					List<String> listWordMody = new ArrayList<String>();
					
					
					
					  for(String word: listWord){
						 
						 int count=0;
						 
						 if(!word.trim().isEmpty()){ 
						 
						 for(String topicTrec:mapTopicKeyWordTrec.keySet()){
							 
							
							  List<String> listKeyTopicTRec=mapTopicKeyWordTrec.get(topicTrec);
							  
							  
							  
							  
							   if(exstractSentence4Crowd.contain(listKeyTopicTRec, word)){
								   count=count+1;
							   }
							  
							  
							 
						 }
						 }
						 
	                      if(count!=0){
								
								String word2="<b>"+word+"</b>";
								listWordMody.add(word2);
							}
						
	                      else{
	                    	  
	                  		listWordMody.add(word);
								  
	                      }
						 
						 
					  }
						 	
						
					   String newBody=Joiner.on(" ").join(listWordMody);
			
					   
					   
					   
					   String path[]=document.getReference().getReference().toString().split(File.separator);
						
						String name=path[path.length-1];
						
						
						try {
							
							Logger.logFiner("Writing file "+basefolderOut+File.separator+name);
							
							FileUtils.writeStringToFile(new File(basefolderOut+File.separator+name), newBody);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					   
							
								
		    
		
				}//end 
				
				
								
				
				
				
				
			
		
		
		
		
		
		
		
		
		
		
		
	}
	
	

	public List<String> splitList(List<String> list,String sep, int index){
		
		
		List<String> newList= new ArrayList<String>();
		
		for(String item:list){
			
			newList.add(item.split(sep)[index]);
			
		}
		
		return newList;
	}




	public List<String> splitList(String token,String sep){
		
		
		List<String> newList= new ArrayList<String>();
		
		String [] tokens=token.split(sep);
		
		return Arrays.asList(tokens);
	}


	
	
public boolean contain(List<String> topic,String s){
		
		boolean f=false;
		
		for(String wordTopic:topic){
			
			if(wordTopic.trim().equals(s)){
				
				f=true;
				break;
				
			}
		}		
				
		return f?true:false;	
		
		
		}
	
	
	
	
public int matchList(List<String> topic,List<String> keyWords){
		
		int count=0;
		
		for(String wordTopic:topic){
			
			for(String keyWord:keyWords){
				
				
				if(wordTopic.toLowerCase().contains(keyWord)){
					
					count++;
				}
				
				
			}
			
		
		
		}
		
		return count;
	}
	
}
