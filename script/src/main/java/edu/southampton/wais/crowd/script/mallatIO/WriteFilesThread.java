package edu.southampton.wais.crowd.script.mallatIO;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.io.FileUtils;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.utility.general.Logger;


public class WriteFilesThread implements Runnable {

	
	
	private ArrayBlockingQueue<CollectionTextFile> arrayBlockingQueue;
	
	
	private String baseFolder;
	
	
	public WriteFilesThread(ArrayBlockingQueue<CollectionTextFile> queque,String baseFolder){
		
		this.arrayBlockingQueue=queque;
		this.baseFolder=baseFolder;
		
	}
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		
					
				CollectionTextFile collectionTextFile;
				try {
					
					collectionTextFile = this.arrayBlockingQueue.take();
			
					
					Logger.logFiner(" start write collection "+collectionTextFile.getName());
					writeCollectionFile(collectionTextFile,this.baseFolder,collectionTextFile.getName());
					Logger.logFiner(" wrote collection "+collectionTextFile.getName());
						
				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	
					Logger.logFiner(this.getClass().toString(), "run..", "Problem queque...");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					Logger.logFiner(this.getClass().toString(), "run..", "Problem writing collection...");
					
				
				}
				
				
				
		
		
		
		
	}



	//write collection
	
	
	
	
	



private  void writeCollectionFile(
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



}
