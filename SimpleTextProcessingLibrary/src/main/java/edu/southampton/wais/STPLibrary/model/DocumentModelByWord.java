package edu.southampton.wais.STPLibrary.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import edu.southampton.wais.STPLibrary.file.TextFile;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;


public class DocumentModelByWord implements Iterable<Entry<String, WordFeatures>> {
	
	
private TextFile textFile;




private HashMap<String, WordFeatures> hashWordMeasure;







public DocumentModelByWord(TextFile textFile) {
	this.textFile = textFile;
	this.hashWordMeasure= new HashMap<String, WordFeatures>();
}





  
public int sizeWords(){
	return this.hashWordMeasure.size();
}



public WordFeatures getWordFeature(String word){
	
	return this.hashWordMeasure.get(word);
	
}




@Override
public Iterator<Entry<String, WordFeatures>> iterator() {
	return this.hashWordMeasure.entrySet().iterator();
}





public void settingWordModel(List<AnnotatedWord> list) throws Exception{
	
	
	int index=0;
	for(AnnotatedWord item: list){
		
		if(this.hashWordMeasure.containsKey(item.getValue(AnnotatedWord.LEMMA))){
			
			
			WordFeatures metadataMeasure= this.hashWordMeasure.get(item);
			
			metadataMeasure.setCount(metadataMeasure.getCount()+1);
			
			metadataMeasure.addPosition(index);
			
			metadataMeasure.addAnnotationWord(item);
		}
		else{
			
			WordFeatures metadataMeasure = new WordFeatures(1,index);
			
			this.hashWordMeasure.put(item.getValue(AnnotatedWord.LEMMA), metadataMeasure);
			
			
		}
	
	   index++;
	}
	
}







public TextFile getTextFile() {
	return textFile;
}










}