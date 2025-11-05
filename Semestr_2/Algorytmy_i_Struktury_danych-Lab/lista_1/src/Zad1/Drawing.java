package Zad1;

public class Drawing {
    public static void drawZigZag(int n, int l) {
        System.out.println("--- n = " + n + " ; l = " + l + " ---");
        if (n < 0 || l < 0) {
            return;
        }

        int sp = 0;
        for (int i = 0; i < n; i++) {
            System.out.print("\t");

            // ******* Rysowanie spacji *******
            drawSpace(sp);

            // !!!!!!!!!!!!! Zygzak zależny od l !!!!!!!!!!!!!
//            if ((i / (l-1)) % 2 == 0) {sp++;} else {sp--;}
            if ((i / 4) % 2 == 0) {
                sp++;
            } else {
                sp--;
            }

            // ******* Rysowanie X'ów *******
            drawX(l);
        }
        System.out.println("\n");
    }

    public static void drawBridge(int n) {
        System.out.println("----- n = " + n + " -----");

        if (n < 0) {
            return;
        }

        // ******* Górna część mostu *******
        for (int i = 0; i < n - 2; i++) {
            drawBridgeLevel(n, i);
        }

        // ******* Dolna część mostu *******
        drawX(2 * n - 1);
        drawBridgeLevel(n, 0);
        System.out.println();

    }

    static void drawX(int k) {
        for (int i = 0; i < k; i++) {
            System.out.print("X");
        }
        System.out.println();
    }

    static void drawSpace(int k) {
        for (int i = 0; i < k; i++) {
            System.out.print(" ");
        }
    }

    static void drawBridgeLevel(int n, int k) {
        if (n < 2) {
            return;
        }

        System.out.print("I");

        if (k > 0) {
            drawSpace(k - 1);
            System.out.print("\\");
            drawSpace(2 * n - 3 - 2 * k);
            System.out.print("/");
            drawSpace(k - 1);
        } else {
            drawSpace(2 * n - 3);
        }

        System.out.println("I");

    }

}
