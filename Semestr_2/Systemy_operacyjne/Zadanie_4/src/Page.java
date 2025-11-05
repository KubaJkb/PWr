public class Page {
    public int reference;
    public int timeLastUsed;

    public Page(int reference) {
        this.reference = reference;
        this.timeLastUsed = 0;
    }

    public Page(Page p) {
        this.reference = p.reference;
        this.timeLastUsed = p.timeLastUsed;
    }

    public int getTimeLastUsed() {
        return timeLastUsed;
    }

    public String toString() {
        return reference + " ";
    }
}