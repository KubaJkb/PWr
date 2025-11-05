public class Test extends Baza {
    public Test(int j) {}

    public static void main(String[] args) {
        Test t = (new Baza()).(new Test(1));
    }
}