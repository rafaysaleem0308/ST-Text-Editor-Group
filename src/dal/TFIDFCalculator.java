package dal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TFIDFCalculator {

	private List<String> corpus = new ArrayList<>();

	public void addDocumentToCorpus(String document) {
	    if (document == null || document.trim().isEmpty()) {
	        return; // Don't add empty documents
	    }
		corpus.add(PreProcessText.preprocessText(document));
	}

	public double calculateDocumentTfIdf(String document) {
	    // FIX 1: Handle null or empty input
	    if (document == null || document.trim().isEmpty()) {
	        return 0.0;
	    }
	    
		String preprocessedDoc = PreProcessText.preprocessText(document);
		String[] words = preprocessedDoc.split("\\s+");
		
		// FIX 2: Handle empty word list after preprocessing
		if (words.length == 0) {
		    return 0.0;
		}
		
		List<String> wordList = Arrays.asList(words);

		Map<String, Double> tf = calculateTermFrequency(wordList);
		Map<String, Double> idf = calculateInverseDocumentFrequency();

		double totalTfIdf = 0.0;
		for (String word : tf.keySet()) {
			double tfValue = tf.get(word);
			double idfValue = idf.getOrDefault(word, Math.log(corpus.size() + 1));
			
			// FIX 3: Handle NaN or Infinite values
			if (Double.isNaN(idfValue) || Double.isInfinite(idfValue)) {
			    idfValue = 0.0;
			}
			
			totalTfIdf += tfValue * idfValue;
		}

		return totalTfIdf / wordList.size();
	}

	private Map<String, Double> calculateTermFrequency(List<String> wordList) {
		Map<String, Double> tf = new HashMap<>();
		double totalWords = wordList.size();
		
		// FIX 4: Handle empty word list
		if (totalWords == 0) {
		    return tf;
		}

		for (String word : wordList) {
			tf.put(word, tf.getOrDefault(word, 0.0) + 1);
		}

		for (String word : tf.keySet()) {
			tf.put(word, tf.get(word) / totalWords);
		}

		return tf;
	}

	private Map<String, Double> calculateInverseDocumentFrequency() {
		Map<String, Double> idf = new HashMap<>();
		int totalDocs = corpus.size();
		
		// FIX 5: Handle empty corpus
		if (totalDocs == 0) {
		    return idf;
		}

		for (String doc : corpus) {
		    if (doc == null || doc.trim().isEmpty()) {
		        continue;
		    }
			Set<String> uniqueWords = Arrays.stream(doc.split("\\s+")).collect(Collectors.toSet());
			for (String word : uniqueWords) {
				idf.put(word, idf.getOrDefault(word, 0.0) + 1);
			}
		}

		for (String word : idf.keySet()) {
			double docCount = idf.get(word);
			// FIX 6: Ensure we don't get log(0) or division by zero
			double idfValue = Math.log((double) totalDocs / (1 + docCount));
			
			// FIX 7: Handle any remaining NaN cases
			if (Double.isNaN(idfValue) || Double.isInfinite(idfValue)) {
			    idfValue = 0.0;
			}
			
			idf.put(word, idfValue);
		}

		return idf;
	}
}