package edu.southampton.wais.STPLibrary.dataStructure;


import java.io.Serializable;
import java.util.Comparator;

public class IntegerSingleNode extends Node implements Comparable,Serializable{

 public String name;
 
 public int values;
 
 
 private boolean comparetorFull=false;
 

 
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



public boolean isComparetorFull() {
	return comparetorFull;
}

public void setComparetorFull(boolean comparetorFull) {
	this.comparetorFull = comparetorFull;
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
public int compareTo(Object o) {
	
	if (!(o instanceof DoubleSingleNode))
	      throw new ClassCastException("A Node object expected.");
    DoubleSingleNode oo=(DoubleSingleNode)o;	   
	if(oo.getValues()==this.values)
		return 0;
	else if (oo.getValues()>this.values)
	     return 1;
	else
		return -1;
	

}




@Override
public int hashCode() {
	return this.comparetorFull ? this.hashCodeFull():this.hashCode();
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

@Override
public boolean equals(Object obj) {
	return this.comparetorFull ? this.equalsFull(obj):this.equalsHalf(obj);
}
 
 

 
	
	
}
