package edu.southampton.wais.utility.datastructure;

public class  SingleNode <T1 extends Number,T2 extends Object>{

	private T1 number;
	
	private T2 object;

	
	
	
public SingleNode(){
		
	
		
	}
	
	public SingleNode(T1 t1,T2 t2){
		
		this.number=t1;
		this.object=t2;
		
		
	}
	
	
	public T1 getNumber() {
		return number;
	}

	public void setNumber(T1 number) {
		this.number = number;
	}

	public T2 getObject() {
		return object;
	}

	public void setObject(T2 object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "(" + number + "," + object + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		SingleNode other = (SingleNode) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}
	
	
	
	
	
	
}
