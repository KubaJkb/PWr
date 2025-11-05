import java.util.*;

public class Algorithms {
    public int NUMBER_OF_FRAMES;
    List<Page> frames;
    List<Page> pageReferences;

    public Algorithms(int NUMBER_OF_FRAMES, List<Page> pageReferences) {
        this.NUMBER_OF_FRAMES = NUMBER_OF_FRAMES;
        frames = new ArrayList<>();
        this.pageReferences = pageReferences;
    }

    public int FIFO() {
        int NoMP = 0;
        ArrayList<Page> pageRefs = new ArrayList<>();
        for (Page p : pageReferences) {
            pageRefs.add(new Page(p));
        }

        mainloop:
        for (Page page : pageRefs) {
            for (Page p : frames) {
                if (p.reference == page.reference) {
                    continue mainloop;
                }
            }

            if (frames.size() == NUMBER_OF_FRAMES) {
                frames.removeFirst();
            }

            frames.add(page);
            NoMP++;
        }

        frames.clear();
        return NoMP;
    }

    public int OPT() {
        int NoMP = 0;
        ArrayList<Page> pageRefs = new ArrayList<>();
        for (Page p : pageReferences) {
            pageRefs.add(new Page(p));
        }

        mainloop:
        for (Page page : pageRefs) {
            for (Page p : frames) {
                if (p.reference == page.reference) {
                    continue mainloop;
                }
            }

            if (frames.size() == NUMBER_OF_FRAMES) {
                removeLongestNotUsed(page, pageRefs);
            }

            frames.add(page);
            NoMP++;
        }

        frames.clear();
        return NoMP;
    }

    public void removeLongestNotUsed(Page page, ArrayList<Page> pageRefs) {
        ArrayList<Page> remainingFrames = new ArrayList<>();
        for (Page i : frames) {
            remainingFrames.add(new Page(i));
        }

        for (int i = pageRefs.indexOf(page) + 1; i < pageRefs.size(); i++) {
            int indexDelete = -1;
            for (Page p : remainingFrames) {
                if (p.reference == pageRefs.get(i).reference) {
                    indexDelete = remainingFrames.indexOf(p);
                }
            }
            if (indexDelete != -1) {
                remainingFrames.remove(indexDelete);
            }

            if (remainingFrames.size() == 1) {
                break;
            }
        }

        int deleteRef = remainingFrames.getFirst().reference;
        frames.removeIf(p -> p.reference == deleteRef);
    }

    public int LRU() {
        int NoMP = 0;
        ArrayList<Page> pageRefs = new ArrayList<>();
        for (Page p : pageReferences) {
            pageRefs.add(new Page(p));
        }

        int time = 1;

        mainloop:
        for (Page page : pageRefs) {
            time++;
            for (Page p : frames) {
                if (p.reference == page.reference) {
                    p.setUtilityValue(time);
                    continue mainloop;
                }
            }

            if (frames.size() == NUMBER_OF_FRAMES) {
                frames.sort(Comparator.comparingInt(Page::getUtilityValue));
                frames.removeFirst();
            }

            frames.add(page);
            NoMP++;
        }

        frames.clear();
        return NoMP;
    }

    public int ALRU() {
        int NoMP = 0;
        ArrayList<Page> pageRefs = new ArrayList<>();
        for (Page p : pageReferences) {
            pageRefs.add(new Page(p));
        }

        mainloop:
        for (Page page : pageRefs) {
            for (Page p : frames) {
                if (p.reference == page.reference) {
                    p.setUtilityValue(1);
                    continue mainloop;
                }
            }

            if (frames.size() == NUMBER_OF_FRAMES) {
                for (Page p : frames) {
                    if (p.utilityValue == 0) {
                        frames.remove(p);
                        break;
                    } else {
                        p.setUtilityValue(0);
                    }
                }

                if (frames.size() == NUMBER_OF_FRAMES) {
                    frames.removeFirst();
                }
            }

            frames.add(page);
            NoMP++;
        }

        frames.clear();
        return NoMP;
    }

    public int RAND() {
        int NoMP = 0;
        ArrayList<Page> pageRefs = new ArrayList<>();
        for (Page p : pageReferences) {
            pageRefs.add(new Page(p));
        }
        Random rand = new Random();

        mainloop:
        for (Page page : pageRefs) {
            for (Page p : frames) {
                if (p.reference == page.reference) {
                    p.setUtilityValue(1);
                    continue mainloop;
                }
            }

            if (frames.size() == NUMBER_OF_FRAMES) {
                frames.remove(rand.nextInt(NUMBER_OF_FRAMES));
            }

            frames.add(page);
            NoMP++;
        }

        frames.clear();
        return NoMP;
    }
}