package problems;

/*
 * --- Day 6: Guard Gallivant ---
 * The Historians use their fancy device again, this time to whisk you all away to the North Pole prototype suit
 * manufacturing lab... in the year 1518! It turns out that having direct access to history is very convenient for a
 * group of historians.
 *
 * You still have to be careful of time paradoxes, and so it will be important to avoid anyone from 1518 while The
 * Historians search for the Chief. Unfortunately, a single guard is patrolling this part of the lab.
 *
 * Maybe you can work out where the guard will go ahead of time so that The Historians can search safely?
 *
 * You start by making a map (your puzzle input) of the situation. For example:
 * ....#.....
 * .........#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#..^.....
 * ........#.
 * #.........
 * ......#...
 *
 * The map shows the current position of the guard with ^ (to indicate the guard is currently facing up from the
 * perspective of the map). Any obstructions - crates, desks, alchemical reactors, etc. - are shown as #.
 *
 * Lab guards in 1518 follow a very strict patrol protocol which involves repeatedly following these steps:
 * - If there is something directly in front of you, turn right 90 degrees.
 * - Otherwise, take a step forward.
 *
 * Following the above protocol, the guard moves up several times until she reaches an obstacle (in this case, a pile
 * of failed suit prototypes):
 * ....#.....
 * ....^....#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#........
 * ........#.
 * #.........
 * ......#...
 *
 * Because there is now an obstacle in front of the guard, she turns right before continuing straight in her new facing
 * direction:
 * ....#.....
 * ........>#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#........
 * ........#.
 * #.........
 * ......#...
 *
 * Reaching another obstacle (a spool of several very long polymers), she turns right again and continues downward:
 * ....#.....
 * .........#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#......v.
 * ........#.
 * #.........
 * ......#...
 *
 * This process continues for a while, but the guard eventually leaves the mapped area (after walking past a tank of
 * universal solvent):
 * ....#.....
 * .........#
 * ..........
 * ..#.......
 * .......#..
 * ..........
 * .#........
 * ........#.
 * #.........
 * ......#v..
 *
 * By predicting the guard's route, you can determine which specific positions in the lab will be in the patrol path.
 * Including the guard's starting position, the positions visited by the guard before leaving the area are marked with
 * an X:
 * ....#.....
 * ....XXXXX#
 * ....X...X.
 * ..#.X...X.
 * ..XXXXX#X.
 * ..X.X.X.X.
 * .#XXXXXXX.
 * .XXXXXXX#.
 * #XXXXXXX..
 * ......#X..
 *
 * In this example, the guard will visit 41 distinct positions on your map.
 *
 * Predict the path of the guard. How many distinct positions will the guard visit before leaving the mapped area?
 * ANSWER: 4789
 *
 *
 *
 * --- Part Two ---
 * While The Historians begin working around the guard's patrol route, you borrow their fancy device and step outside
 * the lab. From the safety of a supply closet, you time travel through the last few months and record the nightly
 * status of the lab's guard post on the walls of the closet.
 *
 * Returning after what seems like only a few seconds to The Historians, they explain that the guard's patrol area is
 * simply too large for them to safely search the lab without getting caught.
 *
 * Fortunately, they are pretty sure that adding a single new obstruction won't cause a time paradox. They'd like to
 * place the new obstruction in such a way that the guard will get stuck in a loop, making the rest of the lab safe to
 * search.
 *
 * To have the lowest chance of creating a time paradox, The Historians would like to know all of the possible positions
 * for such an obstruction. The new obstruction can't be placed at the guard's starting position - the guard is there
 * right now and would notice.
 *
 * In the above example, there are only 6 different positions where a new obstruction would cause the guard to get stuck
 * in a loop. The diagrams of these six situations use O to mark the new obstruction, | to show a position where the
 * guard moves up/down, - to show a position where the guard moves left/right, and + to show a position where the guard
 * moves both up/down and left/right.
 *
 * Option one, put a printing press next to the guard's starting position:
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ....|..#|.
 * ....|...|.
 * .#.O^---+.
 * ........#.
 * #.........
 * ......#...
 *
 * Option two, put a stack of failed suit prototypes in the bottom right quadrant of the mapped area:
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * ......O.#.
 * #.........
 * ......#...
 *
 * Option three, put a crate of chimney-squeeze prototype fabric next to the standing desk in the bottom right quadrant:
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * .+----+O#.
 * #+----+...
 * ......#...
 *
 * Option four, put an alchemical retroencabulator near the bottom left corner:
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * ..|...|.#.
 * #O+---+...
 * ......#...
 *
 * Option five, put the alchemical retroencabulator a bit to the right instead:
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * ....|.|.#.
 * #..O+-+...
 * ......#...
 *
 * Option six, put a tank of sovereign glue right next to the tank of universal solvent:
 * ....#.....
 * ....+---+#
 * ....|...|.
 * ..#.|...|.
 * ..+-+-+#|.
 * ..|.|.|.|.
 * .#+-^-+-+.
 * .+----++#.
 * #+----++..
 * ......#O..
 *
 * It doesn't really matter what you choose to use as an obstacle so long as you and The Historians can put it into
 * position without the guard noticing. The important thing is having enough options that you can find one that
 * minimizes time paradoxes, and in this example, there are 6 different positions you could choose.
 *
 * You need to get the guard stuck in a loop by adding a single new obstruction. How many different positions could you
 * choose for this obstruction?
 * ANSWER: 1304
 */

