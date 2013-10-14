package edu.southampton.wais.crowd.scriptTopic;
import cc.mallet.util.*;
import cc.mallet.types.*;
import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.topics.*;

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class BuildInstanceLists {

	 public static void main(String[] args) throws Exception {	
	
		 
		
		 
		 
		 
	Properties properties= new Properties();
	
	properties.load(new FileInputStream(new File(args[0])));
		 
		 
	 String dir=properties.getProperty("dirCollection");
	 
	 	 
	   
    
    BuildInstanceLists ldaScript= new BuildInstanceLists();
    
    Pipe pipe=ldaScript.buildPipe();
    
	InstanceList instancesList = ldaScript.readDirectory(new File(dir),pipe);
	
    
    
    
    // Create a model with 100 topics, alpha_t = 0.01, beta_w = 0.01
    //  Note that the first parameter is passed as the sum over topics, while
    //  the second is the parameter for a single dimension of the Dirichlet prior.
    int numTopics =  (Integer) properties.get("topic");
    
    ParallelTopicModel model = new ParallelTopicModel(numTopics, 1.0, 0.01);

    model.addInstances(instancesList);

    // Use two parallel samplers, which each look at one half the corpus and combine
    //  statistics after every iteration.
    model.setNumThreads(Runtime.getRuntime().availableProcessors());

    // Run the model for 50 iterations and stop (this is for testing only, 
    //  for real applications, use 1000 to 2000 iterations)
  
    int numIteration =  (Integer) properties.get("iteration");
    
    
    model.setNumIterations(numIteration);
    model.estimate();

    // Show the words and topics in the first instance

    // The data alphabet maps word IDs to strings
    Alphabet dataAlphabet = instancesList.getDataAlphabet();
    
    FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
    LabelSequence topics = model.getData().get(0).topicSequence;
    
    Formatter out = new Formatter(new StringBuilder(), Locale.US);
    for (int position = 0; position < tokens.getLength(); position++) {
        out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
    }
    System.out.println(out);
    
    // Estimate the topic distribution of the first instance, 
    //  given the current Gibbs state.
   
    
 // Get an array of sorted sets of word ID/count pairs
    ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();
    
    
    for(int i=0; i<instancesList.size();i++){
    
    
    double[] topicDistribution = model.getTopicProbabilities(i);

    
    // Show top 5 words in topics with proportions for the first document
    for (int topic = 0; topic < numTopics; topic++) {
    
    	Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();
        
        out = new Formatter(new StringBuilder(), Locale.US);
        out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
        int rank = 0;
        while (iterator.hasNext() && rank < (Integer)(properties.get("word4topic"))) {
            IDSorter idCountPair = iterator.next();
            out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
            rank++;
        }
        System.out.println(out);
    }
    
    }
    
    
    /*// Create a new instance with high probability of topic 0
    StringBuilder topicZeroText = new StringBuilder();
    Iterator<IDSorter> iterator = topicSortedWords.get(0).iterator();

    int rank = 0;
    while (iterator.hasNext() && rank < 5) {
        IDSorter idCountPair = iterator.next();
        topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
        rank++;
    }

    // Create a new instance named "test instance" with empty target and source fields.
    InstanceList testing = new InstanceList(instancesList.getPipe());
    testing.addThruPipe(new Instance(topicZeroText.toString(), null, "test instance", null));

    TopicInferencer inferencer = model.getInferencer();
    double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);
    System.out.println("0\t" + testProbabilities[0]);*/
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
	 
	 public InstanceList readDirectory(File directory,Pipe pipe) {
	        return readDirectories(new File[] {directory},pipe);
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

	        

	      
	        return new SerialPipes(pipeList);
	    }
	 
}
