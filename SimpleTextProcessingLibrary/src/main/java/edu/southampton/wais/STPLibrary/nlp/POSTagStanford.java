package edu.southampton.wais.STPLibrary.nlp;

import java.util.List;

import edu.southampton.wais.STPLibrary.model.AnnotatedWord;

public class POSTagStanford {

	static String cc = "CC"; // Coordinating conjunction
	// e.g. and,but,or...
	static String CD = "CD"; // Cardinal Number
	static String DT = "DT";// Determiner
	static String EX = "EX"; // Existential there
	static String FW = "FW";// Foreign Word
	static String IN = "IN";// Preposision or subordinating conjunction
	static String JJ = "JJ";// Adjective
	static String JJR = "JJR"; // Adjective comparative
	static String JJS = "JJS"; // Adjective, superlative
	static String LS = "LS";// List Item Marker
	static String MD = "MD";// Modal
	// e.g. can, could, might, may...
	static String NN = "NN"; // Noun, singular or mass
	static String NNP = "NNP"; // Proper Noun, singular
	static String NNPS = "NNPS"; // Proper Noun, plural
	static String NNS = "NNS"; // Noun, plural
	static String PDT = "PDT"; // Predeterminer
	// e.g. all, both ... when they precede an article
	static String POS = "POS"; // Possessive Ending
	// e.g. Nouns ending in 's
	static String PRP = "PRP"; // Personal Pronoun
	// e.g. I, me, you, he...
	static String PRP$ = "PRP$"; // Possessive Pronoun
	// e.g. my, your, mine, yours...
	static String R = "RB"; // Adverb
	// Most words that end in -ly as well as degree words like quite, too and
	// very
	static String RBR = "RBR"; // Adverb, comparative
	// Adverbs with the comparative ending -er, with a strictly comparative
	// meaning.
	static String RBS = "RBS"; // Adverb, superlative
	static String RP = "RP"; // Particle
	static String SYM = "SYM";// Symbol
	// Should be used for mathematical, scientific or technical symbols
	static String TO = "TO"; // to
	static String UH = "UH";// Interjection
	// e.g. uh, well, yes, my...
	static String VB = "VB"; // Verb, base form
	// subsumes imperatives, infinitives and subjunctives
	static String VBD = "VBD"; // Verb, past tense
	// includes the conditional form of the verb to be
	static String VBG = "VBG"; // Verb, gerund or persent participle
	static String VBN = "VBN"; // Verb, past participle
	static String VBP = "VBP"; // Verb, non-3rd person singular present
	static String VBZ = "VBZ";
	// Verb, 3rd person singular present
	static String WDT = "WDT"; // Wh-determiner
	// e.g. which, and that when it is used as a relative pronoun
	static String WP = "WP"; // Wh-pronoun
	// e.g. what, who, whom...
	static String WP$ = "WP$"; // Possessive wh-pronoun
	// e.g.
	static String WRB = "WRB"; // Wh-adverb
	// e.g. how, where why

	public final static String ADJ = "a";

	public final static String VERB = "v";
    
	public final static String NOUN = "n";

	public final static String ADVERB = "adv";

	public final static String OTHER = "o";

	public final static String WORD = "word";

	public final static String POSType = "pos";

	public final static String NER = "ner";

	public final static String NERNORM = "nerNorm";

	public final static String LEMMA = "lemma";

	public final static String STEM = "stem";

	public final static String POSNORM = "posNorm";

	public static boolean isVerb(String pos)  {

		

		if (pos.equals(VB) || pos.equals(VBD) || pos.equals(VBG)
				|| pos.equals(VBN) || pos.equals(VBP) || pos.equals(VBZ)

				)
			return true;
		else
			return false;
	}

	public POSTagStanford() {
		super();
	}

	public static boolean isNoun(String pos)  {

		

		if (pos.equals(NN) ||pos.equals(PRP)|| pos.equals(NNP) || pos.equals(NNPS)
				|| pos.equals(NNS))
			return true;
		else
			return false;
	}

	public static boolean isAdjective(String pos) {

		

		if (pos.equals(JJ) || pos.equals(JJR) || pos.equals(JJS)

				)
			return true;
		else
			return false;
	}

	public static String getNormalizedPOS(String s) throws Exception {

		if (s == null || s.isEmpty()) {

			throw new Exception("string empty");

		}

		if (isAdjective(s)) {

			return POSTagStanford.ADJ;

		}

		else if (isNoun(s)) {

			return POSTagStanford.NOUN;

		}

		else if (isVerb(s)) {

			return POSTagStanford.VERB;

		}

		else if (isAdverb(s)) {

			return POSTagStanford.ADVERB;
		}

		else {

			return POSTagStanford.OTHER;

		}

	}

	public static boolean isAdverb(String pos) {

		

		if (pos.equals(RBR) || pos.equals(R) || pos.equals(RBS)

				)
			return true;
		else
			return false;
	}

}
