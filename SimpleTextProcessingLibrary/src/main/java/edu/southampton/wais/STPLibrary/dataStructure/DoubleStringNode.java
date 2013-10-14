package edu.southampton.wais.STPLibrary.dataStructure;

import java.io.Serializable;

public class DoubleStringNode extends Node implements Comparable,Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -4307852611733701234L;
	 
	public String name1;
	
	public String name2;
    
	public double values;
	
	 
	 
	 public DoubleStringNode() {
			super();
		}
	 
	public DoubleStringNode(String name1, String name2, double values) {
		super();
		this.name1 = name1;
		this.name2 = name2;
		this.values = values;
	}
	
        
	
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public double getValues() {
		return values;
	}
	public void setValues(double values) {
		this.values = values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name1 == null) ? 0 : name1.hashCode());
		result = prime * result + ((name2 == null) ? 0 : name2.hashCode());
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
		DoubleStringNode other = (DoubleStringNode) obj;
		
		String  w1=other.name1;
		String w2=other.name2;
		
		if(this.name1.equals(w1)&&this.name2.equals(w2)){
			
			return true;
			
		}
		
		
		else if(this.name1.equals(w2)&&this.name2.equals(w1)){
			
			return true;
			
		}
		
		else{
			
			return false;
			
		}
		
	}

	@Override
	public String toString() {
		return "" + name1 + ";" + name2 + ";"
				+ values + "";
	}

	@Override
	public int compareTo(Object o) {
		
		if (!(o instanceof DoubleStringNode))
		      throw new ClassCastException("A Node object expected.");
	    DoubleStringNode oo=(DoubleStringNode)o;	   
		if(oo.getValues()==this.values)
			return 0;
		else if (oo.getValues()>this.values)
		     return 1;
		else
			return -1;
		
	}
	 
	 
	 
	 

}
