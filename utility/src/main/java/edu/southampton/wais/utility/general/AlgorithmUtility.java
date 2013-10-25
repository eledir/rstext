package edu.southampton.wais.utility.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import cern.colt.matrix.*;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import edu.southampton.wais.utility.datastructure.DoubleDoubleNode;
import edu.southampton.wais.utility.datastructure.DoubleSingleNode;
import edu.southampton.wais.utility.datastructure.DoubleStringNode;
import edu.southampton.wais.utility.datastructure.Node;



public class AlgorithmUtility {
/*
	public static int cutOff(DoubleMatrix1D vet) {

		DoubleMatrix1D vetderivate1 = vetderivate1(vet);

		double[] maxDrop = vetderivate1.getMaxLocation();

		double averagedrop = 0;
		for (int i = 0; i < (int) maxDrop[0]; i++) {
			averagedrop = averagedrop + vetderivate1.get(i);
		}
		averagedrop = averagedrop / (int) maxDrop[0];

		int catOff = 0;
		for (int i = 0; i < vetderivate1.cardinality(); i++) {
			if (vetderivate1.get(i) >= averagedrop) {
				catOff = i;
				break;
			}
		}

		return catOff;

	}
*/
	public static DoubleMatrix1D vetderivate1(DoubleMatrix1D vet) {

		DoubleMatrix1D vetDer = new DenseDoubleMatrix1D(vet.cardinality() - 1);

		for (int i = 1; i < vet.cardinality() - 1; i++) {

			vetDer.set(i, vet.get(i + 1) - vet.get(i));

		}

		return vetDer;

	}

	public static double[] intersectionJaccardTanimotoOverlap(Set<String> item,
			Set<String> item2) {

		double[] count = new double[4];
		for (String word : item) {
			for (String word2 : item2) {

				if (word.equals(word2)) {
					count[0]++;
				}
			}
		}

		count[1] = count[0] / (item.size() + item2.size());
		count[2] = count[0] / (item.size() + item2.size() - count[0]);
		count[3] = count[0] / Math.min(item.size(), item2.size());

		return count;
	}

	public static List<DoubleSingleNode> countEqualElement(List<String> list) {

		List<DoubleSingleNode> listout = new ArrayList<DoubleSingleNode>();
		List<String> singleton = new ArrayList<String>();
		int count;
		boolean off = true;

		for (int i = 0; i < list.size(); i++) {
			count = 0;
			off = true;
			for (int j = 0; j < list.size(); j++) {

				if (i != j) {

					if (list.get(i).equals(list.get(j)))
						count++;
				}

				else if (singleton.contains(list.get(i))) {
					off = false;
					break;
				}

			}

			if (off) {
				singleton.add(list.get(i));
				listout.add(new DoubleSingleNode(list.get(i), count + 1));
			}
		}

		return listout;
	}

	public static List<DoubleSingleNode> getMututualInformationfromSingleNode(
			List<DoubleSingleNode> listToMeasure,
			List<DoubleSingleNode> remainList) {

		DoubleMatrix2D matrix = new DenseDoubleMatrix2D(listToMeasure.size(), 6);

		double dimVocabulary1 = 0;
		double dimVocabulary2 = 0;

		for (int i = 0; i < listToMeasure.size(); i++) {

			dimVocabulary1 = dimVocabulary1 + listToMeasure.get(i).values;
		}

		for (int i = 0; i < remainList.size(); i++) {

			dimVocabulary2 = dimVocabulary2 + remainList.get(i).values;
		}

		List<DoubleSingleNode> listUnion = getUnionList(listToMeasure,
				remainList);

		double dimVoucabulary = listUnion.size();

		for (int i = 0; i < matrix.rows(); i++) {

			// x term, y class
			// P(x=1/y=1)
			double value1 = (listToMeasure.get(i).values + 1)
					/ (dimVocabulary1 + dimVoucabulary);
			matrix.set(i, 0, value1);

			// P(x=1/y=0)
			int searchIndex = remainList.indexOf(listToMeasure.get(i));
			if (searchIndex >= 0) {
				double value2 = (remainList.get(searchIndex).values + 1)
						/ (dimVocabulary2 + dimVoucabulary);
				matrix.set(i, 1, value2);
			} else {
				double value2 = (0 + 1) / (dimVocabulary2 + dimVoucabulary);
				matrix.set(i, 1, value2);

			}

		}

		double somma1 = 0;
		double somma2 = 0;

		for (int i = 0; i < matrix.rows(); i++) {

			somma1 = somma1 + matrix.get(i, 0);
			somma2 = somma2 + matrix.get(i, 1);

		}

		for (int i = 0; i < matrix.rows(); i++) {

			// P(x=0/y=1)
			double value1 = somma1 - matrix.get(i, 0);
			matrix.set(i, 2, value1);

			// P(x=0/y=0)
			double value2 = somma2 - matrix.get(i, 1);
			matrix.set(i, 3, value2);

			// P(x=1)

			double value3 = 0.5 * (matrix.get(i, 0) + matrix.get(i, 1));

			matrix.set(i, 4, value3);

			// P(x=0)
			double value4 = 0.5 * (matrix.get(i, 2) + matrix.get(i, 3));

			matrix.set(i, 5, value4);

		}

		List<DoubleSingleNode> result = new ArrayList<DoubleSingleNode>();

		for (int i = 0; i < matrix.rows(); i++) {

			double value = matrix.get(i, 0)
					* Math.log(matrix.get(i, 0) / matrix.get(i, 4));
			value = value + matrix.get(i, 1)
					* Math.log(matrix.get(i, 1) / matrix.get(i, 4));
			value = value + matrix.get(i, 2)
					* Math.log(matrix.get(i, 2) / matrix.get(i, 5));
			value = value + matrix.get(i, 3)
					* Math.log(matrix.get(i, 3) / matrix.get(i, 5));

			// mutual information formula with conditional probability

			value = 0.5 * value;
			result.add(new DoubleSingleNode(listToMeasure.get(i).getName(),
					value));
		}

		return result;

	}

