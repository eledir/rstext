package edu.mit.svd.utlility;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import edu.mit.tedlab.DMat;
import edu.mit.tedlab.SMat;

public class LSAUtility {

	public static SMat colt2SMat(SparseDoubleMatrix2D matrix) {
		 SMat S= new SMat(matrix.rows(), matrix.columns(),matrix.cardinality());
		 for (int j = 0, n = 0; j < matrix.columns(); j++) {
	            S.pointr[j] = n;
	            for (int i = 0; i < matrix.rows(); i++){
	                double value=matrix.get(i, j);
	            	if (value != 0) {
	                    S.rowind[n] = i;
	                    S.value[n] = value;
	                    n++;
	                }
	        }}
	        S.pointr[S.cols] = S.vals;
	    	
	  S.pointr[S.cols]=S.vals;  		
	    	return	S;
	}


	
	
	public static DenseDoubleMatrix2D dMat2Colt(DMat d){
		
		return new DenseDoubleMatrix2D(d.value);
		
		
	}
	
	
	public static DenseDoubleMatrix1D  vet2Colt(double[] d){
		
		return new DenseDoubleMatrix1D(d);
	}
}