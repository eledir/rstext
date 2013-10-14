package edu.mit.svd.utlility;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.SingularValueDecomposition;

public class SVDColtComputationFactory {

	
	
	
	
	
	
	
	private static SingularValueDecomposition singularValueDecomposition;
	
	
	
	public static SingularValueDecompositionColt SVDColtComputation(SparseDoubleMatrix2D matrix) {
	
		
		
		
	      singularValueDecomposition=new SingularValueDecomposition(matrix); 
	      
	      DoubleMatrix2D u= singularValueDecomposition.getU();
	      DoubleMatrix2D v= singularValueDecomposition.getV();
	      double e[]=singularValueDecomposition.getSingularValues();
	      
	      
	      return new SingularValueDecompositionColt(u,e,v);
	      
	      
	     
		
		// TODO Auto-generated constructor stub
	}


	
	
	
	
	
	
	
	
	
	
}
