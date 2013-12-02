package edu.southampton.wais.STPLibrary.model;

import edu.southampton.wais.utility.datastructure.SingleNode;

public class MetaNode {

	private SingleNode<Integer,String> supersense;
	
	private SingleNode<Integer,String> entity;
	
	
	private boolean supersenseValue=false;
	private boolean entityValue=false;
	
	
	public MetaNode(){}


	public SingleNode<Integer,String> getSupersense() {
		return supersense;
	}


	public void setSupersense(SingleNode<Integer,String> supersense) {
		this.supersense = supersense;
		this.supersenseValue=true;
	}


	public SingleNode<Integer,String> getEntity() {
		return entity;
	}


	public void setEntity(SingleNode<Integer,String> entity) {
		this.entity = entity;
		this.entityValue=true;
	}


	public boolean isSupersense() {
		return supersenseValue;
	}


	public boolean isEntity() {
		return entityValue;
	}
	
	
	
	
	
}
