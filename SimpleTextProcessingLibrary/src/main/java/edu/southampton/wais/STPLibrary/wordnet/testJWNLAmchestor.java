package edu.southampton.wais.STPLibrary.wordnet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.southampton.wais.utility.general.TimeMeasureUtility;


import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;

public class testJWNLAmchestor {

	
	
	
	
	
	public static void main (String args[]){
		
		
		File f= new File("config/file_properties.xml");
		FileInputStream fileImFileInputStream=null;
		try {
			fileImFileInputStream = new FileInputStream(f);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}			
		try {
			JWNL.initialize(fileImFileInputStream);
			
		} catch (JWNLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IndexWord iw1=null;
		try {
			iw1 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, "religion");
		} catch (JWNLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IndexWord iw2=null;
		try {
			iw2 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, "Islam");
		} catch (JWNLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		
		  for(Synset  item1 :iw1.getSenses()){
			
			  
			  
			 for(Synset  item2 :iw2.getSenses()){
		
			
			 
			 Synset a;
			try {
				
				TimeMeasureUtility t1= new TimeMeasureUtility();
				t1.update("start");
				a = ExJWNLUtility.computeCommonAncestor(item1, item2);
				t1.update("start");
				System.out.println(a);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			
			 
			 }
			 
		  
		  }
		
	}
	
	
}
