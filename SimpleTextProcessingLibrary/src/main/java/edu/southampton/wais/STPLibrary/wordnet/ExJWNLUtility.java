package edu.southampton.wais.STPLibrary.wordnet;

import java.util.List;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerType;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.list.PointerTargetNode;
import net.sf.extjwnl.data.list.PointerTargetNodeList;
import net.sf.extjwnl.data.list.PointerTargetTree;
import net.sf.extjwnl.data.relationship.AsymmetricRelationship;
import net.sf.extjwnl.data.relationship.RelationshipFinder;
import net.sf.extjwnl.data.relationship.RelationshipList;

public class ExJWNLUtility {

	public static int totaldepth=89330;

	public static Synset computeCommonAncestor(Synset a, Synset b) throws  CloneNotSupportedException{


		RelationshipList list = RelationshipFinder.findRelationships(a, b, PointerType.HYPERNYM);

		if(list.size()>0){



			AsymmetricRelationship ar = (AsymmetricRelationship) list.getShallowest(); // why 0??
					PointerTargetNodeList nl = ar.getNodeList();
					PointerTargetNode ptn = (PointerTargetNode) nl.get(ar.getCommonParentIndex());
					Synset anc=ptn.getSynset();

					return anc;



		}

		else {return null;
		}
	}

	public static  double computeInformationContent(Synset snynset,int depth) {




		int count=0;

		if(snynset.getPOS().equals(POS.NOUN)){




			PointerTargetTree tre;
			try {
				tre = PointerUtils.getHypernymTree(snynset);
				List l1=tre.toList();

				count=count+l1.size();
				//fre space
				l1.clear();
				l1=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    



			try {
				tre = PointerUtils.getHyponymTree(snynset); 
				List l2=tre.toList();

				count=count+l2.size();
				//fre space
				l2.clear();
				l2=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    




			try {
				PointerTargetNodeList l3= PointerUtils.getSynonyms(snynset); 

				count=count+l3.size();
				//fre space
				l3.clear();
				l3=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    


			try {
				PointerTargetNodeList l4= PointerUtils.getHolonyms(snynset); 

				count=count+l4.size();
				//fre space
				l4.clear();
				l4=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    

			try {
				PointerTargetNodeList l5= PointerUtils.getPartHolonyms(snynset);

				count=count+l5.size();
				//fre space
				l5.clear();
				l5=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			try {
				PointerTargetNodeList l6= PointerUtils.getMemberHolonyms(snynset);

				count=count+l6.size();
				//fre space
				l6.clear();
				l6=null;

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				PointerTargetNodeList l7= PointerUtils.getMeronyms(snynset);

				count=count+l7.size();
				//fre space
				l7.clear();
				l7=null;


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			try {
				PointerTargetNodeList l8= PointerUtils.getPartMeronyms(snynset);
				count=count+l8.size();

				//fre space
				l8.clear();
				l8=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				PointerTargetNodeList l9= PointerUtils.getMemberMeronyms(snynset);
				count=count+l9.size();
				//fre space
				l9.clear();
				l9=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}






		}
		else if(snynset.getPOS().equals(POS.VERB)){





			PointerTargetTree tre;
			try {
				tre = PointerUtils.getHypernymTree(snynset);
				List l1= tre.toList();
				count=count+l1.size();
				//fre space
				l1.clear();
				l1=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    


			try {
				tre = PointerUtils.getHyponymTree(snynset); 
				List l2= tre.toList();
				count=count+l2.size();
				//fre space
				l2.clear();
				l2=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    


			try{
				List l3= PointerUtils.getSynonyms(snynset);    

				count=count+l3.size();
				//fre space
				l3.clear();
				l3=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    


			try {
				tre = PointerUtils.getEntailmentTree(snynset);
				List l4 =tre.toList();
				count=count+l4.size();
				//fre space
				l4.clear();
				l4=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    







		}

		else if(snynset.getPOS().equals(POS.ADJECTIVE)){




			List l1;

			try {
				l1 = PointerUtils.getSynonyms(snynset);

				count=count+l1.size();
				//fre space
				l1.clear();
				l1=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    


			List l2;
			try {
				l2 = PointerUtils.getAlsoSees(snynset);
				count=count+l2.size();
				//fre space
				l2.clear();
				l2=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    


		}



		
		
		


		return(1- (Math.log(count+1)/Math.log(depth)));







	}






	
}








