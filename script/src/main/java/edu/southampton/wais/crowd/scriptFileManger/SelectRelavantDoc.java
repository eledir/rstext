package edu.southampton.wais.crowd.scriptFileManger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;


public class SelectRelavantDoc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		
		String folderIN="/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/LATIMESCleaned";
		
		String folderOut="/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/LATIMESCleanedTraining5";
		
		
		//int tot=100;
		
		CollectionTextFile collectionTextFile = IOUtility4STPLibrary
		.loadCollectionTextFile(new File(folderIN), "collection");

		//Random random= new Random();
		
		
		
		//String [] key= new String[]{"shipwreck","gorges","project","creativity","carbon", "monoxide","poisoning","uv","damage","criminal", "profiling","tourism", "increase","women", "clergy","tourist", "attacks","stirling","engine"};
		
		
		String [] key= new String[]{"carbon", "monoxide", "poisoning"};
		
		
		
		List<TextFile> listDoc= new ArrayList<TextFile>();
		
		
		List<Integer> list= new ArrayList<Integer>();
		
		
		for(TextFile item: collectionTextFile){
			
			List<String> listString=SentenceModelUtility.getWordFromString(item.getBody(), null);
			
			
			boolean ckeck=false;
			for(String keyWord:key){
				
				 if(listString.contains(keyWord)){
			
					 ckeck=true;
				 }else{
						 
						 ckeck=false;
						 break;
								 
					 }	 
					 //listDoc.add(item);
			}		 
					
			
			
			try {
					     if(ckeck){
						 FileUtils.writeStringToFile(new File(folderOut+File.separator+item.getReference().toString()), item.getBody());
					     }} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				
			
			
			
		}
		
		
		
		
	
		
 /*for(TextFile doc: listDoc){
			
		
			
			try {
				FileUtils.writeStringToFile(new File(folderOut+File.separator+doc.getReference().toString()), doc.getBody());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}*/
		
		
		
		
		
		
	}

}
