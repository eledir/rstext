package edu.southampton.wais.STPLibrary.utility;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.text.StyledEditorKit.BoldAction;

import cern.colt.matrix.DoubleMatrix1D;









public class SimpleLogUtility  {

	
	
	
	private boolean variable;

	
	
	
	
	
	public boolean isVariable() {
		return variable;
	}

	public void setVariable(boolean variable) {
		this.variable = variable;
	}
	
	
	public SimpleLogUtility(boolean v) {
		this.variable=v;
	}
	
	

	public void writeLog(String s){


		if(this.variable){
			System.out.println(s);
		} 

	}

	public void writeLog(List<?> s){


		if(this.variable){
			System.out.println(s);
		} 

	}
	
	
	public void writeLog(DoubleMatrix1D vector){


		if(this.variable){
			System.out.println(vector);
		} 

	}
	
	
        
        
        public void writeLog(Object o){


		if(this.variable){
			System.out.println(o.toString());
		} 

	}

}
