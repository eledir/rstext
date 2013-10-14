package edu.southampton.wais.STPLibrary.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.poi.POIOLE2TextExtractor;
import org.apache.poi.POITextExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hwpf.extractor.*;
import org.apache.xmlbeans.XmlException;



public class DocFile extends TextFile implements Serializable {

	
	
	private String body;
	private Reference reference;
	
	
	public DocFile(){
		super();
			
		
	}
	
	
	



	






	@Override
	public String getBody() {
		// TODO Auto-generated method stub
		return super.getBody();
	}













	@Override
	public void setBody(String body) {
		// TODO Auto-generated method stub
		super.setBody(body);
	}













	@Override
	public Reference getReference() {
		// TODO Auto-generated method stub
		return super.getReference();
	}













	@Override
	public void setReference(Reference reference) {
		// TODO Auto-generated method stub
		super.setReference(reference);
	}













	@Override
	public String readFile( File f)throws IOException, InvalidFormatException, OpenXML4JException, XmlException {
      
		  FileInputStream input= new FileInputStream(f);
		  if (super.reference.getType().equals("doc")) {
			  WordExtractor word= new WordExtractor(input);
				this.setBody(word.stripFields(word.getText()));
				
				input.close();
				return this.getBody();
		} else if (super.reference.getType().equals("docx")) {
			
			POITextExtractor poitex = ExtractorFactory.createExtractor(input);
			
			this.setBody(poitex.getText());
			input.close();
			return this.getBody();

			
		}  	
	  
		else return null;

	}




	




	
		
}