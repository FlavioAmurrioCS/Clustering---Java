
/**
 * TrainData
 */

import java.util.*;

public class TrainData extends Data {

    String label;

    public TrainData(String str) {
        super(new Scanner(str));
        Scanner sc = new Scanner(str);
        this.label = sc.next();
        sc.close();
    }

    public String toString() {
        return label + " " + super.toString();
    }

}