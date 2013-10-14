package edu.southampton.wais.STPLibrary.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class TripleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1627007949885126711L;

	SentenceModel context;

	String subj;

	String verb;

	String objt;

	double polarity = -1;

	public TripleModel(SentenceModel context, String subj, String verb,
			String objt) {
		super();
		this.subj = subj;
		this.verb = verb;
		this.objt = objt;

		this.context = context;

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SentenceModel getContext() {
		return context;
	}

	public String getSubj() {
		return subj;
	}

	public String getVerb() {
		return verb;
	}

	public String getObjt() {
		return objt;
	}

	public double getPolarity() {
		return polarity;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this.getClass())
				.add("Sentence : ", this.context).add("", "\n")
				.add("<", this.subj).addValue(this.verb).addValue(this.objt)
				.add(" polarity ", this.polarity).add(" >", "\n").toString();
	}

}
