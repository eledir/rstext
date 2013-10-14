package edu.southampton.wais.STPLibrary.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class TripleSVOModelSyntaxbased  implements Serializable,Iterable<String> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1627007949885126711L;


	


	String context;
	
	
	String subj;
	
	
	
	String verb;
	
	
	String objt;

	
	String subjPos;
	
	String objPos;
	
	
	
double confidence;


private List<String>listSimple;

private List<String>listPos;

	public TripleSVOModelSyntaxbased(String context, String subj,String subjPos, String verb, String objt,String objPos, double confidence) {
		super();
		this.subj = subj;
		this.verb = verb;
		this.objt = objt;
		this.confidence=confidence;
		this.context=context;
		
		this.subjPos=subjPos;
		this.objPos=objPos;
		this.listSimple= Lists.newArrayList();
		this.listPos=Lists.newArrayList();
		
		this.listSimple.add(subj);
		
		
		this.listPos.add(subj);
		
		this.listPos.add(subjPos);
		
		
		this.listSimple.add(verb);
		
		this.listPos.add(verb);
		
		this.listSimple.add(objt);
		
		this.listPos.add(objt);
		this.listPos.add(this.objPos);
		
		
		this.listPos.add((new Double(confidence)).toString());
		
	}



	public String getSubjPos() {
		return subjPos;
	}



	public void setSubjPos(String subjPos) {
		this.subjPos = subjPos;
	}



	public String getObjPos() {
		return objPos;
	}



	public void setObjPos(String objPos) {
		this.objPos = objPos;
	}



	public double getConfidence() {
		return confidence;
	}



	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}



	public String getSubj() {
		return subj;
	}


	public void setSubj(String subj) {
		this.subj = subj;
	}


	public String getVerb() {
		return verb;
	}


	public void setVerb(String verb) {
		this.verb = verb;
	}


	public String getObjt() {
		return objt;
	}


	public void setObjt(String objt) {
		this.objt = objt;
	}


	public String getContext() {
		return context;
	}


	public void setContext(String context) {
		this.context = context;
	}
	
	
	
	
	@Override

	public String toString() {
		return Objects.toStringHelper(this.getClass()).
				add("Sentence : ",this.context).add("", "\n")
				.add("<", this.subj).addValue( this.verb).addValue(this.objt)
				.add(" conf ", this.confidence)
				.add(" >", "\n").toString();	}



	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return this.listSimple.iterator();
	}



	public List<String> getSimpleList() {
		return listSimple;
	}
	
	public List<String> getPOSConficenceList() {
		return listPos;
	}
	
	 
	
	
	
}
