package edu.southampton.wais.STPLibrary.paramater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;




public class Parameter {
    public String AdaptiveSentenceTokenizer="#S";
	
	private Properties properties;

	public String[] folders;
	

	public Parameter(File file) throws FileNotFoundException, IOException {
		super();
	
		
	this.properties=new Properties();
	
	this.properties.load(new FileInputStream(file));
	
	this.dirConf=this.properties.getProperty("nlpFolder");
	
	
	//this.folders=this.properties.getProperty("folders").split(":");
	
	
	this.filePathModelOpenNLPChunker=dirConf+File.separator+"en-chunker.bin";
	
	this.filePathSentenceModelOpenNlp=dirConf+File.separator+"en-sent.bin";
	//"nlp/data";
		
	}
	
	public String dirConf;
	
	

	public  String filePathSentenceModelOpenNlp;
	
	public  Pattern patternSentValidation = Pattern.compile("[a-zA-Z-[\\-\\'\\’\\\"\\”\\“\\”\\t\\n\\s\\-]]+");

	
	
	
	public String language="english";
	
	
	public HashMap<String,String> modelFilePathStanford= new HashMap<String, String>();
	
	
	public String splitStanfordPOS=" ";

	public String slitWordPOSStanford="/|_";

	
	
	
	// means the last two words after the index
	//Example 1 2 3 4 5 6 7 
	// w=3
	// (1,2,3),(1,2,4),
	// (1,3,4)
	
	
	
	
	public int windowTrigram=4;
    // means number -1 as window to formulate the bigram 
	//Example 1 2 3 4 5
	// w=3
	
	// (1,2),(1,3)- (2,3)-(2-4) etc
	
	public int windowBigram=4;

	
	public String filePathModelOpenNLPChunker;



	public int mengifulTerms=3;



	public int chunkMinimuSize=2;
	
	
	//numMin Token for consider a sentence 
	
	public static int numMinTokenSentence=3;

	public static int maxNumTokenSentence=50;

	
	public static int minLengthWordAllowed=3;
	
	

}
