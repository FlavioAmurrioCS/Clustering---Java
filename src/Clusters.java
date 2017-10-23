import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Clusters
 */
public class Clusters {
    public static int DEFAULT_K = 7;
    public static final String K_MEANS = "kMeans";
    public static final String K_PLUS_PLUS = "kPlusPlus";

    public static String K_METHOD = "kMeans";

    private int kCount;
    private ArrayList<TextData> centroids;
    private ArrayList<TextData> lineList;

    public Clusters(int k, String kMethod, ArrayList<LineData> lineList) {
        this.kCount = k;
        this.lineList = lineList;
        this.centroids = new ArrayList<>();
        switch (K_METHOD){
        case K_MEANS:
            kMean();
            break;
        case K_PLUS_PLUS:
            kMeanPlusPlus();
            break;
        default:
            kMeanPlusPlus();
        }
    }

    public Clusters(String filename) {
        this(DEFAULT_K, K_PLUS_PLUS, LineData.fileToList(filename));
    }

    private void kMean() {
        FTimer ft = new FTimer("KMean");
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < this.kCount; i++) {
            int index = rand.nextInt(this.lineList.size());
            this.centroids.add(this.lineList.get(index));
        }
        ft.time();
    }

    private void kMeanPlusPlus() {
        FTimer ft = new FTimer("KMean++");
        ArrayList<LineData> sList = new ArrayList<>(this.lineList);
        LineData ori = getNearOrigin(sList);
        this.centroids.add(ori);
        sList.remove(ori);
        while (this.centroids.size() != kCount) {
            LineData ld = getFarPoint2(this.centroids, sList);
            this.centroids.add(ld);
            sList.remove(ld);
        }
        sList.clear();
        ft.time();
    }

    private static LineData getFarPoint(ArrayList<LineData> kList, ArrayList<LineData> lineList) {
        LineData ret = null;
        double dist = 0;
        for (LineData ld : lineList) {
            double sumDist = 0;
            for (LineData cent : kList) {
                sumDist += cent.distance(ld);
            }
            if (sumDist > dist) {
                ret = ld;
                dist = sumDist;
            }
        }
        return ret;
    }// DO I remove the far point?

    private static LineData getFarPoint2(ArrayList<LineData> kList, ArrayList<LineData> lineList) {
        ArrayList<Pair<LineData>> pairDist = new ArrayList<>();
        for (LineData ld : lineList) {
            double sumDist = 0;
            for (LineData cent : kList) {
                sumDist += cent.distance(ld);
            }
            pairDist.add(new Pair(sumDist, ld));
        }

        Collections.sort(pairDist);
        return pairDist.get((new Random(System.currentTimeMillis())).nextInt(15)).obj;
    }// DO I remove the far point?

    private static LineData getNearOrigin(ArrayList<LineData> lineList) {
        // LineData ori = new LineData();
        // double dist = Double.MAX_VALUE;
        // LineData ret = null;
        // for (LineData ld : lineList) {
        //     double nDist = ori.euDistance(ld);
        //     if (nDist < dist) {
        //         ret = ld;
        //         dist = nDist;
        //     }
        // }
        // return lineList.get((new Random(System.currentTimeMillis())).nextInt(lineList.size()));
            return LineData.getCenter(lineList);
    }

    private ArrayList<ArrayList<LineData>> getCluster() {
        ArrayList<ArrayList<LineData>> retList = new ArrayList<>();
        for (int i = 0; i < this.kCount; i++) {
            retList.add(new ArrayList<>());
        }
        for (LineData ld : this.lineList) {
            (retList.get(ld.getLabel())).add(ld);
        }
        return retList;
    }

    private void reCenter() {
        ArrayList<ArrayList<LineData>> clusters = this.getCluster();
        this.centroids.clear();
        for (int i = 0; i < clusters.size(); i++) {
            ArrayList<LineData> clu = clusters.get(i);
            LineData center = LineData.getCenter(clu);
            this.centroids.add(center);
        }
    }

    public void classify() {
        ProgressBar pb = new ProgressBar(this.lineList.size(), 1);
        for (int i = 0; i < this.lineList.size(); i++) {
            LineData ld = lineList.get(i);
            double dist = 999999999;
            for (int j = 0; j < this.centroids.size(); j++) {
                LineData center = this.centroids.get(j);
                double tempDist = ld.distance(center);
                if (tempDist < dist) {
                    ld.setLabel(j);
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
            ArrayList<LineData> ori = new ArrayList<>(this.centroids);
            this.reCenter();
            change = getDistChange(ori, this.centroids);
        }
        this.classify();
        ft.time();
    }

    public void toFile(String filename) {
        FTools.listToFile(this.lineList, filename);
    }

    public static double getDistChange(ArrayList<LineData> original, ArrayList<LineData> change) {
        double sum = 0;
        for (int i = 0; i < original.size(); i++) {
            sum += original.get(i).distance(change.get(i));
        }
        System.out.println("Sum Distance Change: " + sum);
        return sum;
    }

}

class Pair<J> implements Comparable<Pair<J>>{

    Double dist;
    J obj;

    public Pair(Double dist, J obj){
        this.dist = dist;
        this.obj = obj;
    }

    public int compareTo(Pair pair)
    {
        return -(this.dist.compareTo(pair.dist));
    }




}