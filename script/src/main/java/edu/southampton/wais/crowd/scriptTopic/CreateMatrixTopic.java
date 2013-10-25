package edu.southampton.wais.crowd.scriptTopic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentByTopic;
import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.model.DocumentModelByTopic;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.utility.general.IOFileUtility;
import edu.southampton.wais.utility.general.Logger;

public class CreateMatrixTopic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		 Properties properties= new Properties();
			
			try {
				properties.load(new FileInputStream(new File(args[0])));
		
				
				 String dirIn=properties.getProperty("folderIn");
				 String dirOut=properties.getProperty("folderOut");
						
				 String mameFileMatrix=properties.getProperty("suffixFileMatrix");
					
					String mameFileIndexFeatures=properties.getProperty("nameFileIndexFeatures");
						
					String mameFileIndexFiles=properties.getProperty("nameFileIndexFiles");
					
					
					String mameFileStatistics=properties.getProperty("suffixFileStatistics");
				 
					
					
					String nameStrategy=properties.getProperty("nameStrategy");
					 
					

					Logger.logFine("loading collection ");
					

					CollectionTextFile collectionTextFile = IOUtility4STPLibrary
							.loadCollectionTextFile(new File(dirIn), "collection");
					
					
					Logger.logFine("loading done  ");
			
					
					String nameObjectMapTopic=properties.getProperty("nameObjectMapTopic");
					

					Map<String,List<String>> mapTopicKeyWordLDA= IOFileUtility.readHashMapStringListString(new File(dirIn+File.separator+nameObjectMapTopic));
					
					
					Logger.logFiner("loading map key word lda.. done");
					
					
					String topicSuffix=properties.getProperty("topicSuffix");
					
					String nameCollectionTopic=properties.getProperty("nameCollection");
					
					
					CollectionDocumentByTopic collectionDocumentByTopic=new CollectionDocumentByTopic(nameCollectionTopic);
					
					
					
					Logger.logFiner(" building collection of document of topics...");
					
					
					
					
					
					
					 
					
					for(TextFile item:collectionTextFile){
						
						
						String path[]=item.getReference().getReference().toString().split(File.separator);
						
						String name=path[path.length-1];
						
						
						
						if(!name.contains(topicSuffix)){
							
							
							DocumentModelByTopic doc= new DocumentModelByTopic(item);
							doc.parseFile();
							
							collectionDocumentByTopic.addDocumentModel(doc);
							
						}
						
						
						
						
						
						
						
				}
					
				
					Logger.logFiner(" building a matrix with dimension "+collectionDocumentByTopic.size()+" "+mapTopicKeyWordLDA.keySet().size());		
					
			DoubleMatrix2D matrix= new SparseDoubleMatrix2D(collectionDocumentByTopic.size(), mapTopicKeyWordLDA.keySet().size());
			
			
			
			int index=0;
			
			
			DescriptiveStatistics stat=new DescriptiveStatistics();
			

			Map<Integer,String> mapFile=Maps.newHashMap();
			
			Map<Integer,List<String>> mapFeature=Maps.newHashMap();
			
			
			
			for(DocumentModelByTopic doc:collectionDocumentByTopic){
				

				
				mapFile.put(index, doc.getTextFile().getReference().toString());
			
				double count=0;
				
				
				for(Map.Entry<Integer, Double> topic:doc){
					
				
					
					matrix.set(index, topic.getKey(), topic.getValue());
					
					stat.addValue(topic.getValue());
					
					count=count+topic.getValue();
					
					if(!mapFeature.containsKey(topic.getKey())){
						
						
						int a=topic.getKey();
						
						List<String> list=mapTopicKeyWordLDA.get((new Integer(a)).toString());
						
						mapFeature.put(topic.getKey(),list);
						
						
					}
					
					
					
					
					
				}
				
				
				if(count==0){
					
					Logger.logInfo("no topic for document "+doc.getTextFile().getReference().toString());
				}
				
			  index++;
			
			}
						
			Logger.logFiner(" building a matrix with dimension done");			
			
			
		
			
			CreateMatrixTopic main= new CreateMatrixTopic();
			
			
			try {
				
				Logger.logFiner(" wrting feature map");			
				
			main.printFeatureMap(dirOut, mameFileIndexFeatures, mapFeature);
			
			
			Logger.logFiner(" writing feature map done");			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
				Logger.logInfo("Problem writing the feature map");

				Logger.logInfo(e.getMessage());

			}
			
			
			try {
				Logger.logFiner(" writing file map");			
				
			main.printFileMap(dirOut, mameFileIndexFiles, mapFile);
		
			Logger.logFiner(" writing file map done");			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
				Logger.logInfo("Problem writing the file map");

				Logger.logInfo(e.getMessage());

			}
			
			try {
				
				Logger.logFiner(" writing matrix ");			
				
			main.printMatrix(dirOut, mameFileMatrix, nameStrategy, matrix);
			Logger.logFiner(" writing matrix done");			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
				Logger.logInfo("Problem writing matrix");

				Logger.logInfo(e.getMessage());

			}
			
			
			
			

			try {
				
				Logger.logFiner(" writing stat");			
				
			main.printStat(dirOut, mameFileStatistics, nameStrategy, stat);
			Logger.logFiner(" writing stat done");			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
				Logger.logInfo("Problem writing stat");

				Logger.logInfo(e.getMessage());

			}
			
			
			
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				 
				 
			 
			
			
		}
		


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
			String mameFileIndexFeatures, Map<Integer, List<String>> mapFeature) throws IOException {
		// TODO Auto-generated method stub
		
	

	  List<String> list=Lists.newArrayList();
	  
	  
	  for(Integer i:mapFeature.keySet()){
		  
		 
		  
		  list.add(i +" : "+Joiner.on(" ; ").join(mapFeature.get(i)));
		  
		  
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



}