	public static List<DoubleSingleNode> getMututualInformationfromSingleNode2(
			List<DoubleSingleNode> listToMeasure,
			List<DoubleSingleNode> remainList) {

		DoubleMatrix2D matrix = new DenseDoubleMatrix2D(listToMeasure.size(), 4);

		// N11,N10,N01,N00 (term,class)

		// add one smotting

		double sumTermini1 = 0;
		double sumTermini2 = 0;

		for (int i = 0; i < listToMeasure.size(); i++) {

			sumTermini1 = sumTermini1 + listToMeasure.get(i).values;
		}

		for (int i = 0; i < remainList.size(); i++) {

			sumTermini2 = sumTermini2 + remainList.get(i).values;
		}

		// List<SingleNode>listUnion=getUnionList2(listToMeasure,remainList);

		// double dimVoucabulary=listUnion.size();

		for (int i = 0; i < matrix.rows(); i++) {

			// N11
			double value1 = listToMeasure.get(i).values;

			matrix.set(i, 0, 2 * value1);

			// N10

			// search the word in the other class

			int j = remainList.indexOf(listToMeasure.get(i));

			double value2 = 0;

			if (j < 0) {

				matrix.set(i, 1, 1);

			} else {
				value2 = remainList.get(j).values;
				matrix.set(i, 1, 2 * value2);

			}

			// N01

			double value3 = 2 * sumTermini1 - 2 * value1;

			matrix.set(i, 2, value3);

			// N00

			double value4 = 2 * sumTermini2 - 2 * value2;

			matrix.set(i, 3, value4);
		}

		// MatrixUtility.printOnExDir("matrixMutual.txt", matrix.toArray());

		List<DoubleSingleNode> result = new ArrayList<DoubleSingleNode>();

		for (int i = 0; i < matrix.rows(); i++) {

			String word = listToMeasure.get(i).getName();

			double nTot = matrix.get(i, 0) + matrix.get(i, 1)
					+ matrix.get(i, 2) + matrix.get(i, 3);

			double value = (matrix.get(i, 0) / nTot)
					* MathUtility.log2((nTot * matrix.get(i, 0))
							/ ((matrix.get(i, 0) + matrix.get(i, 1)) * (matrix
									.get(i, 0) + matrix.get(i, 2))));

			value = value
					+ (matrix.get(i, 2) / nTot)
					* MathUtility.log2((nTot * matrix.get(i, 2))
							/ ((matrix.get(i, 2) + matrix.get(i, 3)) * (matrix
									.get(i, 0) + matrix.get(i, 2))));

			value = value
					+ (matrix.get(i, 1) / nTot)
					* MathUtility.log2((nTot * matrix.get(i, 1))
							/ ((matrix.get(i, 0) + matrix.get(i, 1)) * (matrix
									.get(i, 1) + matrix.get(i, 3))));

			value = value
					+ (matrix.get(i, 3) / nTot)
					* MathUtility.log2((nTot * matrix.get(i, 3))
							/ ((matrix.get(i, 2) + matrix.get(i, 3)) * (matrix
									.get(i, 1) + matrix.get(i, 3))));

			// mutual information formula with conditional probability

			result.add(new DoubleSingleNode(listToMeasure.get(i).getName(),
					value));
		}

		return result;

	}

