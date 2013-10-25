package edu.southampton.wais.utility.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DoubleStringListNode<T> implements Iterable<T>,Comparable{
	
	
	
	List<T> list;
	
	private String name;
	
	private String string;
	
	private int valueInt;
	
        private double valueDouble;

	
	
	public int getValueInt() {
		return valueInt;
	}



	public void setValueInt(int valueInt) {
		this.valueInt = valueInt;
	}



	public double getValueDouble() {
		return valueDouble;
	}



	public void setValueDouble(double valueDouble) {
		this.valueDouble = valueDouble;
	}



	public DoubleStringListNode(){
		
		list=new ArrayList<T>();
		
		
	}



	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return this.list.iterator();
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public List<T> getList() {
		return list;
	}



	public void setList(List<T> list) {
		this.list = list;
	}
	


	public void setList(T[]array) {
		this.list = Arrays.asList(array);
	}



	public String getString() {
		return string;
	}



	public void setString(String value) {
		this.string = value;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(valueDouble);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		DoubleStringListNode other = (DoubleStringListNode) obj;
		if (Double.doubleToLongBits(valueDouble) != Double
				.doubleToLongBits(other.valueDouble))
			return false;
		return true;
	}



	@Override
	public int compareTo(Object o) {
		
		DoubleStringListNode<T> oo=(DoubleStringListNode<T>)o;	   
		if(oo.valueDouble==this.valueDouble)
			return 0;
		else if (oo.valueDouble>this.valueDouble)
		     return 1;
		else
			return -1;
		
		
	}




	
	
	



}
