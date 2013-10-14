package edu.southampton.wais.STPLibrary.nlp;

public class POSTagLingPipe {

	
	String sentenceColoser="."; //sentence closer (. ; ? *)
	String leftParen="(";	//left paren
	String rightParen= ")"	;
	String not ="*";//	not, n't
	String dash="--";	//dash
	String comma =",";	
	String colon =":";//	colon
	String prequalifier1="ABL"; //	pre-qualifier (quite, rather)
	String prequantifier2 ="ABN";	//pre-quantifier (half, all)
	String prequantifier3 ="ABX";	//pre-quantifier (both)
	String postdeterminer="AP";	//post-determiner (many, several, next)
	String article ="AT";	//article (a, the, no)
	String be="BE";	//be
	String where="BED";	//were
	String was ="BEDZ";	//was
	String being ="BEG";	//being
	String am =" BEM";	//am
	String been ="BEN";	//been
	String are ="BER"	;//are, art
	String is="BEZ"	;//is
	String conjcntcion ="CC";	//coordinating conjunction (and, or)
	String cardianlNumber="CD";	//cardinal numeral (one, two, 2, etc.)
	String subConjunction="CS";	//subordinating conjunction (if, although)
	String doVerb="DO"	;//do
	String did="DOD";	//did
	String deos ="DOZ";	//does
	String singularDeterminer1="DT";	//singular determiner/quantifier (this, that)
	String singularDeterminer2="DTI";	//singular or plural determiner/quantifier (some, any)
	String pluralDeterminer="DTS";	//plural determiner (these, those)
	String doubleConjunction="DTX";	//determiner/double conjunction (either)
	String existential="EX";	//existential there
	String foreignWord="FW";	//foreign word (hyphenated before regular tag)
	String have="HV";	//have
	String had1="HVD";	//had (past tense)
	String having="HVG";	//having
	String had2="HVN";	//had (past participle)
	String preposition="IN";	//preposition
	String adjective="JJ";	//adjective
	String comparativeadjective ="JJR";	//comparative adjective
	String superlativeAdjective="JJS";	//semantically superlative adjective (chief, top)
	String morphologically="JJT";   //	morphologically superlative adjective (biggest)
	String modalAuliary="MD";	//modal auxiliary (can, should, will)
	String citeWord="NC";	//cited word (hyphenated after regular tag)
	String singularNoun="NN";    //singular or mass noun
	String possssiveSingularNnoun="NN$";	//possessive singular noun
	String pluralNoun=" NNS";	//plural noun
	String possessivePluralNoun="NNS$";	//possessive plural noun
	String properNoun="NP";	//proper noun or part of name phrase
	String possesiveProperNoun="NP$";	//possessive proper noun
	String pluralProperNoun="NPS";	//plural proper noun
	String possessivePluralProperNoun="NPS$";	//possessive plural proper noun
	String adversialNoun="NR";	//adverbial noun (home, today, west)
	String ordninalNumber="OD";	//ordinal numeral (first, 2nd)
	String nominalPronoun="PN";	//nominal pronoun (everybody, nothing)
	String possessiveNominalPronoun="PN$";	//possessive nominal pronoun
	String possessivePersonalPronoun="PP$";	//possessive personal pronoun (my, our)
	String secondNominalPossessivePpronoun="PP$$";	//second (nominal) possessive pronoun (mine, ours)
	String singualrPersoanlPronoun="PPL";	//singular reflexive/intensive personal pronoun (myself)
//	String ="PPLS";	//plural reflexive/intensive personal pronoun (ourselves)
//	String ="PPO" ;//objective personal pronoun (me, him, it, them)
//	String ="PPS";	//3rd. singular nominative pronoun (he, she, it, one)
//	String ="PPSS";	//other nominative personal pronoun (I, we, they, you)
//	String ="QL"	;//qualifier (very, fairly)
//	String ="QLP"	;//post-qualifier (enough, indeed)
//	String ="RB"	;//adverb
//	String ="RBR"	;//comparative adverb
//	String ="RBT"	;//superlative adverb
//	String ="RN"	;//nominal adverb (here, then, indoors)
//	String ="RP"	;//adverb/particle (about, off, up)
//	String ="TO"	;//infinitive marker to
//	String ="UH"	;//interjection, exclamation
	String verbBaseForm="VB"	;//verb, base form
	String verbPastTense="VBD"	;//verb, past tense
	String verbPresent="VBG"	;//verb, present participle/gerund
	String verbPastParticiple="VBN"	;//verb, past participle
	String verb="VBZ"	;//verb, 3rd. singular present
	/*String ="WDT"	;//wh- determiner (what, which)
	String ="WP$"	;//possessive wh- pronoun (whose)
	String ="WPO"	;//objective wh- pronoun (whom, which, that)
	String ="WPS"	;//nominative wh- pronoun (who, which, that)
	String ="WQL"	;//wh- qualifier (how)
	String ="WRB"	;//wh- adverb (how, where, when)
*/
	
	
	public POSTagLingPipe(){
		
	}
	

public  boolean isVerbWithLowerCase(String pos){
	
	
	pos=pos.toUpperCase();
	
	   if(pos.equals(this.be)||
			   pos.equals(this.am)||
			   pos.equals(this.are)||
			   pos.equals(this.be)||
			   pos.equals(this.been)||
			   pos.equals(this.being)||
			   pos.equals(this.did)||
			   pos.equals(this.doVerb)||
			   pos.equals(this.had1)||
			   pos.equals(this.have)||
			   pos.equals(this.have)||
			   pos.equals(this.having)||
			   pos.equals(this.verb)||
			   pos.equals(this.verbPastParticiple)||
			   pos.equals(this.verbBaseForm)||
			   pos.equals(this.verbPresent)||
			   pos.equals(this.verbPastTense)||
			   pos.equals(this.was)||
			   pos.equals(this.where)
			   )
		   return true;
	   else return false;
}
	
	public  boolean  isNounWithLowerCase(String pos){
	
		pos=pos.toUpperCase();
	   if(pos.equals(this.pluralNoun)||
			   pos.equals(this.pluralProperNoun)||
			   pos.equals(this.possesiveProperNoun)||
			   pos.equals(this.possessiveNominalPronoun)||
			   pos.equals(this.possessivePluralProperNoun)||
			   pos.equals(this.possssiveSingularNnoun)||
			   pos.equals(this.singularNoun)
			   )
		   return true;
	   else return false;
	}
	
	
	public  boolean isAdjectiveWithLowerCase(String pos){
		
		 pos=pos.toUpperCase();
		
		   if(pos.equals(this.adjective)||
				   pos.equals(this.comparativeadjective)||
				   pos.equals(this.superlativeAdjective)||
				   pos.equals(this.morphologically)
				   
				   )
			   return true;
		   else return false;
		}


	
	
}