	public static List<DoubleStringNode> getMututualInformationfromDoubleNode(
			List<DoubleStringNode> listToMeasure,
			List<DoubleStringNode> remainList) {

		DoubleMatrix2D matrix = new DenseDoubleMatrix2D(listToMeasure.size(), 4);

		// N11,N10,N01,N00 (term,class)

		// add one smotting

		double sumTermini1 = 0;
		double sumTermini2 = 0;

		for (int i = 0; i < listToMeasure.size(); i++) {

			sumTermini1 = sumTermini1 + listToMeasure.get(i).values;
		}

		for (int i = 0; i < remainList.size(); i++) {

			sumTermini2 = sumTermini2 + remainList.get(i).values;
		}

		// List<SingleNode>listUnion=getUnionList2(listToMeasure,remainList);

		// double dimVoucabulary=listUnion.size();

		for (int i = 0; i < matrix.rows(); i++) {

			// N11
			double value1 = listToMeasure.get(i).values;

			matrix.set(i, 0, 2 * value1);

			// N10

			// search the word in the other class

			int j = remainList.indexOf(listToMeasure.get(i));

			double value2 = 0;

			if (j < 0) {

				matrix.set(i, 1, 1);

			} else {
				value2 = remainList.get(j).values;
				matrix.set(i, 1, 2 * value2);

			}

			// N01

			double value3 = 2 * sumTermini1 - 2 * value1;

			matrix.set(i, 2, value3);

			// N00

			double value4 = 2 * sumTermini2 - 2 * value2;

			matrix.set(i, 3, value4);
		}

		// MatrixUtility.printOnExDir("matrixMutual.txt", matrix.toArray());

		List<DoubleStringNode> result = new ArrayList<DoubleStringNode>();

		for (int i = 0; i < matrix.rows(); i++) {

			double nTot = matrix.get(i, 0) + matrix.get(i, 1)
					+ matrix.get(i, 2) + matrix.get(i, 3);

			double value = (matrix.get(i, 0) / nTot)
					* MathUtility.log2((nTot * matrix.get(i, 0))
							/ ((matrix.get(i, 0) + matrix.get(i, 1)) * (matrix
									.get(i, 0) + matrix.get(i, 2))));

			value = value
					+ (matrix.get(i, 2) / nTot)
					* MathUtility.log2((nTot * matrix.get(i, 2))
							/ ((matrix.get(i, 2) + matrix.get(i, 3)) * (matrix
									.get(i, 0) + matrix.get(i, 2))));

			value = value
					+ (matrix.get(i, 1) / nTot)
					* MathUtility.log2((nTot * matrix.get(i, 1))
							/ ((matrix.get(i, 0) + matrix.get(i, 1)) * (matrix
									.get(i, 1) + matrix.get(i, 3))));

			value = value
					+ (matrix.get(i, 3) / nTot)
					* MathUtility.log2((nTot * matrix.get(i, 3))
							/ ((matrix.get(i, 2) + matrix.get(i, 3)) * (matrix
									.get(i, 1) + matrix.get(i, 3))));

			// mutual information formula with conditional probability

			String word1 = listToMeasure.get(i).getName1();
			String word2 = listToMeasure.get(i).getName2();

			result.add(new DoubleStringNode(word1, word2, value));
		}

		return result;

	}

	private static List<DoubleSingleNode> getUnionList(
			List<DoubleSingleNode> listToMeasure,
			List<DoubleSingleNode> remainList) {
		// TODO Auto-generated method stub

		List<DoubleSingleNode> list = new ArrayList<DoubleSingleNode>();
		for (int i = 0; i < listToMeasure.size(); i++) {

			int j = remainList.indexOf(listToMeasure.get(i));
			if (j < 0) {
				list.add(new DoubleSingleNode(listToMeasure.get(i).name,
						listToMeasure.get(i).values));
			}

		}

		list.addAll(remainList);
		return list;

	}

