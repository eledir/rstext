package edu.southampton.wais.STPLibrary.file;

import java.io.File;
import java.io.Serializable;


public abstract class TextFile implements Serializable {

protected Reference reference;

String body;

String clas;


public String getBody(){
	
	return this.body;
};








public void setBody(String body){
	this.body=body;
	
};








public Reference getReference(){
	
	return this.reference;
};







public abstract String readFile(File f)throws Exception;












@Override
public String toString() {
	return body;
}








public  void setReference(Reference reference){
	
	this.reference=reference;
}








public String getClas() {
	return clas;
}








public void setClas(String clas) {
	this.clas = clas;
};












}