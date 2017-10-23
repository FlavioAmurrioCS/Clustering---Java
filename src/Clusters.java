import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.HashSet;

/**
 * Clusters
 */
public class Clusters {
    public static int DEFAULT_K = 7;
    public static final String K_MEANS = "kMeans";
    public static final String K_PLUS_PLUS = "kPlusPlus";

    public static final String K_CENTER = "kCenter";
    public static final String K_RANDOM = "kRandom";
    public static final String K_ORIGIN = "kOrigin";
    public static final String K_SPECIAL = "kSpecial";

    public static String K_METHOD = K_PLUS_PLUS;
    public static String INITIAL_K = K_SPECIAL;

    private int kCount;
    private ArrayList<TextData> centroids;
    private ArrayList<TextData> dList;

    public Clusters(int k, String kMethod, ArrayList<TextData> dList) {
        this.kCount = k;
        this.dList = dList;
        this.centroids = new ArrayList<>();
        K_METHOD = kMethod;
        switch (K_METHOD) {
        case K_MEANS:
            kMeans();
            break;
        case K_PLUS_PLUS:
            kMeanPlusPlus();
            break;
        default:
            kMeanPlusPlus();
        }
    }

    public Clusters(String filename) {
        this(DEFAULT_K, K_PLUS_PLUS, TextData.fileToList(filename));
    }

    private void kMeans() {
        FTimer ft = new FTimer("KMeans");
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < this.kCount; i++) {
            int index = rand.nextInt(this.dList.size());
            this.centroids.add(this.dList.get(index));
        }
        ft.time();
    }

    private void kMeanPlusPlus() {
        FTimer ft = new FTimer("KMeans++");
        ArrayList<TextData> sList = new ArrayList<>(this.dList);
        TextData first = getFirstPoint(sList);
        this.centroids.add(first);
        sList.remove(first);
        while (this.centroids.size() < kCount) {
            TextData td = getFarPoint(sList);
            this.centroids.add(td);
            sList.remove(td);
        }
        sList.clear();
        ft.time();
    }

    private TextData getFirstPoint(ArrayList<TextData> sList) {
        switch (INITIAL_K) {
        case K_CENTER:
            return getNewCenter(sList);
        case K_ORIGIN:
            return getOriginK(sList);
        case K_RANDOM:
            return getRandomK(sList);
        case K_SPECIAL:
            return getKeyK(sList, 264);
        default:
            return getRandomK(sList);
        }
    }

    private TextData getRandomK(ArrayList<TextData> sList) {
        int index = (new Random(System.currentTimeMillis())).nextInt(sList.size());
        return sList.get(index);
    }

    private TextData getCenter(ArrayList<TextData> sList) {
        TextData nCenter = new TextData();
        double size = sList.size();
        for(TextData td : sList){
            nCenter = new TextData(nCenter.plus(td));
        }
        nCenter = new TextData(nCenter.divide(size));
        return nCenter;
    }

    private TextData getNewCenter(ArrayList<TextData> sList){
        TextData ret = new TextData();
        HashSet<Integer> kSet = new HashSet<>();
        double size = sList.size();
        for (TextData vect : sList) {
            kSet.addAll(vect.keySet());
        }
        for (Integer key : kSet) {
            double sum = 0;
            for (TextData vect : sList) {
                sum += vect.getValue(key);
            }
            sum /= size;
            ret.put(key, sum);
        }
        return ret;
    }

    private TextData getOriginK(ArrayList<TextData> sList) {
        TextData origin = new TextData();
        double dist = Double.MAX_VALUE;
        TextData ret = null;
        for (TextData td : sList) {
            double diff = origin.distance(td);
            if (diff < dist) {
                dist = diff;
                ret = td;
            }
        }
        return ret;
    }
    //264  - football
    private TextData getKeyK(ArrayList<TextData> sList, Integer i){
        ArrayList<TextData> kList = new ArrayList<>();
        for(TextData td : sList){
            if(td.containsKey(i))
            {
                kList.add(td);
            }
        }
        System.out.println(i + " appeared on " + kList.size() + " documents.");
        return getCenter(kList);
    }

    private TextData getFarPoint(ArrayList<TextData> sList) {
        TextData ret = null;
        double dist = 0;
        for (TextData it : sList) {
            double sumDist = 0;
            for (TextData cent : this.centroids) {
                sumDist += it.distance(cent);
            }
            if (sumDist > dist) {
                dist = sumDist;
                ret = it;
            }
        }
        return ret;
    }

    private ArrayList<ArrayList<TextData>> getCluster() {
        ArrayList<ArrayList<TextData>> retList = new ArrayList<>();
        for (int i = 0; i < this.kCount; i++) {
            retList.add(new ArrayList<>());
        }
        for (TextData td : this.dList) {
            int lNum = td.getLabel();
            ArrayList<TextData> choosen = retList.get(lNum);
            choosen.add(td);
        }
        return retList;
    }

    private void reCenter() {
        FTools.tittleMaker("Recentering");
        ArrayList<ArrayList<TextData>> clusters = this.getCluster();
        this.centroids.clear();
        for (int i = 0; i < clusters.size(); i++) {
            ArrayList<TextData> clu = clusters.get(i);
            TextData center = getNewCenter(clu);
            this.centroids.add(center);
        }
    }

    public void classify() {
        ProgressBar pb = new ProgressBar(this.dList.size(), 1);
        for (int i = 0; i < this.dList.size(); i++) {
            TextData td = dList.get(i);
            double dist = 999999999;
            for (int j = 0; j < this.centroids.size(); j++) {
                TextData center = this.centroids.get(j);
                double tempDist = td.distance(center);
                if (tempDist < dist) {
                    td.setLabel(j);
                    dist = tempDist;
                }
            }
            pb.update(i);
        }
    }

    public void mine(double delta) {
        FTimer ft = new FTimer("Mining");
        double change = Double.MAX_VALUE;
        while (change > delta) {
            this.classify();
            ArrayList<TextData> ori = new ArrayList<>(this.centroids);
            this.reCenter();
            change = getDistChange(ori, this.centroids);
        }
        this.classify();
        ft.time();
    }

    public void toFile(String filename) {
        FTools.listToFile(this.dList, filename);
    }

    public static double getDistChange(ArrayList<TextData> original, ArrayList<TextData> change) {
        double sum = 0;
        for (int i = 0; i < original.size(); i++) {
            TextData ori = original.get(i);
            TextData ch = change.get(i);
            sum += ori.distance(ch);
        }
        System.out.println("Sum Distance Change: " + sum);
        return sum;
    }

}

