import java.util.ArrayList;
import java.util.Random;

/**
 * Clusters
 */
public class Clusters {
    int kCount;
    ArrayList<LineData> centroids;
    ArrayList<LineData> lineList;

    public Clusters(int k, ArrayList<LineData> lineList) {
        this.kCount = k;
        this.lineList = lineList;
        this.centroids = new ArrayList<>();
    }

    public void kMean() {
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < this.kCount; i++) {
            int index = rand.nextInt(this.lineList.size());
            this.centroids.add(this.lineList.get(index));
        }
    }

    public void kMeanPlusPlus() {

    }

    public ArrayList<ArrayList<LineData>> getCluster() {
        ArrayList<ArrayList<LineData>> retList = new ArrayList<>();
        for (int i = 0; i < this.kCount; i++) {
            retList.add(new ArrayList<>());
        }
        for (LineData ld : this.lineList) {
            retList.get(ld.label).add(ld);
        }
        return retList;
    }

    public void reCenter() {
        ArrayList<ArrayList<LineData>> clusters = this.getCluster();
        for (int i = 0; i < kCount; i++) {
            LineData center = new LineData();
            center = LineData.getCenter(clusters.get(i));
            this.centroids.set(i, center);
        }
    }

    public void classify() {
        for (LineData ld : this.lineList) {
            double dist = 999999999;
            for (int i = 0; i < this.centroids.size(); i++) {
                double tempDist = ld.euDistance(this.centroids.get(i));
                ld.label = tempDist < dist ? i : ld.label;
                dist = tempDist < dist ? tempDist : dist;
            }
        }
    }

    public void toFile(String filename) {
        FTools.tittleMaker("writing file");
        FTools.listToFile(this.lineList, filename);
    }

}