package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class RuleEdge {

	
	
	
	
private List<String>depType;

	
	public RuleEdge(List<String> depType) {
		super();
		this.depType = depType;
	}

	
	public List<String> getDepType() {
		return depType;
	}

	public void setDepType(List<String> depType) {
		this.depType = depType;
	}
	
	
	public void addDepType(String dep){
		
		this.depType.add(dep);
	}
	
	
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this.getClass())
				.add("Dep Type ", Joiner.on(",").join(depType)).toString();
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
