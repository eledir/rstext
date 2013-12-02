package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class RuleTemplateEdge {

	
	
	private List<String>depType;

    public static enum EdgeType{ Every("*");
		
	
    private final String name;       

    private EdgeType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
	
    
    
    
	};
	
	
	
	
	public RuleTemplateEdge(List<String> depType) {
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
	
	
	
}
