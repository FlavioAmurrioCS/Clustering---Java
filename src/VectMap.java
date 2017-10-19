import java.util.*;

/**
 * VectMap
 */
public class VectMap<K extends Comparable<K>> extends HashMap<K, Double> {

    public VectMap() {
        super();
    }

    public VectMap(K[] key, double[] arr) {
        super();
        for (int i = 0; i < arr.length; i++) {
            this.put(key[i], arr[i]);
        }
    }

    public Double getValue(K key) {
        return this.containsKey(key) ? this.get(key) : 0.0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (K key : this.keySet()) {
            sb.append("(K: " + key.toString() + ", V: " + this.get(key).toString() + "), ");
        }
        sb.append("\b");
        return sb.toString();
    }

    public String toOrderString() {
        ArrayList<K> keyList = this.getSortedKeys();
        StringBuilder sb = new StringBuilder();
        for (K key : keyList) {
            sb.append(key.toString() + ": " + this.getValue(key).toString() + ", ");
        }
        sb.append("\b\b");
        return sb.toString();
    }

    public ArrayList<K> getSortedKeys() {
        ArrayList<K> retList = new ArrayList<>(this.keySet());
        Collections.sort(retList);
        return retList;
    }

    public VectMap<K> plus(VectMap<K> vect) {
        VectMap<K> ret = new VectMap<>();
        HashSet<K> kSet = this.getKeyUnion(vect);
        for (K key : kSet) {
            double a = this.getValue(key);
            double b = vect.getValue(key);
            double ab = a + b;
            if (ab != 0)
                ret.put(key, ab);
        }
        return ret;
    }

    public VectMap<K> minus(VectMap<K> vect) {
        VectMap<K> ret = new VectMap<>();
        HashSet<K> kSet = this.getKeyUnion(vect);
        for (K key : kSet) {
            double a = this.getValue(key);
            double b = vect.getValue(key);
            double ab = a - b;
            if (ab != 0)
                ret.put(key, ab);
        }
        return ret;
    }

    public VectMap<K> multiply(double mul) {
        VectMap<K> ret = new VectMap<>();
        for (K key : this.keySet()) {
            double val = this.getValue(key);
            val *= mul;
            if (val != 0)
                ret.put(key, val);
        }
        return ret;
    }

    public VectMap<K> multiply(VectMap<K> vect) {
        VectMap<K> ret = new VectMap<>();
        HashSet<K> kSet = this.getKeyIntersection(vect);
        for (K key : kSet) {
            double a = this.getValue(key);
            double b = vect.getValue(key);
            double axb = a * b;
            if (axb != 0)
                ret.put(key, axb);
        }
        return ret;
    }

    public VectMap<K> divide(double div) {
        if (div == 0) {
            System.out.println("Can't Divide By Zero!!!");
            System.exit(1);
        }
        return this.multiply(1 / div);
    }

    public double dotProduct(VectMap<K> vect) {
        double sum = 0;
        HashSet<K> kSet = this.getKeyIntersection(vect);
        for (K key : kSet) {
            double a = this.getValue(key);
            double b = vect.getValue(key);
            sum += (a * b);
        }
        return sum;
    }

    public VectMap<K> pow(double p) {
        VectMap<K> ret = new VectMap<>();
        for (K key : this.keySet()) {
            double value = this.getValue(key);
            value = Math.pow(value, p);
            ret.put(key, value);
        }
        return ret;
    }

    public void replace(K key, double value)
    {

    }

    public void setAsKeyCenter(List<VectMap<K>> vList, K key)
    {

    }

    public VectMap<K> square() {
        VectMap<K> ret = new VectMap<>();
        for (K key : this.keySet()) {
            double value = this.getValue(key);
            value = value * value;
            if (value != 0)
                ret.put(key, value);
        }
        return ret;
    }

    public VectMap<K> sqrt() {
        return this.pow(0.5);
    }

    public double sum() {
        double sum = 0;
        for (double value : this.values()) {
            sum += value;
        }
        return sum;
    }

    public double avg() {
        this.trim();
        return this.sum() / (double) this.size();
    }

    public void trim() {
        HashSet<K> keyRem = new HashSet<>();
        for (K key : this.keySet()) {
            if (this.getValue(key) == 0) {
                keyRem.add(key);
            }
        }
        this.keySet().remove(keyRem);
    }

    public double eucleadianDistance(VectMap<K> vect) {
        HashSet<K> kSet = this.getKeyUnion(vect);
        double sum = 0;
        for (K key : kSet) {
            double a = this.getValue(key);
            double b = vect.getValue(key);
            double diff = a - b;
            sum += (diff * diff);
        }
        return Math.sqrt(sum);
    }

    public double manhattanDistance(VectMap<K> vect) {
        HashSet<K> kSet = this.getKeyUnion(vect);
        double sum = 0;
        for (K key : kSet) {
            double a = this.getValue(key);
            double b = vect.getValue(key);
            double diff = (a > b) ? (a - b) : (b - a);
            sum += diff;
        }
        return sum;
    }

    public double cosineSimilarity(VectMap<K> vect) {
        double top = (this.multiply(vect)).sum();
        double tSq = (this.square()).sum();
        tSq = Math.sqrt(tSq);
        double vSq = (vect.square()).sum();
        vSq = Math.sqrt(vSq);
        return (top / (tSq * vSq));
    }

    public double jaccardIndex(VectMap<K> vect) {
        double union = (double) (this.getKeyUnion(vect).size());
        double intersection = (double) (this.getKeyIntersection(vect).size());
        return intersection / union;
    }

    public HashSet<K> getKeyUnion(VectMap<K> vect) {
        HashSet<K> kSet = new HashSet<>();
        kSet.addAll(this.keySet());
        kSet.addAll(vect.keySet());
        return kSet;
    }

    public HashSet<K> getKeyIntersection(VectMap<K> vect) {
        HashSet<K> kSet = new HashSet<>();
        kSet.addAll(this.keySet());
        kSet.retainAll(vect.keySet());
        return kSet;
    }

    public void setAsCenter(List<VectMap<K>> vList) {
        this.clear();
        HashSet<K> kSet = new HashSet<>();
        double size = vList.size();
        for (VectMap<K> vect : vList) {
            kSet.addAll(vect.keySet());
        }
        for (K key : kSet) {
            double sum = 0;
            for (VectMap<K> vect : vList) {
                sum += vect.getValue(key);
            }
            sum /= size;
            this.put(key, sum);
        }
    }

    public void setAsIdf(List<VectMap<K>> vList) {
        this.clear();
        HashSet<K> kSet = new HashSet<>();
        double totalLines = vList.size();
        for (VectMap<K> vect : vList) {
            kSet.addAll(vect.keySet());
        }
        for (K key : kSet) {
            int sum = 0;
            for (VectMap<K> vect : vList) {
                if (vect.containsKey(key))
                    sum++;
            }
            double idf = totalLines / (double) sum;
            idf = Math.log(idf);
            this.put(key, idf);
        }
    }

    private void tf() {
        double size = this.sum();
        VectMap<K> vect = this.divide(size);
        this.clear();
        this.putAll(vect);
    }

    public void tfIdf(VectMap<K> idfMap) {
        this.tf();
        VectMap<K> vect = this.multiply(idfMap);
        this.clear();
        this.putAll(vect);
    }
}