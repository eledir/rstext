/**
 *   @author Adrian Kuhn
 *   @author David Erni   
 *             
 *      Copyright (c) 2010 University of Bern
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package edu.mit.tedlab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class DMat {
    public int rows;
    public int cols;
    public double[][] value; /*
     * Accessed by [row][col]. Free value[0] and value to
     * free.
     */

    public DMat(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.value = new double[rows][cols];
    }


    public  void prinMatrix(File dir, String nameFile,String separator) throws IOException{
		
		
		   


	      
        
        if (!dir.exists())
        {
            dir.mkdirs();
        }




	      
		FileWriter fmat = new FileWriter(dir.getAbsolutePath()+ File.separator +nameFile);
	      PrintWriter pmat = new PrintWriter(fmat);

	      for (int i = 0; i <this.rows ; i++) {
	        for (int j = 0; j <this.cols; j++) {

	          pmat.print(this.value[i][j]);
	          pmat.print(separator);
	        } //termino il primo for
	        pmat.println();

	      } //termino il secondo for
	      pmat.close();
	      fmat.close();

	   

}




}