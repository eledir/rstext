package edu.southampton.wais.crowd.script.engineScript;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;

import weka.gui.InstancesSummaryPanel;

import com.hp.hpl.jena.iri.impl.Test;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.DecisionTree;
import cc.mallet.classify.DecisionTreeTrainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.classify.Trial;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.types.Alphabet;
import cc.mallet.types.InstanceList;
import cc.mallet.util.Randoms;
import edu.southampton.wais.utility.Logger;

public class MallatClassiFicationScript {

	
	
	 public Pipe buildPipe() {
	        ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

	        // Read data from File objects
	        pipeList.add(new Input2CharSequence("UTF-8"));

	        // Regular expression for what constitutes a token.
	        //  This pattern includes Unicode letters, Unicode numbers, 
	        //   and the underscore character. Alternatives:
	        //    "\\S+"   (anything not whitespace)
	        //    "\\w+"    ( A-Z, a-z, 0-9, _ )
	        //    "[\\p{L}\\p{N}_]+|[\\p{P}]+"   (a group of only letters and numbers OR
	        //                                    a group of only punctuation marks)
	        Pattern tokenPattern =
	            Pattern.compile("\\S+");

	        // Tokenize raw strings
	        pipeList.add(new CharSequence2TokenSequence(tokenPattern));

	        // Normalize all tokens to all lowercase
	        pipeList.add(new TokenSequenceLowercase());

	      
	        // Rather than storing tokens as strings, convert 
	        //  them to integers by looking them up in an alphabet.
	        pipeList.add(new TokenSequence2FeatureSequence());

	        // Do the same thing for the "target" field: 
	        //  convert a class label string to a Label object,
	        //  which has an index in a Label alphabet.
	        pipeList.add(new Target2Label());

	        // Now convert the sequence of features to a sparse vector,
	        //  mapping feature IDs to counts.
	        pipeList.add(new FeatureSequence2FeatureVector());

	      
	        return new SerialPipes(pipeList);
	    }

	    public InstanceList readDirectory(File directory,Pipe pipe) {
	        return readDirectories(new File[] {directory},pipe);
	    }

	    public InstanceList readDirectories(File[] directories,Pipe pipe) {
	        
	        // Construct a file iterator, starting with the 
	        //  specified directories, and recursing through subdirectories.
	        // The second argument specifies a FileFilter to use to select
	        //  files within a directory.
	        // The third argument is a Pattern that is applied to the 
	        //   filename to produce a class label. In this case, I've 
	        //   asked it to use the last directory name in the path.
	        FileIterator iterator =
	            new FileIterator(directories, new TxtFilter(),
	                             FileIterator.LAST_DIRECTORY);

	        // Construct a new instance list, passing it the pipe
	        //  we want to use to process instances.
	        InstanceList instances = new InstanceList(pipe);

	        // Now process each instance provided by the iterator.
	        instances.addThruPipe(iterator);

	        return instances;
	    }

	    public static void main (String[] args) throws IOException {

	    	
	    	
	    	
	    	Properties properties = new Properties();

			try {
				properties.load(new FileInputStream(new File(args[0])));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String dirDataNotRelevant = properties.getProperty("basefolderNotRelevant");

			String dirDataRelevant = properties.getProperty("basefolderRelevant");

			
			
			String  dirOut = properties.getProperty("basefolderOut");
	    	
	    	
			String  nameDictonary = properties.getProperty("nameDict");
	    	
	    	
	    	
			
	    	
	    	MallatClassiFicationScript importer = new MallatClassiFicationScript();
	    	
	    	Pipe pipe=importer.buildPipe();
	        
	    	InstanceList instancesNotRelevant = importer.readDirectory(new File(dirDataNotRelevant),pipe);
	    	
	    	
	    	Logger.logInfo("Number of Instances not relevant  "+instancesNotRelevant.size());
	    	
	    	
	    	
	    	InstanceList instancesRelevant = importer.readDirectory(new File(dirDataRelevant),pipe);
	    	
	    	
	    	Logger.logInfo("Number of Instances  relevant  "+instancesRelevant.size());
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	pipe.getAlphabet().dump(new PrintWriter(new File(dirOut+File.separator+nameDictonary )));
	    	
	    	
	    	
	    	instancesNotRelevant.addAll(instancesRelevant);
	    	
	          
	    	
            Alphabet alphabet=instancesNotRelevant.getDataAlphabet();
	        
            
            Logger.logInfo("Number of tokens "+alphabet.size());
	    	
            
            
            InstanceList [] instanceListsSplitted=instancesNotRelevant.split(new Randoms(),
            		new double[] {0.7, 0.3, 0.0});
            
	    	
	    	
	    	ClassifierTrainer trainerMax = new MaxEntTrainer();
	    	
	    	
	    	Logger.logInfo("Start traning with number of instances "+instanceListsSplitted[0].size());
	    	
	    	
	    	Classifier classifierMax =trainerMax.train(instanceListsSplitted[0]);
	    	
	    	Logger.logInfo("Evaluate with number of instances "+instanceListsSplitted[1].size());
	    	
	    	
	    	Trial trialMax= new Trial(classifierMax,instanceListsSplitted[1]);
	    	
	    	
	    	Logger.logInfo(".........MaxEnt..........");
	    		
	    	
	    	Logger.logInfo("all accuracy "+trialMax.getAccuracy());
	    	
	    	
	    	Iterator<String> iterator= instancesNotRelevant.getTargetAlphabet().iterator();
	    	   
		      
		      while(iterator.hasNext()){
		    	   
		    	   String label=iterator.next();
		    	   
		    	   Logger.logInfo("--------------------------------");
		    	   Logger.logInfo("Result for "+label);
		    	   
		    	   Logger.logInfo("precision "+trialMax.getPrecision(label));
		    	   Logger.logInfo("recall "+trialMax.getRecall(label));
		    	   Logger.logInfo("F1 "+trialMax.getF1(label));
		    	   
		    	   
		    	   Logger.logInfo("--------------------------------");
			    		
		    	   
		       }
	    	
	    	


	    }

	    /** This class illustrates how to build a simple file filter */
	    class TxtFilter implements FileFilter {

	        /** Test whether the string representation of the file 
	         *   ends with the correct extension. Note that {@ref FileIterator}
	         *   will only call this filter if the file is not a directory,
	         *   so we do not need to test that it is a file.
	         */
	        public boolean accept(File file) {
	            return file.toString().endsWith(".txt");
	        }
	    }

	}
	
	

