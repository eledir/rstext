package edu.southampton.wais.STPLibrary.wsd;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



public class WSDAnnotation implements Iterable<Entry<String,  WSDObject>>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File file;
	
	private Map<String,WSDObject> map;

	public WSDAnnotation(File file, Map<String, WSDObject> map) {
		super();
		this.file = file;
		this.map = map;
	}

	public File getFile() {
		return file;
	}

	public Map<String, WSDObject> getMap() {
		return map;
	}

	@Override
	public Iterator<Entry<String, WSDObject>> iterator() {
		// TODO Auto-generated method stub
		return this.map.entrySet().iterator();
	}
	
	
	
	
	
	
	
	
	
	
	
}
