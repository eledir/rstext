package edu.southampton.wais.STPLibrary.model;


import java.io.Serializable;
import java.util.List;

import edu.southampton.wais.STPLibrary.wordnet.ExendedSynset;



public class TripleSVOWordnetBased implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1969952258581491969L;

	private List<ExendedSynset> sub;
	
	private List<ExendedSynset> verb;
	
	
	private List<ExendedSynset> objt;


	public List<ExendedSynset> getSub() {
		return sub;
	}


	public void setSub(List<ExendedSynset> sub) {
		this.sub = sub;
	}


	public List<ExendedSynset> getVerb() {
		return verb;
	}


	public void setVerb(List<ExendedSynset> verb) {
		this.verb = verb;
	}


	public List<ExendedSynset> getObjt() {
		return objt;
	}


	public void setObjt(List<ExendedSynset> objt) {
		this.objt = objt;
	}


	public TripleSVOWordnetBased(List<ExendedSynset> sub, List<ExendedSynset> verb,
			List<ExendedSynset> objt) {
		super();
		this.sub = sub;
		this.verb = verb;
		this.objt = objt;
	}


	
	
	
	
	
	
	
}
