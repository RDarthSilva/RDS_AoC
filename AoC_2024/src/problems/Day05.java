package problems;

/*
 * --- Day 5: Print Queue ---
 * Satisfied with their search on Ceres, the squadron of scholars suggests subsequently scanning the stationery stacks
 * of sub-basement 17.
 *
 * The North Pole printing department is busier than ever this close to Christmas, and while The Historians continue
 * their search of this historically significant facility, an Elf operating a very familiar printer beckons you over.
 *
 * The Elf must recognize you, because they waste no time explaining that the new sleigh launch safety manual updates
 * won't print correctly. Failure to update the safety manuals would be dire indeed, so you offer your services.
 *
 * Safety protocols clearly indicate that new pages for the safety manuals must be printed in a very specific order.
 * The notation X|Y means that if both page number X and page number Y are to be produced as part of an update,
 * page number X must be printed at some point before page number Y.
 *
 * The Elf has for you both the page ordering rules and the pages to produce in each update (your puzzle input),
 * but can't figure out whether each update has the pages in the right order.
 *
 * For example:
 * 47|53
 * 97|13
 * 97|61
 * 97|47
 * 75|29
 * 61|13
 * 75|53
 * 29|13
 * 97|29
 * 53|29
 * 61|53
 * 97|53
 * 61|29
 * 47|13
 * 75|47
 * 97|75
 * 47|61
 * 75|61
 * 47|29
 * 75|13
 * 53|13
 *
 * 75,47,61,53,29
 * 97,61,53,29,13
 * 75,29,13
 * 75,97,47,61,53
 * 61,13,29
 * 97,13,75,29,47
 *
 * The first section specifies the page ordering rules, one per line. The first rule, 47|53, means that if an update
 * includes both page number 47 and page number 53, then page number 47 must be printed at some point before page
 * number 53. (47 doesn't necessarily need to be immediately before 53; other pages are allowed to be between them.)
 *
 * The second section specifies the page numbers of each update. Because most safety manuals are different, the pages
 * needed in the updates are different too. The first update, 75,47,61,53,29, means that the update consists of page
 * numbers 75, 47, 61, 53, and 29.
 *
 * To get the printers going as soon as possible, start by identifying which updates are already in the right order.
 *
 * In the above example, the first update (75,47,61,53,29) is in the right order:
 * - 75 is correctly first because there are rules that put each other page after it: 75|47, 75|61, 75|53, and 75|29.
 * - 47 is correctly second because 75 must be before it (75|47) and every other page must be after it according to
 * 47|61, 47|53, and 47|29.
 * - 61 is correctly in the middle because 75 and 47 are before it (75|61 and 47|61) and 53 and 29 are after it
 * (61|53 and 61|29).
 * - 53 is correctly fourth because it is before page number 29 (53|29).
 * - 29 is the only page left and so is correctly last.
 *
 * Because the first update does not include some page numbers, the ordering rules involving those missing page numbers
 * are ignored.
 *
 * The second and third updates are also in the correct order according to the rules. Like the first update, they also
 * do not include every page number, and so only some of the ordering rules apply - within each update, the ordering
 * rules that involve missing page numbers are not used.
 *
 * The fourth update, 75,97,47,61,53, is not in the correct order: it would print 75 before 97, which violates the
 * rule 97|75.
 *
 * The fifth update, 61,13,29, is also not in the correct order, since it breaks the rule 29|13.
 *
 * The last update, 97,13,75,29,47, is not in the correct order due to breaking several rules.
 *
 * For some reason, the Elves also need to know the middle page number of each update being printed. Because you are
 * currently only printing the correctly-ordered updates, you will need to find the middle page number of each
 * correctly-ordered update. In the above example, the correctly-ordered updates are:
 * 75,47,61,53,29
 * 97,61,53,29,13
 * 75,29,13
 *
 * These have middle page numbers of 61, 53, and 29 respectively. Adding these page numbers together gives 143.
 *
 * Of course, you'll need to be careful: the actual list of page ordering rules is bigger and more complicated than the
 * above example.
 *
 * Determine which updates are already in the correct order. What do you get if you add up the middle page number from
 * those correctly-ordered updates?
 * ANSWER: 4766
 *
 *
 *
 * --- Part Two ---
 * While the Elves get to work printing the correctly-ordered updates, you have a little time to fix the rest of them.
 *
 * For each of the incorrectly-ordered updates, use the page ordering rules to put the page numbers in the right order.
 * For the above example, here are the three incorrectly-ordered updates and their correct orderings:
 * - 75,97,47,61,53 becomes 97,75,47,61,53.
 * - 61,13,29 becomes 61,29,13.
 * - 97,13,75,29,47 becomes 97,75,47,29,13.
 *
 * After taking only the incorrectly-ordered updates and ordering them correctly, their middle page numbers are 47, 29,
 * and 47. Adding these together produces 123.
 *
 * Find the updates which are not in the correct order. What do you get if you add up the middle page numbers after
 * correctly ordering just those updates?
 * ANSWER: 6257
 */

import templates.Day;
import tools.Log;

import java.util.*;

public class Day05 extends Day {

    static final int DAY = 5 ;
    static final boolean DEBUG = false ;
    //static final String FILE_NAME = "Example" ;
    static final String FILE_NAME = "Input" ;

    public Day05(Log log) {
        super(log, DAY, FILE_NAME);
    }

    public static void main(String[] args) {
        Day d = new Day05(new Log());
        d.loadInput();
        d.executePart1();
        d.resetInput();
        d.executePart2();
        d.execTime();
    }

