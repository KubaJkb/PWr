import java.util.Comparator;

public class Page {
    public int reference;
    public int utilityValue;

    public Page(int reference, int utilityValue) {
        this.reference = reference;
        this.utilityValue = utilityValue;
    }

    public Page(Page p) {
        this.reference = p.reference;
        this.utilityValue = p.utilityValue;
    }

    public void setUtilityValue(int n) {
        this.utilityValue = n;
    }

    public int getUtilityValue() {
        return utilityValue;
    }

    public String toString() {
        return reference + " ";
    }
}