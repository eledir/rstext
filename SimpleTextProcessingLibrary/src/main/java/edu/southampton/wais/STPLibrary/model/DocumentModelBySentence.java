package edu.southampton.wais.STPLibrary.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.washington.cs.knowitall.commonlib.MultiMap;

public class DocumentModelBySentence implements Serializable {

	HashMap<Integer, SentenceModel> hashMapSentenceModel;

	HashMap<Integer, Boolean> hashMapValidate;

	HashMap<Integer, String> hashMapSetenceBody;

	private TextFile textFile;

	public DocumentModelBySentence(TextFile text) {
		super();
		this.textFile = text;
		this.hashMapSentenceModel = new HashMap<Integer, SentenceModel>();
		this.hashMapValidate = new HashMap<Integer, Boolean>();
		this.hashMapSetenceBody = new HashMap<Integer, String>();

	}

	public void settingSetence(int index, String text) {

		this.hashMapValidate.put(index, true);
		this.hashMapSetenceBody.put(index, text);

	}

	public void settingSetenceModel(int index, SentenceModel sentenceModel) {

		this.hashMapSentenceModel.put(index, sentenceModel);

	}

	public int sizeSentence() {
		return this.hashMapSetenceBody.values().size();
	}

	@Override
	public String toString() {
		return "SentenceModels [list=" + this.hashMapSetenceBody.values() + "]";
	}

	public String getSentence(int i) {

		return this.hashMapSetenceBody.get(i);
	}

	public SentenceModel getSentenceModel(int i) {

		return this.hashMapSentenceModel.get(i);
	}

	public TextFile getTextFile() {
		return textFile;
	}

	public void invalidateSetences(List<Integer> listCancell) {

		for (Integer item : listCancell) {

			this.hashMapValidate.put(item, false);

		}

		// TODO Auto-generated method stub

	}

	public boolean isValidSentense(int index) {

		return this.hashMapValidate.get(index);

	}

	public Set<Entry<Integer, String>> getEntrySet() {

		return this.hashMapSetenceBody.entrySet();

	}

}
