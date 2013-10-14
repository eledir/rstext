package edu.southampton.wais.crowd.scriptFileManger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

import edu.southampton.wais.utility.Logger;

public class ComparingFiles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
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
		
		
		
		
		String basefolderIN1 = properties.getProperty("basefolderIN");
		String basefolderIN2 = properties.getProperty("basefolderIN2");
		
		//	"/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate/20news-bydate-train";

		
		String logFolder = properties.getProperty("logFolder");
		
		String basefolderOut = properties.getProperty("basefolderOut");
		
		Iterator<File>iterator1=FileUtils.iterateFiles(new File(basefolderIN1),new String[]{"txt"}, true);
		
		Iterator<File>iterator2=FileUtils.iterateFiles(new File(basefolderIN2),new String[]{"txt"}, true);
		
		
		
		List<File> listName1=new ArrayList<File>();
		
	    List<String> listName2=new ArrayList<String>();
		
	    
	    
	    
	    while(iterator1.hasNext()){
			
			
			File f=iterator1.next();
			
			listName1.add(f);
			
			
			
		}
	    
	    
       while(iterator2.hasNext()){
			
			
			File f=iterator2.next();
			
			listName2.add(f.getName());
			
			
			
		}
	   
       
   	Logger.logFine("starting order..");
	Collections.sort(listName2);
	Logger.logFine("ordering done..");

	Logger.logFine("searching for missing..");

	
	List<File> listSelectionNotFound= new ArrayList<File>();
	
	
	for(File item : listName1){
		
		
		if(Collections.binarySearch(listName2, item.getName())<0){
			
			
			listSelectionNotFound.add(item);
		}
		
		
		
	}
       
       
	Logger.logFine("searching for missing done .."+listSelectionNotFound.size());
	    
	
	Logger.logFine("starting copy");
	
	for(File item :listSelectionNotFound){
		
		String path[]=item.getAbsolutePath().split(File.separator);
		
		String name=path[path.length-1].trim();
		
		try {
			FileUtils.copyFile(item, new File(basefolderOut+File.separator+name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Logger.logInfo("Problem saving file "+item.getAbsolutePath());
		}
		
	}
	
	
	Logger.logFine("done");
	
	    


	    
	    
	    
	/*	
       String nameFilePairs = properties.getProperty("filePairsPath");
		
		
		
		
		
		Logger.logFine("buliding selection files");
		
		
		try {
		
			List<String> list=FileUtils.readLines(new File(nameFilePairs));
		
			
			List<String> listSelection= new ArrayList<String>();
			
			List<String> listSelectionNotFound= new ArrayList<String>();
			
			for(String pairs:list){
				
				String name =pairs.split("\n")[0].split(" ")[1].trim();
				
				Logger.logFiner("Name Splitted "+name);
				listSelection.add(name);
			
				
			}
			Logger.logFine("done");
			
		
			
			Logger.logFine("starting order..");
			Collections.sort(listSelection);
			Logger.logFine("ordering done..");

			

			while(iterator1.hasNext()){
				
				
				File f=iterator1.next();
				
				int value=Collections.binarySearch(listSelection, f.getName().split(".txt")[0]);
				if(value<0){
					
					listSelectionNotFound.add(f.getAbsolutePath());
					
				}
				
				
				
			}	
			
			
			
			
			Logger.logInfo("not found "+listSelectionNotFound.size());
		
			Logger.logFine("wrting list");
			FileUtils.writeLines(new File(logFolder+File.separator+"fileNotFound.txt"), listSelectionNotFound);
			Logger.logFine("done");
			
			for(String item :listSelectionNotFound){
				
				String path[]=item.split(File.separator);
				
				String name=path[path.length-1].trim();
				
				FileUtils.copyFile(new File(item), new File(basefolderOut+File.separator+name));
				
			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
*/		
			
	
		
	
	
		
		
		
		
		
		
		

		
			
	}

}
