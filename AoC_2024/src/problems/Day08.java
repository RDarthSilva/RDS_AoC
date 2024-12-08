package problems;

/*
 * --- Day 8: Resonant Collinearity ---
 * You find yourselves on the roof of a top-secret Easter Bunny installation.
 *
 * While The Historians do their thing, you take a look at the familiar huge antenna. Much to your surprise, it seems
 * to have been reconfigured to emit a signal that makes people 0.1% more likely to buy Easter Bunny brand Imitation
 * Mediocre Chocolate as a Christmas gift! Unthinkable!
 *
 * Scanning across the city, you find that there are actually many such antennas. Each antenna is tuned to a specific
 * frequency indicated by a single lowercase letter, uppercase letter, or digit. You create a map (your puzzle input)
 * of these antennas. For example:
 * ............
 * ........0...
 * .....0......
 * .......0....
 * ....0.......
 * ......A.....
 * ............
 * ............
 * ........A...
 * .........A..
 * ............
 * ............
 *
 * The signal only applies its nefarious effect at specific antinodes based on the resonant frequencies of the antennas.
 * In particular, an antinode occurs at any point that is perfectly in line with two antennas of the same
 * frequency - but only when one of the antennas is twice as far away as the other. This means that for any pair of
 * antennas with the same frequency, there are two antinodes, one on either side of them.
 *
 * So, for these two antennas with frequency a, they create the two antinodes marked with #:
 * ..........
 * ...#......
 * ..........
 * ....a.....
 * ..........
 * .....a....
 * ..........
 * ......#...
 * ..........
 * ..........
 *
 * Adding a third antenna with the same frequency creates several more antinodes. It would ideally add four antinodes,
 * but two are off the right side of the map, so instead it adds only two:
 * ..........
 * ...#......
 * #.........
 * ....a.....
 * ........a.
 * .....a....
 * ..#.......
 * ......#...
 * ..........
 * ..........
 *
 * Antennas with different frequencies don't create antinodes; A and a count as different frequencies. However,
 * antinodes can occur at locations that contain antennas. In this diagram, the lone antenna with frequency capital A
 * creates no antinodes but has a lowercase-a-frequency antinode at its location:
 * ..........
 * ...#......
 * #.........
 * ....a.....
 * ........a.
 * .....a....
 * ..#.......
 * ......A...
 * ..........
 * ..........
 *
 * The first example has antennas with two different frequencies, so the antinodes they create look like this, plus an
 * antinode overlapping the topmost A-frequency antenna:
 * ......#....#
 * ...#....0...
 * ....#0....#.
 * ..#....0....
 * ....0....#..
 * .#....A.....
 * ...#........
 * #......#....
 * ........A...
 * .........A..
 * ..........#.
 * ..........#.
 *
 * Because the topmost A-frequency antenna overlaps with a 0-frequency antinode, there are 14 total unique locations
 * that contain an antinode within the bounds of the map.
 *
 * Calculate the impact of the signal. How many unique locations within the bounds of the map contain an antinode?
 * ANSWER: 367
 *
 *
 *
 * --- Part Two ---
 * Watching over your shoulder as you work, one of The Historians asks if you took the effects of resonant harmonics
 * into your calculations.
 *
 * Whoops!
 *
 * After updating your model, it turns out that an antinode occurs at any grid position exactly in line with at least
 * two antennas of the same frequency, regardless of distance. This means that some of the new antinodes will occur at
 * the position of each antenna (unless that antenna is the only one of its frequency).
 *
 * So, these three T-frequency antennas now create many antinodes:
 * T....#....
 * ...T......
 * .T....#...
 * .........#
 * ..#.......
 * ..........
 * ...#......
 * ..........
 * ....#.....
 * ..........
 *
 * In fact, the three T-frequency antennas are all exactly in line with two antennas, so they are all also antinodes!
 * This brings the total number of antinodes in the above example to 9.
 *
 * The original example now has 34 antinodes, including the antinodes that appear on every antenna:
 * ##....#....#
 * .#.#....0...
 * ..#.#0....#.
 * ..##...0....
 * ....0....#..
 * .#...#A....#
 * ...#..#.....
 * #....#.#....
 * ..#.....A...
 * ....#....A..
 * .#........#.
 * ...#......##
 *
 * Calculate the impact of the signal using this updated model. How many unique locations within the bounds of the map
 * contain an antinode?
 * ANSWER: 1285
 */

