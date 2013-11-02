package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class RuleTemplateNode {

	
	
	
	private int id;
	
	private List<String>posValue;
	
	private boolean posNormalized=true;

	public static enum RuleTemplateNodeEnum{ Noun("n"),Verb("v"),Adj("a"),
		SubjRole("subj"),VerbRole("verb"),ObjRole("obj"),Every("*");
		
	
	
	
	
	
    private final String name;       

    private RuleTemplateNodeEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
	
    
    
    
	};
	
	
	public RuleTemplateNode(int id) {
		super();
		this.id = id;
	    posValue=Lists.newArrayList();
	
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getPosValue() {
		return posValue;
	}

	public void setPosValue(List<String> posValue) {
		this.posValue = posValue;
	}

	public boolean isPosNormalized() {
		return posNormalized;
	}

	public void setPosNormalized(boolean posNormalized) {
		this.posNormalized = posNormalized;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		RuleTemplateNode other = (RuleTemplateNode) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this.getClass())
				.add("ID", this.id)
				.add("PoS Norm", this.posNormalized)
				.add("Pos Type ", Joiner.on(":").join(posValue)).toString();
	}
	
	
	
}