import templates.Day;
import tools.Log;

import java.util.HashSet;

public class Day06 extends Day {

    static final int DAY = 6 ;
    static final boolean DEBUG = false ;
    //static final String FILE_NAME = "Example" ;
    static final String FILE_NAME = "Input" ;

    public Day06(Log log) {
        super(log, DAY, FILE_NAME);
    }

    public static void main(String[] args) {
        Day d = new Day06(new Log());
        d.loadInput();
        d.executePart1();
        d.resetInput();
        d.executePart2();
        d.execTime();
    }

    public void executePart1() {
        super.executePart1();

        // Declare Variables:
        Lab lab = new Lab();
        lab.print();

        // Simulate Walk:
        boolean done = false;
        do {
            try {
                done = lab.moveGuard();
            } catch(LoopDetectedException e) {
                log.error(e.getMessage());
                done = true;
            }
            lab.printWithPath();
        } while(!done);
        lab.printWithPath();

        log.write("Positions visited: " + lab.getPositionsVisited());
    }

    public void executePart2() {
        super.executePart2();

        // Declare Variables:
        Lab lab = new Lab();

        // Generate Alternative Lab and Simulate Walk:
        Lab altLab;
        int loops = 0;
        for(int l=0;l<lab.lengthL();l++) {
            for(int c=0;c<lab.lengthC();c++) {
                altLab = new Lab(lab);
                if(altLab.createObstruction(l,c)) {
                    altLab.print();
                    boolean done = false;
                    do {
                        try {
                            done = altLab.moveGuard();
                        } catch (LoopDetectedException e) {
                            loops++;
                            done = true;
                        }
                    } while (!done);
                }
            }
        }

        log.write("Loops detected: " + loops);
    }

    class Lab {
        LabFloorCell[][] lab;
        LabGuard guard;

        public Lab() {
            resetInput();

            // Get Matrix Size:
            int columns = input.next().length();
            int lines = 1;
            while(input.hasNext()) {
                lines++;
                input.next();
            }
            lab = new LabFloorCell[lines][columns];
            if(DEBUG) log.write("Matrix Size: " + lines + " x " + columns);

            // Initiate Lab:
            resetInput();
            String line;
            for(int l=0;l<lab.length;l++) {
                line = input.next();
                for (int c = 0; c < lab[l].length; c++) {
                    lab[l][c] = new LabFloorCell(l,c,line.charAt(c));
                    if(lab[l][c].getType() == '^') guard = new LabGuard(lab[l][c]);
                }
            }

            // Create Lab Cell Connections:
            for(int l=0;l<lab.length;l++) {
                for(int c=0;c<lab[l].length;c++) {
                    lab[l][c].populate(lab);
                }
            }
        }

        public Lab(Lab otherLab) {
            lab = new LabFloorCell[otherLab.lengthL()][otherLab.lengthC()];

            // Initiate Lab:
            for(int l=0;l<lab.length;l++) {
                for (int c = 0; c < lab[l].length; c++) {
                    lab[l][c] = new LabFloorCell(otherLab.getPosition(l,c));
                }
            }
            guard = new LabGuard(lab[otherLab.getGuardPosition().getL()][otherLab.getGuardPosition().getC()]);

            // Create Lab Cell Connections:
            for(int l=0;l<lab.length;l++) {
                for(int c=0;c<lab[l].length;c++) {
                    lab[l][c].populate(lab);
                }
            }
        }

