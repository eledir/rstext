package edu.southampton.wais.STPLibrary.example;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class RegularExpressionTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			List<String> s=FileUtils.readLines(new File("exampleRE.txt"));
			
			
			
			
				
			//String a=s.replaceAll("\\n\\s*\\n", ".");
			
			String a="";
           
			System.out.println("@@@@");
			
			
			
			String[] items=a.split("\\.");
			
			for(String item:items){
				
				System.out.println("----");
				
					System.out.println(item);
				
				System.out.println("----");
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
