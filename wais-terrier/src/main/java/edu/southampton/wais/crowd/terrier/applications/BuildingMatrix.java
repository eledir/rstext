package edu.southampton.wais.crowd.terrier.applications;

import org.terrier.structures.Index;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.log4j.Logger;

import org.terrier.indexing.Collection;
import org.terrier.indexing.CollectionFactory;
import org.terrier.indexing.Document;
import org.terrier.matching.ResultSet;
import org.terrier.matching.models.InL2;
import org.terrier.matching.models.WeightingModel;
import org.terrier.matching.models.WeightingModelFactory;


import org.terrier.matching.models.queryexpansion.Bo1;
import org.terrier.querying.Manager;
import org.terrier.querying.Request;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.DirectIndex;
import org.terrier.structures.DocumentIndex;
import org.terrier.structures.DocumentIndexEntry;
import org.terrier.structures.Index;
import org.terrier.structures.Lexicon;
import org.terrier.structures.LexiconEntry;
import org.terrier.structures.MetaIndex;
import org.terrier.structures.postings.IterablePosting;

import org.terrier.utility.ArrayUtils;
import org.terrier.utility.Files;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;

public class BuildingMatrix {
	/** The logger used */
	protected static final Logger logger = Logger.getLogger(BuildingMatrix.class);

	private static Properties properties;
	
	/** The filename of the last file results were output to. */
	protected String resultsFilename;
	
	protected String wModel = "PL2";

