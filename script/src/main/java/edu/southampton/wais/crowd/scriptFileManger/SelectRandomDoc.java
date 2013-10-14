package edu.southampton.wais.crowd.scriptFileManger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;

public class SelectRandomDoc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		

		String folderIN="/home/antonio/Data/dataChallenge/docChallenge/Archive/FBISCleaned";
		
		String folderOut="/home/antonio/Data/dataChallenge/docChallenge/Archive/FBISCleanedRandom";
		
		
		int tot=200;
		
		CollectionTextFile collectionTextFile = IOUtility4STPLibrary
		.loadCollectionTextFile(new File(folderIN), "collection");

		Random random= new Random();

		List<TextFile> listDoc= new ArrayList<TextFile>();
		
		
		for(int i=0; i<tot;i++){
			
			int idex=random.nextInt(collectionTextFile.sizeTexts());
		
			TextFile f=collectionTextFile.getDocByIndex(idex);
			
			listDoc.add(f);
		}
		

		
		for(TextFile item:listDoc){
			
			
			 
			 try {
				FileUtils.writeStringToFile(new File(folderOut+File.separator+item.getReference().toString()), item.getBody());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}

}
