package edu.southampton.wais.STPLibrary.wsd;



import java.io.Serializable;

import edu.southampton.wais.STPLibrary.wordnet.ExendedSynset;
import edu.southampton.wais.utility.datastructure.ListStringNode;



public class WSDObject implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4046516976688718228L;

	private ListStringNode node;
	
	private ExendedSynset synset;
	
	private int score;
	
	
	public WSDObject(ListStringNode node,ExendedSynset synset, int score){
		
		
		
		this.node=node;
		this.synset=synset;
		
		this.score=score;
		
		
		
		
		
	}




	









	public ListStringNode getNode() {
		return node;
	}




	public ExendedSynset getSynset() {
		return synset;
	}




	@Override
	public String toString() {
		return "[node=" + node + ", synset=" + synset + " score "+score+" ]";
	}














	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((synset == null) ? 0 : synset.hashCode());
		return result;
	}














	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WSDObject other = (WSDObject) obj;
		if (synset == null) {
			if (other.synset != null)
				return false;
		} else if (!synset.equals(other.synset))
			return false;
		return true;
	}
	
	

	
	
	
	

}
