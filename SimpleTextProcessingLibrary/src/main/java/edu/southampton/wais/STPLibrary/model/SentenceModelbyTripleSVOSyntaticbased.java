package edu.southampton.wais.STPLibrary.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class SentenceModelbyTripleSVOSyntaticbased implements Iterable<TripleSVOWordnetBased>{

	
	List<TripleSVOWordnetBased> listTriples;

	

	public SentenceModelbyTripleSVOSyntaticbased(){
		this.listTriples= new ArrayList<TripleSVOWordnetBased>();
		
	}



	public SentenceModelbyTripleSVOSyntaticbased(List<TripleSVOWordnetBased> listTrigram) {
		
		this.listTriples= new ArrayList<TripleSVOWordnetBased>();
		
		this.listTriples.addAll(listTrigram);
	}



	@Override
	public Iterator<TripleSVOWordnetBased> iterator() {
		// TODO Auto-generated method stub
		return this.listTriples.iterator();
	}	








	public void addTriple(TripleSVOWordnetBased triple){
		this.listTriples.add(triple);
	}
		
	public void addAll(List<TripleSVOWordnetBased>list){
		this.listTriples=list;
		
	}



	@Override
	public String toString() {
		return Objects.toStringHelper(this.getClass()).add("Triples...\n", Joiner.on("\n").join(this.listTriples)).toString();
	}
		


	
	public int sizeTriples(){
		return this.listTriples.size();
	}

		
	}
