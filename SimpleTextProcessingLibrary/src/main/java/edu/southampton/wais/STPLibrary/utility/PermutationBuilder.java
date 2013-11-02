package edu.southampton.wais.STPLibrary.utility;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import edu.umbc.cs.maple.utils.ColtUtils;

public class PermutationBuilder {

	

	public static void main(String[] args) {

		Multimap<Integer, Integer> map = ArrayListMultimap.create();

		map.putAll(0, Lists.newArrayList(1, 3, 4,8));
		map.putAll(1, Lists.newArrayList(6,7));
		map.putAll(2, Lists.newArrayList(1));

		int[] pMax = new int[map.keySet().size()];

		for (int i = 0; i < pMax.length; i++) {
			pMax[i] = map.get(i).size();
		}

		System.out.println(Joiner.on(" ").join(
				Lists.newArrayList(cumulativeProductVet(pMax))));

		DoubleMatrix2D matrix = createPermute(pMax);

		System.out.println(StringUtils.repeat("*", 10));

		System.out.println(matrix);
		System.out.println(StringUtils.repeat("*", 10));

	}
	
	
	public static DoubleMatrix2D buildIndexMatrix(int[]vetSize){
		
		
		
		
		return createPermute(vetSize);
		
	}
	
	

	private static DoubleMatrix2D createPermute(int[] pMax) {
		// TODO Auto-generated method stub
		int[] cumPruVet = cumulativeProductVet(pMax);
		int[] repVer= new int[cumPruVet.length];
		
		for (int i = 0; i < repVer.length; i++) {
			repVer[i]=cumPruVet[i]/pMax[i];
		}
		
		
		int totalPermu = cumPruVet[0];

		// cumulativeProduct(0, pMax);

		DoubleMatrix2D matrix = new DenseDoubleMatrix2D(totalPermu, pMax.length);
		
		
		for (int i = 0; i < cumPruVet.length; i++) {
		
			  DoubleMatrix1D vet=createVetPermutation(pMax[i], repVer[i],totalPermu);
			  
			  ColtUtils.setcol(matrix, i,vet.toArray());
			
			
		}
		
		
		

		return matrix;

	}
	
	
	
	

	private static DoubleMatrix1D createVetPermutation(int numElem,int rep, int totalPermu){
		
		
		DoubleMatrix1D array= new DenseDoubleMatrix1D(totalPermu);
		
		
		
		for (int i = 0,elem=0; i < totalPermu; i=i+rep,elem++) {
			
			 
			 for (int j = i; j < i+rep; j++) {
				
				   
				   array.set(j, elem%numElem);
			}
		
		 
		} 
		
		
		return array;
		
	}

	private static int[] cumulativeProductVet(int[] pMax) {
		// TODO Auto-generated method stub

		int[] result = new int[pMax.length];

		result[pMax.length - 1] = pMax[pMax.length - 1];

		for (int j = result.length - 2; j >= 0; j--) {

			int previousCum = cumulativePruduct(j, pMax);
			
			result[j] = previousCum;

		}

		return result;
	}

	private static int cumulativePruduct(int j, int[] pMax) {

		if (j == pMax.length - 1) {
			return pMax[pMax.length - 1];
		}

		else {

			return pMax[j] * cumulativePruduct(j + 1, pMax);

		}

	}

}
