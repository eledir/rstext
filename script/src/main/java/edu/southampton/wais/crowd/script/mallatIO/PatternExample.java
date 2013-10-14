package edu.southampton.wais.crowd.script.mallatIO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;

public class PatternExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		String v0="===========================================================================|             Tom Carter             |     carter@photon";
		
		String v1="8) version of the working model of MicroCal Origin, a scientific and technical 2D/3D graphics and data analysis package for Windows 3";
		
		String v2=" It also says it has an expiration date of Sept";
		
		String v3=" 1, 1993";
		
		String v4="737772666@pv025f";
		
		String v5="";
		
		
		
		
	       
		
		PatternExample.process(v0);
		
		PatternExample.process(v1);
		
		
		PatternExample.process(v2);
		
		PatternExample.process(v3);
		PatternExample.process(v4);
		PatternExample.process(v5);

	
	
	}

	
	public static void print(String []v){
		
		
		for(String item : v){
			
			System.out.print(item);
			
		}
		
		System.out.println();
		
	}
	
	
	
	public static void process(String v0){
		
		
		  //    "\\S+"   (anything not whitespace)
        //    "\\w+"    ( A-Z, a-z, 0-9, _ )
        //    "[\\p{L}\\p{N}_]+|[\\p{P}]+"   (a group of only letters and numbers OR
        //                                    a group of only punctuation marks)
    
		
		 Pattern tokenPattern =
	            Pattern.compile("\\w+|[\\p{L}\\p{N}_]+|[\\p{P}]+");
		 
		 
		 
		 
		 ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

	        // Read data from File objects
	        pipeList.add(new Input2CharSequence("UTF-8"));

	        // Regular expression for what constitutes a token.
	        //  This pattern includes Unicode letters, Unicode numbers, 
	        //   and the underscore character. Alternatives:
	        //    "\\S+"   (anything not whitespace)
	        //    "\\w+"    ( A-Z, a-z, 0-9, _ )
	        //    "[\\p{L}\\p{N}_]+|[\\p{P}]+"   (a group of only letters and numbers OR
	        //                                    a group of only punctuation marks)
	       	        // Tokenize raw strings
	        pipeList.add(new CharSequence2TokenSequence(tokenPattern));
	        // Normalize all tokens to all lowercase
	        
	      
	        // Rather than storing tokens as strings, convert 
	        //  them to integers by looking them up in an alphabet.
	        pipeList.add(new TokenSequence2FeatureSequence());

	        // Do the same thing for the "target" field: 
	        //  convert a class label string to a Label object,
	        //  which has an index in a Label alphabet.
	        pipeList.add(new Target2Label());

	        // Now convert the sequence of features to a sparse vector,
	        //  mapping feature IDs to counts.
	        pipeList.add(new FeatureSequence2FeatureVector());
	        
	        

	        SerialPipes serialPipes=new SerialPipes(pipeList);
		
		
		
		 
          Instance instance= new Instance(v0, "a", "a", v0);
       
        
        
          serialPipes.instanceFrom(instance);
          
          
          Alphabet a= instance.getDataAlphabet();
          
          String s=Joiner.on(" ").join(Arrays.asList(a.toArray()));
          
          
          System.out.println(s);
        
        
        
        
        
        
        
        
        
        
        
        
        
	}
	
}
