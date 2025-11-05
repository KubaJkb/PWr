public class EqTest {
    private int wartosc;

    public EqTest(int wartosc) {
        this.wartosc = wartosc;
    }

    public boolean equals(EqTest inny) {return this.wartosc == inny.wartosc;}

    public static void main(String[] args) {
        EqTest e = new EqTest(8);
        EqTest f = new EqTest(8);
        System.out.println(e == f);
        System.out.println(e.equals(f));
        System.out.println(e.wartosc == f.wartosc);
        System.out.println(f.equals(e));
    }

}