import templates.Day;
import tools.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class Day08 extends Day {

    static final int DAY = 8 ;
    static final boolean DEBUG = false ;
    //static final String FILE_NAME = "Example" ;
    static final String FILE_NAME = "Input" ;

    public Day08(Log log) {
        super(log, DAY, FILE_NAME);
    }

    public static void main(String[] args) {
        Day d = new Day08(new Log());
        d.loadInput();
        d.executePart1();
        d.resetInput();
        d.executePart2();
        d.execTime();
    }

    public void executePart1() {
        super.executePart1();

        // Declare Variables:
        CityMap city = new CityMap();

        // Generate Antinodes:
        city.generateAntinotesA();

        // Print City:
        city.print();
        city.printAntinodes();

        log.write("Locations with antinodes: " + city.getAntinodesCount());
    }

    public void executePart2() {
        super.executePart2();

        // Declare Variables:
        CityMap city = new CityMap();

        // Generate Antinodes:
        city.generateAntinotesB();

        // Print City:
        city.print();
        city.printAntinodes();

        log.write("Locations with antinodes: " + city.getAntinodesCount());
    }

    class CityMap {
        private CityCell[][] locations;
        private ArrayList<Antenna> antennas;

        public CityMap() {
            antennas = new ArrayList<>();

            resetInput();

            // Get Matrix Size:
            int columns = input.next().length();
            int lines = 1;
            while (input.hasNext()) {
                lines++;
                input.next();
            }
            locations = new CityCell[lines][columns];
            if (DEBUG) log.write("Matrix Size: " + lines + " x " + columns);

            // Initiate Lab:
            resetInput();
            String line;
            for (int l = 0; l < locations.length; l++) {
                line = input.next();
                for (int c = 0; c < locations[l].length; c++) {
                    locations[l][c] = new CityCell(l,c);
                    if(line.charAt(c) != '.') antennas.add(new Antenna(locations[l][c], line.charAt(c)));
                }
            }

            // Create Antenna Pairs:
            Iterator<Antenna> i = antennas.iterator();
            while(i.hasNext()) {
                i.next().findPairs(antennas);
            }
        }

        public void print() {
            if(!DEBUG) return;
            String line;
            for(int l=0;l<locations.length;l++) {
                line = "";
                for (int c = 0; c < locations[l].length; c++) {
                    line += locations[l][c].getRepresentation();
                }
                log.write(line);
            }
            log.write("");
        }

        public void printAntinodes() {
            if(!DEBUG) return;
            String line;
            for(int l=0;l<locations.length;l++) {
                line = "";
                for (int c = 0; c < locations[l].length; c++) {
                    if(locations[l][c].hasAntinode()) line += '#';
                    else line += '.';
                }
                log.write(line);
            }
            log.write("");
        }

        public void printAntinodesCount() {
            if(!DEBUG) return;
            String line;
            for(int l=0;l<locations.length;l++) {
                line = "";
                for (int c = 0; c < locations[l].length; c++) {
                    line += locations[l][c].getAntinodesCount() + " ";
                }
                log.write(line);
            }
            log.write("");
        }

        public void generateAntinotesA() {
            Iterator<Antenna> i = antennas.iterator();
            while(i.hasNext()) {
                i.next().generateAntinodesA(this);
            }
        }
        public void generateAntinotesB() {
            Iterator<Antenna> i = antennas.iterator();
            while(i.hasNext()) {
                i.next().generateAntinodesB(this);
            }
        }

        public boolean createAntinode(int l, int c, Antenna antenna) {
            if(l < 0 || l >= locations.length || c < 0 || c >= locations[l].length) {
                if(DEBUG) log.write("Antinode in (" + l + "," + c + ") generated out of bounds.");
                return false;
            }
            locations[l][c].createAntinode(antenna);
            return true;
        }
        public void createAntinodeLine(CityCell startLocation, int lVector, int cVector, Antenna antenna) {
            int l = startLocation.getL();
            int c = startLocation.getC();
            do{
                l += lVector;
                c += cVector;
            } while(createAntinode(l,c,antenna));
        }

        public int getAntinodesCount() {
            int count = 0;
            for(int l=0;l<locations.length;l++) {
                for (int c = 0; c < locations[l].length; c++) {
                    if(locations[l][c].hasAntinode()) count++;
                }
            }
            return count;
        }
    }

    class CityCell {
        private int l;
        private int c;
        private Antenna antenna;
        private ArrayList<AntiNode> antiNodes;

        public CityCell(int l, int c) {
            this.l = l;
            this.c = c;
            antenna = null;
            antiNodes = new ArrayList<>();
        }

        public void setAntenna(Antenna antenna) { this.antenna = antenna; }

        public int getL() { return l; }
        public int getC() { return c; }

        public char getRepresentation() {
            if(antenna == null) return '.';
            else return antenna.getFrequency();
        }
        public int getAntinodesCount() {
            return antiNodes.size();
        }
        public boolean hasAntinode() {
            return antiNodes.size() > 0;
        }

        public void createAntinode(Antenna antenna) {
            antiNodes.add(new AntiNode(this, antenna));
        }
    }

    class Antenna {
        private CityCell location;
        private char frequency;
        private ArrayList<Antenna> pairs;

        public Antenna(CityCell location, char frequency) {
            this.location = location;
            this.frequency = frequency;
            this.pairs = new ArrayList<>();
            location.setAntenna(this);
        }

        public CityCell getLocation() { return location; }
        public char getFrequency() { return frequency; }

        public void findPairs(ArrayList<Antenna> antennas) {
            Iterator<Antenna> i = antennas.iterator();
            Antenna otherAntenna;
            while(i.hasNext()) {
                otherAntenna = i.next();
                if(otherAntenna == this) continue;
                if(otherAntenna.getFrequency() == this.frequency) pairs.add(otherAntenna);
            }
        }

        public void generateAntinodesA(CityMap city) {
            Iterator<Antenna> i = pairs.iterator();
            Antenna otherAntenna;
            int antinodeL, antinodeC;
            while(i.hasNext()) {
                otherAntenna = i.next();
                if(otherAntenna.getLocation().getL() == location.getL()) antinodeL = location.getL();
                else if(otherAntenna.getLocation().getL() < location.getL()) antinodeL = location.getL()
                                                                + (location.getL() - otherAntenna.getLocation().getL());
                else antinodeL = location.getL() - (otherAntenna.getLocation().getL() - location.getL());
                if(otherAntenna.getLocation().getC() == location.getC()) antinodeC = location.getC();
                else if(otherAntenna.getLocation().getC() < location.getC()) antinodeC = location.getC()
                        + (location.getC() - otherAntenna.getLocation().getC());
                else antinodeC = location.getC() - (otherAntenna.getLocation().getC() - location.getC());
                city.createAntinode(antinodeL, antinodeC, this);
            }
        }
        public void generateAntinodesB(CityMap city) {
            Iterator<Antenna> i = pairs.iterator();
            Antenna otherAntenna;
            int antinodeLVector, antinodeCVector;
            while(i.hasNext()) {
                otherAntenna = i.next();
                antinodeLVector = location.getL() - otherAntenna.getLocation().getL();
                antinodeCVector = location.getC() - otherAntenna.getLocation().getC();
                city.createAntinodeLine(location, antinodeLVector, antinodeCVector, this);
                city.createAntinodeLine(location, (-1)*antinodeLVector, (-1)*antinodeCVector, this);
            }
        }
    }

    class AntiNode {
        private CityCell location;
        private Antenna antenna;

        public AntiNode(CityCell location, Antenna antenna) {
            this.location = location;
            this.antenna = antenna;
        }
    }
}
