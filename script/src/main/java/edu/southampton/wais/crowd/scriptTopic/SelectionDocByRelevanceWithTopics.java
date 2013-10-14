package edu.southampton.wais.crowd.scriptTopic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Joiner;

import cc.mallet.topics.tui.TopicTrainer;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentByTopic;
import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.dataStructure.DoubleSingleNode;
import edu.southampton.wais.STPLibrary.dataStructure.DoubleStringListNode;
import edu.southampton.wais.STPLibrary.dataStructure.DoubleStringNode;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.model.DocumentModelByTopic;
import edu.southampton.wais.STPLibrary.utility.IOFileUtility;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;

import org.apache.commons.io.FileUtils;

import edu.southampton.wais.utility.Logger;

import gnu.trove.TPrimitiveHash;

public class SelectionDocByRelevanceWithTopics {

	public static void main(String args[]) throws FileNotFoundException, IOException{

	Properties properties= new Properties();
	
	properties.load(new FileInputStream(new File(args[0])));
		 
	
	
	SelectionDocByRelevanceWithTopics computeRelevanceForDocument= new SelectionDocByRelevanceWithTopics();
	
	//load the dic of the topic...
	
	
	 String resultLDA=properties.getProperty("folderResultLDA");
		
	
	String nameObjectMapTopic=properties.getProperty("nameObjectMapTopic");
	

	Map<String,List<String>> mapTopicKeyWordLDA= IOFileUtility.readHashMapStringListString(new File(resultLDA+File.separator+nameObjectMapTopic));
	
	
	Logger.logFiner("loading map key word lda.. done");
	
	
	
	
	
	//load the map with the words for trec topics
	
	
	Logger.logFiner("loading map key word topic trec..");
	
	 
	 String nameTopicIndexString=properties.getProperty("topicsSelection");
	
	 
	 List<String>topicIndex=computeRelevanceForDocument.splitList(nameTopicIndexString, ":");
	
	
	 Map<String,List<String>> mapTopicKeyWordTrec= new HashMap<String, List<String>>(); 
		
	 
	 for(String item:topicIndex){
		 
		 
		 String keyWordTopic=properties.getProperty(item);
				 
		 mapTopicKeyWordTrec.put(item, computeRelevanceForDocument.splitList(keyWordTopic, ":"));
		 
		 
	 }
	

	 Logger.logFiner("loading map key word topic trec.. done");
	
	
	
	 Logger.logFiner("search for relevant topics...");
		
	
	
	 List< DoubleStringNode> listRelevantTopicLDAValue= new ArrayList< DoubleStringNode>(); 
		
	 List<String> listRelevantTopicLDA= new ArrayList<String>(); 
	
	 
	 
	 Map<String,List<DoubleStringListNode<String>>> mapRelevantLDATopic4TrecTopic= new HashMap<String, List<DoubleStringListNode<String>>>();
	 
	 
	 
	 String folderResultLDATopicSelected=properties.getProperty("folderResultLDATopicSelectby4TREC");
	 
	 
	 
	 
	 int maxConsideringWord4Topic=new Integer(properties.getProperty("maxConsideringWord4Topic"));
		
	 
	 
	 
	 
	
		 
		 
	
		 for(String topicTREC: mapTopicKeyWordTrec.keySet()){
	
			 
			 for(String topicLDA: mapTopicKeyWordLDA.keySet()){
				 
			 //split the value	 
			 List<String> listWordLDATopic=computeRelevanceForDocument.splitList(mapTopicKeyWordLDA.get(topicLDA), "\t", 0);
			 List<String> listWordLDATopic2=null;
			 
			 
			 //sublist of kewords
			 if(listWordLDATopic.size()>maxConsideringWord4Topic){
			
				  listWordLDATopic2=listWordLDATopic.subList(0, maxConsideringWord4Topic);
				 
				 
			 }
			 
			 else{
				 
				 listWordLDATopic2=listWordLDATopic;
				 
			 }
			 
			 
			 
			 //get count
			 int count=computeRelevanceForDocument.matchList(listWordLDATopic2,mapTopicKeyWordTrec.get(topicTREC));
			 
			 
			 
			 
			 if(count!=0){
				 
			      //relevant add to the total list of relevant
				  listRelevantTopicLDA.add(topicLDA);
				 
				  DoubleStringNode node= new DoubleStringNode(topicLDA,topicTREC, (double)count);
				  
				  listRelevantTopicLDAValue.add(node);
			 
				  
				  //add to the map of relvant for each trec topic
				  if(mapRelevantLDATopic4TrecTopic.containsKey(topicTREC)){
					  
                      DoubleStringListNode<String> nodeLDA= new DoubleStringListNode<String>();
					  
					  nodeLDA.setName(topicLDA);
					  nodeLDA.setValueDouble((double)count);
					  nodeLDA.setList(mapTopicKeyWordLDA.get(topicLDA));
					  
					  
					  mapRelevantLDATopic4TrecTopic.get(topicTREC).add(nodeLDA);
					  
				  }
				  
				  else{
					  
					  List<DoubleStringListNode<String>>l= new ArrayList<DoubleStringListNode<String>>();
					  
					  DoubleStringListNode<String> nodeLDA= new DoubleStringListNode<String>();
					  
					  nodeLDA.setName(topicLDA);
					  nodeLDA.setValueDouble((double)count);
					  nodeLDA.setList(mapTopicKeyWordLDA.get(topicLDA));
					  
					  l.add(nodeLDA);
					  mapRelevantLDATopic4TrecTopic.put(topicTREC, l);
					  
					  
					  
				  }
				  
				  
			 }
			 
			
			 
		 }
		 
		}
	
		
		Logger.logFiner(" order topic relevant .. ");
				
		Collections.sort(listRelevantTopicLDAValue);
				
	    Logger.logFiner("done ");
		
	    
	    // print all list of relevant topic
			 
		StringBuffer buffer= new StringBuffer();
		
		buffer.append("---------------------------------------------------------------\n");
		
		for(DoubleStringNode item :listRelevantTopicLDAValue){
			
			
			buffer.append(item.name1+"  "+item.name2+" -----> "+item.values);
			buffer.append("\n");
			
			buffer.append(Joiner.on(" ; ").join(mapTopicKeyWordLDA.get(item.name1)));
			buffer.append("\n");
			buffer.append("---------------------------------------------------------------\n");
			
		}
			 
			 
	 
	
		 String nameFileRankRelevantTopic=properties.getProperty("nameFileRankRelevantTopic");
		
		Logger.logFiner("write topic relevant ");
		 FileUtils.writeStringToFile(new File(folderResultLDATopicSelected+File.separator+nameFileRankRelevantTopic), buffer.toString());
		 
		Logger.logFiner("done");

	 
	 
	 
	   Logger.logFine("stating loading the collection of document by topic");
	
	 
	
	 
	
	   for(String topicTrec:mapRelevantLDATopic4TrecTopic.keySet()){
		   
		   
		   List<DoubleStringListNode<String>> l= mapRelevantLDATopic4TrecTopic.get(topicTrec);
		   
		   Collections.sort(l);
		   
		   
		   
		    buffer= new StringBuffer();
			
			buffer.append("---------------------------------------------------------------\n");
			
			for(DoubleStringListNode<String> item :l){
				
				
				buffer.append(item.getName()+" -----> "+item.getValueDouble());
				buffer.append("\n");
				
				buffer.append(Joiner.on(" ; ").join(item.getList()));
				buffer.append("\n");
				buffer.append("---------------------------------------------------------------\n");
				
			}
				 
				 
		 
		
			
			Logger.logFiner("write topic relevant for "+topicTrec);
			 FileUtils.writeStringToFile(new File(folderResultLDATopicSelected+File.separator+topicTrec+".txt"), buffer.toString());
			 
			Logger.logFiner("done");

		   
		   
		   
		   
		   
	   }
	   
	 
	
	 
	 
	 
	 
		

		CollectionTextFile collectionTextFile = IOUtility4STPLibrary
				.loadCollectionTextFile(new File(resultLDA), "collection");
		
		
	

		
		
		
	
		String topicSuffix=properties.getProperty("topicSuffix");
		
		String nameCollectionTopic=properties.getProperty("nameCollection");
	
		
		CollectionDocumentByTopic collectionDocumentByTopic=new CollectionDocumentByTopic(nameCollectionTopic);
		
		
		
		Logger.logFiner(" building collection of document of topics...");
		
		
		
		
		
		
		 
		
		for(TextFile item:collectionTextFile){
			
			
			String path[]=item.getReference().getReference().toString().split(File.separator);
			
			String name=path[path.length-1];
			
			
			
			if(!name.contains(topicSuffix)){
				
				
				DocumentModelByTopic doc= new DocumentModelByTopic(item);
				doc.parseFile();
				
				collectionDocumentByTopic.addDocumentModel(doc);
				
			}
			
			
			
			
			
			
			
	}
		
		Logger.logFiner("building collection of topic done..");
		
		
		
		
		
		
		
		
		
		
		
			
	
			
			
		
		
		 
		 Logger.logFiner("Starting Computing Relevance for each document");
		 
		 
		 
			String folderOutRelevanceDoc =properties.getProperty("basefolderOut");
		 	 
			String nameFileRelevanceAll =properties.getProperty("nameFileRelevanceAll");
			
			
			String nameFileRelevanceThre =properties.getProperty("nameFileRelevanceThre");
			
		 
			int th= new Integer(properties.getProperty("relevanceThreshold"));
		 
		 
		 
		 
		 
			 
		for(String topicTREC: mapTopicKeyWordTrec.keySet()){	
	
			
			 List<DoubleSingleNode> docRelevant= new ArrayList<DoubleSingleNode>();
				
			

			 for(DocumentModelByTopic documentModelByTopic:collectionDocumentByTopic){
				 
			
				 Logger.logFiner("Starting Computing Relevance for topic doc "+documentModelByTopic.getTextFile().getReference() +" topic trec "+topicTREC);
					
				 double relevanceDoc=0;
				 
				 for(Map.Entry<Integer, Double> topicProportion:documentModelByTopic){
					 
					 
					 
					 double valueTopicProportionDoc=topicProportion.getValue();
					 
					 
				
					 
					 for(DoubleStringNode node:listRelevantTopicLDAValue){
					
						 
							
					
				                        if(node.name1.equals(topicProportion.getKey().toString().trim())&&node.name2.trim().equals(topicTREC.trim())){	
						                
				                        	relevanceDoc=relevanceDoc+node.values*valueTopicProportionDoc;
				                        
				                        }  
						 
						 
					 }
					 
					 
					 
					
				 }
					 
					 
              
				DoubleSingleNode nodeDoc= new DoubleSingleNode(documentModelByTopic.getTextFile().getReference().toString(),relevanceDoc);
				nodeDoc.setComparetorFull(false); 
				docRelevant.add(nodeDoc);
						  
						  
			
				
			 }
             Logger.logFiner("order document by relevance..");
			 
			 Collections.sort(docRelevant);
			 
			 Logger.logFiner("ordering done");
				 
				 
			 
			 File fAll= new File(folderOutRelevanceDoc+File.separator+topicTREC+"_"+nameFileRelevanceAll);
			 
			 
			 Logger.logFiner("writing the all sublist..");
				
			FileUtils.writeLines(fAll, docRelevant);
			
			 Logger.logFiner("done..");
				
			
			File fTh= new File(folderOutRelevanceDoc+File.separator+topicTREC+"_"+nameFileRelevanceThre);
			 
			
	
			 
			 Logger.logFiner("writing the threshold sublist..");
				
			FileUtils.writeLines(fTh, docRelevant.subList(0, th));
			
			 Logger.logFiner("writing the threshold sublist done");
				
			 
			 
			 
			 
			 
			 }
			 
			 
			 
			
			 	 
			 
	
			
			
			
				 
				 
			 
			
		
		
		
		
		
		
		
		
		
		
	
	
	
	
	
	}





	public int matchList(List<String> topic,List<String> keyWords){
		
		int count=0;
		
		for(String wordTopic:topic){
			
			for(String keyWord:keyWords){
				
				
				if(wordTopic.toLowerCase().equals(keyWord)){
					
					count++;
				}
				
				
			}
			
		
		
		}
		
		return count;
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







}
