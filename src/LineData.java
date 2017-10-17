import java.util.*;

/**
 * LineData
 */
public class LineData {

    public static final String EUCLEADIAN = "eu";
    public static final String COSINESIM = "cos";
    public static String DIST_METHOD = "eu";
    public static boolean TF_IDF = true;

    private HashMap<Integer, Double> lineMap;
    private int label = -1;
    private double distance;
    private String lineStr;
    private double sumSquare = 0;
    private int wordCount = 0;

    // If normalizing, consider using BigDecimal for the count
    public LineData(String str) {
        this.lineStr = str;
        Scanner sc = new Scanner(str);
        lineMap = new HashMap<>();
        while (sc.hasNext()) {
            Integer id = sc.nextInt();
            int value = sc.nextInt();
            this.wordCount += value;
            Double count = (double) value;
            lineMap.put(id, count);
            this.sumSquare += count * count; //Not need unless implementing cosine similarity()
        }
        this.sumSquare = Math.sqrt(this.sumSquare);
        sc.close();
    }

    public Set<Map.Entry<Integer,Double>> entrySet() {
        return this.lineMap.entrySet();
    }

    public Set<Integer> keySet() {
        return this.lineMap.keySet();
    }

    public Collection<Double> valueSet() {
        return this.lineMap.values();
    }

    public HashMap<Integer, Double> getHashMap() {
        return this.lineMap;
    }

    public LineData() {
        this.lineMap = new HashMap<>();
    }

    public void setLabel(int k) {
        this.label = k;
    }

    public int getLabel() {
        return this.label;
    }

    public void clearLabel() {
        this.label = -1;
    }

    public boolean containsKey(Integer key) {
        return this.lineMap.containsKey(key);
    }

    public double get(Integer key) {
        return this.containsKey(key) ? this.lineMap.get(key) : 0.0;
    }

    public double getSumSquare() {
        return this.sumSquare;
    }

    private double cosDistance(LineData ld) {
        double sum = 0;
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            Integer key = entry.getKey();
            Double value = entry.getValue();
            sum += value * ld.get(key);
        }
        return (sum / (this.getSumSquare() * ld.getSumSquare()));
    }

    private double euDistance(LineData ld) {
        HashMap<Integer, Double> tempMap = new HashMap<>(ld.getHashMap());
        tempMap.keySet().removeAll(this.keySet());
        double sum = 0;
        for (Double it : tempMap.values()) {
            sum += it * it;
        } // This takes care of the values in ld that are not in this
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            Integer key = entry.getKey();
            Double value = entry.getValue();
            double temp = value - ld.get(key);
            sum += temp * temp;
        }
        return sum;
    }

    public double distance(LineData ld) {
        switch (LineData.DIST_METHOD) {
        case EUCLEADIAN:
            return this.euDistance(ld);
        case COSINESIM:
            return this.cosDistance(ld);
        default:
            return this.euDistance(ld);
        }
    }

    private void addFeature(Integer key, Double value) {
        if (!this.containsKey(key)) {
            this.lineMap.put(key, value);
        } else {
            Double val = this.get(key);
            this.lineMap.put(key, value + val);
        }
    }

    public static LineData getCenter(ArrayList<LineData> lineList) {
        double size = (double) lineList.size();
        LineData lineData = new LineData();

        for (LineData ld : lineList) {
            for (Map.Entry<Integer, Double> entry : ld.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
                lineData.addFeature(key, value);
            }
            ld.clearLabel();
        }
        for (Map.Entry<Integer, Double> entry : lineData.entrySet()) {
            entry.setValue(entry.getValue() / size);
        }
        return lineData;
    }

    public static ArrayList<LineData> fileToList(String filename) {
        FTimer ft = new FTimer("Reading Input File");
        Scanner sc = FTools.fileOpener(filename);
        ArrayList<LineData> retList = new ArrayList<>();
        while (sc.hasNext()) {
            retList.add(new LineData(sc.nextLine()));
        }
        sc.close();
        ft.time();
        if (TF_IDF)
            performTfIdf(retList);
        return retList;
    }

    public String toString() {
        return "" + this.label;
    }

    private void normalize(HashMap<Integer, Double> iMap) {
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            Integer key = entry.getKey();
            Double value = entry.getValue();
            Double idf = iMap.get(key);
            Double tf = value / (double) this.wordCount;
            entry.setValue(tf * idf);
        }
    }

    private static HashMap<Integer, Double> getIdfMap(ArrayList<LineData> lineList) {
        Double size = (double) lineList.size();
        HashSet<Integer> keySet = new HashSet<>();
        for (LineData ld : lineList) {
            keySet.addAll(ld.keySet());
        }
        HashMap<Integer, Double> iMap = new HashMap<>();
        for (Integer it : keySet) {
            int count = 0;
            for (LineData ld : lineList) {
                if (ld.containsKey(it)) {
                    count++;
                }
            }
            double idf = size / ((double) count + 1);
            idf = Math.log(idf) + 1;
            iMap.put(it, idf);
        }
        return iMap;
    }

    private static void performTfIdf(ArrayList<LineData> lineList) {
        FTimer ft = new FTimer("Normalizing Data");
        HashMap<Integer, Double> iMap = getIdfMap(lineList);
        for (LineData ld : lineList) {
            ld.normalize(iMap);
        }
        ft.time();
    }

}