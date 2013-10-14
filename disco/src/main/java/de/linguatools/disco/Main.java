/*******************************************************************************
 *   Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 Peter Kolb
 *   peter.kolb@linguatools.org
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *   use this file except in compliance with the License. You may obtain a copy
 *   of the License at 
 *   
 *        http://www.apache.org/licenses/LICENSE-2.0 
 *
 *   Unless required by applicable law or agreed to in writing, software 
 *   distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *   WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 *   License for the specific language governing permissions and limitations
 *   under the License.
 *
 ******************************************************************************/

package de.linguatools.disco;

import java.io.*;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/*******************************************************************************
 * This class provides the command line interface for DISCO.
 * @author peter
 *******************************************************************************/
public class Main{
    
    
    /*******************************************************************
     * Print usage information.
     *******************************************************************/
    private static void printUsage(){
        System.out.println("DISCO V1.3 -- www.linguatools.de/disco/");
        System.out.println("(C) 2007-2012 Peter Kolb");
        System.out.println("Usage: java -jar disco-1.3.jar <indexDir> <option>");
        System.out.println("Options:\t-f <w>\t\treturn corpus frequency of word " +
                "<w>");
        System.out.println("\t\t-s <w1> <w2>\treturn first order similarity " +
                "between words <w1> and <w2>");
        System.out.println("\t\t-s2 <w1> <w2>\treturn second order similarity " +
                "between words <w1> and <w2>");
        System.out.println("\t\t-bn <w> <n>\treturn the <n> most similar words " +
                "for word <w>");
        System.out.println("\t\t-bs <w> <s>\treturn all words that are at least" +
                " <s> similar to word <w>");
        System.out.println("\t\t-bc <w> <n>\treturn the <n> best collocations" +
                " for word <w>");
        System.out.println("\t\t-cc <w1> <w2>\treturn the common context for" +
                " <w1> and <w2>");
        System.out.println("\t\t-n\t\treturn the number of words in the index");
        System.out.println("\t\t-wl <file>\twrite word frequency list to file");
    }    
    
