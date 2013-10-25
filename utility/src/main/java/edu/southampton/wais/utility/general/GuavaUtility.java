package edu.southampton.wais.utility.general;

import java.util.List;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import edu.southampton.wais.utility.datastructure.IntegerSingleNode;

public class GuavaUtility {

	
	
	
	
	public static List<IntegerSingleNode> orderingIntegerSingleNodeByValue(List<IntegerSingleNode>list){
		
	
		
		Ordering<IntegerSingleNode> byValueOrder = new Ordering<IntegerSingleNode>() {
			  public int compare(IntegerSingleNode left, IntegerSingleNode right) {
			    return Ints.compare(left.values, right.values);
			  }
			};
		
		return byValueOrder.immutableSortedCopy(list);
		
	}
	
	
	
	
}
