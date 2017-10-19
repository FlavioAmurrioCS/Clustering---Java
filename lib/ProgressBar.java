/**
 * ProgBar
 */
public class ProgressBar {
    long time;
    int size;
    int unit;
    int interval;
    int lastPrint = 0;

    public ProgressBar(int size, int interval) {
        this.size = size;
        this.interval = interval;
        this.unit = this.size / 100;
        this.time = System.currentTimeMillis();
    }

    public void update(int i) {
        int t = i / this.unit;
        int j = t / this.interval;
        if (j != this.lastPrint) {
            long elapse = System.currentTimeMillis() - this.time;
            double total = (double) 100 * elapse / j;
            total -= elapse;
            int rem = (int) total;
            lastPrint = j;
            System.out.print("\r" + barMaker(t) + " " + FTimer.timeToString(rem));
        }
        if (i == this.size - 1) {
            System.out.println();
        }
    }

    public String barMaker(int perc) {
        char bar[] = "..................................................".toCharArray();
        for (int i = 0; i < perc / 2; i++) {
            if (i < bar.length) {
                bar[i] = '#';
            }
        }
        perc = perc > 100 ? 100 : perc;
        return "Progress: [" + perc + "%][" + new String(bar) + "]";
    }
}