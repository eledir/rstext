package edu.southampton.wais.STPLibrary.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SentenceModelByTrigram  implements Iterable<TrigramModel>{

	
	List<TrigramModel> listTrigram;

	

	public SentenceModelByTrigram(){
		this.listTrigram= new ArrayList<TrigramModel>();
		
	}



	public SentenceModelByTrigram(List<TrigramModel> listTrigram) {
		this.listTrigram= new ArrayList<TrigramModel>();
		
		this.listTrigram.addAll(listTrigram);
	}



	@Override
	public Iterator<TrigramModel> iterator() {
		// TODO Auto-generated method stub
		return this.listTrigram.iterator();
	}	








	public void addTrigram(TrigramModel trigramModel){
		this.listTrigram.add(trigramModel);
	}
		
	public void addAll(List<TrigramModel>list){
		this.listTrigram=list;
		
	}



	@Override
	public String toString() {
		return "SentenceModelByTrigram [" + listTrigram + "]";
	}
		


	
	public int sizeTrigram(){
		return this.listTrigram.size();
	}

		
	}
