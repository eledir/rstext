package edu.mit.svd.utlility;


import java.io.File;
import java.io.IOException;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;


import edu.mit.tedlab.SMat;
import edu.mit.tedlab.SVDRec;
import edu.mit.tedlab.Svdlib;

public class LSAComputation {


private Svdlib svdlib;	
private SVDColt svdColt;
private SVDRec svdRec;
	
	public LSAComputation() {
	
		this.svdlib= new Svdlib();
	}

	
	
	
	
	
	public SVDRec computeSVD(SMat mat){
		
		return this.svdlib.svdLAS2A(mat, Math
				.min(mat.cols, mat.rows));
		
	}
	
public SVDRec computeSVD( SparseDoubleMatrix2D mat){
		
	
	    SMat mat2= LSAUtility.colt2SMat(mat);       
	
		this.svdRec= this.svdlib.svdLAS2A(mat2, Math
				.min(mat.columns(), mat.rows()));
		return this.svdRec;
	}
	
	               
  








public void  computeSVDforColt(SparseDoubleMatrix2D mat){
            	 
            	 this.svdRec=this.computeSVD(mat);
            	 
                 
            	    //svdrec.Ut.prinMatrix(new File(System.getProperty("user.dir")),"javaUt.txt", "\t");
					//svdrec.Vt.prinMatrix(new File(System.getProperty("user.dir")),"javaVt.txt", "\t");
	            
            	 
            	 
            	 
            	 
            	 this.svdColt= new SVDColt();
                 
            	 this.svdColt.setUt(LSAUtility.dMat2Colt(svdRec.Ut));
            	 this.svdColt.setVt(LSAUtility.dMat2Colt(svdRec.Vt));
            	 this.svdColt.setEng(LSAUtility.vet2Colt(svdRec.S));
                 
                 
                      
  
  }  
         
	
  
  public void  computeApproximationDoc(int dimension){
	  
	 
	  
	  
	  DenseDoubleMatrix2D vt=svdColt.getVt();
	  DenseDoubleMatrix1D engV=svdColt.getEng();
	  
	  
	  
	 Algebra algebra= new Algebra();
	   
	  
	  DoubleMatrix2D vtk=algebra.subMatrix(vt, 0 , dimension,0, vt.columns()-1);
	  DenseDoubleMatrix2D vtengk= new DenseDoubleMatrix2D(dimension+1, vtk.columns());
	  for (int i = 0; i < vtk.rows(); i++) {
		for (int j = 0; j < vtk.columns(); j++) {
			
			vtengk.set(i, j, vtk.get(i, j)*engV.get(i));
		}
	} 
	  
	  
	  
	  svdColt.setVtengk(vtengk);
	 
	 
	    
	  
	  
  }

public void  computeApproximationTerm(int dimension){
	  
	 
	  
	  DenseDoubleMatrix2D ut=svdColt.getUt();
	  DenseDoubleMatrix1D engV=svdColt.getEng();
	  
	  Algebra algebra= new Algebra();
	  
	  
	 
	  
	  DoubleMatrix2D utk=algebra.subMatrix(ut, 0 , dimension,0, ut.columns()-1);
	  DenseDoubleMatrix2D utengk= new DenseDoubleMatrix2D(dimension+1, utk.columns());
	  
	  for (int i = 0; i < utk.columns(); i++) {
		for (int j = 0; j < utk.rows(); j++) {
			
			utengk.set(j, i, utk.get(j, i)*engV.get(j));
		}
	}
	  
	  
	  
	  
	  
	  
	  svdColt.setUtengk(utengk);
	  
	 
	    
	  
	  
  }


  



public SVDColt getSvdColt() {
	return svdColt;
}






public SVDRec getSvdRec() {
	return svdRec;
}
  
  
  
}
