package edu.southampton.wais.STPLibrary.utility;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.linguatools.disco.DISCO;
import edu.southampton.wais.utility.datastructure.DoubleDoubleNode;


public class AlgorithmUtilitySTPLibrary {

	
	
	
	public static double computeJaccardSimilarity(
			List<DoubleDoubleNode> listPivot, List<DoubleDoubleNode> listInput,
			double thL, double thSem, double thSyn, DISCO disco) {

		/*
		 * System.out.println("Size input" + listInput.size());
		 * System.out.println(listInput);
		 * 
		 * System.out.println("Size pivot" + listPivot.size());
		 * System.out.println(listPivot);
		 */

		if (listPivot.size() < listInput.size()) {

			Set<String> pivot = new HashSet<String>();

			double count = thL;

			for (DoubleDoubleNode item : listPivot) {

				// just the first thl elment
				if (count == 0) {

					break;
				}

				pivot.add(item.name1);
				count--;

			}

			Set<String> input = new HashSet<String>();

			for (int index = 0; index < pivot.size(); index++) {

				input.add(listInput.get(index).name1);

			}

			double r = jaccardSimilarityWithDisco(pivot, input, thSem, thSyn,
					disco);

			return r;

		}

		else {

			Set<String> pivot = new HashSet<String>();

			Set<String> input = new HashSet<String>();
			double count = thL;

			for (DoubleDoubleNode item : listInput) {
				// just the first thl elment
				if (count == 0) {

					break;
				}

				input.add(item.name1);
				count--;

			}

			for (int index = 0; index < input.size(); index++) {

				pivot.add(listPivot.get(index).name1);

			}

			double r = jaccardSimilarityWithDisco(pivot, input, thSem, thSyn,
					disco);

			return r;

		}

	}

	
	public static double jaccardSimilarityWithDisco(Set<String> pivot,
			Set<String> input, double thSem, double thSyn, DISCO disco) {

		int countIntersection = 0;

		for (String a : pivot) {

			for (String b : input) {

				// equat
				if (a.equals(b)) {
					countIntersection++;
				}
				// syntacti approach

				double simSyn = SyntacticDistanceUtility.getSimilarity(a, b);
				if (simSyn >= thSyn) {
					countIntersection++;
				}

				// semantic approach

				double simSem;
				try {
					simSem = disco.secondOrderSimilarity(a, b);
					if (simSem >= thSem) {
						countIntersection++;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					System.out.print(e.toString());
					System.out.print("Problem with Disco Distance..");
				}

			}
		}// secondo for

		return ((double) countIntersection)
				/ ((double) (pivot.size() + input.size() - countIntersection));

	}

	
	
	public static int computeIntersection(Collection<String> reference,
			Collection<String> input, double th,
			List<String> ListTermIntersection) {

		int count = 0;

		for (String s1 : reference) {

			for (String s2 : input) {

				double value = SyntacticDistanceUtility.getSimilarity(s1, s2);

				if (value >= th) {
					count++;

					ListTermIntersection.add(s2);
				}

			}
		}

		return count;

	}
	
	
}
