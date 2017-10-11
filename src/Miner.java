import java.util.*;

/**
 * Miner
 */
public class Miner {

    public static final String FEATURES_FILE = "./res/original/1507224963_474688_features.dat";
    public static final String INPUT_FILE = "./res/original/1507224963_4758732_input.dat";
    public static final String SCRATCH_FILE = "./res/gen/scratch.txt";

    public static void main(String[] args) {

        // ArrayList<WordCount> sList = FileToStringList(FEATURES_FILE);

        // Scanner sc = FTools.fileOpener(INPUT_FILE);
        // while(sc.hasNextInt())
        // {
        //     int i = sc.nextInt();
        //     int count = sc.nextInt();
        //     sList.get(i).increment(count);
        // }

        // Collections.sort(sList);

        // FTools.listToFile(sList, SCRATCH_FILE);


        ArrayList<String> li = (ArrayList<String>)FTools.fileToList(FEATURES_FILE, String.class);


        // ArrayList<TrainData> tData = (ArrayList<TrainData>)(FTools.fileToList(ORIGINAL_FILE, TrainData.class)); 

        // System.out.println("Item: " + tData.get(0).toString());
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