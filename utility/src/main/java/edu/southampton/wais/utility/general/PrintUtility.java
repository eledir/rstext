package edu.southampton.wais.utility.general;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import edu.southampton.wais.utility.datastructure.IntegerSingleNode;

public class PrintUtility {





	public static  void printMultiSet(File f,Multiset<? extends Object>multiset,String sep) throws IOException{



		List<String> listToWrite= Lists.newArrayList();    



		for (Object item : multiset.elementSet()) {

			listToWrite.add(item.toString() +sep+ multiset.count(item));


		}

		String body=Joiner.on("\n").join(listToWrite);

		FileUtils.writeStringToFile(f, body);









	}



	public static  void printOrderingMultiSet(File f,Multiset<? extends Object>multiset,String sep) throws IOException{



		List<IntegerSingleNode> list= Lists.newArrayList();    


		List<String> listToWrite= Lists.newArrayList();    



		for (Object item : multiset.elementSet()) {


			list.add(new IntegerSingleNode(item.toString(),multiset.count(item)));



		}


		list=GuavaUtility.orderingIntegerSingleNodeByValue(list);


		for(IntegerSingleNode node:list){

			listToWrite.add(node.name +sep+ node.values);

		}
		String body=Joiner.on("\n").join(listToWrite);

		FileUtils.writeStringToFile(f, body);









	}





	public static  <T>void printMultiMap(File f,Multimap<T, T>multimap,String sepKey,String sepValue,String sepFre) throws IOException{



		List<String> listToWrite= Lists.newArrayList();    

		
		
		for (T key : multimap.keySet()) {

		
		   List<IntegerSingleNode> listNode=Lists.newArrayList();

			
		   for(T value:multimap.get(key)){
			
				
				int fre=Iterables.frequency(multimap.get(key),value); 
				
				listNode.add(new IntegerSingleNode(value.toString(),fre));
				
			}
			
			
		   listNode=GuavaUtility.orderingIntegerSingleNodeByValue(listNode);

		   List<String> listToWriteValue= Lists.newArrayList();    

		   for(IntegerSingleNode node:listNode){

			   listToWriteValue.add(node.name +sepFre+ node.values);

			}
			
		    String body=Joiner.on(sepValue).join(listToWriteValue);

			listToWrite.add(key.toString()+sepKey+body);
			
			
			
			
		}
		
		
		

		
		String fullBody=Joiner.on("\n").join(listToWrite);

		FileUtils.writeStringToFile(f, fullBody);



	}


}
