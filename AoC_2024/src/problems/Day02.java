package problems;

/*
 * --- Day 2: Red-Nosed Reports ---
 * Fortunately, the first location The Historians want to search isn't a long walk from the Chief Historian's office.
 *
 * While the Red-Nosed Reindeer nuclear fusion/fission plant appears to contain no sign of the Chief Historian, the
 * engineers there run up to you as soon as they see you. Apparently, they still talk about the time Rudolph was saved
 * through molecular synthesis from a single electron.
 *
 * They're quick to add that - since you're already here - they'd really appreciate your help analyzing some unusual
 * data from the Red-Nosed reactor. You turn to check if The Historians are waiting for you, but they seem to have
 * already divided into groups that are currently searching every corner of the facility. You offer to help with the
 * unusual data.
 *
 * The unusual data (your puzzle input) consists of many reports, one report per line. Each report is a list of numbers
 * called levels that are separated by spaces. For example:
 *   7 6 4 2 1
 *   1 2 7 8 9
 *   9 7 6 2 1
 *   1 3 2 4 5
 *   8 6 4 4 1
 *   1 3 6 7 9
 *
 * This example data contains six reports each containing five levels.
 *
 * The engineers are trying to figure out which reports are safe. The Red-Nosed reactor safety systems can only
 * tolerate levels that are either gradually increasing or gradually decreasing. So, a report only counts as safe if
 * both of the following are true:
 * - The levels are either all increasing or all decreasing.
 * - Any two adjacent levels differ by at least one and at most three.
 *
 * In the example above, the reports can be found safe or unsafe by checking those rules:
 * - 7 6 4 2 1: Safe because the levels are all decreasing by 1 or 2.
 * - 1 2 7 8 9: Unsafe because 2 7 is an increase of 5.
 * - 9 7 6 2 1: Unsafe because 6 2 is a decrease of 4.
 * - 1 3 2 4 5: Unsafe because 1 3 is increasing but 3 2 is decreasing.
 * - 8 6 4 4 1: Unsafe because 4 4 is neither an increase or a decrease.
 * - 1 3 6 7 9: Safe because the levels are all increasing by 1, 2, or 3.
 * So, in this example, 2 reports are safe.
 *
 * Analyze the unusual data from the engineers. How many reports are safe?
 * ANSWER: 282
 *
 *
 *
 * --- Part Two ---
 * The engineers are surprised by the low number of safe reports until they realize they forgot to tell you about the
 * Problem Dampener.
 *
 * The Problem Dampener is a reactor-mounted module that lets the reactor safety systems tolerate a single bad level in
 * what would otherwise be a safe report. It's like the bad level never happened!
 *
 * Now, the same rules apply as before, except if removing a single level from an unsafe report would make it safe, the
 * report instead counts as safe.
 *
 * More of the above example's reports are now safe:
 * - 7 6 4 2 1: Safe without removing any level.
 * - 1 2 7 8 9: Unsafe regardless of which level is removed.
 * - 9 7 6 2 1: Unsafe regardless of which level is removed.
 * - 1 3 2 4 5: Safe by removing the second level, 3.
 * - 8 6 4 4 1: Safe by removing the third level, 4.
 * - 1 3 6 7 9: Safe without removing any level.
 *
 * Thanks to the Problem Dampener, 4 reports are actually safe!
 *
 * Update your analysis by handling situations where the Problem Dampener can remove a single level from unsafe reports.
 * How many reports are now safe?
 * ANSWER: 349
 */

