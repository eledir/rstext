package edu.southampton.wais.STPLibrary.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;


public class PdfFile extends TextFile implements Serializable{
	private String body;
	private Reference reference;
	PDDocument  pdf;
	
	
	
	public PdfFile(){
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
	public String readFile(File f) throws Exception {
		
		PDFParser parser = new PDFParser(new FileInputStream(f));

		
		parser.parse();
		COSDocument cosDoc = parser.getDocument();
		PDFTextStripper  pdfStripper = new PDFTextStripper();
		PDDocument pdDoc = new PDDocument(cosDoc);

		this.setBody(pdfStripper.getText(pdDoc));
		pdDoc.close();
		cosDoc.close();
		
		
		return this.getBody();
		
		
	}

}
