package edu.southampton.wais.utility.general;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;

public class ColtUtility {
	
	
	
	public static void printColtMatrix(File dir, String nameFile, DoubleMatrix2D matrix,String separator) throws IOException{
		
		
		   


	      
	         
	          if (!dir.exists())
	          {
	              dir.mkdirs();
	          }




		      
			FileWriter fmat = new FileWriter(dir.getAbsolutePath()+ File.separator +nameFile);
		      PrintWriter pmat = new PrintWriter(fmat);

		      for (int i = 0; i < matrix.rows(); i++) {
		        for (int j = 0; j <matrix.columns(); j++) {

		          pmat.print(matrix.get(i, j));
		          pmat.print(separator);
		        } //termino il primo for
		        pmat.println();

		      } //termino il secondo for
		      pmat.close();
		      fmat.close();

		   

    }

	
	
	
	public static void printSparseColtMatrix(File dir, String nameFile, DoubleMatrix2D matrix,String separator) throws IOException{
		
		
		   


	      
        
        if (!dir.exists())
        {
            dir.mkdirs();
        }




	      
		FileWriter fmat = new FileWriter(dir.getAbsolutePath()+ File.separator +nameFile);
	      PrintWriter pmat = new PrintWriter(fmat);

	      pmat.print(matrix.rows());
	      pmat.println();
     
	      pmat.print(matrix.columns());
	       pmat.println();
	      
	      
	      for (int i = 0; i < matrix.rows(); i++) {
	        for (int j = 0; j <matrix.columns(); j++) {
               double value=matrix.get(i, j);
	           if(value!=0){
	        	     pmat.print(i +separator+j+separator+value);
	        	     pmat.println();
        	   
	           }
	          
	        } //termino il primo for
	        
	      } //termino il secondo for
	      pmat.close();
	      fmat.close();

	   

}
	
	
	
	
	
	
	public static void printColtMatrix(File dir, String nameFile,
		  DoubleMatrix1D eng, String separator) throws IOException {
		
		  if (!dir.exists())
          {
              dir.mkdirs();
          }




	      
		 FileWriter fmat = new FileWriter(dir.getAbsolutePath()+ File.separator +nameFile);
	      PrintWriter pmat = new PrintWriter(fmat);

	      for (int i = 0; i < eng.cardinality(); i++) {
	        

	          pmat.print(eng.get(i));
	          pmat.print(separator);
	        } //termino il primo for
	        pmat.println();

	    
	      pmat.close();
	      fmat.close();

	   
	}
		


public static SparseDoubleMatrix2D transpose (SparseDoubleMatrix2D matrix){
	
	SparseDoubleMatrix2D matrixT= new SparseDoubleMatrix2D(matrix.columns(),matrix.rows());
	
	
	for (int i = 0; i < matrix.rows(); i++) {
		for (int j = 0; j < matrix.columns(); j++) {
	 
	       matrixT.set(j, i, matrix.get(i, j));
		
		}
	
	}
	
	
	return null;
}




public static String  vet2String(DenseDoubleMatrix1D vet,String separator){
	 
	 StringBuffer buffer= new StringBuffer();
	 for (int i = 0; i < vet.size()-1; i++) {
		  buffer.append(vet.get(i)+separator);
		
	}
 return buffer.append(vet.get((int)vet.size()-1)).toString();
}

public static DenseDoubleMatrix1D getRowVector(int i, DoubleMatrix2D matrix) {
	 DenseDoubleMatrix1D vet= new DenseDoubleMatrix1D(matrix.columns());
	 for (int j = 0; j < matrix.columns(); j++) {
		 vet.set(j,matrix.get(i, j));
		
	}
    return vet;
}





}

 
