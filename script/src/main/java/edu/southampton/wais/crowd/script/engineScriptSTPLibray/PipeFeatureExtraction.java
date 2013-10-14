package edu.southampton.wais.crowd.script.engineScriptSTPLibray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.sentdetect.SentenceDetectorME;

import com.google.common.collect.Lists;

import edu.southampton.wais.STPLibrary.collection.CollectionTextFile;
import edu.southampton.wais.STPLibrary.nlp.OpenNLPChunker;
import edu.southampton.wais.STPLibrary.nlp.OpenNLPTextSentenceProcessor;
import edu.southampton.wais.STPLibrary.paramater.Parameter;
import edu.southampton.wais.STPLibrary.pipes.PipeBigramFeature;
import edu.southampton.wais.STPLibrary.pipes.PipeChunkFeature;
import edu.southampton.wais.STPLibrary.pipes.PipeRudeFeature;
import edu.southampton.wais.STPLibrary.pipes.PipeRverbFeature;
import edu.southampton.wais.STPLibrary.pipes.PipeWordFeature;
import edu.southampton.wais.STPLibrary.pipes.PipeTrigramFeature;
import edu.southampton.wais.STPLibrary.stanfordWrapper.StanfordCoreNLPService;
import edu.southampton.wais.STPLibrary.utility.IOFileUtility;
import edu.southampton.wais.STPLibrary.utility.IOUtility4STPLibrary;
import edu.southampton.wais.utility.Logger;
import edu.washington.cs.knowitall.extractor.ReVerbExtractor;
import edu.washington.cs.knowitall.extractor.conf.ConfidenceFunction;
import edu.washington.cs.knowitall.extractor.conf.ConfidenceFunctionException;
import edu.washington.cs.knowitall.extractor.conf.ReVerbOpenNlpConfFunction;
import edu.washington.cs.knowitall.nlp.OpenNlpSentenceChunker;
import edu.washington.cs.knowitall.nlp.extraction.ChunkedBinaryExtraction;

public class PipeFeatureExtraction {

