package edu.southampton.wais.utility.general;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLUtility {

	
	public Document getDocumentfromFile(File xmlfile) throws ParserConfigurationException, SAXException, IOException{
	
	if (xmlfile.exists()){
		  
		  
		  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder builder  = factory.newDocumentBuilder();
          Document  doc = builder.parse(xmlfile);
          
          return doc;
    
	}
	
	else{
		
		 throw new FileNotFoundException(xmlfile.getAbsolutePath());
	
	}
	
	
	
	}
	
	
	
	
	
}
