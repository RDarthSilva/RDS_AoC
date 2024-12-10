package problems;

/*
 * --- Day 10: Hoof It ---
 * You all arrive at a Lava Production Facility on a floating island in the sky. As the others begin to search the
 * massive industrial complex, you feel a small nose boop your leg and look down to discover a reindeer wearing a hard
 * hat.
 *
 * The reindeer is holding a book titled "Lava Island Hiking Guide". However, when you open the book, you discover that
 * most of it seems to have been scorched by lava! As you're about to ask how you can help, the reindeer brings you a
 * blank topographic map of the surrounding area (your puzzle input) and looks up at you excitedly.
 *
 * Perhaps you can help fill in the missing hiking trails?
 *
 * The topographic map indicates the height at each position using a scale from 0 (lowest) to 9 (highest). For example:
 * 0123
 * 1234
 * 8765
 * 9876
 *
 * Based on un-scorched scraps of the book, you determine that a good hiking trail is as long as possible and has an
 * even, gradual, uphill slope. For all practical purposes, this means that a hiking trail is any path that starts at
 * height 0, ends at height 9, and always increases by a height of exactly 1 at each step. Hiking trails never include
 * diagonal steps - only up, down, left, or right (from the perspective of the map).
 *
 * You look up from the map and notice that the reindeer has helpfully begun to construct a small pile of pencils,
 * markers, rulers, compasses, stickers, and other equipment you might need to update the map with hiking trails.
 *
 * A trailhead is any position that starts one or more hiking trails - here, these positions will always have height 0.
 * Assembling more fragments of pages, you establish that a trailhead's score is the number of 9-height positions
 * reachable from that trailhead via a hiking trail. In the above example, the single trailhead in the top left corner
 * has a score of 1 because it can reach a single 9 (the one in the bottom left).
 *
 * This trailhead has a score of 2:
 * ...0...
 * ...1...
 * ...2...
 * 6543456
 * 7.....7
 * 8.....8
 * 9.....9
 *
 * (The positions marked . are impassable tiles to simplify these examples; they do not appear on your actual
 * topographic map.)
 *
 * This trailhead has a score of 4 because every 9 is reachable via a hiking trail except the one immediately to the
 * left of the trailhead:
 * ..90..9
 * ...1.98
 * ...2..7
 * 6543456
 * 765.987
 * 876....
 * 987....
 *
 * This topographic map contains two trailheads; the trailhead at the top has a score of 1, while the trailhead at the
 * bottom has a score of 2:
 * 10..9..
 * 2...8..
 * 3...7..
 * 4567654
 * ...8..3
 * ...9..2
 * .....01
 *
 * Here's a larger example:
 * 89010123
 * 78121874
 * 87430965
 * 96549874
 * 45678903
 * 32019012
 * 01329801
 * 10456732
 *
 * This larger example has 9 trailheads. Considering the trailheads in reading order, they have scores of 5, 6, 5, 3, 1,
 * 3, 5, 3, and 5. Adding these scores together, the sum of the scores of all trailheads is 36.
 *
 * The reindeer gleefully carries over a protractor and adds it to the pile. What is the sum of the scores of all
 * trailheads on your topographic map?
 * ANSWER: 717
 *
 *
 *
 * --- Part Two ---
 * The reindeer spends a few minutes reviewing your hiking trail map before realizing something, disappearing for a few
 * minutes, and finally returning with yet another slightly-charred piece of paper.
 *
 * The paper describes a second way to measure a trailhead called its rating. A trailhead's rating is the number of
 * distinct hiking trails which begin at that trailhead. For example:
 * .....0.
 * ..4321.
 * ..5..2.
 * ..6543.
 * ..7..4.
 * ..8765.
 * ..9....
 *
 * The above map has a single trailhead; its rating is 3 because there are exactly three distinct hiking trails which
 * begin at that position:
 * .....0.   .....0.   .....0.
 * ..4321.   .....1.   .....1.
 * ..5....   .....2.   .....2.
 * ..6....   ..6543.   .....3.
 * ..7....   ..7....   .....4.
 * ..8....   ..8....   ..8765.
 * ..9....   ..9....   ..9....
 *
 * Here is a map containing a single trailhead with rating 13:
 * ..90..9
 * ...1.98
 * ...2..7
 * 6543456
 * 765.987
 * 876....
 * 987....
 *
 * This map contains a single trailhead with rating 227 (because there are 121 distinct hiking trails that lead to the
 * 9 on the right edge and 106 that lead to the 9 on the bottom edge):
 * 012345
 * 123456
 * 234567
 * 345678
 * 4.6789
 * 56789.
 *
 * Here's the larger example from before:
 * 89010123
 * 78121874
 * 87430965
 * 96549874
 * 45678903
 * 32019012
 * 01329801
 * 10456732
 *
 * Considering its trailheads in reading order, they have ratings of 20, 24, 10, 4, 1, 4, 5, 8, and 5. The sum of all
 * trailhead ratings in this larger example topographic map is 81.
 *
 * You're not sure how, but the reindeer seems to have crafted some tiny flags out of toothpicks and bits of paper and
 * is using them to mark trailheads on your topographic map. What is the sum of the ratings of all trailheads?
 * ANSWER: 1686
 */

