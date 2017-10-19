/**
 * Fraction
 */
public class Fraction implements Comparable<Fraction> {
    public long top;
    public long bottom;

    public static void main(String[] args) {
        System.out.println("Hellow World");
        Fraction a = new Fraction(1, 7);
        System.out.println(a.toString());
        a.pow(20);
        System.out.println(a.toString() + "\tDecimal: " + a.toDecimal());
    }

    public Fraction(long top, long bottom) {
        this.top = top;
        this.bottom = bottom;
        simplify();
    }

    public Fraction(long num) {
        this.top = num;
        this.bottom = 1;
    }

    public void plus(Fraction fr) {
        if (this.bottom != fr.bottom) {
            long t = this.bottom;
            this.multTopBottom(fr.bottom);
            fr.multTopBottom(t);
        }
        this.top += fr.top;
        simplify();
    }

    public void plus(long p) {
        Fraction fr = new Fraction(p);
        this.plus(fr);
    }

    public void plus(long t, long b) {
        Fraction fr = new Fraction(t, b);
        this.plus(fr);
    }

    public void minus(Fraction fr) {
        if (this.bottom != fr.bottom) {
            long t = this.bottom;
            this.multTopBottom(fr.bottom);
            fr.multTopBottom(t);
        }
        this.top -= fr.top;
        simplify();
    }

    public void minus(long p) {
        Fraction fr = new Fraction(p);
        this.minus(fr);
    }

    public void minus(long t, long b) {
        Fraction fr = new Fraction(t, b);
        this.minus(fr);
    }

    private void simplify() {
        long mult = gcd(this.top, this.bottom);
        this.divTopBottom(mult);
    }

    public static long gcd(long a, long b) {
        if (a == 0 || b == 0)
            return a + b; // base case
        return gcd(b, a % b);
    }

    public void multiply(long mult) {
        Fraction fr = new Fraction(mult);
        this.multiply(fr);
    }

    public void multiply(long t, long b) {
        Fraction fr = new Fraction(t, b);
        this.multiply(fr);
    }

    public void multiply(Fraction fr) {
        this.top *= fr.top;
        this.bottom *= fr.bottom;
        simplify();
    }

    public void divide(Fraction fr) {
        this.top *= fr.bottom;
        this.bottom *= fr.top;
        simplify();
    }

    public void divide(long div) {
        Fraction fr = new Fraction(div);
        this.divide(fr);
    }

    public void divide(long t, long b) {
        Fraction fr = new Fraction(t, b);
        this.divide(fr);
    }

    private void multTopBottom(long mult) {
        this.bottom *= mult;
        this.top *= mult;
    }

    private void divTopBottom(long mult) {
        this.bottom /= mult;
        this.top /= mult;
    }

    public static long toPow(long num, long pow) {
        if (pow == 0) {
            return 1;
        }
        long ret = num;
        for (int i = 1; i < pow; i++) {
            ret *= num;
            if (ret < num) {
                System.out.println("OverFlow");
                System.exit(1);
            }
        }
        return ret;
    }

    public void pow(long exp) {
        this.top = toPow(this.top, exp);
        this.bottom = toPow(this.bottom, exp);
    }

    public String toString() {
        return this.bottom == 1 ? "" + this.top : this.top + "/" + this.bottom;
    }

    public double toDecimal() {
        simplify();
        return ((double) this.top) / ((double) this.bottom);
    }

    public int compareTo(Fraction fr) {
        if (this.bottom != fr.bottom) {
            long t = this.bottom;
            this.multTopBottom(fr.bottom);
            fr.multTopBottom(t);
        }
        return (int)(fr.top - this.top);
    }

    public boolean equals(Fraction fr) {
        return this.top == fr.top && this.bottom == fr.bottom;
    }

}