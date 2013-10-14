package edu.mit.svd.utlility;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;


public class SingularValueDecompositionColt {
	
	
	
	private  DoubleMatrix2D u;
	private  DoubleMatrix2D v;
	private  DoubleMatrix2D vt;
	
	
	private boolean  transpose=false;
	private  DoubleMatrix1D e;
	
	
	
	
	
	
	public SingularValueDecompositionColt(DoubleMatrix2D u,double[] e, DoubleMatrix2D v ) {
		
		this.u = u;
		this.v = v;
		this.e = new DenseDoubleMatrix1D(e);
	}
	public DoubleMatrix2D getU() {
		return u;
	}
	public DoubleMatrix2D getV() {
		return v;
	}
	
	public DoubleMatrix1D getE() {
		return e;
	}
	
	
	

	
	
	public DoubleMatrix2D getVT() {
		
		if(!transpose){
			Algebra algebra= new Algebra();
		vt=algebra.transpose(v);
	    transpose=true;	
		return vt;
	 	
		}
		else{
			return vt;
		}
	
	
	}
	
	
	
	public DoubleMatrix2D  computeApproximationDoc(int dimension){
		  
		 
		  
		  
		 DoubleMatrix2D vt=this.vt;
		 DoubleMatrix1D engV=this.e;
		  
		 Algebra algebra= new Algebra(); 
		  
		 
		   
		  
		  DoubleMatrix2D vtk=algebra.subMatrix(vt, 0 , dimension,0, vt.columns()-1);
		  
		  DoubleMatrix2D vtengk= new DenseDoubleMatrix2D(dimension+1, vtk.columns());
		  for (int i = 0; i < vtk.rows(); i++) {
			for (int j = 0; j < vtk.columns(); j++) {
				
				vtengk.set(i, j, vtk.get(i, j)*engV.get(i));
			}
		} 
		  
		  
		  
		  return vtengk;
		 
		 
		    
		  
		  
	  }
	
	public  int cutOff(DoubleMatrix1D vet) {

		DoubleMatrix1D vetderivate1 = vetderivate1(vet);
		
		
		

		Algebra algebra= new Algebra(); 
		
	
		double[] maxDrop = getMaxLocation(vetderivate1);

		/*double averagedrop = 0;
		for (int i = 0; i < (int) maxDrop[1]; i++) {
			averagedrop = averagedrop + vetderivate1.get(i);
		}
		averagedrop = averagedrop / maxDrop[1];

		int catOff = 0;
		for (int i = 0; i < vetderivate1.cardinality(); i++) {
			if (vetderivate1.get(i) >= averagedrop) {
				catOff = i;
				break;
			}
		}*/

		return (int)maxDrop[1]+1;

	}

	private  double[] getMaxLocation(DoubleMatrix1D vetderivate1) {
		
		     double[] r= new double[2];
		
		     r[0]=vetderivate1.get(0);
		     r[1]=0;
		     
		     
		     for (int i = 1; i < vetderivate1.size(); i++) {
				
		    	   if(r[0]<=vetderivate1.get(i)){
		    		   r[0]=vetderivate1.get(i);
		  		       r[1]=i;
		  		     	   
		    		   
		    	   }
			}
		
		// TODO Auto-generated method stub
		return r;
	}
	public static DoubleMatrix1D vetderivate1(DoubleMatrix1D vet) {

		DoubleMatrix1D vetDer = new DenseDoubleMatrix1D(vet.cardinality() - 1);

		for (int i = 0; i < vet.cardinality() - 1; i++) {

			vetDer.set(i, Math.abs(vet.get(i + 1) - vet.get(i)));

		}

		return vetDer;

	}
	public DoubleMatrix2D computeAutomaticApproximation() {
		
		
		//found the best cutt off
		//int index=this.cutOff(e);
		
		int index=this.e.size()/2;
		
		
		System.out.println("cut off "+index);
		
		//produce a rank approximaiton  of eing
		
		
		DoubleMatrix2D egink= new SparseDoubleMatrix2D(this.e.size(),this.e.size());
		
		for (int i = 0; i <index; i++) {
			 egink.set(i, i, e.get(i));
			
		}
		
		
		
		//compute a produtct
		
		
		Algebra algebra= new Algebra();
		
		DoubleMatrix2D uk=algebra.mult(u, egink);
		DoubleMatrix2D vk=algebra.mult(uk, v);
		
		
		
		// TODO Auto-generated method stub
		return vk;
	}

	
}
