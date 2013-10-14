package edu.southampton.wais.crowd.scriptFileManger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import edu.southampton.wais.utility.Logger;



public class CombineFileFromDirs {

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
		
		Iterator<File>iterator=FileUtils.iterateFiles(new File(basefolderIN),new String[]{"txt"}, true);
		
		
		
		Logger.logFine("starting writing..");
		
		
		while(iterator.hasNext()){
			
			
			File f=iterator.next();
			
			
			
			
			try {
			
				FileUtils.copyFile(f, new File(basefolderOut+File.separator+f.getName()));
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	
				
				Logger.logFine("problem writing file "+f.getAbsolutePath());
				
				
				
			}
			
			
		}
		
		
		Logger.logFine("done writing..");
		
			}

}
