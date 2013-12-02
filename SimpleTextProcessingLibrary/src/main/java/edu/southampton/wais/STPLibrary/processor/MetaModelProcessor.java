package edu.southampton.wais.STPLibrary.processor;

import java.util.List;

import com.google.common.collect.Lists;

import edu.southampton.wais.STPLibrary.model.MetaNode;
import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.model.TripleMetaModel;
import edu.southampton.wais.STPLibrary.model.TripleModel;
import edu.southampton.wais.STPLibrary.wordnet.WordnetSuperSenseRetrival;
import edu.southampton.wais.utility.datastructure.SingleNode;
import edu.southampton.wais.utility.general.Logger;

public class MetaModelProcessor {

	
	
	public static List<TripleMetaModel> extract(List<TripleModel> list,SentenceModel sm){
		
		
		List<TripleMetaModel> listresult= Lists.newArrayList();
		
		
		for(TripleModel model:list){
			
			try {	
			
				
				
			TripleMetaModel mm= new TripleMetaModel(model);	
			
			SingleNode<Integer,String> subj=model.getSubj();
			
		
			if(isEntity(subj,sm)){
		
			   SingleNode<Integer,String> subjS=getEntity(subj, sm);
			
			   MetaNode metaNode= new MetaNode();
			   metaNode.setEntity(subjS);
			   
			   mm.setSub(metaNode);
				
			}else{
			
				SingleNode<Integer,String> subjS=getSuperSense(subj, sm);
			
				 MetaNode metaNode= new MetaNode();
				 metaNode.setSupersense(subjS);;
				   
				  mm.setSub(metaNode);
					
			
			
			}
			
			
			
			SingleNode<Integer,String> verb=model.getVerb();
			
			
			SingleNode<Integer,String> verbS=getSuperSense(verb, sm);
			
			 MetaNode metaNodeVerb= new MetaNode();
				
			 metaNodeVerb.setSupersense(verbS);
			 
			 mm.setVerb(metaNodeVerb);
			
			
			SingleNode<Integer,String> obj=model.getObjt();
	
			
			if(isEntity(subj,sm)){
				
				SingleNode<Integer,String> objS=getEntity(obj, sm);
				MetaNode metaNode= new MetaNode();
				 metaNode.setEntity(objS);
				   
				   mm.setObj(metaNode);
					
			}
			else{
				SingleNode<Integer,String> objS=getSuperSense(obj, sm);
				
				MetaNode metaNode= new MetaNode();
				  metaNode.setSupersense(objS);
				   mm.setObj(metaNode);
					
				
			}
			
			
			listresult.add(mm);

			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				Logger.logFine("The following triple is escaped for supersense ");
				
				Logger.logFine("Triple : "+model);
				
			}
			
			
						
		}
		
		
		
		
		return listresult;
	}
	
	
	private static SingleNode<Integer, String> getEntity(
			SingleNode<Integer, String> s, SentenceModel sm) {
		
		int index=s.getNumber();
		String enity=sm.getNer().get(index);
		
		return new SingleNode<Integer, String>(index,enity);
	}


	private static boolean isEntity(SingleNode<Integer, String> s,
			SentenceModel sm) {
		
		
		int index=s.getNumber();
		String enity=sm.getNer().get(index);
		
		if(enity.equals("O")){
		
		return false;
		
		}
		else{
			
		return true;	
		}
		
	}


	private static SingleNode<Integer,String> getSuperSense(SingleNode<Integer,String> sn,SentenceModel sm) throws Exception{
		
		String posNorm=sm.getPosNormalizeWord().get(sn.getNumber());
		
		String lemma=sm.getLemma().get(sn.getNumber());
		
		
		String ss=WordnetSuperSenseRetrival.getSuperSenseToken(lemma, posNorm);
	    
		return new SingleNode<Integer, String>(sn.getNumber(),ss);
		
		
	}
	
	
}
