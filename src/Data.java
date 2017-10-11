import java.util.*;

/**
 * Data<T>
 */
public abstract class  Data {

    ArrayList<String> dList;
    String line;
    HashSet<String> dSet;

    public Data(Scanner sc) {
        StringBuilder sb = new StringBuilder();
        this.dList = new ArrayList<>();
        if (sc.hasNext()) {
            String temp = sc.next();
            if (!temp.equals("1") && !temp.equals("0")) {
                dList.add(temp);
                sb.append(temp + " ");
            }
        }
        while (sc.hasNext()) {
            String str = sc.next();
            sb.append(str + " ");
            dList.add(str);
        }
        sc.close();
        this.line = sb.toString().trim();
        this.dSet = new HashSet<>();
        dSet.addAll(this.dList);
    }


    public Data(String str) {
        this(new Scanner(str));
    }

    public String toString() {
        return line;
    }

    public void sort() {
        Collections.sort(dList);
    }

}