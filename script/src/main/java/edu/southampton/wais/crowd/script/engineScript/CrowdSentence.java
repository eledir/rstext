package edu.southampton.wais.crowd.script.engineScript;

public class CrowdSentence {

	public int id;
	public String sentence;
	
	public int valueInt;
	
	public float valueFloat;

	public CrowdSentence(int id, String sentence) {
		super();
		this.id = id;
		this.sentence = sentence;
	}

	public int getValueInt() {
		return valueInt;
	}

	public void setValueInt(int valueInt) {
		this.valueInt = valueInt;
	}

	public float getValueFloat() {
		return valueFloat;
	}

	public void setValueFloat(float valueFloat) {
		this.valueFloat = valueFloat;
	}

	public int getId() {
		return id;
	}

	public String getSentence() {
		return sentence;
	}

	@Override
	public String toString() {
		return "@@IdSent= " + id + " @@Sent= " + sentence
				+ " @@Value=" + valueFloat+"\n";
	};
	
	
	
	
	
}
