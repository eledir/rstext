package edu.southampton.wais.crowd.scriptFileManger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import org.apache.commons.io.FileUtils;

import edu.southampton.wais.utility.Logger;

public class SelectCorpus {

	
	
	public static void main(String args[]){
		
		

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

		
		
		String basefolderIN = properties.getProperty("basefolderIN");
			
		//	"/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate/20news-bydate-train";

		
		String basefolderOut = properties.getProperty("basefolderOut");
		
		
		String logFolder = properties.getProperty("logFolder");
		
		
		
		
		
		Logger.logFine("loading collection ");
		

		CollectionTextFile collectionTextFile = IOUtility4STPLibrary
				.loadCollectionTextFile(new File(basefolderIN), "collection");
		
		
		
		Logger.logFine("loading collection done ");
		
		
		
		Logger.logFine("loading pairs.. ");
		
		
		String nameFilePairs = properties.getProperty("filePairsPath");
		
		
		Map<String,List<TextFile>> map= new HashMap<String, List<TextFile>>();
		
		
		List<String> notfound= new ArrayList<String>();
		
		
		try {
		
			List<String> list=FileUtils.readLines(new File(nameFilePairs));
			
			for(TextFile item : collectionTextFile){
				
				boolean found=false;
				
			
				
				
				String name=item.getReference().toString().split(".txt")[0].trim();
				
		//		Logger.logFine("Search for "+name);
				
				for(String pairs:list){
					
					String[] info=pairs.split("\n")[0].split(" ");
					
			
					
					
					
					if(name.equals(info[1])){
						
			
						//Logger.logFine("Found for "+name);
						
						found=true;
						
					if(map.containsKey(info[0])){
						
							
							map.get(info[0]).add(item);
							
						}
			
					else{
						
						
						List<TextFile> l= new ArrayList<TextFile>();
						l.add(item);
						map.put(info[0], l);
						
						
					}	
						
					
					
					}
					
				}//for list pair
				
				
			
			 if(!found){
				 
				 notfound.add(name);
				 
			 }
			
			
			}
		 
			
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		Logger.logFine("loading pairs mathcing done");
		
		
		
		Logger.logFine("start to wrting the collections ");
		
		
		
		
		try {
			FileUtils.writeLines(new File(logFolder+File.separator+"fileNotFound.txt"), notfound);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			Logger.logInfo("problem writing not found");
			
		}
		
		
		for(String key: map.keySet()){
			
			
			try {
				
				File out=new File(basefolderOut+File.separator+key);
				
				writeCollectionFile(map.get(key),out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
				Logger.logInfo("problem writing folder "+key);
				
			}
				
			
			
		}
		
		
	}
	
	
	
	
	private static  void writeCollectionFile(
			List<TextFile> list, File dir) throws IOException {
		// TODO Auto-generated method stub
		
		
		if(dir.isDirectory()){

//			FileUtils.cleanDirectory(dir);
		}
		else{
		
			dir.mkdir();
		
		}
		
		
		
		
		for(TextFile doc: list){
			
		
			
			FileUtils.writeStringToFile(new File(dir.getAbsolutePath()+File.separator+doc.getReference().toString()), doc.getBody());
			
			
			
		}
		
		
		
		
		
	}
	
	
}
