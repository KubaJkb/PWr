//Jakub Kacprzyk


public class Zad3 {
    public static void main(String[] args) {

        double n = 7;

        System.out.println("e^x = " + E_x_JK(n, 100));
        System.out.println("sin(x) = " + Sin_x_JK(n));
        System.out.println("cos(x) = " + Cos_x_JK(n));
    }

//    static double E_x_JK (double x) {
//        double e = 0, element = 1;
//        long k = 0;
//
//        while ((Math.abs(e + element) <= Double.MAX_VALUE) && (Math.abs(e + element) >= Double.MIN_VALUE) ) {
//            e += element;
//            k += 1;
//            //Sprawdzamy czy suma z kolejnym elementem nie wykracza poza range zmiennej Double
//            if ((Math.abs(e + element*x/k) > Double.MAX_VALUE) ) {
//                System.out.print("Ta liczba jest za duża/mała aby można było ją obliczyć! ");
//                return 0;
//            }
//            //Sprawdzamy czy kolejny element sumy nie wykracza poza range zmiennej Double
//            if ((Math.abs(element*x/k) > Double.MAX_VALUE) || (Math.abs(element*x/k) < Double.MIN_VALUE)){
//                break;
//            }
//            element = element * x / k;
//        }
//        return e;
//    }

    static double E_x_JK (double x, int dokladnosc) {
        double e = 1, element = 1;
        long k = 0;

        for (int i=1; i<=dokladnosc; i++) {
            k += 1;
            element = element * x / k;

            //Sprawdzamy czy suma z kolejnym elementem nie wykracza poza range zmiennej Double
            if ((Math.abs(e + element) > Double.MAX_VALUE) ) {
                System.out.print("Ta liczba jest za duża aby można było ją obliczyć! ");
                return 0;
            }
            e += element;
        }
        return e;
    }

    static double Sin_x_JK (double x) {
        x %= Math.PI*2;

        double sin = 1*x/1, element = 1*x/1;
        long k = 1;

        while (Math.abs(element * (-1) * (x*x) / (2*k * (2*k+1))) >= Double.MIN_VALUE) {
            element = element * (-1) * (x*x) / (2*k * (2*k+1));
            sin += element;
            k += 1;
        }
        return sin;
    }

    static double Cos_x_JK (double x) {
        x %= Math.PI*2;

        double cos = 1*1/1, element = 1*1/1;
        long k = 1;

        while (Math.abs(element * (-1) * (x*x) / ((2*k-1) * 2*k)) >= Double.MIN_VALUE) {
            element = element * (-1) * (x*x) / ((2*k-1) * 2*k);
            cos += element;
            k += 1;
        }
        return cos;
    }
}
