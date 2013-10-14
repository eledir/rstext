package edu.southampton.wais.crowd.scriptTopic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.utility.IOFileUtility;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import org.apache.commons.io.FileUtils;

import edu.southampton.wais.utility.Logger;

public class TopicMapWordBuilder {

	public static void main(String args[]){
		
		
		
		
        Properties properties= new Properties();
		
		try {
			properties.load(new FileInputStream(new File(args[0])));
	
			
			 String dirIn=properties.getProperty("baseTopicfolderIN");
			 String dirOut=properties.getProperty("baseTopicfolderOut");
					
		
			 

				Logger.logFine("loading collection ");
				

				CollectionTextFile collectionTextFile = IOUtility4STPLibrary
						.loadCollectionTextFile(new File(dirIn), "collection");
				
				
				Logger.logFine("loading done  ");
		
		
				 String sufTopic=properties.getProperty("topicSuffix");
					
		   
				 HashMap<String,List<String>> map= new HashMap<String, List<String>>();
				 
				 Logger.logFine("selection start ");
					
				 
				 for(TextFile item: collectionTextFile){
					
					
					String path[]=item.getReference().getReference().toString().split(File.separator);
					
					String name=path[path.length-1].split("_")[0];
					
					
					
					Logger.logFiner("name "+name);
					
					Logger.logFiner("sufxtopic "+sufTopic);
						
					
					if(name.equals(sufTopic)){

						String number=item.getReference().getReference().toString().split("_")[1].split(".txt")[0];
						
						Logger.logFiner("number "+number);
						
						Logger.logFiner("mathcing done");

						
						List<String> lines=FileUtils.readLines(new File(dirIn+File.separator+item.getReference().toString()));
						
						map.put(number, lines);
						
						
					}
					
				}
		
				 Logger.logFine("selection done ");
					
		
				 Logger.logFine("writing up ");
					
				 String topicNameFile=properties.getProperty("topicMapName");
				
				IOFileUtility.save(new File(dirOut+File.separator+topicNameFile+".bin"), map);
				
				
				 Logger.logFine("writing up done ");
					
				
		
		
		
		
		
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
			 
		 
		
		
	}
	
}