	private Index index;
	
	
	public BuildingMatrix() {
		this.loadIndex();
		
		}
	
	
	protected void loadIndex() {
		long startLoading = System.currentTimeMillis();
		index = Index.createIndex();
		if (index == null) {
			logger.fatal("Failed to load index. " + Index.getLastIndexLoadError());
			throw new IllegalArgumentException("Failed to load index: " + Index.getLastIndexLoadError());
		}
		long endLoading = System.currentTimeMillis();
		if (logger.isInfoEnabled())
			logger.info("time to intialise index : "
					+ ((endLoading - startLoading) / 1000.0D));
	}
	
	
	
	
	public void buildMatrices(){
		
		
		long startTime = System.currentTimeMillis();

		
		this.loadIndex();
		
		String folderOut=properties.getProperty("folderOut");
		
		String mameFileMatrix=properties.getProperty("suffixFileMatrix");
		
		String mameFileIndexFeatures=properties.getProperty("nameFileIndexFeatures");
			
		String mameFileIndexFiles=properties.getProperty("nameFileIndexFiles");
		
		
		String mameFileStatistics=properties.getProperty("suffixFileStatistics");
		
		
		final String collectionName = MyApplicationSetup.getProperty("trec.collection.class", "TRECCollection");
		
		
		Collection collectionTREC = CollectionFactory.loadCollection(collectionName);
	
		
		
		DirectIndex id1=index.getDirectIndex();
		DocumentIndex id2=index.getDocumentIndex();
		Lexicon<String>lex=index.getLexicon();
		
		MetaIndex mi=index.getMetaIndex();
		
		//System.out.println(le.numberOfEntries());
		
		
		WeightingModel tfidf = WeightingModelFactory.newInstance("TF_IDF", this.index);
		
		WeightingModel drfree = WeightingModelFactory.newInstance("DFRee", this.index);
		
		
		tfidf.setCollectionStatistics(this.index.getCollectionStatistics());
		
		tfidf.setKeyFrequency(1);
		
		drfree.setCollectionStatistics(this.index.getCollectionStatistics());
		
		drfree.setKeyFrequency(1);
		
		
		
		
		
		System.out.println("row number "+id2.getNumberOfDocuments());
		System.out.println("col number "+lex.numberOfEntries());		
		
		
		DoubleMatrix2D matrixCount= new SparseDoubleMatrix2D(id2.getNumberOfDocuments(), lex.numberOfEntries());
		
		DoubleMatrix2D matrixBinary= new SparseDoubleMatrix2D(id2.getNumberOfDocuments(), lex.numberOfEntries());
		
		DoubleMatrix2D matrixTF_IDF= new SparseDoubleMatrix2D(id2.getNumberOfDocuments(), lex.numberOfEntries());
		
		DoubleMatrix2D matrixDRF= new SparseDoubleMatrix2D(id2.getNumberOfDocuments(), lex.numberOfEntries());
		
		

		Map<Integer,String> mapFile=Maps.newHashMap();
		
		Map<Integer,String> mapFeature=Maps.newHashMap();
		
		DescriptiveStatistics statCount=new DescriptiveStatistics();
		
		DescriptiveStatistics statTFIDF=new DescriptiveStatistics();
		
		
		DescriptiveStatistics statDFR=new DescriptiveStatistics();
		
		
		
		
		while(true){
			
			if(collectionTREC.endOfCollection()){
				break;
			}
			
			collectionTREC.nextDocument();
			
			Document doc=collectionTREC.getDocument();
			
			String docIdProperty= doc.getProperty("docno");
			
		//	System.out.println("docno "+docIdProperty);
			
			String  filename=doc.getProperty("filename");
			
			//System.out.println("filename "+filename);
			
			
			
			int docid;
			try {
				docid = mi.getDocument("docno", docIdProperty);
			
				//System.out.println("docid "+docid);
				
			    mapFile.put(docid, filename);
			  
				DocumentIndexEntry docie=id2.getDocumentEntry(docid);
			
			
			
/*			IterablePosting postings = id1.getPostings(docie);
			
			while (postings.next() != IterablePosting.EOL) {
				
				Map.Entry<String,LexiconEntry> lee = lex.getLexiconEntry(postings.getId());
				
				//System.out.println(lee.getKey() + " with frequency " + postings.getFrequency());
			
			}
*/			
			
			
			int [][] mat=id1.getTerms(docie);
		     
			
			 for (int j = 0; j < mat[0].length; j++) {
					
		        	java.util.Map.Entry<String, LexiconEntry> item=lex.getLexiconEntry(mat[0][j]);
		        	
		        	
		          //System.out.println(mat[0][j]);	
		          //System.out.println(mat[1][j]);	
		         //System.out.println(mat[2][j]);	
			        	
		          //System.out.println(item.getKey());
		        	
		        	
		        	mapFeature.put(mat[0][j], item.getKey());
		        	
		        	tfidf.setEntryStatistics(item.getValue());
		        	tfidf.prepare();
		        	
		        	drfree.setEntryStatistics(item.getValue());
		        	drfree.prepare();
		        	
					//System.out.println(item.getValue().getDocumentFrequency());
					//System.out.println(item.getValue().getFrequency());
		       double a=(double)mat[1][j];
		       
		       
		       statCount.addValue(a);
		       
		       double b=docie.getDocumentLength();
			   
		       double valueTFIDF=tfidf.score(a,b);
			
		       double valueDRF=drfree.score(a,b);
				
		       statDFR.addValue(valueDRF);
		       
		       statTFIDF.addValue(valueTFIDF);
		       
		       matrixTF_IDF.set(docid, mat[0][j], valueTFIDF);
		       
			   matrixDRF.set(docid, mat[0][j], valueDRF);
			   
			   matrixCount.set(docid,mat[0][j], a);
			   
			   matrixBinary.set(docid,mat[0][j], 1);
				       	
					
					
		        }
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
		
		
		
		logger.info("Serializing matrices,maps and statistics");
		
		try {
			printMatrix(folderOut,mameFileMatrix,"TF_IDF",matrixTF_IDF);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		

			logger.info("Problem writing matrix tf_idf");
			
			logger.info(e1);
		
		}
		
		try {
			printMatrix(folderOut,mameFileMatrix,"DFR",matrixDRF);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
	logger.info("Problem writing matrix dfr");
			
			logger.info(e1);
		
		}
		
		
		try {
			printMatrix(folderOut,mameFileMatrix,"BINARY",matrixBinary);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
	logger.info("Problem writing matrix binary");
			
			logger.info(e1);
		
		}
		
		
		try {
			printMatrix(folderOut,mameFileMatrix,"COUNT",matrixCount);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		
	logger.info("Problem writing matrix count");
			
			logger.info(e1);
		
		}
		
		
		
		try {
			printFeatureMap(folderOut,mameFileIndexFeatures,mapFeature);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			logger.info("Problem writing the feature map");
			
			logger.info(e);

		
		}
		
		try {
			printFileMap(folderOut, mameFileIndexFiles,mapFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			logger.info("Problem writing the file map");

			logger.info(e);

		}
		
		
		try {
			printStat(folderOut,mameFileStatistics,"TF_IDF",statTFIDF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			logger.info("Problem writing the stat tfidf");

			logger.info(e);

		
		}
		
		try {
			printStat(folderOut,mameFileStatistics,"COUNT",statCount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			logger.info("Problem writing the stat count");

			logger.info(e);

		}
		
		try {
			printStat(folderOut,mameFileStatistics,"DFR",statDFR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			logger.info("Problem writing the stat dfr");

			logger.info(e);

		
		}
		
		
		
		logger.info("Done");
		
		
		
		
		
		long endTime = System.currentTimeMillis();
			
			if(logger.isInfoEnabled())
			
				
				logger.info("Elapsed time="+((endTime-startTime)/1000.0D));
		
		
			
		
		
		}
			
			
		
	/*	int docid = 10; //docids are 0-based
		IterablePosting postings = di.getPostings((BitIndexPointer)doi.getDocumentEntry(docid));
		while (postings.next() != IterablePosting.EOL) {
			Map.Entry<String,LexiconEntry> lee = lex.getLexiconEntry(postings.getId());
			System.out.print(lee.getKey() + " with frequency " + postings.getFrequency());
		}
		
		
		
		
		
		for (int i = 0; i < id2.getNumberOfDocuments(); i++) {
			
			try {
				
				String filenMname=mi.getItem("docno", i);
				
				
				DocumentIndexEntry docie=id2.getDocumentEntry(i);
			
				docie.
				
				 int [][] mat=id1.getTerms(docie);
			        
			        for (int j = 0; j < mat[0].length; j++) {
					
			        	java.util.Map.Entry<String, LexiconEntry> item=le.getLexiconEntry(mat[0][j]);
			        	
			         //System.out.println(mat[0][j]);	
			         //System.out.println(mat[1][j]);	
			         //System.out.println(mat[2][j]);	
				        	
			        	System.out.println(item.getKey());
			        	
			        	tfidf.setEntryStatistics(item.getValue());
			        	tfidf.prepare();
			        	
						//System.out.println(item.getValue().getDocumentFrequency());
						//System.out.println(item.getValue().getFrequency());
			       double a=(double)mat[1][j];
			       double b=docie.getDocumentLength();
						System.out.println(tfidf.score(a,b));
						
			        }
				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
		
	
	





private void printStat(String folderOut, String mameFileStatistics,
			String string, DescriptiveStatistics statTFIDF) throws IOException {
		// TODO Auto-generated method stub
		
	
	
	FileUtils.writeStringToFile(new File(folderOut+File.separator+mameFileStatistics+"_"+string+".txt"),statTFIDF.toString() );
	
	
	}


private void printFileMap(String folderOut, String mameFileIndexFiles,
			Map<Integer, String> mapFile) throws IOException {
		// TODO Auto-generated method stub
		
	
	  List<String> list=Lists.newArrayList();
	  
	  
	  for(Integer i:mapFile.keySet()){
		  
		  
		  list.add(i +" : "+mapFile.get(i));
		  
		  
	  }
	  
	  FileUtils.writeLines(new File(folderOut+File.separator+mameFileIndexFiles), list);
	
	
	}


private void printFeatureMap(String folderOut,
			String mameFileIndexFeatures, Map<Integer, String> mapFeature) throws IOException {
		// TODO Auto-generated method stub
		
	

	  List<String> list=Lists.newArrayList();
	  
	  
	  for(Integer i:mapFeature.keySet()){
		  
		  
		  list.add(i +" : "+mapFeature.get(i));
		  
		  
	  }
	  
	  FileUtils.writeLines(new File(folderOut+File.separator+mameFileIndexFeatures), list);
	
	
	
	
	
	
	}


private void printMatrix(String folderOut, String mameFileMatrix,
			String string, DoubleMatrix2D matrix) throws IOException {
		// TODO Auto-generated method stub
		
	
	File dir= new File(folderOut);
	
	 if (!dir.exists())
     {
         dir.mkdirs();
     }




	      
		FileWriter fmat = new FileWriter(dir.getAbsolutePath()+ File.separator +mameFileMatrix+"_"+string+".txt");
	      PrintWriter pmat = new PrintWriter(fmat);

	      pmat.print(matrix.rows());
	      pmat.println();
  
	      pmat.print(matrix.columns());
	       pmat.println();
	      
	      
	      for (int i = 0; i < matrix.rows(); i++) {
	        for (int j = 0; j <matrix.columns(); j++) {
            double value=matrix.get(i, j);
	           if(value!=0){
	        	     pmat.print(i +" "+j+" "+value);
	        	     pmat.println();
     	   
	           }
	          
	        } //termino il primo for
	        
	      } //termino il secondo for
	      pmat.close();
	      fmat.close();
	
	}


public static void main(String args[]){
	
	
	properties = new Properties();

	try {
		properties.load(new FileInputStream(new File(args[0])));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.info(e);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.info(e);
		
	}
	
	BuildingMatrix matrix= new BuildingMatrix();
	matrix.buildMatrices();
	
}


}
