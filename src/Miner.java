import java.util.*;

/**
 * Miner
 */
public class Miner {

    public static final String FEATURES_FILE = "./res/original/1507224963_474688_features.dat";
    public static final String INPUT_FILE = "./res/original/1507224963_4758732_input.dat";
    public static final String SCRATCH_FILE = "./res/gen/scratch.txt";
    public static final String OUTPUT_FILE = "./res/gen/output.txt";

    public static void main(String[] args) {

        System.out.println(TfIdf.getTfIdf(INPUT_FILE, OUTPUT_FILE));



        // ArrayList<LineData> lineList = LineData.fileToList(INPUT_FILE);
        // Clusters cls = new Clusters(7, lineList);
        // cls.kMean();
        // ProgressBar pb = new ProgressBar(10, 1);
        // FTools.tittleMaker("INIT");
        // for(int i = 0; i < 5; i++)
        // {
        //     FTools.tittleMaker("Inside the loop");
        //     cls.classify();
        //     cls.reCenter();
        //     // pb.update(i);
        //     System.out.println("Number: " + i);
        // }
        // FTools.tittleMaker("Done");
        // cls.classify();
        // cls.toFile(OUTPUT_FILE);
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

class WordCount implements Comparable<WordCount>{

    String str;
    int count;

    public WordCount(String feature){
        this.str = feature;
        this.count = 0;
    }

    public int compareTo(WordCount wd)
    {
        return -(this.count - wd.count);
    }

    public String toString()
    {
        return this.str + " " + this.count;
    }

    public void increment(int i)
    {
        this.count+=i;        
    }
}