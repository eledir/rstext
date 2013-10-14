package edu.southampton.wais.STPLibrary.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

import com.hp.hpl.jena.sparql.pfunction.library.concat;

import edu.southampton.wais.STPLibrary.collection.CollectionDocumentBySentence;
import edu.southampton.wais.STPLibrary.model.DocumentModelBySentence;
import edu.southampton.wais.STPLibrary.nlp.RegularExpressionUtility;
import edu.southampton.wais.STPLibrary.nlp.StringProcessor;

public class CollectionSentenceModelUtility {

	public static List<File> writeCollectionToFiles(
			CollectionDocumentBySentence collectionSentenceModel, String dir,
			String defaulName) throws IOException {

		List<File> listFile = new ArrayList<File>();

		for (DocumentModelBySentence item : collectionSentenceModel) {

			for (Map.Entry<Integer, String> sentence : item.getEntrySet()) {

				File f = new File(dir + File.separator + defaulName + "_"
						+ sentence.getKey() + ".txt");

				listFile.add(f);

				FileUtils.writeStringToFile(f, sentence.getValue());

			}

		}

		return listFile;

	}

}