	public static void main(String args[]) throws Exception {

		Parameter parameter = null;
		try {
			parameter = new Parameter(new File(args[0]));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

			Logger.logInfo("error load propoerties file...");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

			Logger.logInfo("error load propoerties file...");

		}

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

		String basefolderIN = properties.getProperty("basefolderIN");

		// "/Users/antoniopenta/Documents/DatiRicerca/dataChallenge/20news-bydate/20news-bydate-train";

		String basefolderOut = properties.getProperty("basefolderOut");

		int splitNumber = new Integer(
				properties.getProperty("splitNumberCollection"));

		int minimumNumberWordForSentence = new Integer(
				properties.getProperty("minimumNumberWordForSentence"));


		//setting the minimum number of word to consider for each sentence..
		
		parameter.mengifulTerms=minimumNumberWordForSentence;
		
		

		int pipe =new Integer(
				properties.getProperty("typePipe"));;


				Logger.logFine("loading collection ");

				CollectionTextFile collectionTextFile = IOUtility4STPLibrary
						.loadCollectionTextFile(new File(basefolderIN), "collection");

				Logger.logFine("splitting  collection");

				CollectionTextFile[] collectionTextFilesVet = splitCollections(
						collectionTextFile, splitNumber);

				Logger.logFine("done");



				ExecutorService executorService = Executors.newFixedThreadPool(Runtime
						.getRuntime().availableProcessors() - 1);

				Logger.logFine("pool size "
						+ Runtime.getRuntime().availableProcessors());

				StanfordCoreNLPService stanfordCoreNLPService=null;
				try {

					stanfordCoreNLPService = StanfordCoreNLPService.StanfordPOSTagger(parameter.dirConf);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Logger.logInfo("exception "+e.getMessage());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block

					Logger.logInfo("exception "+e.getMessage());
				}

				// build a collection of metadata
				String nameFileStopList1 = parameter.dirConf + File.separator
						+ "StopWords.txt";
				String nameFileStopList2 = parameter.dirConf + File.separator
						+ "StopWords2.txt";

				HashSet<String> listStop1 = IOFileUtility
						.readHashSetStringFromFile(new File(nameFileStopList1));

				HashSet<String> listStop2 = IOFileUtility
						.readHashSetStringFromFile(new File(nameFileStopList2));

				listStop1.addAll(listStop2);

				List<Future<CollectionTextFile>> list = Lists.newArrayList();
				
				
				SentenceDetectorME modelSentence=OpenNLPTextSentenceProcessor.loadModel(parameter);

				ChunkerModel chunkerModel=OpenNLPChunker.loadModel(parameter);
				
				OpenNlpSentenceChunker chunker=new OpenNlpSentenceChunker();
				

				ReVerbExtractor reverb = new ReVerbExtractor();
				ConfidenceFunction confFunc = new  ReVerbOpenNlpConfFunction();
				
				
				for (int i = 0; i < collectionTextFilesVet.length; i++) {


					Logger.logFine("set thread " + i);


					Future<CollectionTextFile> submit=null;
					//try {


					switch(pipe){

					case 0:
						submit = executorService.submit(


								new PipeRudeFeature(collectionTextFilesVet[i], parameter, listStop1));
						break;



					case 1:
						submit = executorService.submit(


								new PipeWordFeature(collectionTextFilesVet[i], stanfordCoreNLPService, parameter, listStop1));
						break;





					case 2:
						submit = executorService.submit(


								new PipeBigramFeature(collectionTextFilesVet[i], stanfordCoreNLPService,modelSentence, parameter, listStop1));
						break;




					case 3:
						submit = executorService.submit(


								new PipeTrigramFeature(collectionTextFilesVet[i], stanfordCoreNLPService,modelSentence, parameter, listStop1));
						break;


						
					case 4:
						submit = executorService.submit(


								new PipeChunkFeature(collectionTextFilesVet[i], stanfordCoreNLPService,modelSentence, chunkerModel,parameter, listStop1));
						break;
	
						
						
					case 5:
						try {
							submit = executorService.submit(


									new PipeRverbFeature(collectionTextFilesVet[i],stanfordCoreNLPService, chunker,modelSentence,reverb,confFunc, parameter, listStop1));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						
							Logger.logInfo("exception "+e.getMessage());
						}
						break;
						
						
					
					default: throw new Exception("the id pipe is not correct..");	             
					}

					list.add(submit);

					
					/*} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.logInfo("Error starting the thread associated to the  collection "+ collectionTextFilesVet[i].getName());
			}
					 */	






					Logger.logFine("done " + i);

					}




					for (Future<CollectionTextFile> future : list) {
						try {


							CollectionTextFile col=future.get();


							Logger.logFine(" start write collection "+col.getName());

							Logger.logFine(" start write collection in "+basefolderOut+File.separator+col.getName());


							IOUtility4STPLibrary.writeCollectionFile(future.get(),basefolderOut,col.getName());

							Logger.logFine(" wrote collection done ");




						} catch (InterruptedException e) {
							e.printStackTrace();
							Logger.logInfo("exception "+e.getMessage());

						} catch (ExecutionException e) {
							e.printStackTrace();
							Logger.logInfo("exception "+e.getMessage());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Logger.logInfo("exception "+e.getMessage());
						}
					}




					Logger.logFine("shutdown");

					executorService.shutdown();

					Logger.logFine("done");










					Logger.logFine("shutdown done");

				}

	private static CollectionTextFile[] splitCollections(
			CollectionTextFile collectionTextFile, int splitNumber) {

		CollectionTextFile[] vet = new CollectionTextFile[splitNumber];

		int count = collectionTextFile.sizeTexts() / splitNumber;

		int index = 0;

		int countTemp = count;
		for (int i = 0; i < vet.length - 1; i++) {

			vet[i] = new CollectionTextFile(collectionTextFile.getName() + i);

			while (countTemp > 0) {

				vet[i].add(collectionTextFile.getDocByIndex(index));
				index++;
				countTemp--;

			}

			countTemp = count;

		}

		vet[vet.length - 1] = new CollectionTextFile(
				collectionTextFile.getName() + (vet.length - 1));

		while (index < collectionTextFile.sizeTexts()) {

			vet[vet.length - 1].add(collectionTextFile.getDocByIndex(index));
			index++;
		}

		// TODO Auto-generated method stub
		return vet;
	}

}
