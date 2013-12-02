package edu.southampton.wais.STPLibrary.stanfordRule;

import edu.southampton.wais.utility.datastructure.SingleNode;

public class RuleIstanceNode {

	
	private int id;
	
	private String type;
	
	private String value;
	
	private SingleNode<Integer,String>token;
	
	
	
	public RuleIstanceNode(int id, String type,String value,SingleNode<Integer,String> token) {
		super();
		this.id = id;
		this.type = type;
		this.value=value;
		this.token=token;
		
	}






	public String getValue() {
		return value;
	}






	public String getType() {
		return type;
	}






	






	public int getId() {
		return id;
	}






	public SingleNode<Integer,String> getToken() {
		return token;
	}






	@Override
	public String toString() {
		return "[" + id + "," + value + "]";
	}






	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