	private static List<DoubleSingleNode> getUnionList2(
			List<DoubleSingleNode> listToMeasure,
			List<DoubleSingleNode> remainList) {
		// TODO Auto-generated method stub

		List<DoubleSingleNode> list = new ArrayList<DoubleSingleNode>();

		if (remainList.size() >= listToMeasure.size()) {

			list.addAll(remainList);

			for (int i = 0; i < listToMeasure.size(); i++) {

				int j = list.indexOf(listToMeasure.get(i));
				if (j < 0) {
					list.add(new DoubleSingleNode(listToMeasure.get(i).name,
							listToMeasure.get(i).values));

				}

				else {
					double valueOld = list.get(j).values;

					list.get(j).setValues(
							valueOld + listToMeasure.get(i).values);

				}

			}

		}

		else {

			list = null;

		}

		return list;

	}

	private static int getSizeUnionList(List<? extends Node> listToMeasure,
			List<? extends Node> remainList) {
		// TODO Auto-generated method stub

		int count = 0;
		for (int i = 0; i < listToMeasure.size(); i++) {

			int j = remainList.indexOf(listToMeasure.get(i));
			if (j < 0) {
				count++;
			}

		}

		return count + remainList.size();

	}

	public static double computeCorrelationList1(
			List<DoubleDoubleNode> listPivot, List<DoubleDoubleNode> listInput,
			double th) {

		double absenceCount = 0;

		double numConcordance = 0;

		double numDiscordance = 0;

		for (DoubleDoubleNode item : listPivot) {

			int j = listInput.indexOf(item);

			if (j < 0) {

				absenceCount++;

			}

			else {

				DoubleDoubleNode elem = listInput.get(j);

				double d = Math.abs(item.values1 - elem.values1);

				if (d <= th) {
					numConcordance++;
				}

				else {

					numDiscordance++;
				}
			}

		}

		return numConcordance - numDiscordance;

		/*
		 * if(absenceCount>listPivot.size()/2){ return 0; }
		 * 
		 * else{
		 * 
		 * return numConcordance-numDiscordance;
		 * 
		 * }
		 */

	}

	public static double computeCorrelationList2(
			List<DoubleDoubleNode> listPivot, List<DoubleDoubleNode> listInput,
			double th) {

		/*
		 * System.out.println("Size input" + listInput.size());
		 * System.out.println(listInput);
		 * 
		 * System.out.println("Size pivot" + listPivot.size());
		 * System.out.println(listPivot);
		 */

		if (listPivot.size() < listInput.size()) {

			Set<String> pivot = new HashSet<String>();

			for (DoubleDoubleNode item : listPivot) {

				pivot.add(item.name1);

			}

			Set<String> input = new HashSet<String>();

			for (int index = 0; index < pivot.size(); index++) {

				input.add(listInput.get(index).name1);

			}

			double[] r = intersectionJaccardTanimotoOverlap(pivot, input);

			return r[1];

		}

		else {

			Set<String> pivot = new HashSet<String>();

			Set<String> input = new HashSet<String>();

			for (DoubleDoubleNode item : listInput) {

				input.add(item.name1);

			}

			for (int index = 0; index < input.size(); index++) {

				pivot.add(listPivot.get(index).name1);

			}

			double[] r = intersectionJaccardTanimotoOverlap(pivot, input);

			return r[1];

		}
	}

	
	
	public static double jaccardSimilarityString(Collection<String> pivot,
			Collection<String> input) {

		int countIntersection = 0;

		for (String a : pivot) {

			for (String b : input) {

				// equat
				if (a.equals(b)) {
					countIntersection++;
				}

			}
		}// secondo for

		return ((double) countIntersection)
				/ ((double) (pivot.size() + input.size() - countIntersection));

	}

	public static double jaccardSimilarity(List pivot, List input) {

		int countIntersection = 0;

		for (Object a : pivot) {

			for (Object b : input) {

				// equat
				if (a.equals(b)) {
					countIntersection++;
				}

			}
		}// secondo for

		return ((double) countIntersection)
				/ ((double) (pivot.size() + input.size() - countIntersection));

	}

	public static double computeKendallTau(int[] X, int[] Y) {
		int n = X.length;
		int numOfPairs = n * (n - 1) / 2;
		int[] XSign = new int[numOfPairs];
		int[] YSign = new int[numOfPairs];
		int counter = 0;
		for (int i = 0; i < n - 1; i++)
			for (int j = i + 1; j < n; j++) {
				try {
					XSign[counter] = X[j] - X[i];
					YSign[counter] = Y[j] - Y[i];
					counter++;
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					System.err.println("counter = " + counter
							+ ", numOfPairs = " + numOfPairs);
					System.err.println("i = " + i + ", j = " + j);
					System.exit(1);
				}
			}
		if (counter != numOfPairs) {
			System.err.println("Error: counter != numOfPairs");
			System.err.println("counter = " + counter + ", numOfPairs = "
					+ numOfPairs);
			System.err.println("Exiting...");
			System.exit(1);
		}
		int C = getNumberOfConcordantPairs(XSign, YSign);
		int D = numOfPairs - C;
		int S = C - D;
		double tau = 2d * S / (n * (n - 1));
		return tau;
	}

