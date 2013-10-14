package edu.southampton.wais.STPLibrary.nlp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

/**
 * @author LabGuest
 * 
 */
public class StringProcessor {

	private static final String puncs = ".!,:;)(_'\"|/*?#=```^!][";

	private static Map<String,String> mapReplace= new HashMap<String, String>();
	
	
	
	public StringProcessor(){
		
		mapReplace.put("�", " ");
		mapReplace.put("�", " ");
		mapReplace.put("'", "");
		mapReplace.put("^", " ");
		mapReplace.put("�", "");
		mapReplace.put("�", "");
		mapReplace.put("�", "");
		
		
		
	}
	
	
	
	public static boolean containsNumbers(String word) {
		if (word.contains("0") || word.contains("1") || word.contains("2")
				|| word.contains("3") || word.contains("4")
				|| word.contains("5") || word.contains("6")
				|| word.contains("7") || word.contains("8")
				|| word.contains("9")) {
			return true;
		} else
			return false;
	}

	public static boolean StringToBoolean(String in) {

		if (in.equals("true"))
			return true;
		else
			return false;

	}

	public static boolean isNullValue(String in)

	{
		if (in == null)
			return true;
		else
			return in.equals("");

	}

	/*
	 * Remove a character at a specified position from a string
	 */
	public static String removeCharAt(String s, int pos) {
		return s.substring(0, pos) + s.substring(pos + 1);
	}

	
	
