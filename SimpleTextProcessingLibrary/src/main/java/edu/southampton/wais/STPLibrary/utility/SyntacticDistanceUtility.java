package edu.southampton.wais.STPLibrary.utility;

public class SyntacticDistanceUtility {
	
	
	
	
	
	
	
	public  static double getSimilarity(String wordA, String wordB) {
        final double levensteinDistance = getUnNormalisedSimilarity(wordA,
                        wordB);
        // convert into zero to one return

        // get the max possible levenstein distance score for string
        float maxLen = wordA.length();
        if (maxLen < wordB.length()) {
                maxLen = wordB.length();
        }

        // check for 0 maxLen
        if (maxLen == 0) {
                return 1.0; // as both strings identically zero length
        }

        // return actual / possible levenstein distance to get 0-1 range
        return 1.0 - (levensteinDistance / maxLen);
}

/**
 * <p>
 * implements the Levenstein distance function.
 * </p>
 *
 * <pre>
 * Copy character from string1 over to string2 (cost 0)
 * Delete a character in string1 (cost 1)
 * Insert a character in string2 (cost 1)
 * Substitute one character for another (cost 1)
 *
 * D(i-1,j-1) + d(si,tj) //subst/copy
 * D(i,j) = min D(i-1,j)+1 //insert
 * D(i,j-1)+1 //delete
 *
 * d(i,j) is a function whereby d(c,d)=0 if c=d, 1 else.
 * </pre>
 *
 * @param s
 * @param t
 * @return the levenstein distance between given strings
 */
public static double getUnNormalisedSimilarity(final String s, final String t) {
        final double[][] d; // matrix
        final int n; // length of s
        final int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        double cost; // cost

        // Step 1
        n = s.length();
        m = t.length();
        if (n == 0) {
                return m;
        }
        if (m == 0) {
                return n;
        }
        d = new double[n + 1][m + 1];

        // Step 2
        for (i = 0; i <= n; i++) {
                d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
                d[0][j] = j;
        }

        // Step 3
        for (i = 1; i <= n; i++) {
                // Step 4
                for (j = 1; j <= m; j++) {
                        // Step 5
                        cost = getCost(s, i - 1, t, j - 1);

                        // Step 6

                       

                        d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1,
                                        d[i - 1][j - 1] + cost);
                }
        }




        


        // Step 7
        return d[n][m];
}

private static final double getCost(final String str1, final int string1Index,
                final String str2, final int string2Index) {

    
        if (str1.charAt(string1Index) == str2.charAt(string2Index)) {

        // System.out.println(str1.charAt(string1Index)+ " "+ str2.charAt(string2Index));
         return 0.0;
        }


       

        return 1.0;
}







/**
 * Min Value
 *
 * @param d
 * @return the min value in the array
 */
private static double min(double... d) {
        double result = Double.MAX_VALUE;
        for (double e : d) {
                result = Math.min(result, e);
        }

        return result;
}


}
