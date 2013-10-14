package edu.southampton.wais.crowd.scriptFileManger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import edu.southampton.wais.utility.Logger;



public class CompareFilefromListAndCopy {

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
			
			
			
			
			String basefolderIN1 = properties.getProperty("basefolderIN");
			
			
			
			Logger.logFine("loading iterator files  ");
			
			
			Iterator<File>iterator1=FileUtils.iterateFiles(new File(basefolderIN1),new String[]{"txt"}, true);
			
			Logger.logFine("done");
			
			
			String basefolderIN2 = properties.getProperty("basefolderIN2");
			
			
			String basefolderOut = properties.getProperty("basefolderOut");
			
			
			String nameFileList = properties.getProperty("nameFileList");
			
			
			CompareFilefromListAndCopy compareFilefromListAndCopy= new CompareFilefromListAndCopy();
			
			try {
			
				Logger.logFine("readinf list files ");
				
				
				List<String> listNameFile=FileUtils.readLines(new File(basefolderIN2+File.separator+nameFileList));
			
				Logger.logFine("number fo file to copy "+listNameFile.size());
				
				Logger.logFine("done");
				
				//List<String> listNameFile2=compareFilefromListAndCopy.splitList(listNameFile, ",", 0);
			
              
				//Collections.sort(listNameFile2);
				
				while(iterator1.hasNext()){
					
					
					File f= iterator1.next();
					
				
					
					for(String item:listNameFile){
					
						
						//String[] item2=item.split(",");
						
						String[] item2=item.split(" ");
						
						//if(item2[0].trim().equals(f.getName())){
						
						if(item2[2].trim().equals(f.getName().split(".txt")[0])){
					
					//if(Collections.binarySearch(listNameFile2,f.getName())>=0){
						
						Logger.logFiner("write file "+f.getAbsolutePath() +" in  "+basefolderOut+File.separator+f.getName());
						
						
						//FileUtils.copyFile(f, new File(basefolderOut+File.separator+f.getName().split(".txt")[0]+"_"+d.intValue()+".txt"));
						
						//FileUtils.copyFile(f, new File(basefolderOut+File.separator+"all"+File.separator+f.getName()));
						
						FileUtils.copyFile(f, new File(basefolderOut+File.separator+File.separator+f.getName().split(".txt")[0]+"_"+item2[0]+"_"+item2[3]+".txt"));
						
						
						
						}
						
						
					}
					
					
				}
				
				Logger.logFine("end");
				
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
			
		

	}
	

public List<String> splitList(List<String> list,String sep, int index){
	
	
	List<String> newList= new ArrayList<String>();
	
	for(String item:list){
		
		newList.add(item.split(sep)[index].trim());
		
	}
	
	return newList;
}

	
	

}