	/*
	 * Remove a character at a specified position from a string
	 */
	public static String replaceCharAt(String s,String replace, int pos) {
		return s.substring(0, pos) +replace+ s.substring(pos + 1);
	}
	
	
	/*
	 * Remove a character from a string
	 */
	public static String removeChar(String s, char c) {
		String r = "";

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != c)
				r += s.charAt(i);
		}

		return r;
	}

	public static String precessDash(String p) {
		String[] split = p.split("-");
		int inde = 0;
		if (split.length == 1)
			return split[0];
		StringBuffer out = new StringBuffer();

		for (int i = 0; i < split.length; i++) {
			split[i] = split[i].trim();
			if (split[i].length() <= 2) {
				split[i] = null;
				++inde;
			}

		}

		for (int i = 0; i < split.length; i++) {
			if (split[i] != null) {
				out.append(split[i]);
				if (i != split.length - 1)
					out.append("-");
			}
		}

		return out.toString();
	}

	/*
	 * Remove Punctuations within a word
	 */
	public static String removeWeirdPunctuations(String word) {
		int count = 0;

		while (word.substring(count).length() > 0) {
			if (puncs.contains(word.substring(count, count + 1)))
				word = removeCharAt(word, count);
			else
				count++;
		}

		return word;
	}

	
	public String replaceWeirdPunctuations(String word){
		
		
		int count = 0;

		while (word.substring(count).length() > 0) {
			if (mapReplace.containsKey(word.substring(count, count + 1)))
				
				word = replaceCharAt(word,mapReplace.get(word.substring(count, count + 1)), count);
			
			else
				count++;
		}
		
		
		return word;
		
	}
	
	
	/*
	 * Arbitrary String adjustments
	 */
	public static String killChars(String word) {
		// Remove aposthrope and plural s's
		if (word.endsWith("'s")) {
			word = word.substring(0, word.length() - 2);
		}

		// Remove aposthrope and plural s's
		if (word.endsWith("'")) {
			word = word.substring(0, word.length() - 1);
		}

		// Remove aposthrope and plural s's
		if (word.endsWith(">")) {
			word = word.substring(0, word.length() - 1);
		}

		// Remove )
		if (word.endsWith(")")) {
			word = word.substring(0, word.length() - 1);
		}

		// Remove ?
		if (word.endsWith("?")) {
			word = word.substring(0, word.length() - 1);
		}

		// Remove (
		if (word.startsWith("(")) {
			word = word.substring(1, word.length());
		}

		// Remove (
		if (word.startsWith(">")) {
			word = word.substring(1, word.length());
		}

		// Remove will ('ll)
		if (word.endsWith("'ll")) {
			word = word.substring(0, word.length() - 3);
		}

		// Remove words enclosed in <>
		if ((word.startsWith("<") && word.endsWith(">"))
				|| (word.startsWith("[") && word.endsWith("]"))) {
			word = word.substring(1, word.length() - 1);
		}
		if (word.contains("<") || word.contains(">") || word.contains("[")
				|| word.contains("}"))
			word = "";

		// Remove had, would ('d)
		/*if (word.endsWith("'d")) {
			word = word.substring(0, word.length() - 2);
		}
*/
		// Remove 't (like in don't didn't)
		// Remove the word completely, because it may be used frequently
		// But using them frequently does not mean that they are keywords.
		// They can be in in front of any verb.
		/*if (word.endsWith("'t")) {
			word = "";

			// if(word.equals("don't") ||
			// word.equals("haven't") ||
			// word.equals("hadn't") ||
			// word.equals("wouldn't") )
			// word = word.substring(0, word.length()-3);

			// if(word.equals("won't"))
			// word = ""; // will be removed later, since its length is 0.
		}*/

		// Remove emails
		if (word.contains("@")) {
			word = "";
		}

		// Remove web links
		if (word.contains("http") || word.contains("www")) {
			word = "";
		}

		// Remove ~'s
		if (word.contains("~")) {
			word = "";
		}

		// Remove $'s
		if (word.contains("$")) {
			word = "";
		}

		// Remove &'s
		if (word.contains("&")) {
			word = "";
		}

		// Remove 'm (like in I'm)
		if (word.contains("'m")) {
			word = word.substring(0, word.length() - 2);
		}

		/*// remove 're (lie you're)
		if (word.contains("'re")) {
			word = word.substring(0, word.length() - 2);
		}
*/
		return word;
	}

	public String correctingString(String word) {

		word = killChars(word);

		word = removeWeirdPunctuations(word);
		
		word=replaceWeirdPunctuations(word);

		word = precessDash(word);

		word = removePunctuation(word);

		return containsNumbers(word)? "":word;
	}

	
	
	
	
	
	public static boolean union(String word) {

		StringTokenizer tokenizer;

		tokenizer = new StringTokenizer(word, ".");

		if (tokenizer.countTokens() > 1) {

			return true;
		}

		else
			return false;

	}

	public static StringTokenizer separateunion(String word) {

		StringTokenizer tokenizer;

		tokenizer = new StringTokenizer(word, ".");

		return tokenizer;

	}

	// removePunctuation() method to remove punctuations
	// at the end or at the beginnig of the words
	public static String removePunctuation(String word) {
		while (word.endsWith(".") || word.endsWith("!") || word.endsWith(",")
				|| word.endsWith(":") || word.endsWith("\"")
				|| word.endsWith(";") || word.endsWith(")")
				|| word.endsWith("'") || word.endsWith("/")
				|| word.endsWith("\\") || word.endsWith("?")
				|| word.endsWith("]") || word.endsWith("]")
				|| word.endsWith("”") || word.endsWith("’")
				|| word.endsWith("}") || word.endsWith("{")
				|| word.endsWith("-")) {
			word = word.substring(0, word.length() - 1);
		}

		while (word.startsWith("\"") || word.startsWith("'")
				|| word.startsWith("(") || word.startsWith("-")
				|| word.startsWith("[") || word.startsWith("’")
				|| word.startsWith("‘") || word.startsWith("}")
				|| word.startsWith("{") || word.startsWith("-")
				|| word.startsWith("”") || word.startsWith(":")
				|| word.startsWith(";") || word.startsWith(".")
				|| word.startsWith("!") || word.startsWith(",")
				|| word.startsWith(":") || word.startsWith("/")
				|| word.startsWith("\\") || word.startsWith("?")
				|| word.startsWith("]")

		) {
			word = word.substring(1, word.length());
		}

		return word;
	}

	public static String[] splitWordChar(String word) {

		String re = ".|:|;|_|/|\\*|`|``|-";

		// TODO Auto-generated method stub
		return word.split(re);
	}

	public List<String> cleanSentence(String sent,Pattern tokenPattern){

		 PTBTokenizer ptbt = new PTBTokenizer(new StringReader(sent),
	              new CoreLabelTokenFactory(), "ptb3Escaping=false");

		 CoreLabel label;
		
		List<String> list=Lists.newArrayList();
		
		for (; ptbt.hasNext(); ) {
	        label = (CoreLabel) ptbt.next();
		
	       String word=label.originalText();
		
	       
	       
	       
		   if(isEnable(tokenPattern, word)){
		
			String tokenClean=correctingString(word);

			if(!tokenClean.isEmpty()){

				list.add(tokenClean);

			}

		}
		}

		return list;




	}

	private static List<String> cleanNumber(List<Object> asList) {

		List<String> list = new ArrayList<String>();

		for (Object item : asList) {

			if (!containsNumbers((String) item)) {

				list.add((String) item);
			}

		}

		// TODO Auto-generated method stub
		return list;
	}

	public static boolean isEnable(Pattern p, String word) {

		Matcher m = p.matcher(word);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}

	}

	public static void main(String args[]) {

		String[] ss = new String[] { ">about", ">a", "conspiracy]",
				"correction:", "vulture...", "____/", "holak)" };

		String[] ss2 = new String[] { "it", "expounds", "each surah as]",
				"a:"};

		
		StringProcessor p= new StringProcessor();
		
		Pattern patternSentValidation = Pattern.compile("[a-zA-Z\\-]+");

		
		String sent="Tadabbur-i-Qur'an is a tafsir (exegeses) of the Qur'an by Amin Ahsan Islahi based on the concept of thematic and structural coherence, which was originally inspired by Allama Hamiduddin Farahi.";
		
		List<String>newSent=p.cleanSentence(sent, patternSentValidation);
		
		System.out.println(newSent);
		
		
		
		
		
		for (String item : ss2) {

			String newItem = p.correctingString(item);

			System.out.println("old item " + item);
			System.out.println("new  item " + newItem);

		}

	}

}