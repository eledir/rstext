package edu.southampton.wais.STPLibrary.utility;

public class PrintFormattingUtility {

	
	  public static String pad(String in, int length) {
			if (in.length() > length) return in.substring(0,length-3) + "...";
			if (in.length() == length) return in;
			StringBuilder sb = new StringBuilder(length);
			sb.append(in);
			while (sb.length() < length) sb.append(' ');
			return sb.toString();
			
		    }
}
