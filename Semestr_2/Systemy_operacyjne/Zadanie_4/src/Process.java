import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Process {
    public List<Frame> frames;
    public int firstPageNr;
    public int lastPageNr;
    public int pageRange;

    public List<Integer> timeTable = new LinkedList<>();
    public List<Integer> NoMPtimeTable = new LinkedList<>();
    public HashSet<Integer> WSS = new HashSet<>();

    public double PFF;

    public Process(int firstPageNr, int lastPageNr) {
        frames = new ArrayList<>();
        this.firstPageNr = firstPageNr;
        this.lastPageNr = lastPageNr;
        pageRange = lastPageNr - firstPageNr + 1;
    }

    public boolean stealFrame() {
        // PROCES NIE MA RAMEK
        if (frames.isEmpty()) {
            return false;
        }

        // SZUKANIE PUSTEJ RAMKI
        for (Frame frame : frames) {
            if (frame.currentPage == null) {
                frames.remove(frame);
                return true;
            }
        }

        // SZUKANIE NAJDŁUŻEJ NIEUŻYWANEJ RAMKI
        int longestNotUsedFrame = 0;
        int smallestTimeLastUsed = Integer.MAX_VALUE;
        for (int i = 0; i < frames.size(); i++) {
            if (frames.get(i).currentPage.timeLastUsed < smallestTimeLastUsed) {
                longestNotUsedFrame = i;
                smallestTimeLastUsed = frames.get(i).currentPage.timeLastUsed;
            }
        }
        frames.remove(longestNotUsedFrame);
        return true;
    }

    public boolean LRU(Page replacementPage) {
        // PROCES NIE MA ŻADNYCH RAMEK
        if (frames.isEmpty()) {
            return true;
        }

        // W RAMKACH PROCESU ZNAJDUJE SIĘ JUŻ DANA STRONA
        for (Frame f : frames) {
            if (f.currentPage != null) {
                if (f.currentPage.reference == replacementPage.reference) {
                    f.currentPage = replacementPage;
                    return false;
                }
            }
        }

        // SZUKANIE PUSTEJ RAMKI
        Frame availableFrame = null;
        for (Frame frame : frames) {
            if (frame.currentPage == null) {
                availableFrame = frame;
                break;
            }
        }
        if (availableFrame != null) { // ZNALEZIONO PUSTĄ RAMKĘ
            availableFrame.currentPage = replacementPage;
            return true;
        }

        // SZUKANIE NAJDŁUŻEJ NIE UŻYWANEJ RAMKI
        Frame longestNotUsedFrame = null;
        int smallestTimeLastUsed = Integer.MAX_VALUE;
        for (Frame frame : frames) {
            if (frame.currentPage.timeLastUsed < smallestTimeLastUsed) {

                longestNotUsedFrame = frame;
                smallestTimeLastUsed = frame.currentPage.timeLastUsed;
            }
        }
        longestNotUsedFrame.currentPage = replacementPage;
        return true;
    }

    public void addFrame(Frame frame) {
        frame.currentPage = null;
        frames.add(frame);
    }

    public int stopProcess() {
        int retVal = frames.size();
        frames = new ArrayList<>();
        return retVal;
    }

}
