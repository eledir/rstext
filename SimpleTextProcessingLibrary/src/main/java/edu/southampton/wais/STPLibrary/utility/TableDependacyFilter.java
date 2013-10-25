package edu.southampton.wais.STPLibrary.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class TableDependacyFilter {

	public static Table<String,String,String> fileterXYZYVerb(
			Table<String, String, String> table) {

		
		Table<String,String,String> tableFilter=HashBasedTable.create();
		
		for (String y : table.columnKeySet()) {

			String[] ys = y.split("-");

			if (POSTagStanford.isVerb(ys[1])) {

				Map<String, String> portion = table.column(y);

				for (Map.Entry<String, String> xz : portion.entrySet()) {

					tableFilter.put(xz.getKey(),y , xz.getValue());

				}

			}

		}

		return tableFilter;

	}

	public static 	Table<String,String,String> fileterXYZYNoun(
			Table<String, String, String> table) {

		
		Table<String,String,String> tableFilter=HashBasedTable.create();
		
		for (String y : table.columnKeySet()) {

			String[] ys = y.split("-");

			if (POSTagStanford.isNoun(ys[1])) {

				Map<String, String> portion = table.column(y);

				for (Map.Entry<String, String> xz : portion.entrySet()) {

					tableFilter.put(xz.getKey(),y , xz.getValue());

				}

			}

		}

		return tableFilter;

	}

	
	
	
	
	
	

	public static Table<String,String,String> fileterXYZYInputZNoun(
			Table<String, String, String> table, String y) {

		Table<String,String,String> tableFilter=HashBasedTable.create();

		Map<String, String> portion = table.column(y);

		for (Map.Entry<String, String> xz : portion.entrySet()) {

			String[] zs = xz.getValue().split("-");

			if (POSTagStanford.isNoun(zs[1])) {

				
				tableFilter.put(xz.getKey(),y , xz.getValue());
				

			}

		}

		return tableFilter;

	}

	public static Table<String,String,String> fileterXYZYInputZAdj(
			Table<String, String, String> table, String y) {


		Table<String,String,String> tableFilter=HashBasedTable.create();

		Map<String, String> portion = table.column(y);

		for (Map.Entry<String, String> xz : portion.entrySet()) {

			String[] zs = xz.getValue().split("-");

			if (POSTagStanford.isAdjective(zs[1])) {

				
				tableFilter.put(xz.getKey(),y , xz.getValue());
				

			}

		}

		return tableFilter;

	}


	
	
	public static Table<String,String,String> fileterXYZYXNeg(
			Table<String, String, String> table) {


		Table<String,String,String> tableFilter=HashBasedTable.create();

		for (String x : table.rowKeySet()) {

		
			if (x.equals("neg")) {

				Map<String, String> portion = table.row(x);

				for (Map.Entry<String, String> yz : portion.entrySet()) {

					tableFilter.put(x,yz.getKey() , yz.getValue());

				}

			}

		}
		
		
		
		return tableFilter;

	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Table<Integer, String, String> table = HashBasedTable.create();

		table.put(1, "a", "1a");
		table.put(1, "b", "1b");
		table.put(2, "a", "2a");
		table.put(2, "b", "2b");

		
		System.out.println(table);
		
		System.out.println(table.columnKeySet());
		for (String k : table.columnKeySet())

			System.out.println(table.column(k));

		System.out.println(table.rowKeySet());

	}

}
