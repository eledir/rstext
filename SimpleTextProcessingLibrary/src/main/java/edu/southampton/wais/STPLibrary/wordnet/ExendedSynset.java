package edu.southampton.wais.STPLibrary.wordnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import edu.southampton.wais.STPLibrary.utility.SentenceModelUtility;

import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.data.list.PointerTargetNode;
import net.sf.extjwnl.data.list.PointerTargetNodeList;
import net.sf.extjwnl.data.list.PointerTargetTree;








public class ExendedSynset implements Serializable {


	private  double score;


	private Synset snynset;


	private POS posType;



	private boolean computedInformationContent=false;



	private double informationContent;


	public ExendedSynset(Synset snynset) {
		super();
		this.snynset = snynset;
		posType=this.snynset.getPOS();
	}

	public ExendedSynset(double score, Synset snynset) {
		super();
		this.score = score;
		this.snynset = snynset;
		posType=this.snynset.getPOS();
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Synset getSnynset() {
		return snynset;
	}

	public void setSnynset(Synset snynset) {
		this.snynset = snynset;
	}

	public POS getPosType() {
		return posType;
	}

	public void setPosType(POS posType) {
		this.posType = posType;
	}


	public  List<String> getGloss(HashSet<String> stopList){




		//process and remove stop list word;
		return SentenceModelUtility.getWordFromString(this.snynset.getGloss(),stopList);




	}


	public  void addVectorToList(String[] vet, List<String> list){


		for(String item : vet){

			list.add(item);

		}

	}

	private void getHypernyms(Synset syn, Collection l,HashSet<String>stopList, int depth) 
	{	List list=null;
		try
		{
			
			
		
			PointerTargetTree tre= PointerUtils.getHypernymTree(syn, depth);    
			
			list=tre.toList();
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG Hyper : " + syn );
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet(list, l,stopList);
	}

	
	
	private void getHyponyms(Synset syn, Collection l,HashSet<String>stopList, int depth) 
	{
		List ptnl = null;
		try
		{
			
		
			PointerTargetTree tre= PointerUtils.getHyponymTree(syn, depth);    
			ptnl= tre.toList();
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG HYPOni : " + syn );
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet(ptnl, l,stopList);
	}
	
	private void getHolonyms(Synset syn, Collection l,HashSet<String>stopList) 
	{
		PointerTargetNodeList ptnl = null;
		try
		{
			
			ptnl= PointerUtils.getHolonyms(syn);    
			
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG HOLNY : " + syn );
			
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet2(ptnl, l,stopList);
	}
	
	
	private void getMemberHolonyms(Synset syn, Collection l,HashSet<String>stopList) 
	{
		PointerTargetNodeList ptnl = null;
		try
		{
			
			ptnl= PointerUtils.getMemberHolonyms(syn);    
			
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG Member holony : " + syn );
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet2(ptnl, l,stopList);
	}
	
	private void getPartHolonyms(Synset syn, Collection l,HashSet<String>stopList) 
	{
		PointerTargetNodeList ptnl = null;
		try
		{
			
			ptnl= PointerUtils.getPartHolonyms(syn);    
			
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG part holony : " + syn );
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet2(ptnl, l,stopList);
	}
	private void getMeronyms(Synset syn, Collection l,HashSet<String>stopList) 
	{
		PointerTargetNodeList ptnl = null;
		try
		{
			
			ptnl= PointerUtils.getMeronyms(syn);    
			
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG meronym : " + syn );
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet2(ptnl, l,stopList);
	}
	
	
	private void getPartMeronym(Synset syn, Collection l,HashSet<String>stopList) 
	{
		PointerTargetNodeList ptnl = null;
		try
		{
			
			ptnl= PointerUtils.getPartMeronyms(syn);    
			
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG part meronym : " + syn );
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet2(ptnl, l,stopList);
	}
	
	
	private void getMemberMeronym(Synset syn, Collection l,HashSet<String>stopList) 
	{
		PointerTargetNodeList ptnl = null;
		try
		{
			
			ptnl= PointerUtils.getMemberMeronyms(syn);    
			
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG mber meronym : " + syn );
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet2(ptnl, l,stopList);
	}
	
	
	private void getSynoyms(Synset syn, Collection l,HashSet<String>stopList,int depth) 
	{
	              List ptnl = null;
		try
		{
			
		
			PointerTargetTree tre= PointerUtils.getSynonymTree(syn, depth);    
	
			ptnl= tre.toList();
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG synomim: " + syn );
			
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet(ptnl, l,stopList);
	}
	

	private void getEntailments(Synset syn, Collection l,HashSet<String>stopList,int depth) 
	{
		List ptnl = null;
		try
		{
			
			PointerTargetTree tre= PointerUtils.getEntailmentTree(syn, depth);    
			ptnl= tre.toList();
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG emtailment : " + syn );
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet(ptnl, l,stopList);
	}
	
	
	private void getAlso(Synset syn, Collection l,HashSet<String>stopList,int depth) 
	{
	     List ptnl = null;
		try
		{
			
			PointerTargetTree tre= PointerUtils.getAlsoSeeTree(syn, depth);    
			ptnl= tre.toList();
			
		}
		catch (NullPointerException e)
		{
			// bug in jwnl, throws null-pointer instead of returning null or 0-size
			// list
			System.out.println("JWNL BUG: " + e);
			System.out.println("JWNL BUG ALSO : " + syn );
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JWNL BUG: " + e);
		}
		getLemmaSet(ptnl, l,stopList);
	}
	
	
	
	private void getLemmaSet(List source, Collection dest,HashSet<String>stopList)
	{
		

		for (Iterator i = source.iterator(); i.hasNext();)
		{
			
			
			
			PointerTargetNodeList ptnl = (PointerTargetNodeList) i.next();
			getLemmaSet2(ptnl,dest,stopList);
			
		}  		
	}		
			
	private void getLemmaSet2(List source, Collection dest,HashSet<String>stopList){
	
		if (source == null)
		      return;

		    for (Iterator i = source.iterator(); i.hasNext();)
		    {
	
			PointerTargetNode targetNode = (PointerTargetNode) i.next();
			if (!targetNode.isLexical())
			{
				Synset syn = targetNode.getSynset();
				if (syn != null){
				
					// add words
					addLemmas(syn.getWords(), dest);
				    //add also from words example and gloss
					
					String gloss=syn.getGloss();
				    List<String> list2 =SentenceModelUtility.getWordFromString(gloss,stopList);
				
				    
				    
				}   
				
				}
			else
			{
				addLemma(targetNode.getWord(), dest);
				
				
			    
				
			    
			    
			}
		}
	}

	
	
	
	
	
	
	private void addLemmas(List<Word> words, Collection dest)
	{
		if (words == null || words.size() == 0)
			return;
		for (int k = 0; k < words.size(); k++)
			addLemma(words.get(k), dest);
	}

	private void addLemma(Word word, Collection dest)
	{
		this.addLemma(word.getLemma(), dest);
	}

	
	private void addLemma(List<String> list, Collection dest){
		
		for(String item: list){
		
		this.addLemma(item, dest);
		}
	}
	
	
	private void addLemma(String lemma, Collection dest)
	{
		if (isCompound(lemma))
			return;
		if (Character.isUpperCase(lemma.charAt(0)))
			return;
		lemma = cleanLemma(lemma);
		if (!dest.contains(lemma)) // no dups
			dest.add(lemma);
	}
	private String cleanLemma(String lemma)
	{
		// / TODO!!!
		if (lemma.endsWith(")"))
			lemma = lemma.substring(0, lemma.length() - 3);
		lemma = replace(lemma, '_', '-');
		return lemma;
	}

	private String replace(String src, char c, char r)
	{
		if (src.indexOf(c) < 0) return src;
		StringBuffer buffer = new StringBuffer(src);
		for (int i = 0; i < buffer.length(); i++)
			if (buffer.charAt(i) == c)
				buffer.replace(i, i + 1, (r + ""));
		return buffer.toString();
	}



	private  boolean isCompound(String word)
	{
		return word.indexOf(' ') > 0 || word.indexOf('-') > 0 || word.indexOf('_') > 0;
	}



	public List<String> getExendedGloss(HashSet<String> stopList,int depth){


		//ckeck if is noun


		List<String> list=this.getGloss(stopList);


		if(this.posType==POS.NOUN){


			//get the data from relations 	



             this.getHypernyms(this.snynset, list, stopList, depth);   
             
             this.getHyponyms(this.snynset, list, stopList, depth);
             
             this.getSynoyms(snynset, list, stopList, depth);
             
             
             // nounn based
             
             this.getHolonyms(this.snynset, list, stopList);
            
             this.getPartHolonyms(snynset, list, stopList);
             
             this.getMemberHolonyms(snynset, list, stopList);
             
             this.getMeronyms(snynset, list, stopList);
             
             this.getPartMeronym(snynset, list, stopList);

			 this.getMemberMeronym(snynset, list, stopList);
			
			
		 







		}

		else if (this.posType==POS.VERB) {



            this.getHypernyms(this.snynset, list, stopList, depth);   
            
            this.getHyponyms(this.snynset, list, stopList, depth);
            
            this.getSynoyms(snynset, list, stopList, depth);
            
            this.getEntailments(snynset, list, stopList, depth);
            
          
			


		}

		else if(this.posType==POS.ADJECTIVE){
			
			 this.getSynoyms(snynset, list, stopList, depth);
			 
			 this.getAlso(snynset, list, stopList, depth);
			
			
		}


		return list;

	}

	public  double computeInformationCotenent(int depth) {
		
		
		if(!computedInformationContent){
		
		int count=0;
		
		if(this.posType.equals(POS.NOUN)){
		
			
			
			
			PointerTargetTree tre;
			try {
				
				//	System.out.println(" hypertree noun ");
				tre = PointerUtils.getHypernymTree(this.snynset);
				List l1=tre.toList();
				
				count=count+l1.size();
				
				//System.out.println("done");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			
			
			
			try {
				
				//System.out.println(" hypo noun ");
				//System.out.println(this.snynset);
				tre = PointerUtils.getHyponymTree(this.snynset); 
				
				List l2=tre.toList();
				//System.out.println(l2.size());
				
				count=count+l2.size();
				
				//System.out.println("done");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		    
			
		
			
			try {
				
				//System.out.println(" syno noun ");
				
				PointerTargetNodeList l3= PointerUtils.getSynonyms(this.snynset); 
				
				count=count+l3.size();
				
				//System.out.println("done");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			

			try {
				
				//System.out.println(" holy noun ");
				
				PointerTargetNodeList l4= PointerUtils.getHolonyms(snynset); 
				
				count=count+l4.size();
				
				//System.out.println("done");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			
			try {
				
				//System.out.println(" partholy noun ");
				
				
				PointerTargetNodeList l5= PointerUtils.getPartHolonyms(snynset);
				
				count=count+l5.size();
				
				
				//System.out.println("done");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				
				//System.out.println(" member holy noun ");
				
				PointerTargetNodeList l6=PointerUtils.getMemberHolonyms(snynset);
				
				count=count+l6.size();
				
				//System.out.println("done");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				//System.out.println("  merony noun ");
				
				PointerTargetNodeList l7= PointerUtils.getMeronyms(snynset);
				
				count=count+l7.size();
				
				//System.out.println("done");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			try {
				
				//System.out.println(" part merony noun ");
				
				PointerTargetNodeList l8= PointerUtils.getPartMeronyms(snynset);
				count=count+l8.size();
				//System.out.println("done");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//System.out.println(" member merony noun ");
				
				PointerTargetNodeList l9= PointerUtils.getMemberMeronyms(snynset);
				count=count+l9.size();
				// System.out.println("done");
			  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
				
			
			
		}
		else if(this.posType.equals(POS.VERB)){
			
		
			
			
			
			PointerTargetTree tre;
			try {
				
				//System.out.println(" hypertree verb ");
				tre = PointerUtils.getHypernymTree(this.snynset);
				List l1= tre.toList();
			  count=count+l1.size();
			  // System.out.println("done");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			
			
			try {
				//System.out.println(" hypotree verb ");
				tre = PointerUtils.getHyponymTree(this.snynset); 
				List l2= tre.toList();
			  count=count+l2.size();
			  
			  //System.out.println("done");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		

			try{
				
				//System.out.println(" syni verb ");
			List l3= PointerUtils.getSynonyms(this.snynset);    
			
			count=count+l3.size();
			//System.out.println("done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
			
			
			try {
				//System.out.println(" entail verb ");
				tre = PointerUtils.getEntailmentTree(this.snynset);
				List l4 =tre.toList();
			  count=count+l4.size();
			  //System.out.println("done");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		
			
			
			
			
			
		
		}
			
		else if(this.posType.equals(POS.ADJECTIVE)){
			
			
			
			 
				
			 List l1;
			try {
				//System.out.println("Syno adj");
				l1 = PointerUtils.getSynonyms(this.snynset);
				
				count=count+l1.size();
				
				//System.out.println("done");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
				
				
				List l2;
				try {
					//System.out.println("also see adj");
					l2 = PointerUtils.getAlsoSees(this.snynset);
					count=count+l2.size();
					//System.out.println("done");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    
				
				
		}
			
		
		
	
	this.computedInformationContent=true;
	this.informationContent=1- (Math.log(count+1)/Math.log(depth));
	
	return this.informationContent;
	
		}
		
		else 
			
			return informationContent;
	
	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((snynset == null) ? 0 : snynset.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExendedSynset other = (ExendedSynset) obj;
		if (snynset == null) {
			if (other.snynset != null)
				return false;
		} else if (!snynset.equals(other.snynset))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ES[sny=" + snynset + " "+this.snynset.getGloss()+"]";
	}

	
	
	
	
	
	
}