	public static int getNumberOfConcordantPairs(int XSign[], int YSign[]) {
		int C = 0;
		int n = XSign.length;
		for (int i = 0; i < n; i++) {
			if (XSign[i] > 0 && YSign[i] > 0 || XSign[i] < 0 && YSign[i] < 0
					|| XSign[i] == 0 && YSign[i] == 0)
				C++;
		}
		return C;
	}

	public static double computeJaccardSimilarity2(
			List<DoubleDoubleNode> listPivot, List<DoubleDoubleNode> listInput,
			double lenJaccard) {

		/*
		 * System.out.println("Size input" + listInput.size());
		 * System.out.println(listInput);
		 * 
		 * System.out.println("Size pivot" + listPivot.size());
		 * System.out.println(listPivot);
		 */

		if (listPivot.size() < listInput.size()) {

			Set<String> pivot = new HashSet<String>();

			long limit = Math.round((double) listPivot.size() / lenJaccard);

			int count = 0;

			for (DoubleDoubleNode item : listPivot) {

				pivot.add(item.name1);

				if (count < limit) {
					count++;
				}

				else {
					break;
				}
			}

			Set<String> input = new HashSet<String>();

			count = 0;

			for (int index = 0; index < pivot.size(); index++) {

				input.add(listInput.get(index).name1);
				if (count < limit) {
					count++;
				}

				else {
					break;
				}

			}

			double[] r = intersectionJaccardTanimotoOverlap(pivot, input);

			return r[1];

		}

		else {

			Set<String> pivot = new HashSet<String>();

			Set<String> input = new HashSet<String>();

			long limit = Math.round((double) listInput.size() / lenJaccard);

			int count = 0;

			for (DoubleDoubleNode item : listInput) {

				input.add(item.name1);
				if (count < limit) {
					count++;
				}

				else {
					break;
				}

			}

			count = 0;

			for (int index = 0; index < input.size(); index++) {

				pivot.add(listPivot.get(index).name1);
				if (count < limit) {
					count++;
				}

				else {
					break;
				}

			}

			double[] r = intersectionJaccardTanimotoOverlap(pivot, input);

			return r[1];

		}

	}

	public static double computeJaccard(int[] intlist1, int[] intlist2) {

		double count = 0;

		for (int i = 0; i < intlist1.length; i++) {

			for (int j = 0; j < intlist2.length; j++) {

				if (intlist1[i] == intlist2[j]) {
					count++;

				}

			}
		}

		count = count / ((double) 2 * intlist1.length - count);

		// TODO Auto-generated method stub
		return count;
	}

	public static List<String> splitSentences(String[] sentence,
			int limitLength, String reg) {

		List<String> listNewSentence = new ArrayList<String>();

		for (int i = 0; i < sentence.length; i++) {

			String[] word = sentence[i].split(reg);

			if (word.length <= limitLength) {
				listNewSentence.add(sentence[i]);

			}

			else {

				int resto = word.length % limitLength;
				int portion = word.length / limitLength;

				String[][] splitSentence = new String[portion][];

				for (int j = 0; j < word.length - 1; i = i + limitLength) {

					splitSentence[i] = new String[limitLength];

					for (int k = 0; k < limitLength; k++) {
						splitSentence[i][j] = word[i + j];

					}

				}

				// add the tie to the last one
				splitSentence[splitSentence.length - 1] = new String[limitLength
						+ resto];

				for (int j = 0; j < limitLength + resto; j++) {
					splitSentence[splitSentence.length - 1][j] = word[splitSentence.length
							- 1 + j];

				}

				for (int j = 0; j < splitSentence.length; j++) {

					listNewSentence.addAll(Arrays.asList(splitSentence[i]));

				}

			}// for

		}// else

		return listNewSentence;

	}

	

	public static String joinList(List<String> list, String exp) {

		StringBuffer buffer = new StringBuffer();

		for (String item : list) {

			buffer.append(item + exp);

		}

		return buffer.toString();
	}

}
