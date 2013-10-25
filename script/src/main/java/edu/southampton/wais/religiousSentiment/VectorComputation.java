package edu.southampton.wais.religiousSentiment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.JWNLException;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.sentiment.PolaritySuperSense2;
import edu.southampton.wais.STPLibrary.sentiment.SentimentComputing;
import edu.southampton.wais.utility.general.UtilitySearialization;

import java.io.*;
public class VectorComputation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


        File f= new File("config/file_properties.xml");
		
		
		
		
		//Load list sentiment
		
		try {
			
			
			
			Map<String,List<String>> mapAll=(Map<String, List<String>>) UtilitySearialization.deserialiseObject(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/mapSentiment/mapAll.bin"));
			
			
			List<String> location=FileUtils.readLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/LOCATION.txt"));
			
			Collections.sort(location);
			
			List<String> organization=FileUtils.readLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/ORGANIZATION.txt"));
			
			
			Collections.sort(organization);
			
			
			List<String> person=FileUtils.readLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/PERSON.txt"));
			
			
			Collections.sort(person);
				
			
			
			
			Iterator<File>iterator1=FileUtils.iterateFiles(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/objectSentence"),new String[]{"bin"}, true);
			
			
			while(iterator1.hasNext()){
				
					
					SentenceModel sm=(SentenceModel) UtilitySearialization.deserialiseObject(iterator1.next());
			
			
					PolaritySuperSense2 polaritySuperSense2= new PolaritySuperSense2(sm, mapAll);
					
					
					
					
			}
			
			
			
			
			
			
			
			
			
			
			
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//load entity list
		
		//load the sentence model
		
		
		
		//compute the vector
		
		
		
	}

}
