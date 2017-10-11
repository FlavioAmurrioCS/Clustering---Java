/**
 * SWatch
 */
public class FTimer {
    long cTime;

    public FTimer() {
        this.cTime = System.currentTimeMillis();
    }

    public String toString() {
        long diff = System.currentTimeMillis() - this.cTime;
        return timeToString(diff);
    }

    public void print() {
        System.out.println(this.toString());
    }

    public static String timeToString(long time) {
        time /= 1000;
        if (time > 60) {
            return "Time: " + time / 60 + "m " + time % 60 + "s        ";
        } else {
            return "Time: " + time + "s                      ";
        }

    }
}