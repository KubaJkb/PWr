
class A {
    public void wykonaj() {
        System.out.print("A");
    }
}

class B extends A {
    public void wykonaj() {

        System.out.print("B");
    }
}

class C extends B {
    public void wykonaj() {

        System.out.print("C");
    }
}

class ABC {
    public static void main(String[] args) {
        B b = new B();
        A ab = (A) b;
        A ac = new C();
        b.wykonaj();
        ab.wykonaj();
        ac.wykonaj();
    }
}