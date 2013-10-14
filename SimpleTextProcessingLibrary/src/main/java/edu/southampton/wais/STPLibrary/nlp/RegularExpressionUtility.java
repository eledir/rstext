package edu.southampton.wais.STPLibrary.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionUtility {

	
Pattern listPattern;
private boolean enable=false;


	
	
public RegularExpressionUtility(){
	
	
}	
	



public void setPatterEnglish(){
	this.enable=true;
	this.listPattern=Pattern.compile("[a-zA-Z-[\\-\\'\\’\\\"\\”\\“\\”\\t\\n\\s\\-]]+");
}





public boolean isEnable(String word){
	
		   
		    
	if(enable){			
				
	Matcher m =this.listPattern.matcher(word);    
				if(m.matches()){
				 return true;}
				else{
					System.out.println(word);
				return false;
				}  	
	}
	else
		return true;
		
}


public void disable(){
	this.enable=false;
}


}
