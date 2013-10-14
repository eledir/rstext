package edu.southampton.wais.STPLibrary.wsd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import edu.southampton.wais.STPLibrary.dataStructure.ListStringNode;
import edu.southampton.wais.STPLibrary.utility.IOFileUtility;
import edu.southampton.wais.STPLibrary.utility.SyntacticDistanceUtility;
import edu.southampton.wais.STPLibrary.wordnet.ExendedSynset;

import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;








//this is based on the wsd extended gloss

public class WSD {
	
   
	
	


 
private HashSet<String> listStop1;

private Dictionary dic;
 
//private FileChannel fileSynsetIndex;

//private SenseIndexParser senseINdexPareser;
  
  
	
	public 	WSD (){
	   
	   
	     
	   
	    System.out.println("start load wordnet jwnl");
		
		try {
		
			

			File f= new File("config/file_properties.xml");
			
			
			FileInputStream fileImFileInputStream = new FileInputStream(f);			
			JWNL.initialize(fileImFileInputStream);
		
		dic=Dictionary.getInstance();
		
		
		} catch (FileNotFoundException e) {
			  
			
				
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("problem  loading wordnet  file jwnl");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("problem  loading wordnet  file jwnl");
		}
		
	
		
		
		//get stop list
		
		// build a collection of metadata 
		String nameFileStopList1=System.getProperty("user.dir")+File.separator+"fileConfig"+File.separator+"StopWords.txt";
		String nameFileStopList2=System.getProperty("user.dir")+File.separator+"fileConfig"+File.separator+"StopWords2.txt";



	 listStop1 = IOFileUtility
		.readHashSetStringFromFile(new File(nameFileStopList1));

		HashSet<String> listStop2 = IOFileUtility
		.readHashSetStringFromFile(new File(nameFileStopList2));


		
   //add the list		
		
	for(String item : listStop2){
		
		 listStop1.add(item);
		
	}
		
		
	    
		
	
	   
	   
	   
   }
	
	
   
	
	//get the sentence in input plus the type keyword too search
	
	public  Map<String,WSDObject> run(List<ListStringNode> list, String word,  String pos, String lemma, String stem, int depth) throws Exception{
		
		 Map<String,WSDObject> mapWord2WSDObject= new HashMap<String, WSDObject>();
		
		
		// for each word retrieve the synsets
		
		
		
		//get context
		
		List<String> context=getContext(list,stem);
		
		
		for(ListStringNode node: list){
			
			 
			
			
		List<Synset> synsets=null;
			   
			//verifay that is a valid node 
			
			
			
			
			if(node.isValidbyType(stem)){
			
			//if a noun
			
				System.out.println("START DISAMBUIATE NODE THE FOLLOWING SENTENCE PARSERED: ");
				System.out.println(node);
				
				
				
		         
				
				if(node.getValue(pos).equals("n")){
				
				String lemmaValue=node.getValue(lemma);	
				
				IndexWord indexWord=this.dic.lookupIndexWord(POS.NOUN,lemmaValue);  
					
				try{
				synsets =indexWord.getSenses();
				}
				catch (Exception e) {
				
					// TODO: handle exception
					
					
				}
					
				  
						
					
				}
				
				else if(node.getValue(pos).equals("v")){
					
					String lemmaValue=node.getValue(lemma);	
					
					IndexWord indexWord=this.dic.lookupIndexWord(POS.VERB,lemmaValue);  
						
					
					try{
						synsets =indexWord.getSenses();
						}
						catch (Exception e) {
							
							// TODO: handle exception
						}
							
					
						
				}
				else if(node.getValue(pos).equals("a")){
					
                    String lemmaValue=node.getValue(lemma);	
					
					IndexWord indexWord=this.dic.lookupIndexWord(POS.ADJECTIVE,lemmaValue);  
						
					
					try{
						synsets =indexWord.getSenses();
						}
						catch (Exception e) {
						
							// TODO: handle exception
						}
					
					
					
				}
				
				else{
					
					
                   String lemmaValue=node.getValue(lemma);	
					
					IndexWord indexWord=this.dic.lookupIndexWord(POS.ADVERB,lemmaValue);  
						
					try{
				 synsets =indexWord.getSenses();
					}
					catch (Exception e) {
						
						// TODO: handle exception
					}
					
					
				}
				
					
			      
					// for each synset compute the score 
					
					int score=0;
				
					
					
					if(synsets!=null){
					
					
					
					ExendedSynset choose=null;
					
					for(Synset synset : synsets){
						
						
				         ExendedSynset exendedSynset = new ExendedSynset(synset);
						
						   
						    
					      List<String> listExndended= exendedSynset.getExendedGloss(listStop1,depth);
						   
						   
						   //get context
						   Map<String,Integer> mapIntersection=new HashMap<String,Integer>();
						   
						   
						   int tmp=computeScore(listExndended,context,mapIntersection,0.7);
						   
						   
						  // Integer max= Collections.max(mapIntersection.values());
							   
						   
						   
						   if(tmp>=score){
							   
							   score=tmp;
							   
							   //List<String>listWordIntersect= new ArrayList<String>();
							   
							   //compute the first 3 word that intersect
							   //getWordIntersect(mapIntersection,3,max,listWordIntersect);
							   
							   choose=exendedSynset;
							   
							   
						   }
						   
						   
						   
						
					}//for on the synset
					
			
					
			//as key we select the word as in the sentence		
			String keyword=node.getValue(word);		
			System.out.println("Word "+keyword);
			
			if(choose!=null){
				
				
			
			WSDObject object= new WSDObject(node, choose, score);
			
			mapWord2WSDObject.put(keyword, object);
				
			System.out.println("Synset "+choose.getSnynset());
			
			System.out.println("Synset "+choose.getSnynset().getGloss());
			
			System.out.println("with score "+score);
			
			System.out.println(" FINISH  DISAMBUIATE NODE--------------- ");
			
			}		
		}			
			else{
				System.out.println("THIS NODE IS NOT VALID FOR WSD ");
				System.out.println(node);
				System.out.println("----------------------------- ");
				
				
			}	
		
		
		}
		}
		
		
		return mapWord2WSDObject;
		
	}






	

	



  // words that have a similarity greater than a thresholds are compared
	private int computeScore(List<String> listExndended, List<String> context, Map<String,Integer> mapItersection, double thSim) {
		
		
		int count=0;
		
		for(String item1: listExndended){
			
			
			for(String item2: context){
				
				
				double d=SyntacticDistanceUtility.getSimilarity(item1, item2);
				
				
				if(d>thSim){
					count++;
					
					
					
					if(mapItersection.containsKey(item1)){
						int tmp= mapItersection.get(item1)+1;
						mapItersection.put(item1,tmp);
						
						
					}
					
					else{
						
						mapItersection.put(item1,1);
					}
					
				}
				
			}
		}
		
		
		// TODO Auto-generated method stub
		return count;
	}




	

		
		
			
			
			

   
   
   
   



	
		
private List<String>  getContext(List<ListStringNode> list, String s) throws Exception {
	
	
	List<String> context= new ArrayList<String>();
	
	for(ListStringNode item:list){
	
		if(item.isValidbyType(s)){
		context.add(item.getValue(s));
		}
		
	}


	// TODO Auto-generated method stub
	return context;
}
	
   
	

}

