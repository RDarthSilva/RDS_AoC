package problems;

/*
 * --- Day 4: Ceres Search ---
 * "Looks like the Chief's not here. Next!" One of The Historians pulls out a device and pushes the only button on it.
 * After a brief flash, you recognize the interior of the Ceres monitoring station!
 *
 * As the search for the Chief continues, a small Elf who lives on the station tugs on your shirt;
 * she'd like to know if you could help her with her word search (your puzzle input).
 * She only has to find one word: XMAS.
 *
 * This word search allows words to be horizontal, vertical, diagonal, written backwards, or even overlapping other
 * words. It's a little unusual, though, as you don't merely need to find one instance of XMAS - you need to find all
 * of them. Here are a few ways XMAS might appear, where irrelevant characters have been replaced with .:
 *
 * ..X...
 * .SAMX.
 * .A..A.
 * XMAS.S
 * .X....
 *
 * The actual word search will be full of letters instead. For example:
 * MMMSXXMASM
 * MSAMXMSMSA
 * AMXSXMAAMM
 * MSAMASMSMX
 * XMASAMXAMM
 * XXAMMXXAMA
 * SMSMSASXSS
 * SAXAMASAAA
 * MAMMMXMMMM
 * MXMXAXMASX
 *
 * In this word search, XMAS occurs a total of 18 times; here's the same word search again, but where letters not
 * involved in any XMAS have been replaced with .:
 * ....XXMAS.
 * .SAMXMS...
 * ...S..A...
 * ..A.A.MS.X
 * XMASAMX.MM
 * X.....XA.A
 * S.S.S.S.SS
 * .A.A.A.A.A
 * ..M.M.M.MM
 * .X.X.XMASX
 *
 * Take a look at the little Elf's word search. How many times does XMAS appear?
 * ANSWER: 2401
 *
 *
 *
 * --- Part Two ---
 * The Elf looks quizzically at you. Did you misunderstand the assignment?
 *
 * Looking for the instructions, you flip over the word search to find that this isn't actually an XMAS puzzle;
 * it's an X-MAS puzzle in which you're supposed to find two MAS in the shape of an X. One way to achieve that is like
 * this:
 * M.S
 * .A.
 * M.S
 *
 * Irrelevant characters have again been replaced with . in the above diagram. Within the X, each MAS can be written
 * forwards or backwards.
 *
 * Here's the same example from before, but this time all of the X-MASes have been kept instead:
 * .M.S......
 * ..A..MSMS.
 * .M.S.MAA..
 * ..A.ASMSM.
 * .M.S.M....
 * ..........
 * S.S.S.S.S.
 * .A.A.A.A..
 * M.M.M.M.M.
 * ..........
 *
 * In this example, an X-MAS appears 9 times.
 *
 * Flip the word search from the instructions back over to the word search side and try again. How many times does an
 * X-MAS appear?
 * ANSWER: 1822
 */