class Pair<J> implements Comparable<Pair<J>> {

    Double dist;
    J obj;

    public Pair(Double dist, J obj) {
        this.dist = dist;
        this.obj = obj;
    }

    public int compareTo(Pair pair) {
        return -(this.dist.compareTo(pair.dist));
    }

}

// private static LineData getFarPoint(ArrayList<LineData> kList, ArrayList<LineData> lineList) {
//     LineData ret = null;
//     double dist = 0;
//     for (LineData ld : lineList) {
//         double sumDist = 0;
//         for (LineData cent : kList) {
//             sumDist += cent.distance(ld);
//         }
//         if (sumDist > dist) {
//             ret = ld;
//             dist = sumDist;
//         }
//     }
//     return ret;
// }// DO I remove the far point?

// private static LineData getFarPoint2(ArrayList<LineData> kList, ArrayList<LineData> lineList) {
//     ArrayList<Pair<LineData>> pairDist = new ArrayList<>();
//     for (LineData ld : lineList) {
//         double sumDist = 0;
//         for (LineData cent : kList) {
//             sumDist += cent.distance(ld);
//         }
//         pairDist.add(new Pair(sumDist, ld));
//     }

//     Collections.sort(pairDist);
//     return pairDist.get((new Random(System.currentTimeMillis())).nextInt(15)).obj;
// }// DO I remove the far point?