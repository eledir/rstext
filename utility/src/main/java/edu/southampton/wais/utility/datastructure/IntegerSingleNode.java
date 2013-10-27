package edu.southampton.wais.utility.datastructure;


import java.io.Serializable;
import java.util.Comparator;

public class IntegerSingleNode extends Node implements Serializable{

 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private  boolean comparetorFull = false;

public String name;
 
 public int values;
 
 
 
 
 
 
 public static Comparator<IntegerSingleNode> COMPARE_BY_WORD = new Comparator<IntegerSingleNode>() {
     public int compare(IntegerSingleNode one, IntegerSingleNode other) {
         return one.name.compareTo(other.name);
     }
 };

 public static Comparator<IntegerSingleNode> COMPARE_BY_VALUE = new Comparator<IntegerSingleNode>() {
     public int compare( IntegerSingleNode one, IntegerSingleNode other) {
         return one.values > other.values ? 1
              : one.values < other.values ? -1
              : 0; // Maybe compare by name here? I.e. if same age, then order by name instead.
     };
 
 
 };
 
 
 
 
 
 
 

 
 public IntegerSingleNode( ) {
		
	} 
 
 public IntegerSingleNode(String name) {
		super();
		this.name = name;
		
	}
 
public IntegerSingleNode(String name, int values) {
	super();
	this.name = name;
	this.values = values;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getValues() {
	return values;
}

public void setValues(int values) {
	this.values = values;
}





@Override
public String toString() {
	return  name + ","+ values;
}


public int hashCodeFull() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	long temp;
	temp = Double.doubleToLongBits(values);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
}


public boolean equalsFull(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	DoubleSingleNode other = (DoubleSingleNode) obj;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (Double.doubleToLongBits(values) != Double
			.doubleToLongBits(other.values))
		return false;
	return true;
}







@Override
public int hashCode() {
	return this.comparetorFull ? this.hashCodeFull():this.hashCodePartial();
}





public boolean equalsHalf(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	DoubleSingleNode other = (DoubleSingleNode) obj;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	return true;
}


public int hashCodePartial() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	IntegerSingleNode other = (IntegerSingleNode) obj;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	return true;
}

public boolean isComparetorFull() {
	return comparetorFull;
}

public void setComparetorFull(boolean comparetorFull) {
	this.comparetorFull = comparetorFull;
}
 
 

 
	
	
 };
