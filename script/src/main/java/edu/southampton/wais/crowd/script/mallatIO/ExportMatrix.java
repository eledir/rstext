package edu.southampton.wais.crowd.script.mallatIO;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
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
import cc.mallet.types.FeatureVector;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Label;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import edu.southampton.wais.STPLibrary.utility.ColtUtility;


public class ExportMatrix {

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
	            Pattern.compile("[\\p{L}\\p{N}_]+|[\\p{P}]+");

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

	    	
	    	String dirTrain="/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate/20news-bydate-train";
	    	String dirTest="/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate/20news-bydate-test";
	    	
	    	
	    	MallatClassiFicationScript importer = new MallatClassiFicationScript();
	    	
	    	Pipe pipe=importer.buildPipe();
	        
	    	InstanceList instancesTrain = importer.readDirectory(new File(dirTrain),pipe);
	    	
	    	pipe.getAlphabet().stopGrowth();
	    	
	    	InstanceList instancesTest = importer.readDirectory(new File(dirTest),pipe);
	    
	    	System.out.println("Instances test "+instancesTest.size());
	    	System.out.println("Instances train "+instancesTrain.size());
	    
	          
	    	
	    	
	    	
	    	
	    	SparseDoubleMatrix2D matrixTrain= new SparseDoubleMatrix2D(instancesTrain.size(), pipe.getDataAlphabet().size()+1);
	    	
	    	int numFeatures = instancesTrain.getDataAlphabet().size();

	    	
           
           //alphabetTarget.dump();
           
           
           int indexDoc=0;
           
	    
	    	for(Instance inst: instancesTrain){
	    		
		    	FeatureVector fv = (FeatureVector) inst.getData ();
		    	
	    		
		    	Label o=(Label) inst.getTarget();
		    	
		    	Integer indexLabel=o.getIndex();
		    	
		    	
		    	
		    	
		    	
	    		for (int fvi=0; fvi<numFeatures; fvi++){

	    			//Object feat = golbalAlphabet.lookupObject(fvi);
	    			
	    			double feat_value = fv.value(fvi);

	    			matrixTrain.set(indexDoc, fvi, feat_value);
		    		
	    		   
	    		}	    		
	    		
	    		matrixTrain.set(indexDoc, numFeatures, indexLabel);
	    			
	    		indexDoc++;
	    		
 	    		
	    		   
	    		   
	    		
	    	}
	    	
	   
	    	System.out.println("write matrix");
	    	
	    	ColtUtility.printSparseColtMatrix(new File("/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate"), "matrix_rude_parser_train.txt", matrixTrain, ",");
	    	
	    	System.out.println("done");
	    	
	    	
	    	
	    	
SparseDoubleMatrix2D matrixTest= new SparseDoubleMatrix2D(instancesTest.size(), pipe.getDataAlphabet().size()+1);
	    	
	    numFeatures = instancesTest.getDataAlphabet().size();

	    	
   
	    	
	    	
	    	
	    	  indexDoc=0;
	           
	 	    
		    	for(Instance inst: instancesTest){
		    		
			    	FeatureVector fv = (FeatureVector) inst.getData ();
			    	
		    		
			    	Label o=(Label) inst.getTarget();
			    	
			    	Integer indexLabel=o.getIndex();
			    	
			    	
			    	
			    	
			    	
		    		for (int fvi=0; fvi<numFeatures; fvi++){

		    			//Object feat = golbalAlphabet.lookupObject(fvi);
		    			
		    			double feat_value = fv.value(fvi);

		    			matrixTest.set(indexDoc, fvi, feat_value);
			    		
		    		   
		    		}	    		
		    		
		    		matrixTest.set(indexDoc, numFeatures, indexLabel);
		    			
		    		indexDoc++;
		    		
	 	    		
		    		   
		    		   
		    		
		    	}
	    	
		    	System.out.println("write matrix");
		    	
	    	
		    	ColtUtility.printSparseColtMatrix(new File("/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate"), "matrix_rude_parser_test.txt", matrixTest, ",");
			    
		    	System.out.println("done");
		    	
	    	
	    	
	    	
	    	
	    	
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

