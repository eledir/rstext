package edu.southampton.wais.STPLibrary.weka;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;



import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class WekaClassifierExample {

	
	
	
	
	public  static void main(String []args){
		
		
		
		String fileTrainingName="/home/antonio/Data/documentIextreme/training.arff";
	
		String fileTestingName="/home/antonio/Data/documentIextreme/testing.arff";
		
		
		 DataSource sourceTrainig;
		
		 DataSource sourceTesting;
		 
		 
		 
			
		 try {
			sourceTrainig = new DataSource(fileTrainingName);
			
			
			Instances instancesTrain = sourceTrainig.getDataSet();
			
			
			Instance firstInstanceTrain=instancesTrain.firstInstance();
			
			
			// Make the last attribute be the class
			
			instancesTrain.setClassIndex(instancesTrain.numAttributes() - 1);
			
			
			
            J48 cModelJ48 = new J48();
			
			
			System.out.println("building classifier j48");
			
			cModelJ48.buildClassifier(instancesTrain);
			
			System.out.println("done");
			
			
			
		    Classifier cModelNB =(Classifier)new NaiveBayes();
			
			
			System.out.println("building classifier nayeve bayes");
			
			cModelNB.buildClassifier(instancesTrain);
			
			System.out.println("done");
			
			
			
            Classifier cModelLogistic =(Classifier)new Logistic();
			
			
			System.out.println("building classifier nayeve bayes");
			
			cModelLogistic.buildClassifier(instancesTrain);
			
			System.out.println("done");
			
			
			
			
			
			sourceTesting=new DataSource(fileTestingName);
			
			
			Instances instancesTesting= sourceTesting.getDataSet();
			
			
			
			
			File fileOut= new File("/home/antonio/Data/documentIextreme/error.log");
			
			FileWriter fileWriter = new FileWriter(fileOut,true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
				
			
			 
			 
			System.out.println("Start classification..");
			 
			for(int i=0;i<instancesTesting.numInstances();i++ ){
				
			
				
				
				
                Instance instanceTesting=instancesTesting.instance(i);
				
				
				Attribute at=instanceTesting.attribute(0);
				
				String name=at.value(i);
				
				
				String namVet[]=name.split(":");
				
				name=namVet[1];
				
				Integer value= new Integer(namVet[0]);
				
				
				Instance newInstance = new Instance(instanceTesting.numAttributes()-1);
				
			 try{
				
				for(int j= 0; j<instanceTesting.numAttributes()-1;j++){
					
					try{
					newInstance.setValue(firstInstanceTrain.attribute(j), instanceTesting.value(instancesTesting.attribute(j+1)));
					}
					catch (Exception e){
						
						System.out.println(e);
						 System.out.println(firstInstanceTrain.attribute(j));
							
							System.out.println(instancesTesting.attribute(j+1));
								
						
					}
				}
				
				newInstance.setDataset(instancesTrain );
				
				classifierDecisionTree(cModelJ48,newInstance,value.intValue(),name);
				
				classifierNayve(cModelNB,newInstance,value.intValue(),name);
				
				classifierLogistic(cModelLogistic,newInstance,value.intValue(),name);
				
			 }
			
			 catch (Exception e){
				
			           System.out.println(e);
				       writeException(name,bufferedWriter);   
				 
				
			}	
				 
				 
				 
				
				
				
				
				
				
				
				
				
				
				
				
				
			}	
			 
			 bufferedWriter.close();
			 fileWriter.close();
					 
			
				
				
			
			
			
			
			
			
			
			
			
		
			
			
		
		
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}

	private static void writeException(String name, BufferedWriter buffer ) throws IOException {
		// TODO Auto-generated method stub
		
		
		
		
		buffer.write(name);
		buffer.write("\n");
		
		
		
		
		
		
	}

	private static void classifierLogistic(Classifier cModelLogistic,
			Instance newInstance,int value,String name) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		// Get the likelihood of each classes 
		 // fDistribution[0] is the probability of being �positive� 
		 // fDistribution[1] is the probability of being �negative� 
		 double[] fDistribution =  cModelLogistic.distributionForInstance(newInstance);
		
		 System.out.println("----------------------------------------------------");
		 
		 double valueC= cModelLogistic.classifyInstance(newInstance);
		 
		 System.out.println("Classification logistic result for "+name+ " "+ valueC);
		 
		 
		 System.out.println(fDistribution[0]);
		 
		 System.out.println(fDistribution[1]);
		 
		 System.out.println("----------------------------------------------------");
		 
		
		
		 
		
		
		
	}

	private static void classifierNayve(Classifier cModelNB,
			Instance newInstance,int value,String name) throws Exception {
		
		
		
		// Get the likelihood of each classes 
		 // fDistribution[0] is the probability of being �positive� 
		 // fDistribution[1] is the probability of being �negative� 
		 double[] fDistribution =  cModelNB.distributionForInstance(newInstance);
		
		 System.out.println("----------------------------------------------------");
		 
		 double valueC= cModelNB.classifyInstance(newInstance);
		 
		 System.out.println("Classification nayeve result for "+name+ " "+ valueC);
		 
		 
		 System.out.println(fDistribution[0]);
		 
		 System.out.println(fDistribution[1]);
		 
		 System.out.println("----------------------------------------------------");
		 
		
		
		 
	}

	private static void classifierDecisionTree(J48 cModelJ48,
		Instance newInstance,int value,String name) throws Exception {
		
		
		
		
		// Get the likelihood of each classes 
		 // fDistribution[0] is the probability of being �positive� 
		 // fDistribution[1] is the probability of being �negative� 
		 double[] fDistribution =  cModelJ48.distributionForInstance(newInstance);
		
		 System.out.println("----------------------------------------------------");
		 
		 double valueC= cModelJ48.classifyInstance(newInstance);
		 
		 System.out.println("Classification decisiontee result for "+name+ " "+ valueC);
		 
		 
		 System.out.println(fDistribution[0]);
		 
		 System.out.println(fDistribution[1]);
		 
		 System.out.println("----------------------------------------------------");
		 
		
		
		 
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
