package edu.southampton.wais.STPLibrary.stanfordRule;

import edu.stanford.nlp.graph.DirectedMultiGraph;




public class ProvaGraph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		DirectedMultiGraph<String,String> g = new 
				DirectedMultiGraph<String,String> () ;
		
		
		g.addVertex("a");
		
		g.addVertex("b");
		
		g.addVertex("c");
		
		
		g.add("a", "c", "r1");
		
		g.add("a", "b", "r1");
		
		System.out.println(g);
		
		
	}

}