import templates.Day;
import tools.Convert;
import tools.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Day10 extends Day {

    static final int DAY = 10 ;
    static final boolean DEBUG = false ;
    //static final String FILE_NAME = "Example" ;
    static final String FILE_NAME = "Input" ;

    public Day10(Log log) {
        super(log, DAY, FILE_NAME);
    }

    public static void main(String[] args) {
        Day d = new Day10(new Log());
        d.loadInput();
        d.executePart1();
        d.resetInput();
        d.executePart2();
        d.execTime();
    }

    public void executePart1() {
        super.executePart1();

        // Declare Variables:
        TopographicMap map = new TopographicMap();
        map.print();

        // Process Trails:
        map.processTrails();
        log.write("Trailheads Total Score: " + map.getTrailheadsScore());
    }

    public void executePart2() {
        super.executePart2();

        // Declare Variables:
        TopographicMap map = new TopographicMap();
        map.print();

        // Process Trails:
        map.processTrails();
        log.write("Trailheads Total Rating: " + map.getTrailheadsRating());
    }

    class TopographicMap {
        TopographicCell[][] map;
        ArrayList<Trailhead> trailheads;

        public TopographicMap() {
            trailheads = new ArrayList<>();

            resetInput();

            // Get Matrix Size:
            int columns = input.next().length();
            int lines = 1;
            while (input.hasNext()) {
                lines++;
                input.next();
            }
            map = new TopographicCell[lines][columns];
            if (DEBUG) log.write("Matrix Size: " + lines + " x " + columns);

            // Initiate Map:
            resetInput();
            String line;
            for (int l = 0; l < map.length; l++) {
                line = input.next();
                for (int c = 0; c < map[l].length; c++) {
                    map[l][c] = new TopographicCell(l, c, Convert.toInt(line.charAt(c)));
                    if(map[l][c].getHeight() == 0) trailheads.add(new Trailhead(map[l][c]));
                }
            }

            // Create Map Cell Connections:
            for(int l=0;l<map.length;l++) {
                for(int c=0;c<map[l].length;c++) {
                    map[l][c].populate(map);
                }
            }
        }

        public void print() {
            if(!DEBUG) return;
            String line;
            for(int l=0;l<map.length;l++) {
                line = "";
                for (int c = 0; c < map[l].length; c++) {
                    line += map[l][c].getHeight();
                }
                log.write(line);
            }
            log.write("");
        }

        public void processTrails() {
            Iterator<Trailhead> i = trailheads.iterator();
            Trailhead trailhead;
            while(i.hasNext()) {
                trailhead = i.next();
                trailhead.findTrails();
            }
        }

        public int getTrailheadsScore() {
            int sum = 0;
            Iterator<Trailhead> i = trailheads.iterator();
            while(i.hasNext()) sum += i.next().getScore();
            return sum;
        }

        public int getTrailheadsRating() {
            int sum = 0;
            Iterator<Trailhead> i = trailheads.iterator();
            while(i.hasNext()) sum += i.next().getRating();
            return sum;
        }
    }

    class Trailhead {
        private TopographicCell start;
        private ArrayList<Trail> trails;

        public Trailhead(TopographicCell start) {
            this.start = start;
            trails =  new ArrayList<>();
            trails.add(new Trail(start));
        }

        public void findTrails() {
            if(trails.get(0).finished(9)) return;
            ArrayList<Trail> step = new ArrayList<>();
            while(!trails.isEmpty()) step.add(trails.remove(0));
            Iterator<Trail> i = step.iterator();
            Trail[] nextMoves;
            while(i.hasNext()) {
                nextMoves = i.next().getNextMoves();
                for(Trail trail: nextMoves) if(trail != null) trails.add(trail);
            }
            findTrails();
        }

        public int getScore() {
            HashSet<TopographicCell> peaks = new HashSet<>();
            Iterator<Trail> i = trails.iterator();
            while(i.hasNext()) peaks.add(i.next().getPeak());
            return peaks.size();
        }
        public int getRating() {
            return trails.size();
        }
    }

    class Trail {
        ArrayList<TopographicCell> trail;

        public Trail(TopographicCell start) {
            trail = new ArrayList<>();
            trail.add(start);
        }
        public Trail(Trail otherTrail) {
            trail = (ArrayList<TopographicCell>) otherTrail.getTrail().clone();
        }

        public ArrayList<TopographicCell> getTrail() { return trail; }
        public boolean finished(int destinationHeight) { return trail.get(trail.size()-1).getHeight() ==  destinationHeight; }
        public TopographicCell getPeak() { return trail.get(trail.size()-1); }

        public void add(TopographicCell next) {
            trail.add(next);
        }

        public Trail[] getNextMoves() {
            Trail[] moves = new Trail[4];
            TopographicCell curPos = trail.get(trail.size()-1);
            Trail trail;

            if(curPos.getUp() != null && curPos.getUp().getHeight() - curPos.getHeight() == 1) {
                moves[0] = new Trail(this);
                moves[0].add(curPos.getUp());
            } else moves[0] = null;

            if(curPos.getRight() != null && curPos.getRight().getHeight() - curPos.getHeight() == 1) {
                moves[1] = new Trail(this);
                moves[1].add(curPos.getRight());
            } else moves[1] = null;

            if(curPos.getDown() != null && curPos.getDown().getHeight() - curPos.getHeight() == 1) {
                moves[2] = new Trail(this);
                moves[2].add(curPos.getDown());
            } else moves[2] = null;

            if(curPos.getLeft() != null && curPos.getLeft().getHeight() - curPos.getHeight() == 1) {
                moves[3] = new Trail(this);
                moves[3].add(curPos.getLeft());
            } else moves[3] = null;

            return moves;
        }
    }

    class TopographicCell {
        private int l;
        private int c;
        private int height;
        private TopographicCell up;
        private TopographicCell right;
        private TopographicCell down;
        private TopographicCell left;

        public TopographicCell(int l, int c, int height) {
            this.l = l;
            this.c = c;
            this.height = height;
            up = null;
            right = null;
            down = null;
            left = null;
        }

        public void populate(TopographicCell[][] map) {
            if(l > 0) up = map[l-1][c];
            if(c < map[l].length-1) right = map[l][c+1];
            if(l < map.length-1) down = map[l+1][c];
            if(c > 0) left = map[l][c-1];
        }

        public int getHeight() { return height; }
        public TopographicCell getUp() { return up; }
        public TopographicCell getRight() { return right; }
        public TopographicCell getDown() { return down; }
        public TopographicCell getLeft() { return left; }
    }
}
