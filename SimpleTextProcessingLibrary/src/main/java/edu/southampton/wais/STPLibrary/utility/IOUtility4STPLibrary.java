package edu.southampton.wais.STPLibrary.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;



import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.collection.CollectionDocumentModelByWord;
import edu.southampton.wais.STPLibrary.dataStructure.AnnotationObject;
import edu.southampton.wais.STPLibrary.file.DocFile;
import edu.southampton.wais.STPLibrary.file.PdfFile;
import edu.southampton.wais.STPLibrary.file.Reference;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.file.TxtFile;
import edu.southampton.wais.STPLibrary.model.DocumentModelByWord;
import edu.southampton.wais.STPLibrary.model.VocabularyMeasure;
import edu.southampton.wais.STPLibrary.model.VocabularyModel;
import edu.southampton.wais.STPLibrary.model.WordFeatures;
import edu.southampton.wais.utility.general.Logger;



public class IOUtility4STPLibrary {

	
	
	public static void writeHashMap(File f,Map<Integer,String>map) throws IOException{
		
		  FileWriter fileWriter = null;
	    PrintWriter pr = null;
	   
	        fileWriter = new FileWriter(f);
	        pr = new PrintWriter(fileWriter);
	        
	        Set<Integer> set=map.keySet();
	        for (Integer item : set) {
	            pr.write(item.toString() +" = "+ map.get(item).toString()+"\n");
	        }

	        pr.close();
	        fileWriter.close();

	   
	    
		
		
		
	}

