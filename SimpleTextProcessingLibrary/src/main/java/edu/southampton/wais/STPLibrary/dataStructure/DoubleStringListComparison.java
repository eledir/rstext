package edu.southampton.wais.STPLibrary.dataStructure;

public class DoubleStringListComparison implements Comparable<DoubleStringListComparison>{

	
	
	private DoubleStringListNode<?> doubleStringListNode;
	
	
	private double similarityValue;
	
	private int index;

	
	
	
	
	
	
	
	
	
	
	public DoubleStringListComparison() {
		super();
	}

	public DoubleStringListComparison(
			DoubleStringListNode<?> doubleStringListNode, double value,
			int index) {
		super();
		this.doubleStringListNode = doubleStringListNode;
		this.similarityValue = value;
		this.index = index;
	}

	public DoubleStringListNode<?> getDoubleStringListNode() {
		return doubleStringListNode;
	}

	public void setDoubleStringListNode(DoubleStringListNode<?> doubleStringListNode) {
		this.doubleStringListNode = doubleStringListNode;
	}




	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getSimilarityValue() {
		return similarityValue;
	}

	public void setSimilarityValue(double similarityValue) {
		this.similarityValue = similarityValue;
	}

	@Override
	public int compareTo(DoubleStringListComparison arg0) {
		
		
		Double in= new Double(arg0.similarityValue);
		Double thisValue= new Double(similarityValue);
		
		
		// TODO Auto-generated method stub
		return -thisValue.compareTo(in);
	}
	
	
	
	
	
	
	
	
}
