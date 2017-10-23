import java.util.*;

/**
 * Miner
 */
public class Miner {

    public static final String FEATURES_FILE = "./res/original/1507224963_474688_features.dat";
    public static final String INPUT_FILE = "./res/original/1507224963_4758732_input.dat";
    public static final String SCRATCH_FILE = "./res/gen/scratch.txt";
    public static final String OUTPUT_FILE = "./res/gen/output";
    public static final String FEAT_FILE = "./res/gen/featfile.txt";
    public static final String MAP_FILE = "./res/gen/mapfile.txt";
    public static final String DISTCHANGE_FILE = "./res/gen/DisChange.txt";

    public static void main(String[] args) {
        // reIndex();
        // reindex2();

        FTools.SHOW_LOG = false;
        TextData.TF_IDF = true;
        VectMap.setDistMethod(VectMap.EUCLEADIAN_SQUARE);
        Clusters.INITIAL_K = Clusters.K_RANDOM;
        Clusters.OTHERPOINT = Clusters.FAR_POINT_RANDOM;
        Clusters.K_METHOD = Clusters.K_MEANS;
        Clusters model = new Clusters(INPUT_FILE);

        

        while(true){
            printInfo();
            model.kMethod();                        
            int sumSq = model.mine(0.2);
            long time = System.currentTimeMillis();
            String filename = OUTPUT_FILE + "-" + VectMap.distMethod + "-" + Clusters.K_METHOD + "-" + sumSq + "-"+ time + ".txt";
            model.toFile(filename);
        }        
    }

    public static void printInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Normalize(Tf-Idf): " + TextData.TF_IDF);
        sb.append("\nDistance Method: " + VectMap.distMethod);
        sb.append("\nKMethod: " + Clusters.K_METHOD);
        sb.append("\nInitial K: " + Clusters.INITIAL_K);
        System.out.println(sb.toString());

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


// public static void reIndex() {
//     HashSet<Integer> sSet = new HashSet<>();
//     Scanner sc = FTools.fileOpener(INPUT_FILE);
//     while (sc.hasNextInt()) {
//         sSet.add(sc.nextInt());
//         sc.next();
//     }
//     sc.close();
//     ArrayList<Integer> sList = new ArrayList<>(sSet);
//     Collections.sort(sList);
//     FTools.listToFile(sList, FEAT_FILE);
// }
// public static void reindex2()
// {
//     HashMap<String, Integer> fMap = FTools.fileToHashMap(FEAT_FILE);
//     ArrayList<String> output = new ArrayList<>();
//     Scanner sc = FTools.fileOpener(INPUT_FILE);
//     while(sc.hasNextLine())
//     {
//         String line = sc.nextLine();
//         String[] sp = line.split(" ");
//         for(int i = 0; i < sp.length; i+=2)
//         {
//             String n = fMap.get(sp[i]).toString();
//             sp[i] = n;
//         }
//         output.add(arrToString(sp));
//     }
//     FTools.listToFile(output, MAP_FILE);
// }

// public static String arrToString(String[] arr){
//     StringBuilder sb = new StringBuilder();
//     for(String str : arr){
//         sb.append(str + " ");
//     }
//     return sb.toString().trim();
// }