import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.math.MathContext;

/**
 * TfIdf
 * tf * idf
 * tf = (# of selected word / # of total words)
 * idf = ln(# of total lines/ # lines containing the word)
 * http://www.tfidf.com/
 * 
 * TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document).
 * IDF(t) = log_e(Total number of documents / Number of documents with term t in it).
 * 
 * 
 */
public class TfIdf {

    public static HashMap<Integer, BigDecimal> getTfIdf(String filename, String destination) {
        HashMap<Integer, BigDecimal> rMap = new HashMap<>();
        Scanner sc = FTools.fileOpener(filename);
        int wordCount = 0;

        while (sc.hasNext()) {
            Integer key = sc.nextInt();
            BigDecimal value = sc.nextBigDecimal();
            wordCount += value.intValue();
            if (rMap.containsKey(key)) {
                BigDecimal bd = rMap.get(key);
                rMap.put(key, value.add(bd));
            } else {
                rMap.put(key, value);
            }
        }
        for (Map.Entry<Integer, BigDecimal> entry : rMap.entrySet()) {
            BigDecimal value = entry.getValue();
            value = value.divide(new BigDecimal(wordCount), MathContext.DECIMAL128);
            entry.setValue(value);
        }

        return rMap;

    }
}