	public static void writeHashMap(File f,
			HashMap<Integer, DocumentModelByWord> docMap) throws IOException {
		
		  FileWriter fileWriter = null;
		    PrintWriter pr = null;
		   
		        fileWriter = new FileWriter(f);
		        pr = new PrintWriter(fileWriter);
		        
		        Set<Integer> set=docMap.keySet();
		        for (Integer item : set) {
		            pr.write(item.toString() +" = "+ docMap.get(item).getTextFile().getReference().toString()+"\n");
		        }

		        pr.close();
		        fileWriter.close();

		   
		    
			
			
			
		}
	
	

public static void writeCollectionMetadataFile(File f,
		CollectionDocumentModelByWord list) throws IOException {
	
	  FileWriter fileWriter = null;
	    PrintWriter pr = null;
	   
	        fileWriter = new FileWriter(f);
	        pr = new PrintWriter(fileWriter);
	        
	        pr.write("--------------------------------------------------------------------------\n");
	        for (DocumentModelByWord item : list) {
	        
	        	pr.write("File : "+item.getTextFile().getReference().getNameForProperties()+"\n");
	          //  pr.write("Class : "+item.getClassMembership() +"\n");
	            pr.write(getPrintTable4MetadataTextFile(item));
	            pr.write("\n");
	            pr.write("--------------------------------------------------------------------------\n");
		        
	        }

	        pr.close();
	        fileWriter.close();

	   
	    
		
		
		
	}

public static String getPrintTable4MetadataTextFile(DocumentModelByWord item){
	
	StringBuffer s = new StringBuffer();
	int count=10;
	for(Map.Entry<String, WordFeatures> word : item){
	s.append("("+word.getKey()+"|"+word.getValue().getCount()+"|"+word.getValue().getValue()+"),");
	count--;
	if(count<0){
		s.append("\n");
		count=10;
	}
	}
	
	return s.toString();
}

/*public static String getPrintTable4MetadataTextFile(MetaDataTextFile item){
	
	StringBuffer s = new StringBuffer("Nome: "+item.getTextFile().getReference().toString());
	s.append("\n");
	s.append("Class: "+item.getClassMembership());
	s.append("\n");
	s.append("--------------------------------------------------------------------------\n");
	s.append("Term               | LocalCount                | LocalWeight             |\n");
	s.append("--------------------------------------------------------------------------\n");
	for(Map.Entry<String, MetaDataMeasure> word : item){
	s.append(word.getKey()+"     |"+word.getValue().getCount()+"|"+word.getValue().getValue()+"\n");
	s.append("--------------------------------------------------------------------------\n");
	}
	
	return s.toString();
}*/


public static void writeVocabulary(File f, VocabularyModel voc) throws IOException{
	FileWriter fmat = new FileWriter(f);
    PrintWriter pmat = new PrintWriter(fmat);
    
    
    
    pmat.write("Vocabulary [words:" + voc.sizeWords()+"  files: "+voc.sizeMetaDataTextCollection() + "]\n");
    pmat.write("GlobalMeaasure [max:" + voc.getMaxValueGlobalMeasure()+"  min: "+voc.getMinValueGlobalMeasure() + "]\n");
    pmat.write("--------------------------------------------------\n");
    int count=10;
    for(Map.Entry<String, VocabularyMeasure>item :voc){
    	 
    	 pmat.write(item.getKey()+" "+item.getValue().getGlobalMeasureValue()+"\n");
    	 
    	 for(DocumentModelByWord item2: item.getValue()){
    	 pmat.write(item2.getTextFile().getReference().toString()+",");
    	 count--;
    	 if(count<0){
    		 pmat.write("\n");
    		 count=10;
    	 }
    	 }
    	 
    	 pmat.write("\n---------------------------------------------------------\n");
    	   
    }
    
    
    pmat.close();
    fmat.close();
}




public static CollectionTextFile loadCollectionTextFile(File dir,String name){
	
	
	///get List File, remember hidden files or directory

	//Collection<File> list = FileUtils.listFiles(dir, FileFilterUtils.trueFileFilter(), FileFilterUtils.falseFileFilter());

	//=Arrays.asList(listOfFiles);

	//Create a collection of text file

	File[] list=dir.listFiles();

	CollectionTextFile colTextFile= new CollectionTextFile(name);


	///build a collection of file
	for (File item : list){

		TextFile t;
       
		
		Logger.logFiner("load file "+item.getName());
		
		if(item.getName().split("\\.")[1].equals("doc")||item.getName().split("\\.")[1].equals("DOC") ){

			t= new DocFile();
			t.setReference(new Reference(item,"doc"));
			try {
				t.readFile(item);
				

				colTextFile.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}


		else if(item.getName().split("\\.")[1].equals("docx")){
			t= new DocFile();
			t.setReference(new Reference(item,"docx"));
			try {
				t.readFile(item);
				

				colTextFile.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

		else if(item.getName().split("\\.")[1].equals("txt")){
			t= new TxtFile();
			try {
				t.readFile(item);
				t.setReference(new Reference(item));
				colTextFile.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

		else if(item.getName().split("\\.")[1].equals("pdf")){
			t= new PdfFile();
			try {
				t.readFile(item);
				t.setReference(new Reference(item));
				colTextFile.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		
		else{
			
			System.out.println("this file is excluded : "+item.getAbsolutePath());
		}
		
	
	
	
	}

	
	return colTextFile;
}





public static TextFile getTextFile(File item){
	
	
	TextFile t=null;
    
	
	Logger.logFiner("load file "+item.getName());
	
	if(item.getName().split("\\.")[1].equals("doc")||item.getName().split("\\.")[1].equals("DOC") ){

		t= new DocFile();
		t.setReference(new Reference(item,"doc"));
		try {
			t.readFile(item);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}


	else if(item.getName().split("\\.")[1].equals("docx")){
		t= new DocFile();
		t.setReference(new Reference(item,"docx"));
		try {
			t.readFile(item);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	else if(item.getName().split("\\.")[1].equals("txt")){
		t= new TxtFile();
		try {
			t.readFile(item);
			t.setReference(new Reference(item));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	else if(item.getName().split("\\.")[1].equals("pdf")){
		t= new PdfFile();
		try {
			t.readFile(item);
			t.setReference(new Reference(item));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	
	else{
		
		System.out.println("this file is excluded : "+item.getAbsolutePath());
	}
	


	return t;
	

}
	










public  static void writeCollectionFile(
		CollectionTextFile collectionTextFile, String basefolderOut,
		String string) throws IOException {
	// TODO Auto-generated method stub
	
	File dir=new File(basefolderOut+File.separator+string);
	
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




 public static CollectionTextFile  loadCollectionTextAll(File root,String name) {
	// TODO Auto-generated method stub

	 
	Iterator<File> iterator= FileUtils.iterateFiles(root, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
	 
	CollectionTextFile colTextFile= new CollectionTextFile(name);
	while(iterator.hasNext())	{	
TextFile t;
       
File item=iterator.next();
		
		Logger.logFiner("load file "+item.getName());
		
		if(item.getName().split("\\.")[1].equals("doc")||item.getName().split("\\.")[1].equals("DOC") ){

			t= new DocFile();
			t.setReference(new Reference(item,"doc"));
			try {
				t.readFile(item);
				

				colTextFile.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}


		else if(item.getName().split("\\.")[1].equals("docx")){
			t= new DocFile();
			t.setReference(new Reference(item,"docx"));
			try {
				t.readFile(item);
				

				colTextFile.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

		else if(item.getName().split("\\.")[1].equals("txt")){
			t= new TxtFile();
			try {
				t.readFile(item);
				t.setReference(new Reference(item));
				colTextFile.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

		else if(item.getName().split("\\.")[1].equals("pdf")){
			t= new PdfFile();
			try {
				t.readFile(item);
				t.setReference(new Reference(item));
				colTextFile.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		
		else{
			
			System.out.println("this file is excluded : "+item.getAbsolutePath());
		}
		
		
	}
	 
	return colTextFile;
	
	 
}


 
 public static void save(File f, AnnotationObject annotationObject) {
		// TODO Auto-generated method stub

		
		ObjectOutputStream writer=null;
		try {
			writer = new ObjectOutputStream(new FileOutputStream(f));
			
			
			writer.writeObject(annotationObject);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		finally{
			
			if(writer!=null){
				
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		}
		
		
		
	}

 
 public static void writeListTextFileIntoDirClassInfo(
			File dir, List<TextFile> files) {

		int index=0;   
			for(TextFile item : files){
			
			
				String name=item.getReference().toString().split("\\.")[0]+"_"+item.getClas()+".txt";
				
			        
				    File  file= new File(dir+File.separator+name);
		               
				    
				    try {
						FileUtils.writeStringToFile(file, item.getBody());
					
				    
					    int a=index++ % 10;
						if (a==1)
					        System.gc();

				    
				    } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}	
	}

 

	public static AnnotationObject  readAnnotationObject(File f) {
		// TODO Auto-generated method stub

		
		ObjectInputStream reader=null;
		AnnotationObject objet=null;
		try {
			reader = new ObjectInputStream(new FileInputStream(f));
			
		   objet=(AnnotationObject) reader.readObject();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		finally{
			
			if(reader!=null){
				
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		}
		
		return objet;
		
	}
 
 
 
 
 public static void main(String args[]){
	 
	 String path="/home/antonio/Data/dataChallenge/docChallenge/CorpusPairsCleanedWord";
	 
	CollectionTextFile col= loadCollectionTextAll(new File(path), "");
	 
	
	System.out.println(col.sizeTexts());
	 
	 
 }
 

}
