package edu.southampton.wais.religiousSentiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.model.AnnotatedWord;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.IOFileUtility;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;
import edu.southampton.wais.utility.Logger;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TagSentences {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String dir="/home/antoniodesktop/Documents/data/nlpdata";
		
		MaxentTagger tagger = new MaxentTagger(dir + File.separator
				+ "english-left3words-distsim.tagger");
		
		
		try {
		  
		 //List<String>list=FileUtils.readLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/allTerm.txt"));
		
		  //Collections.sort(list);
		  
		   
		   List<String>listSentence=FileUtils.readLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/allSentece.txt"));
		
		   
		   
		  
			
			// Get the tokens (and delimiters)
			
			
			
		//   SparseDoubleMatrix2D matrix= new SparseDoubleMatrix2D(listSentence.size(), list.size());		
		
		   
		   List<String> listString=Lists.newArrayList();  
		   
		   
		   
		   for(String doc : listSentence){
			   
			   
			   String tagged = tagger.tagString(doc);
				
				
			   listString.add(tagged);				        
					
					
					
					    
			        
				}
			
			   
		   FileUtils.writeLines(new File("/home/antoniodesktop/Documents/data/iestremedata/allSentence/allSentenceTagged.txt"),listString);	
		   
		   
		/*   
		 System.out.println("write matrix");  
		   
		   BufferedWriter writer = null;
		    try {

		        writer = new BufferedWriter(new FileWriter("/home/antoniodesktop/Documents/data/iestremedata/allSentence/matrix.dat"));
		        for ( int i = 0; i <matrix.rows(); i++)
		        {      
		        	
		        	for ( int j = 0; j <matrix.columns(); j++)
			        {      
			        	
		        	
		        		double v=matrix.get(i, j);
		        		if(v!=0){
		        	
		        			writer.write(i+"\t"+j+"\t"+v+"\n");
		   		          	
		        		
		        		}
		        }
		        }
		    } catch(IOException ex) {
		        ex.printStackTrace();
		    } finally{
		        if(writer!=null){
		            writer.close();
		        }  
		    }	   
*/		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}

}
