package edu.southampton.wais.STPLibrary.dataStructure;

public class DoubleDoubleNode  {

	 public String name1;
	 public double values1;
	 public double values2;
	
	 
	 
	 public DoubleDoubleNode() {
			super();
		}



	public DoubleDoubleNode(String name1, double values1, double values2) {
		super();
		this.name1 = name1;
		this.values1 = values1;
		this.values2 = values2;
	}



	public String getName1() {
		return name1;
	}



	public void setName1(String name1) {
		this.name1 = name1;
	}



	public double getValues1() {
		return values1;
	}



	public void setValues1(double values1) {
		this.values1 = values1;
	}



	public double getValues2() {
		return values2;
	}



	public void setValues2(double values2) {
		this.values2 = values2;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name1 == null) ? 0 : name1.hashCode());
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
		DoubleDoubleNode other = (DoubleDoubleNode) obj;
		if (name1 == null) {
			if (other.name1 != null)
				return false;
		} else if (!name1.equals(other.name1))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "(n=" + name1 + ", v1=" + values1
				+ ", v2=" + values2 + ")";
	}
	 

	 
	 
	 

}
	 
	 
	 
