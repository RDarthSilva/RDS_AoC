package problems;

/*
 * Problem Description
 */

import templates.Day;
import tools.Log;

public class Day03 extends Day {

    static final int DAY = 3 ;
    static final boolean DEBUG = true ;
    static final String FILE_NAME = "Example" ;
    //static final String FILE_NAME = "Input" ;

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

        // CODE HERE (Part 1)
        while(input.hasNext()) {
            log.write(input.next());
        }
    }

    public void executePart2() {
        super.executePart2();

        // CODE HERE (Part 2)
        //while(input.hasNext()) {
        //    log.write(input.next());
        //}
    }
}