import templates.Day;
import tools.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Day02 extends Day {

    static final int DAY = 2 ;
    static final boolean DEBUG = false ;
    //static final String FILE_NAME = "Example" ;
    static final String FILE_NAME = "Input" ;

    public Day02(Log log) {
        super(log, DAY, FILE_NAME);
    }

    public static void main(String[] args) {
        Day d = new Day02(new Log());
        d.loadInput();
        d.executePart1();
        d.resetInput();
        d.executePart2();
        d.execTime();
    }

    public void executePart1() {
        super.executePart1();

        // Declare variables:
        ArrayList<Report> reports = new ArrayList<>();
        Iterator<Report> iReports;
        Report report;

        // Load Levels:
        while(input.hasNext())
            reports.add(new Report(input.next()));

        // Print Loaded Levels:
        if(DEBUG) {
            iReports = reports.iterator();
            while(iReports.hasNext()) {
                log.write(iReports.next().toString());
            }
        }

        // Check Safety:
        int safeCount = 0;
        int reportNum = 0;
        iReports = reports.iterator();
        while(iReports.hasNext()) {
            reportNum++;
            report = iReports.next();
            if(report.isSafe(3)) safeCount++;
            else if(DEBUG) log.write("Report " + reportNum + " is unsafe!");
        }
        log.write("Safe Reports: " + safeCount);
    }

    public void executePart2() {
        super.executePart2();

        // Declare variables:
        ArrayList<Report> reports = new ArrayList<>();
        Iterator<Report> iReports;
        Report report;

        // Load Levels:
        while(input.hasNext())
            reports.add(new Report(input.next()));

        // Print Loaded Levels:
        if(DEBUG) {
            iReports = reports.iterator();
            while(iReports.hasNext()) {
                log.write(iReports.next().toString());
            }
        }

        // Check Safety:
        int safeCount = 0;
        int reportNum = 0;
        iReports = reports.iterator();
        while(iReports.hasNext()) {
            reportNum++;
            report = iReports.next();
            if(report.isSafeWithDampener(3)) safeCount++;
            else if(DEBUG) log.write("Report " + reportNum + " is unsafe!");
        }
        log.write("Safe Reports: " + safeCount);
    }

    class Report {
        private ArrayList<Integer> levels;

        public Report(String levels) {
            StringTokenizer t = new StringTokenizer(levels);
            this.levels = new ArrayList<>();
            while(t.hasMoreTokens()) this.levels.add(Integer.parseInt(t.nextToken()));
        }
        public Report(ArrayList<Integer> levels) {
            this.levels = levels;
        }

        public String toString() {
            String s = "";
            Iterator<Integer> i = levels.iterator();
            while(i.hasNext()) s += i.next() + " " ;
            return s;
        }

        public boolean isSafe(int maxDiff) {
            if(isSafeUp(maxDiff)) return true;
            else if(isSafeDown(maxDiff)) return true;
            return false;
        }

        private boolean isSafeUp(int maxDiff) {
            int prevLevel, currLevel;
            Iterator<Integer> i = levels.iterator();
            prevLevel = i.next();
            while(i.hasNext()) {
                currLevel = i.next();
                if(currLevel - prevLevel < 1) return false;
                if(currLevel - prevLevel > maxDiff) return false;
                prevLevel = currLevel;
            }
            return true;
        }

        private boolean isSafeDown(int maxDiff) {
            int prevLevel, currLevel;
            Iterator<Integer> i = levels.iterator();
            prevLevel = i.next();
            while(i.hasNext()) {
                currLevel = i.next();
                if(prevLevel - currLevel < 1) return false;
                if(prevLevel - currLevel > maxDiff) return false;
                prevLevel = currLevel;
            }
            return true;
        }

        public boolean isSafeWithDampener(int maxDiff) {
            if(isSafe(maxDiff)) return true;
            ArrayList<Report> alternatives = getAlternatives() ;
            Iterator<Report> iAlts = alternatives.iterator();
            while(iAlts.hasNext()) {
                if(iAlts.next().isSafe(maxDiff)) return true;
            }
            return false;
        }

        private ArrayList<Report> getAlternatives() {
            ArrayList<Report> alts = new ArrayList<>();
            ArrayList<Integer> currReport ;
            Iterator<Integer> i = levels.iterator();
            Iterator<Integer> i2 ;
            int skip = 0;
            int n;
            while(i.hasNext()) {
                currReport = new ArrayList<>();
                i2 = levels.iterator();
                n = 0 ;
                while(i2.hasNext()) {
                    if(n++ != skip) currReport.add(i2.next());
                    else i2.next();
                }
                skip++;
                i.next();
                alts.add(new Report(currReport));
            }
            return alts;
        }
    }
}
