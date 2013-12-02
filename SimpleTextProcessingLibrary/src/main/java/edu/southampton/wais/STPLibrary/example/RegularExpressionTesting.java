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

		
			
			Pattern patter=Pattern.compile("(subj|obj|verb)=.+");
			
			String test="subjobj=1,2";
			
			System.out.println(patter.matcher(test).matches());
			
			
			
		
		
		
		
	}

}