    /********************************************************************************
     * Main method. Invoke from command line. For options type "java -jar
     * disco-1.3.jar".
     * For more information consult the documentation or visit DISCO's 
     * <a href="http://www.linguatools.de/disco/disco_en.html">web site</a>.
     * @param args command line options
     *********************************************************************************/
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            printUsage();
        }else{
            // erstes Argument muss Index-Verzeichnis sein
            File indexDir = new File(args[0]);
            if( ! indexDir.isDirectory() ){
                System.out.println("Error: can't open directory "+args[0]);
                printUsage();
                return;
            }
            
            // jetzt kommt einer der acht Befehle -f -s -s2 -bn -bs -bc -cc -n
            
            //////////////////////////////////////////            
            // -f <w>: return frequency of word <w> //
            //////////////////////////////////////////
            if( args[1].equals("-f") ){
                if ( args[2] == null ){
                    printUsage();
                    return;
                }
                try {
                    DISCO d = new DISCO(args[0], false);
                    int freq = d.frequency(args[2]);
                    System.out.println(freq);
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }
            }
            /////////////////////////////////////////////////////////////////            
            // -s <w1> <w2>: return similarity between words <w1> and <w2> //
            /////////////////////////////////////////////////////////////////
            else if( args[1].equals("-s") ){
                if ( args[2] == null || args[3] == null ){
                    printUsage();
                    return;
                }
                try {
                    DISCO d = new DISCO(args[0], false);
                    float sim = d.firstOrderSimilarity(args[2], args[3]);
                    if ( sim == -1 ){
                        System.out.println("Error: Word not found in index.");
                    }else{
                        System.out.println(sim);
                    }
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }  
            }
            ///////////////////////////////////////////////////////////////////////////////            
            // -s2 <w1> <w2>: return second order similarity between words <w1> and <w2> //
            ///////////////////////////////////////////////////////////////////////////////
            else if( args[1].equals("-s2") ){
                if ( args[2] == null || args[3] == null ){
                    printUsage();
                    return;
                }
                try {
                    DISCO d = new DISCO(args[0], false);
                    float sim = d.secondOrderSimilarity(args[2], args[3]);
                    if ( sim == -1 ){
                        System.out.println("Error: Word not found in index.");
                    }else{
                        System.out.println(sim);
                    }
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }
            }
            /////////////////////////////////////////////////////////////////            
            // -bn <w> <n>: return the <n> most similar words for word <w> //
            /////////////////////////////////////////////////////////////////
            else if( args[1].equals("-bn") ){
                if ( args[2] == null || args[3] == null ){
                    printUsage();
                    return;
                }
                ReturnDataBN res = new ReturnDataBN();
                try {
                    DISCO d = new DISCO(args[0], false);
                    res = d.similarWords(args[2]);
                    if ( res == null ){ 
                        System.out.println("The word \""+args[2]+"\" was not found."); 
                        return; 
                    }
                    int n = Integer.parseInt(args[3]) - 1;
                    for(int k = 1; k < res.words.length; k++){   // BUG im Indexer für V1
                        System.out.println(res.words[k]+"\t0."+res.values[k]);
                        if( k > n ) break;   // k >= n
                    }
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }
            }
            ////////////////////////////////////////////////////////////////////////            
            // -bs <w> <s>: return all words that are at least <s> similar to <w> //
            ////////////////////////////////////////////////////////////////////////
            else if( args[1].equals("-bs") ){
                if ( args[2] == null || args[3] == null ){
                    printUsage();
                    return;
                }
                ReturnDataBN res = new ReturnDataBN();
                try {
                    DISCO d = new DISCO(args[0], false);
                    res = d.similarWords(args[2]);
                    if ( res == null ){ 
                        System.out.println("The word \""+args[2]+"\" was not found."); 
                        return; 
                    }
                    float s = Float.parseFloat(args[3]);
                    for(int k = 1; k < res.words.length; k++){
                        if( Float.parseFloat("0."+res.values[k]) < s ) break;
                        System.out.println(res.words[k]+"\t0."+res.values[k]);
                    }
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }
            }
            ////////////////////////////////////////////////////////////////            
            // -bc <w> <n>: return the <n> best collocations for word <w> //
            ////////////////////////////////////////////////////////////////
            else if( args[1].equals("-bc") ){
                if ( args[2] == null || args[3] == null ){
                    printUsage();
                    return;
                }
                ReturnDataCol[] res;
                try {
                    DISCO d = new DISCO(args[0], false);
                    res = d.collocations(args[2]);
                    if ( res == null ){ 
                        System.out.println("The word \""+args[2]+"\" was not found."); 
                        return; 
                    }
                    int n = Integer.parseInt(args[3]) - 1;
                    for(int k = 0; k < res.length; k++){
                        System.out.println(res[k].word+"\t"+res[k].value);
                        if( k >= n ) break;
                    }
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }
            }
            ////////////////////////////////////////////////////////////////            
            // -cc <w1> <w2>: return the common context for <w1> and <w2> //
            ////////////////////////////////////////////////////////////////
            else if( args[1].equals("-cc") ){
                if ( args[2] == null || args[3] == null ){
                    printUsage();
                    return;
                }
                // die Kollokationen (= Kontext) zum ersten Wort holen und mit 
                // ihren Werten in ein Hash speichern
                ReturnDataCol[] res;
                HashMap colloHash = new HashMap();
                DISCO d = new DISCO(args[0], false);
                try {
                    res = d.wordvector(args[2]);
                    if ( res == null ){ 
                        System.out.println("The word \""+args[2]+"\" was not found."); 
                        return; 
                    }
                    for(int k = 0; k < res.length; k++){
                        colloHash.put(res[k].word+Integer.toString(res[k].relation),
                                res[k].value);
                    }
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }
                // die Kollokationen zum zweiten Wort holen und mit denen im Hash
                // vergleichen. Gleiche Wörter in ein neues Hash speichern.
                ReturnDataCol[] res2;
                HashMap resHash = new HashMap();
                try {
                    res2 = d.wordvector(args[3]);
                    if ( res2 == null ){ 
                        System.out.println("The word \""+args[3]+"\" was not found."); 
                        return; 
                    }
                    for(int k = 0; k < res2.length; k++){
                        if ( colloHash.containsKey(res2[k].word+Integer.toString(res2[k].relation)) ){
                            resHash.put(res2[k].word, res2[k].value);
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }
                // die Wörter aus dem Ergebnis-Hash in ein Array speichern und sortieren              
                ReturnDataCol[] fin = new ReturnDataCol[resHash.size()];            
                int i = 0;
                for( Iterator it = resHash.keySet().iterator(); it.hasNext(); ){
                    String w = (String) it.next();
                    fin[i++] = new ReturnDataCol(w, ((Float)resHash.get(w)).floatValue() );
                }
                // sortiere Array ReturnDataCol[] nach hoechstem Signifikanzwert
                Arrays.sort( fin, new ValueComparator() );
                for(int k = 0; k < fin.length; k++){
                    System.out.println(fin[k].word+"\t"+fin[k].value);
                }
            }
            /////////////////////////////////////////////////
            // -n: return the number of words in the index //
            /////////////////////////////////////////////////
            else if( args[1].equals("-n") ){
                try {
                    DISCO d = new DISCO(args[0], false);
                    int n = d.numberOfWords();
                    System.out.println(n);
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }    
            }
            ////////////////////////////////////////////
            // -wl: write word frequency list to file //
            ////////////////////////////////////////////
            else if( args[1].equals("-wl") ){
                try {
                    DISCO d = new DISCO(args[0], false);
                    int i = d.wordFrequencyList(args[2]);
                    System.out.println(i+" of "+d.numberOfWords()+" words were written.");
                } catch (IOException ex) {
                    System.out.println("Error: IOException: "+ex);
                }  
                
            // unbekannte Option
            }else{
                System.out.println("Error: unknown command line option: "+args[1]);
                printUsage();
                return;
            }
        }
    }

}// end of class