import templates.Day;
import tools.Convert;
import tools.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class Day04 extends Day {

    static final int DAY = 4 ;
    static final boolean DEBUG = false ;
    //static final String FILE_NAME = "Example" ;
    static final String FILE_NAME = "Input" ;

    public Day04(Log log) {
        super(log, DAY, FILE_NAME);
    }

    public static void main(String[] args) {
        Day d = new Day04(new Log());
        d.loadInput();
        d.executePart1();
        d.resetInput();
        d.executePart2();
        d.execTime();
    }

    public void executePart1() {
        super.executePart1();

        // L = Line
        // C = Column

        // Declare variables:
        ArrayList<XMatrixCell> starters = new ArrayList<>();
        XMatrixCell[][] matrix;

        // Get Matrix Size:
        int c = input.next().length();
        int l = 1;
        while(input.hasNext()) {
            l++;
            input.next();
        }
        matrix = new XMatrixCell[l][c];
        if(DEBUG) log.write("Matrix Size: " + l + " x " + c);

        // Initiate Matrix:
        resetInput();
        String line;
        for(l=0;l<matrix.length;l++) {
            line = input.next();
            for(c=0;c<matrix[l].length;c++) {
                matrix[l][c] = new XMatrixCell(l,c,line.charAt(c));
                if(matrix[l][c].getChar() == 'X') starters.add(matrix[l][c]);
            }
        }

        // Create Matrix Connections:
        for(l=0;l<matrix.length;l++) {
            for(c=0;c<matrix[l].length;c++) {
                matrix[l][c].populate(matrix);
            }
        }

        // Print Matrix:
        if(DEBUG) {
            for(l=0;l<matrix.length;l++) {
                line = "";
                for (c = 0; c < matrix[l].length; c++) {
                    line += matrix[l][c].getChar();
                }
                log.write(line);
            }
            log.write("");
        }

        // Count XMAS:
        int count = 0;
        Iterator<XMatrixCell> i = starters.iterator();
        while(i.hasNext()) count += i.next().countXMAS();
        log.write("XMAS Count: " + count);

        // Print XMAS Matrix:
        if(DEBUG) {
            for(l=0;l<matrix.length;l++) {
                line = "";
                for (c = 0; c < matrix[l].length; c++) {
                    line += matrix[l][c].getInvolved();
                }
                log.write(line);
            }
            log.write("");
        }

        // Print XMAS Matrix Counts:
        if(DEBUG) {
            for(l=0;l<matrix.length;l++) {
                line = "";
                for (c = 0; c < matrix[l].length; c++) {
                    line += "[" + Convert.toString(matrix[l][c].getXMASCount(),2) + "]";
                }
                log.write(line);
            }
            log.write("");
        }
    }

    public void executePart2() {
        super.executePart2();

        // L = Line
        // C = Column

        // Declare variables:
        ArrayList<XMatrixCell> starters = new ArrayList<>();
        XMatrixCell[][] matrix;

        // Get Matrix Size:
        int c = input.next().length();
        int l = 1;
        while(input.hasNext()) {
            l++;
            input.next();
        }
        matrix = new XMatrixCell[l][c];
        if(DEBUG) log.write("Matrix Size: " + l + " x " + c);

        // Initiate Matrix:
        resetInput();
        String line;
        for(l=0;l<matrix.length;l++) {
            line = input.next();
            for(c=0;c<matrix[l].length;c++) {
                matrix[l][c] = new XMatrixCell(l,c,line.charAt(c));
                if(matrix[l][c].getChar() == 'A') starters.add(matrix[l][c]);
            }
        }

        // Create Matrix Connections:
        for(l=0;l<matrix.length;l++) {
            for(c=0;c<matrix[l].length;c++) {
                matrix[l][c].populate(matrix);
            }
        }

        // Print Matrix:
        if(DEBUG) {
            for(l=0;l<matrix.length;l++) {
                line = "";
                for (c = 0; c < matrix[l].length; c++) {
                    line += matrix[l][c].getChar();
                }
                log.write(line);
            }
            log.write("");
        }

        // Count X-MAS:
        int count = 0;
        Iterator<XMatrixCell> i = starters.iterator();
        while(i.hasNext()) if(i.next().isX_MAS()) count++;
        log.write("X-MAS Count: " + count);
    }

    class XMatrixCell {
        private int l;
        private int c;
        private char ch;
        private XMatrixCell e;
        private XMatrixCell se;
        private XMatrixCell s;
        private XMatrixCell sw;
        private XMatrixCell w;
        private XMatrixCell nw;
        private XMatrixCell n;
        private XMatrixCell ne;
        boolean involved;
        int xCount;

        public XMatrixCell(int l, int c, char ch) {
            this.l = l;
            this.c = c;
            this.ch = ch;
            e = null;
            se = null;
            s = null;
            sw = null;
            w = null;
            nw = null;
            n = null;
            ne = null;
            involved = false;
            xCount = 0;
        }

        public char getChar() { return ch; }
        public char getInvolved() { if(involved) return ch; else return '.'; }
        public int getXMASCount() { return xCount; }

        public void populate(XMatrixCell[][] matrix) {
            if(c < matrix[l].length-1) e = matrix[l][c+1];
            if(l < matrix.length-1 && c < matrix[l].length-1) se = matrix[l+1][c+1];
            if(l < matrix.length-1) s = matrix[l+1][c];
            if(l < matrix.length-1 && c > 0) sw = matrix[l+1][c-1];
            if(c > 0) w = matrix[l][c-1];
            if(l > 0 && c > 0) nw = matrix[l-1][c-1];
            if(l > 0) n = matrix[l-1][c];
            if(l > 0 && c < matrix[l].length-1) ne = matrix[l-1][c+1];
        }

        public boolean isX_MAS() {
            if(nw != null && ne != null && se != null && sw != null) {
                return ( (nw.isMAS(1) || se.isMAS(5)) && (ne.isMAS(3) || sw.isMAS(7)) );
            } else return false;
        }

        public int countXMAS() {
            int count = 0;
            if(e != null && e.isMAS(0)) count++;
            if(se != null && se.isMAS(1)) count++;
            if(s != null && s.isMAS(2)) count++;
            if(sw != null && sw.isMAS(3)) count++;
            if(w != null && w.isMAS(4)) count++;
            if(nw != null && nw.isMAS(5)) count++;
            if(n != null && n.isMAS(6)) count++;
            if(ne != null && ne.isMAS(7)) count++;
            if(count > 0) involved = true;
            xCount = count ;
            return count;
        }

        public boolean isMAS(int direction) {
            if(this.ch != 'M') return false;
            switch(direction) {
                case 0: if(e != null) return e.isAS(direction); else return false ;
                case 1: if(se != null) return se.isAS(direction); else return false ;
                case 2: if(s != null) return s.isAS(direction); else return false ;
                case 3: if(sw != null) return sw.isAS(direction); else return false ;
                case 4: if(w != null) return w.isAS(direction); else return false ;
                case 5: if(nw != null) return nw.isAS(direction); else return false ;
                case 6: if(n != null) return n.isAS(direction); else return false ;
                case 7: if(ne != null) return ne.isAS(direction); else return false ;
                default: return false;
            }
        }

        public boolean isAS(int direction) {
            if(this.ch != 'A') return false;
            switch(direction) {
                case 0: if(e != null) return e.getChar() == 'S'; else return false ;
                case 1: if(se != null) return se.getChar() == 'S'; else return false ;
                case 2: if(s != null) return s.getChar() == 'S'; else return false ;
                case 3: if(sw != null) return sw.getChar() == 'S'; else return false ;
                case 4: if(w != null) return w.getChar() == 'S'; else return false ;
                case 5: if(nw != null) return nw.getChar() == 'S'; else return false ;
                case 6: if(n != null) return n.getChar() == 'S'; else return false ;
                case 7: if(ne != null) return ne.getChar() == 'S'; else return false ;
                default: return false;
            }
        }
    }
}
