package edu.southampton.wais.STPLibrary.wordnet;

public class AnchestorObject {

	
	
	private ExendedSynset s1;
	
	
	private ExendedSynset s2;
	
	
	private ExendedSynset anchestor;


	private boolean existAnchestor=false; 
	
	public AnchestorObject(ExendedSynset s1, ExendedSynset s2,
			ExendedSynset anchestor) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.anchestor = anchestor;
	}


	public AnchestorObject(ExendedSynset s1, ExendedSynset s2) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		anchestor=null;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((s1 == null) ? 0 : s1.hashCode());
		result = prime * result + ((s2 == null) ? 0 : s2.hashCode());
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
		AnchestorObject other = (AnchestorObject) obj;
		if (s1 == null) {
			if (other.s1 != null)
				return false;
		} else if (!s1.equals(other.s1))
			return false;
		if (s2 == null) {
			if (other.s2 != null)
				return false;
		} else if (!s2.equals(other.s2))
			return false;
		return true;
	}


	public ExendedSynset getS1() {
		return s1;
	}


	public ExendedSynset getS2() {
		return s2;
	}


	public ExendedSynset getAnchestor() {
		return anchestor;
	}


	public void setAnchestor(ExendedSynset anchestor) {
		this.anchestor = anchestor;
	}


	public boolean isExistAnchestor() {
		return existAnchestor;
	}


	public void setExistAnchestor(boolean existAnchestor) {
		this.existAnchestor = existAnchestor;
	}
	
	
	
	
	
	
	
	
	
	
}
