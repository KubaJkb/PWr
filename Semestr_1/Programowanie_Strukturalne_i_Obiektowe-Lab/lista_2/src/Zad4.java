//Jakub Kacprzyk


public class Zad4 {
    public static void main(String[] args) {
        System.out.println(E_x_JK(5, 100));
        System.out.println(Sin_x_JK(20));
        System.out.println(Cos_x_JK(20));
    }

    static double potega(double x, long k) {
        double wynik = 1;
        while (k > 0) {
            if (k % 2 == 1) {
                if ((Math.abs(wynik * x) > Double.MAX_VALUE) || (Math.abs(wynik * x) < Double.MIN_VALUE)){
                    return 0;
                }
                wynik *= x;
            }
            x *= x;
            k /= 2;
        }
        return wynik;
    }

    static double silnia(long x) {
        double wynik = 1;
        for (int i = 2; i <= x; i += 1) {
            if ((Math.abs(wynik * i) > Double.MAX_VALUE) || (Math.abs(wynik * i) < Double.MIN_VALUE)){
                return 0;
            }
            wynik *= i;
        }
        return wynik;
    }
    
    static double E_x_JK (double x, int l) {
        double e = 0, element = 1;

        for (int k = 1; k <= l; k += 1) {
            double pot = potega(x,k), sil = silnia(k);
            if ((Math.abs(e + element) > Double.MAX_VALUE) || (pot == 0) || (sil == 0)) {
                System.out.println("Podany x lub dokładność jest za duża!");
                return 0;
            }
            e += element;
            element = pot / sil;
        }
        return e;
    }

    static double Sin_x_JK (double x) {
        x %= Math.PI*2;

        double sin = 0, element = 1*x/1;
        long k = 0;

        while (Math.abs(sin + element) <= Double.MAX_VALUE) {
            k += 1;
            sin += element;
            element = potega(-1,k) * potega(x,2*k+1) / silnia(2*k+1);
        }
        return sin;
    }

    static double Cos_x_JK (double x) {
        x %= Math.PI*2;

        double cos = 0, element = 1*1/1;
        long k = 0;

        while (Math.abs(cos + element) <= Double.MAX_VALUE) {
            k += 1;
            cos += element;
            element = potega(-1,k) * potega(x,2*k) / silnia(2*k);
        }
        return cos;
    }
}
