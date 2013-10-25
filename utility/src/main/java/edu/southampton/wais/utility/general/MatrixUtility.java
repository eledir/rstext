package edu.southampton.wais.utility.general;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;



public class MatrixUtility {

	 public static void printOnExDir(String nameFile, double[][] mat) {
		    int iw = mat[0].length;
		    int ih = mat.length;

		    try {
		      String homeDirectory = System.getProperty("user.dir");
		          File f = new File(homeDirectory + File.separator +
		                            "stampa_prove");
		          if (!f.exists())
		          {
		              f.mkdir();
		          }





		      
			FileWriter fmat = new FileWriter(f.getAbsolutePath()+ File.separator +nameFile);
		      PrintWriter pmat = new PrintWriter(fmat);

		      for (int i = 0; i < ih; i++) {
		        for (int j = 0; j < iw; j++) {

		          pmat.print(mat[i][j]);
		          pmat.print("\t");
		        } //termino il primo for
		        pmat.println();

		      } //termino il secondo for
		      pmat.close();
		      fmat.close();

		    } //termine del try
		    catch (Exception xx) {}

		  } //termine metodo stampa

	 
	 
	 
	 public static void printOnExDir(File file, double[][] mat) {
		    int iw = mat[0].length;
		    int ih = mat.length;

		    

         //create File
		    
		    if(!file.exists()){
		    	try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		      
			
			try {
				FileWriter fmat;
				fmat = new FileWriter(file);
			
		      PrintWriter pmat = new PrintWriter(fmat);

		      for (int i = 0; i < ih; i++) {
		        for (int j = 0; j < iw; j++) {

		          pmat.print(mat[i][j]);
		          pmat.print("\t");
		        } //termino il primo for
		        pmat.println();

		      } //termino il secondo for
		      pmat.close();
		      fmat.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		  } //termine metodo stampa
	
//adaptation from 	 http://www.cs.columbia.edu/~hanhua/
	 /** binary file of unsigned bytes */
	    public final static int DT_BYTE = 1;
	    /** binary file of signed short integers (16-bits) */
	    public final static int DT_SHORT = 2;
	    /** binary file of signed integers (32-bits) */
	    public final static int DT_INT = 3;
	    /** binary file in IEEE floating format (32-bits) */
	    public final static int DT_FLOAT = 4;
	    /** binary file in IEEE floating format (64-bits) */
	    public final static int DT_DOUBLE = 5;

	    private static int[] dt_size = new int[]{ 0, 1, 2, 4, 4, 8 };

	    /** read a matrix from a binary file.  Note at least one dimension should
	     * be positive
	     * @param fn      the file name
	     * @param type    data type: DT_BYTE, DT_SHORT, DT_INT, DT_FLOAT, DT_DOUBLE
	     * @param m       number of rows (determined by file size if <= 0)
	     * @param n       number of columns (determined by file size if <=0)
	     * @param skip    number of bytes to be skipped from the beginning
	     */
	    public static  double[][] load( String fn, int type, int m, int n) 
	        throws IOException {

	        if ( m <= 0 && n <= 0 )
	            return null;

	        RandomAccessFile file = new RandomAccessFile( fn, "r" );
	        long l = file.length();
	        if ( l < 0 )
	        {
	            file.close();
	            return null;
	        }

	        l /= dt_size[type];
	        if ( m <= 0 )
	            m = (int)( l / n );
	        else if ( n <= 0 )
	            n = (int)( l / m );
	        if ( m <= 0 || n <= 0 )
	        {
	            file.close();
	            return null;
	        }
	        
	        

	       double [][] x = new double[m][n] ;
	        
	        /* file is default saved by columns */
	        switch ( type )
	        {
	        case DT_BYTE:  /* Java byte is signed!! */
	            for ( int j=0; j<n; j++ )
	                for ( int i=0; i<m; i++ )
	                    x[i][j]=(double) (0xff & (int) file.readByte());
	            break;
	        case DT_SHORT:
	            for ( int j=0; j<n; j++ )
	                for ( int i=0; i<m; i++ )
	                	x[i][j]= (double) file.readShort();
	            break;
	        case DT_INT:
	            for ( int j=0; j<n; j++ )
	                for ( int i=0; i<m; i++ )
	                	x[i][j]=(double) file.readInt() ;
	            break;
	        case DT_FLOAT:
	            for ( int j=0; j<n; j++ )
	                for ( int i=0; i<m; i++ )
	                	x[i][j]=(double) file.readFloat() ;
	            break;
	        case DT_DOUBLE:
	            for ( int j=0; j<n; j++ )
	                for ( int i=0; i<m; i++ )
	                	x[i][j]= file.readDouble() ;
	            break;
	        default:
	            file.close();
	            throw new IllegalArgumentException( "Illegal data type" );
	        }
	        file.close();

	        return x;
	    }

	   
	  

	    /** write a matrix to a binary file.  
	     * @param fn      the file name
	     * @param type    data type: DT_BYTE, DT_SHORT, DT_INT, DT_FLOAT, DT_DOUBLE
	     * @param skip    number of bytes to be skipped from the beginning
	     */
	    public static void save( String fn, int type,double[][] matrix) 
	        throws IOException {

	        RandomAccessFile file = new RandomAccessFile( fn, "rw" );
	        
	            
	        /* file is default saved by columns */
	        switch ( type )
	        {
	        case DT_BYTE:
	            for ( int j=0; j<matrix[0].length; j++ )
	                for ( int i=0; i<matrix.length; i++ )
	                    file.writeByte( (byte) (int) matrix[i][ j]  );
	            break;
	        case DT_SHORT:
	        	for ( int j=0; j<matrix[0].length; j++ )
	                for ( int i=0; i<matrix.length; i++ )
	                    file.writeShort( (short) (int) matrix[i][ j] );
	            break;
	        case DT_INT:
	        	for ( int j=0; j<matrix[0].length; j++ )
	                for ( int i=0; i<matrix.length; i++ )
	                    file.writeInt( (int) matrix[i][ j] );
	            break;
	        case DT_FLOAT:
	        	for ( int j=0; j<matrix[0].length; j++ )
	                for ( int i=0; i<matrix.length; i++ )
	                    file.writeFloat( (float) matrix[i][ j] );
	            break;
	        case DT_DOUBLE:
	        	for ( int j=0; j<matrix[0].length; j++ )
	                for ( int i=0; i<matrix.length; i++ )
	                    file.writeDouble( matrix[i][ j] );
	            break;
	        default:
	            file.close();
	            throw new IllegalArgumentException( "Illegal data type" );
	        }
	        file.close();
	    }




		public static void printOnExDir(File file, double[] array) {
			// TODO Auto-generated method stub
			
			   

			    

	         //create File
			    
			    if(!file.exists()){
			    	try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			      
				
				try {
					FileWriter fmat;
					fmat = new FileWriter(file);
				
			      PrintWriter pmat = new PrintWriter(fmat);

			      for (int i = 0; i < array.length; i++) {
			        

			          pmat.print(array[i]);
			          pmat.println();
			        } //termino il primo for
			        

			     
			      pmat.close();
			      fmat.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			  } //termine metodo stampa

	    

	 
}
