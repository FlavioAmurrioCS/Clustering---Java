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

        // System.out.println(TfIdf.getTfIdf(INPUT_FILE, OUTPUT_FILE));
        FTimer ft = new FTimer();



        FTools.appendFile(DISTCHANGE_FILE, (new Date()).toString());
        ArrayList<LineData> lineList = LineData.fileToList(INPUT_FILE);
        Clusters cls = new Clusters(7, lineList);
        cls.kMean();
        // cls.kMeanPlusPlus();
        // ProgressBar pb = new ProgressBar(10, 1);
        // FTools.tittleMaker("INIT");
        // FTools.appendFile(DISTCHANGE_FILE, cls.centroids.toString());
        for(int i = 0; i < 10; i)
        {
            // FTools.tittleMaker("Inside the loop");
            cls.classify();
            ArrayList<LineData> ori = new ArrayList<>(cls.centroids);
            cls.reCenter();
            FTools.appendFile(DISTCHANGE_FILE, getDistChange(ori, cls.centroids));
            // pb.update(i);
            System.out.println("Number: " + i);
        }
        FTools.tittleMaker("Done");
        cls.classify();
        cls.toFile(OUTPUT_FILE);
        ft.print();
    }

    public static String getDistChange(ArrayList<LineData> original, ArrayList<LineData> change)
    {
        double sum = 0;
        for(int i = 0; i< original.size(); i++)
        {
            sum += original.get(i).euDistance(change.get(i));
        }
        System.out.println("Sum Distance Chnage: " + sum);
        return "Sum Distance Chnage: " + sum;
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