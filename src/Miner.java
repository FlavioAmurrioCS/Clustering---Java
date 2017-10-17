import java.util.*;

/**
 * Miner
 */
public class Miner {

    public static final String FEATURES_FILE = "./res/original/1507224963_474688_features.dat";
    public static final String INPUT_FILE = "./res/original/1507224963_4758732_input.dat";
    public static final String SCRATCH_FILE = "./res/gen/scratch.txt";
    public static final String OUTPUT_FILE = "./res/gen/output.txt";
    public static final String DISTCHANGE_FILE = "./res/gen/DisChange.txt";

    public static void main(String[] args) {
        FTimer ft = new FTimer("Whole Process");
        LineData.TF_IDF = true;
        Clusters cls = new Clusters(INPUT_FILE);
        cls.mine(0.0);
        cls.toFile(OUTPUT_FILE);
        ft.print();
    }
}

class WordCount implements Comparable<WordCount> {

    String str;
    int count;

    public WordCount(String feature) {
        this.str = feature;
        this.count = 0;
    }

    public int compareTo(WordCount wd) {
        return -(this.count - wd.count);
    }

    public String toString() {
        return this.str + " " + this.count;
    }

    public void increment(int i) {
        this.count += i;
    }

    public static ArrayList<WordCount> FileToStringList(String filename) {
        Scanner sc = FTools.fileOpener(filename);
        ArrayList<WordCount> retList = new ArrayList<>();
        while (sc.hasNext())
            retList.add(new WordCount(sc.next()));
        sc.close();
        return retList;
    }
}