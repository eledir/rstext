package edu.southampton.wais.STPLibrary.file;
import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.io.*;
import java.net.*;

public class Reference implements Serializable {


File file;

URL url;

String type;


private String classMembership;

public Reference() {
	super();
}




public Reference(File item) {
this.file=item;
	// TODO Auto-generated constructor stub
}




public Reference(File file, String type) {
	super();
	this.file = file;
	this.type = type;
}




public Reference(URL url, String type) {
	super();
	this.url = url;
	this.type = type;
}




public String getType() {
	return type;
}




public void setType(String type) {
	this.type = type;
}




public Reference(URL url) {
	super();
	this.url = url;
}




public Object getReference(){
	
	 if(file!=null){
		 return file;
	 }
	
	 else if (url!=null)
	 {
		return url; 
		 
		 
	 }
		 
	 else
		 return null;
}




@Override
public String toString() {
	 if(file!=null){
		 return file.getName();
	 }
	
	 else if (url!=null)
	 {
		return url.getFile(); 
		 
		 
	 }
		 
	 else
		 return "empty resources..";
}




public String getNameForProperties(){
	
	if(file!=null){
		 return file.getName();
	 }
	
	 else if (url!=null)
	 {
		return url.getFile(); 
		 
		 
	 }
		 
	 else 
	 { return null;}
	
}




public String getClassMembership() {
	return classMembership;
}




public void setClassMembership(String classMembership) {
	this.classMembership = classMembership;
}





}