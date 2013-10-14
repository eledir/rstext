package edu.southampton.wais.STPLibrary.wordnet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerType;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.data.list.PointerTargetNode;
import net.sf.extjwnl.data.list.PointerTargetNodeList;
import net.sf.extjwnl.data.list.PointerTargetTree;
import net.sf.extjwnl.data.relationship.AsymmetricRelationship;
import net.sf.extjwnl.data.relationship.RelationshipFinder;
import net.sf.extjwnl.data.relationship.RelationshipList;
import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;


public class TestJWNL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		
		
		
		
		try {  
			
			
			File f= new File("config/file_properties.xml");
			
			
			FileInputStream fileImFileInputStream = new FileInputStream(f);			
			JWNL.initialize(fileImFileInputStream);
			
			IndexWord iw1 = Dictionary.getInstance().lookupIndexWord(POS.VERB, "moderate");
			IndexWord iw2 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, "cat");
			 
			
			
			
			
			  for(Synset  item1 :iw1.getSenses()){
				
				  
				  
				 for(Synset  item2 :iw2.getSenses()){
							 
				 
				 
					 
					 
					 
			
			 RelationshipList list = RelationshipFinder.findRelationships(item1, item2, PointerType.HYPERNYM);
			    
			    
			 System.out.println("Index word 1: " + iw1.toString());
				
				System.out.println("Index word 2 : " + iw2.toString());
				
			 
			   System.out.println(item1.getKey().toString());
			   System.out.println(item1.getGloss());
			   
			   for(Word i:item1.getWords())
			   System.out.println(i.getLemma());
				
			   System.out.println(item2.getKey().toString());
			   System.out.println(item2.getGloss());
			 
			   
         if(list.size()>0){
			   
        	   System.out.println(list.size());
			   System.out.print("DEEPEST");
        	   System.out.println(list.getDeepest());
        	   System.out.print("SHAllowest");
         	  
        	   System.out.println(list.getShallowest());
			   
			    AsymmetricRelationship ar = (AsymmetricRelationship) list.get(0); // why 0??
			    PointerTargetNodeList nl = ar.getNodeList();
			    PointerTargetNode ptn = (PointerTargetNode) nl.get(ar.getCommonParentIndex());
			    Synset anc=ptn.getSynset();
			    System.out.println("FOUND ....."+anc.getKey().toString());
				   System.out.println("GLOSS...."+anc.getGloss());
         }
         
         else{
        	 
        	 
        	 System.out.println("Not elmeent found..");
        	 
         }
			    
			  }
			
			
			
			
			
				 
				 
				 
				 
				 
		 
			 }
		   
	 
          
			
		} catch (JWNLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
		
		
	}

}
