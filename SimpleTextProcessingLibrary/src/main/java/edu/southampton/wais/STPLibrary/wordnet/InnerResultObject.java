package edu.southampton.wais.STPLibrary.wordnet;

import edu.southampton.wais.STPLibrary.wsd.WSDObject;



public class InnerResultObject implements Comparable<InnerResultObject>{
	 
	 
	 
	  private WSDObject conceptOntology;
	  
	  private WSDObject inputConcept;
	  
	  Double score;

	public InnerResultObject(WSDObject conceptOntology, WSDObject inputConcept,
			Double score) {
		super();
		this.conceptOntology = conceptOntology;
		this.inputConcept = inputConcept;
		this.score = score;
	}

	public WSDObject getConceptOntology() {
		return conceptOntology;
	}

	public WSDObject getInputConcept() {
		return inputConcept;
	}

	public Double getScore() {
		return score;
	}

	@Override
	public int compareTo(InnerResultObject arg0) {
		// TODO Auto-generated method stub
		return this.score.compareTo(arg0.getScore());
	}
	  
	  
	
	
	  
	  
	  
	  
	 
	 
}
