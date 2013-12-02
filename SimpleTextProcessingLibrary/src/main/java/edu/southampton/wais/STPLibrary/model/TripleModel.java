package edu.southampton.wais.STPLibrary.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import edu.southampton.wais.utility.datastructure.IntegerSingleNode;
import edu.southampton.wais.utility.datastructure.SingleNode;

public class TripleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1627007949885126711L;

	SentenceModel context;

	SingleNode<Integer,String> subj;

	SingleNode<Integer,String> verb;

	SingleNode<Integer,String> objt;

	double polarity = -1;

	public TripleModel(SentenceModel context,SingleNode<Integer,String> subj, SingleNode<Integer,String> verb,
			SingleNode<Integer,String> objt) {
		super();
		this.subj = subj;
		this.verb = verb;
		this.objt = objt;

		this.context = context;

	}

	
	
	public TripleModel(SentenceModel context, SingleNode<Integer,String> verb) {
		super();
		this.verb = verb;
		
		this.context = context;

	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SentenceModel getContext() {
		return context;
	}

	public SingleNode<Integer,String> getSubj() {
		return subj;
	}

	public SingleNode<Integer,String> getVerb() {
		return verb;
	}

	public SingleNode<Integer,String> getObjt() {
		return objt;
	}

	public double getPolarity() {
		return polarity;
	}

	
	
	
	
	public void setContext(SentenceModel context) {
		this.context = context;
	}



	public void setSubj(SingleNode<Integer,String> subj) {
		this.subj = subj;
	}



	public void setVerb(SingleNode<Integer,String> verb) {
		this.verb = verb;
	}



	public void setObjt(SingleNode<Integer,String> objt) {
		this.objt = objt;
	}



	public void setPolarity(double polarity) {
		this.polarity = polarity;
	}



	@Override
	public String toString() {
		return Objects.toStringHelper(this.getClass())
				.add("Sentence : ", this.context).add("", "\n")
				.addValue("<"+this.subj.getObject()).addValue(this.verb.getObject()).addValue(this.objt.getObject())
				.add(" polarity ", this.polarity).addValue(" >\n").toString();
	}

}
