/**
 * TestData
 */
public class TestData extends Data{

    String prediction;

    public TestData(String str) {
        super(str);
    }

    public void setPrediction(String label) {
        this.prediction = label;
    }

    public String toString() {
        return this.prediction != null? this.prediction : "No Prediction";
    }
}