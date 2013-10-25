package edu.southampton.wais.crowd.scriptFileManger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.utility.general.Logger;

public class CopyAllFile {

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

		
		
		String basefolderIN = properties.getProperty("basefolderIN");
			
		//	"/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate/20news-bydate-train";

		
		String basefolderOut = properties.getProperty("basefolderOut");
		
		
		
		
		
		
		Logger.logFine("loading collection ");
		

		CollectionTextFile collectionTextFile = IOUtility4STPLibrary
				.loadCollectionTextAll(new File(basefolderIN), "collection");
		
		
		Logger.logFine("done");
	
	
	System.out.println(collectionTextFile.sizeTexts());
		
	try {
		writeCollectionFile(collectionTextFile, new File(basefolderOut));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	
	
	
	
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
