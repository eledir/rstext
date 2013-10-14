package edu.southampton.wais.STPLibrary.file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.net.*;


public class TxtFile extends TextFile implements Serializable {

	
 	
	@Override
	public String toString() {
		return "TxtFile [reference=" + reference + ", body=" + body + "]";
	}



	Reference reference;

	public TxtFile() {
		super();
	}



	@Override
	public String readFile(File f) throws Exception {
		  final StringBuffer fileString = new StringBuffer("");

	        try {
	            final FileReader fileR = new FileReader(f);
	            final BufferedReader bfr = new BufferedReader(fileR);
	            String line;

	            while (true) {
	                line = bfr.readLine();
	                if (line == null) {
	                    break;
	                }

	                if (line.length() != 0) {
	                    
	                	fileString.append(line+"\n");
	                    
	                }
	            
	            
	            }

	            
	            fileR.close();
	            
	            
	            
	        } catch (Exception e) {
	            //todo should this be empty
	        }

	        
	        this.setBody(fileString.toString());
			
	        
	        
	        return this.getBody();
	        
	       
	    }
	









}