        public void print() {
            if(!DEBUG) return;
            String line;
            for(int l=0;l<lab.length;l++) {
                line = "";
                for (int c = 0; c < lab[l].length; c++) {
                    if(guard.getLocation() == lab[l][c]) line += "G";
                    else line += lab[l][c].getType();
                }
                log.write(line);
            }
            log.write("Guard at " + guard.getLocation().getCoords());
            log.write("");
        }
        public void printWithPath() {
            if(!DEBUG) return;
            String line;
            for(int l=0;l<lab.length;l++) {
                line = "";
                for (int c = 0; c < lab[l].length; c++) {
                    if(guard.getLocation() == lab[l][c]) line += "G";
                    else if(lab[l][c].hasBeenVisited()) line += "X";
                    else line += lab[l][c].getType();
                }
                log.write(line);
            }
            log.write("Guard at " + guard.getLocation().getCoords());
            log.write("");
        }

        public LabFloorCell getGuardPosition() { return guard.getLocation(); }
        public boolean moveGuard() throws LoopDetectedException {
            return guard.move();
        }

        public int getPositionsVisited() {
            int visited = 0;
            for(int l=0;l<lab.length;l++) {
                for (int c = 0; c < lab[l].length; c++) {
                    if(lab[l][c].hasBeenVisited()) visited++;
                }
            }
            return visited;
        }

        public int lengthL() { return lab.length; }
        public int lengthC() { return lab[0].length; }
        public LabFloorCell getPosition(int l, int c) { return lab[l][c]; }

        public boolean createObstruction(int l, int c) {
            if(guard.getLocation() == lab[l][c]) return false;
            return lab[l][c].createObstruction();
        }
    }

    /*
     * Directions:
     * - 0: Up
     * - 1: Right
     * - 2: Down
     * - 3: Left
     */
    class LabGuard {
        private LabFloorCell location;
        private int direction;
        private HashSet<String> route;

        public LabGuard(LabFloorCell location) {
            this.location = location;
            direction = 0;
            location.guardInit();
            route = new HashSet<>();
        }

        public LabFloorCell getLocation() { return location; }

        public boolean move() throws LoopDetectedException {
            LabFloorCell nextLocation = null;
            switch (direction) {
                case 0: nextLocation = location.getUp(); break;
                case 1: nextLocation = location.getRight(); break;
                case 2: nextLocation = location.getDown(); break;
                case 3: nextLocation = location.getLeft(); break;
                default: break;
            }
            if(nextLocation == null) {
                return true;
            }
            if(nextLocation.getType() == '#') {
                direction++;
                if(direction == 4) direction = 0;
                addLocationToRoute();
            } else {
                location.guardOut();
                location = nextLocation;
                location.guardIn();
                addLocationToRoute();
            }
            return false;
        }

        public void addLocationToRoute() throws LoopDetectedException{
            if(route.contains(location.getCoords() + direction)) throw new LoopDetectedException("Loop Detected!");
            route.add(location.getCoords() + direction);
        }
    }

    class LoopDetectedException extends Exception {
        public LoopDetectedException(String message) { super(message); }
    }

    class LabFloorCell {
        private int l;
        private int c;
        private char type;
        private boolean visited;
        private boolean hasGuard;
        private LabFloorCell up;
        private LabFloorCell right;
        private LabFloorCell down;
        private LabFloorCell left;

        public LabFloorCell(int l, int c, char type) {
            this.l = l;
            this.c = c;
            this.type = type;
            visited = false;
            hasGuard = false;
            up = null;
            right = null;
            down = null;
            left = null;
        }

        public LabFloorCell(LabFloorCell otherCell) {
            l = otherCell.getL();
            c = otherCell.getC();
            type = otherCell.getType();
            visited = false;
            hasGuard = false;
            up = null;
            right = null;
            down = null;
            left = null;
        }

        public char getType() { return type; }
        public String getCoords() {
            return "(" + l + "," + c + ")";
        }
        public int getL() { return l; }
        public int getC() { return c; }
        public boolean hasBeenVisited() { return visited; }

        public void guardInit() {
            type = '.';
            guardIn();
        }
        public void guardIn() {
            visited = true;
            hasGuard = true;
        }
        public void guardOut() {
            hasGuard = false;
        }

        public void populate(LabFloorCell[][] lab) {
            if(l > 0) up = lab[l-1][c];
            if(c < lab[l].length-1) right = lab[l][c+1];
            if(l < lab.length-1) down = lab[l+1][c];
            if(c > 0) left = lab[l][c-1];
        }
        public LabFloorCell getUp() { return up; }
        public LabFloorCell getRight() { return right; }
        public LabFloorCell getDown() { return down; }
        public LabFloorCell getLeft() { return left; }

        public boolean createObstruction() {
            if(type == '#') return false;
            type = '#';
            return true;
        }
    }
}
