package edu.southampton.wais.STPLibrary.model;

public class TripleMetaModel {

	
	private MetaNode sub;
	private MetaNode verb;
	private MetaNode obj;
	
	private TripleModel tripleModel;
	
	
	
	
	public TripleMetaModel(TripleModel triple){
		this.tripleModel=triple;
	}
	
	
	
	
	
	
	
	public MetaNode getSub() {
		return sub;
	}







	public void setSub(MetaNode sub) {
		this.sub = sub;
	}







	public MetaNode getVerb() {
		return verb;
	}







	public void setVerb(MetaNode verb) {
		this.verb = verb;
	}







	public MetaNode getObj() {
		return obj;
	}







	public void setObj(MetaNode obj) {
		this.obj = obj;
	}







	public TripleModel getTripleModel() {
		return tripleModel;
	}







	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
