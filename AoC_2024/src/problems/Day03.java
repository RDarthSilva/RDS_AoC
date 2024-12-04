package problems;

/*
 * --- Day 3: Mull It Over ---
 * "Our computers are having issues, so I have no idea if we have any Chief Historians in stock! You're welcome to
 * check the warehouse, though," says the mildly flustered shopkeeper at the North Pole Toboggan Rental Shop. The
 * Historians head out to take a look.
 *
 * The shopkeeper turns to you. "Any chance you can see why our computers are having issues again?"
 *
 * The computer appears to be trying to run a program, but its memory (your puzzle input) is corrupted.
 * All of the instructions have been jumbled up!
 *
 * It seems like the goal of the program is just to multiply some numbers. It does that with instructions like mul(X,Y),
 * where X and Y are each 1-3 digit numbers. For instance, mul(44,46) multiplies 44 by 46 to get a result of 2024.
 * Similarly, mul(123,4) would multiply 123 by 4.
 *
 * However, because the program's memory has been corrupted, there are also many invalid characters that should be
 * ignored, even if they look like part of a mul instruction. Sequences like mul(4*, mul(6,9!, ?(12,34),
 * or mul ( 2 , 4 ) do nothing.
 *
 * For example, consider the following section of corrupted memory:
 * xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
 *
 * Only the four highlighted sections are real mul instructions. Adding up the result of each instruction produces
 * 161 (2*4 + 5*5 + 11*8 + 8*5).
 *
 * Scan the corrupted memory for uncorrupted mul instructions. What do you get if you add up all of the results of the
 * multiplications?
 * ANSWER: 162813399
 *
 *
 *
 * --- Part Two ---
 * As you scan through the corrupted memory, you notice that some of the conditional statements are also still intact.
 * If you handle some of the uncorrupted conditional statements in the program, you might be able to get an even more
 * accurate result.
 *
 * There are two new instructions you'll need to handle:
 * - The do() instruction enables future mul instructions.
 * - The don't() instruction disables future mul instructions.
 *
 * Only the most recent do() or don't() instruction applies. At the beginning of the program, mul instructions are
 * enabled.
 *
 * For example:
 * xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
 *
 * This corrupted memory is similar to the example from before, but this time the mul(5,5) and mul(11,8) instructions
 * are disabled because there is a don't() instruction before them. The other mul instructions function normally,
 * including the one at the end that gets re-enabled by a do() instruction.
 *
 * This time, the sum of the results is 48 (2*4 + 8*5).
 *
 * Handle the new instructions; what do you get if you add up all of the results of just the enabled multiplications?
 * ANSWER: 53783319
 */

import templates.Day;
import tools.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class Day03 extends Day {

    static final int DAY = 3 ;
    static final boolean DEBUG = false ;
    //static final String FILE_NAME = "Example" ;
    //static final String FILE_NAME = "Example2" ;
    static final String FILE_NAME = "Input" ;

    public Day03(Log log) {
        super(log, DAY, FILE_NAME);
    }

    public static void main(String[] args) {
        Day d = new Day03(new Log());
        d.loadInput();
        d.executePart1();
        d.resetInput();
        d.executePart2();
        d.execTime();
    }

    public void executePart1() {
        super.executePart1();

        // Declare variables:
        ArrayList<Mull> mulls = new ArrayList<>();
        String sub;
        Mull mull;

        // Process Line
        String corruptedLine = input.next();
        for (int n=0; n<corruptedLine.length(); n++) {
            if(corruptedLine.charAt(n) == 'm') {
                sub = corruptedLine.substring(n);
                sub = sub.substring(0,sub.indexOf(')')+1);
                mull = null;
                if(sub.length()>7) mull = getMull(sub);
                if(mull != null) mulls.add(mull);
            }
        }

        // Calculate Mulls:
        int sum = 0;
        Iterator<Mull> i = mulls.iterator();
        while(i.hasNext()) sum += i.next().getMull();

        log.write("Sum of Mulls: " + sum);
    }

    public void executePart2() {
        super.executePart2();

        // Declare variables:
        ArrayList<Mull> mulls = new ArrayList<>();
        boolean enabled = true;
        String sub;
        Mull mull;

        // Process Line
        String corruptedLine = input.next();
        for (int n=0; n<corruptedLine.length(); n++) {
            if(corruptedLine.charAt(n) == 'd') {
                sub = corruptedLine.substring(n);
                sub = sub.substring(0,sub.indexOf(')')+1);
                if(isDont(sub)) enabled = false;
                if(isDo(sub)) enabled = true;
            }
            if(enabled) {
                if (corruptedLine.charAt(n) == 'm') {
                    sub = corruptedLine.substring(n);
                    sub = sub.substring(0, sub.indexOf(')') + 1);
                    mull = null;
                    if (sub.length() > 7) mull = getMull(sub);
                    if (mull != null) mulls.add(mull);
                }
            }
        }

        // Calculate Mulls:
        int sum = 0;
        Iterator<Mull> i = mulls.iterator();
        while(i.hasNext()) sum += i.next().getMull();

        log.write("Sum of Mulls: " + sum);
    }

    public Mull getMull(String sub) {
        if(DEBUG) log.write("Evaluating " + sub);
        // Mul( X ):
        if(sub.substring(0,4).equals("mul(")) {
            sub = sub.substring(4,sub.length()-1);
            //if(DEBUG) log.write("- op ok, now evaluating " + sub);
        } else return null;

        // X = Split and Digits:
        int split = sub.indexOf(',');
        if(split == -1) return null;
        int n1,n2;

        try {
            n1 = Integer.parseInt(sub.substring(0,split));
            n2 = Integer.parseInt(sub.substring(split+1));
        } catch(NumberFormatException e) { return null; }

        return new Mull(n1,n2);
    }

    public boolean isDont(String sub) {
        if(DEBUG) log.write("Evaluating " + sub);
        return sub.equals("don't()");
    }

    public boolean isDo(String sub) {
        if(DEBUG) log.write("Evaluating " + sub);
        return sub.equals("do()");
    }
    class Mull {
        private int n1;
        private int n2;

        public Mull(int n1, int n2) {
            this.n1 = n1;
            this.n2 = n2;
        }

        public int getMull() { return n1*n2; }
    }
}