    public void executePart1() {
        super.executePart1();

        // Declare variables:
        Manual manual = new Manual();
        ArrayList<Update> updates = new ArrayList<>();
        Page page, page1, page2;
        String line;
        StringTokenizer t;

        // Load Rules:
        while(input.hasNext()) {
            line = input.next();
            if(line.length() == 0) break;
            t = new StringTokenizer(line,"|");
            page1 = manual.getPage(Integer.parseInt(t.nextToken()));
            page2 = manual.getPage(Integer.parseInt(t.nextToken()));
            page1.addNextCandidates(page2);
            page2.addPreviousCandidate(page1);
        }

        // Load Updates:
        ArrayList<Page> pages;
        while(input.hasNext()) {
            pages = new ArrayList<>();
            t = new StringTokenizer(input.next(),",");
            while(t.hasMoreTokens()) pages.add(manual.getPage(Integer.parseInt(t.nextToken())));
            updates.add(new Update(pages));
        }

        // Validate Manuals
        Iterator<Update> i = updates.iterator();
        Update update;
        int sum = 0;
        while(i.hasNext()) {
            update = i.next();
            if(update.isValid()) {
                if(DEBUG) log.write(update.toString() + " is valid, middle page: " + update.getMiddlePage().getPageNumber());
                sum += update.getMiddlePage().getPageNumber();
            } else {
                if(DEBUG) log.write(update.toString() + " is invalid, ignoring...");
            }
        }

        log.write("Answer: " + sum);

    }

    public void executePart2() {
        super.executePart2();

        // Declare variables:
        Manual manual = new Manual();
        ArrayList<Update> updates = new ArrayList<>();
        Page page, page1, page2;
        String line;
        StringTokenizer t;

        // Load Rules:
        while(input.hasNext()) {
            line = input.next();
            if(line.length() == 0) break;
            t = new StringTokenizer(line,"|");
            page1 = manual.getPage(Integer.parseInt(t.nextToken()));
            page2 = manual.getPage(Integer.parseInt(t.nextToken()));
            page1.addNextCandidates(page2);
            page2.addPreviousCandidate(page1);
        }

        // Load Updates:
        ArrayList<Page> pages;
        while(input.hasNext()) {
            pages = new ArrayList<>();
            t = new StringTokenizer(input.next(),",");
            while(t.hasMoreTokens()) pages.add(manual.getPage(Integer.parseInt(t.nextToken())));
            updates.add(new Update(pages));
        }

        // Validate Manuals
        Iterator<Update> i = updates.iterator();
        Update update;
        int sum = 0;
        while(i.hasNext()) {
            update = i.next();
            if(update.isValid()) {
                if(DEBUG) log.write(update.toString() + " is valid, ignoring...");
            } else {
                if(DEBUG) log.write(update.toString() + " is invalid, reordering...");
                update = update.reorder();
                if(DEBUG) log.write("   " + update.toString() + " is now valid, middle page: " + update.getMiddlePage().getPageNumber());
                sum += update.getMiddlePage().getPageNumber();
            }
        }

        log.write("Answer: " + sum);
    }

    class Manual {
        HashMap<Integer,Page> pages;

        public Manual() {
            pages = new HashMap<>();
        }

        public Page getPage(int num) {
            if(!pages.containsKey(num)) pages.put(num, new Page(num));
            return pages.get(num);
        }

    }

    class Page {
        private int num;
        private ArrayList<Page> previousCandidates;
        private ArrayList<Page> nextCandidates;

        public Page(int num) {
            this.num = num;
            previousCandidates = new ArrayList<>();
            nextCandidates = new ArrayList<>();
        }

        public int getPageNumber() { return num; }

        public void addPreviousCandidate(Page page) {
            if(!previousCandidates.contains(page)) previousCandidates.add(page);
        }
        public void addNextCandidates(Page page) {
            if(!nextCandidates.contains(page)) nextCandidates.add(page);
        }

        public boolean isValid(ArrayList<Page> previousPages, ArrayList<Page> nextPages) {
            Iterator<Page> i = previousPages.iterator();
            while(i.hasNext()) if(nextCandidates.contains(i.next())) return false;
            i = nextPages.iterator();
            while(i.hasNext()) if(previousCandidates.contains(i.next())) return false;
            return true;
        }

    }

    class Update {
        private ArrayList<Page> pages;

        public Update(ArrayList<Page> pages) {
            this.pages = pages;
        }

        public boolean isValid() {
            ArrayList<Page> previousPages = new ArrayList<>();
            ArrayList<Page> nextPages = (ArrayList<Page>) pages.clone();
            Page page;
            for(int n=0; n<pages.size(); n++) {
                page = nextPages.remove(0);
                if(!page.isValid(previousPages, nextPages)) return false;
                previousPages.add(page);
            }
            return true;
        }

        public Update reorder() {
            ArrayList<Page> orderedUpdate = new ArrayList<>();
            ArrayList<Page> pagesToPlace = (ArrayList<Page>) pages.clone();
            ArrayList<Page> previousPages, nextPages;
            Page page;
            while(pagesToPlace.size()>0) {
                page = pagesToPlace.remove(0);
                if(orderedUpdate.isEmpty()) orderedUpdate.add(page);
                else {
                    previousPages = new ArrayList<>();
                    nextPages = (ArrayList<Page>) orderedUpdate.clone();
                    for(int n=0; n<=orderedUpdate.size(); n++) {
                        if(page.isValid(previousPages,nextPages)) {
                            orderedUpdate.add(n,page);
                            break;
                        } else previousPages.add(nextPages.remove(0));
                    }
                }
            }
            return new Update(orderedUpdate);
        }

        public String toString() {
            String out = "";
            Iterator<Page> i = pages.iterator();
            while(i.hasNext()) out += i.next().getPageNumber() + ",";
            return out.substring(0,out.length()-1);
        }

        public Page getMiddlePage() {
            return pages.get(pages.size()/2);
        }
    }
}
