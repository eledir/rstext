package edu.southampton.wais.crowd.scriptFileManger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import org.apache.commons.io.FileUtils;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.utility.general.Logger;
public class SplitLargeFolder {

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

		
		
		String basefolderIN = properties.getProperty("basefolderIN");
			
		//	"/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate/20news-bydate-train";

		
		String basefolderOut = properties.getProperty("basefolderOut");
		
		
		int splitNumber=new Integer(properties.getProperty("splitNumberCollection"));
		
		
		
		
		Logger.logFine("loading collection ");
		

		CollectionTextFile collectionTextFile = IOUtility4STPLibrary
				.loadCollectionTextFile(new File(basefolderIN), "collection");
		
		
		Logger.logFine("splitting  collection");
			
		CollectionTextFile[] collectionTextFilesVet=splitCollections(collectionTextFile,splitNumber);
		
		Logger.logFine("done");
		
		
		int index=0;
		
		for(CollectionTextFile item:collectionTextFilesVet){
			
			File f= new File(basefolderOut+File.separator+index);
			
			try {
				writeCollectionFile(item, f);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			Logger.logInfo("error in writing "+index);
			}
			
			index++;
		}
		
		
		
		
	}


	private static CollectionTextFile[] splitCollections(
			CollectionTextFile collectionTextFile, int splitNumber) {
		
		
		CollectionTextFile [] vet= new CollectionTextFile[splitNumber];
		
		
		int count=collectionTextFile.sizeTexts()/splitNumber;
		
		int index=0;
		
		int countTemp=count;
		for(int i=0;i<vet.length-1;i++){
			
			vet[i]= new CollectionTextFile(collectionTextFile.getName());
			
			
			while(countTemp>0){
				
				
				vet[i].add(collectionTextFile.getDocByIndex(index));
				index++;
				countTemp--;
				
				
				
			}
			
			
			countTemp=count;
			
		}
		
		
		vet[vet.length-1]=new CollectionTextFile(collectionTextFile.getName());
		
		
		while(index<collectionTextFile.sizeTexts()){
			
			vet[vet.length-1].add(collectionTextFile.getDocByIndex(index));
			index++;
		}
		
		
		
		// TODO Auto-generated method stub
		return vet;
	}

	private static  void writeCollectionFile(
			CollectionTextFile collectionTextFile, File dir) throws IOException {
		// TODO Auto-generated method stub
		
		
		if(dir.isDirectory()){

			FileUtils.cleanDirectory(dir);
		}
		else{
		
			dir.mkdir();
		
		}
		
		
		
		
		for(TextFile doc: collectionTextFile){
			
		
			
			FileUtils.writeStringToFile(new File(dir.getAbsolutePath()+File.separator+doc.getReference().toString()), doc.getBody());
			
			
			
		}
		
		
		
		
		
	}

	
	
}
