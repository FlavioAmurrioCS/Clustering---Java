/**
 * SWatch
 */
public class FTimer {
    long cTime;
    String method;

    public FTimer() {
        this.cTime = System.currentTimeMillis();
        this.method = "";
    }

    public FTimer(String method) {
        this.cTime = System.currentTimeMillis();
        this.method = method;
        System.out.print(this.method + " ...");
    }

    public String toString() {
        long diff = System.currentTimeMillis() - this.cTime;
        return timeToString(diff);
    }

    public void print() {
        System.out.println(this.method + " took: " + this.toString());
    }

    public static String timeToString(long time) {
        time /= 1000;
        if (time > 60) {
            return "Time: " + time / 60 + "m " + time % 60 + "s        ";
        } else {
            return "Time: " + time + "s                      ";
        }
    }

    public void time() {
        long markTime = System.currentTimeMillis();
        long ellapseTime = markTime - this.cTime;
        ellapseTime /= 1000;
        String timeStr = (ellapseTime < 60) ? ellapseTime + "s" : (ellapseTime / 60) + "m " + ellapseTime % 60 + "s";
        if (this.method.equals("")) {
            System.out.println("Time: " + timeStr);
        } else {
            System.out.println("\r" + this.method + " took: " + timeStr);
        }
    }
}