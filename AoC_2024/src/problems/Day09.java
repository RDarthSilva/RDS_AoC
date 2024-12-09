package problems;

/*
 * --- Day 9: Disk Fragmenter ---
 * Another push of the button leaves you in the familiar hallways of some friendly amphipods! Good thing you each
 * somehow got your own personal mini submarine. The Historians jet away in search of the Chief, mostly by driving
 * directly into walls.
 *
 * While The Historians quickly figure out how to pilot these things, you notice an amphipod in the corner struggling
 * with his computer. He's trying to make more contiguous free space by compacting all of the files, but his program
 * isn't working; you offer to help.
 *
 * He shows you the disk map (your puzzle input) he's already generated. For example:
 * 2333133121414131402
 *
 * The disk map uses a dense format to represent the layout of files and free space on the disk. The digits alternate
 * between indicating the length of a file and the length of free space.
 *
 * So, a disk map like 12345 would represent a one-block file, two blocks of free space, a three-block file, four
 * blocks of free space, and then a five-block file. A disk map like 90909 would represent three nine-block files in a
 * row (with no free space between them).
 *
 * Each file on disk also has an ID number based on the order of the files as they appear before they are rearranged,
 * starting with ID 0. So, the disk map 12345 has three files: a one-block file with ID 0, a three-block file with ID 1,
 * and a five-block file with ID 2. Using one character for each block where digits are the file ID and . is free space,
 * the disk map 12345 represents these individual blocks:
 * 0..111....22222
 *
 * The first example above, 2333133121414131402, represents these individual blocks:
 * 00...111...2...333.44.5555.6666.777.888899
 *
 * The amphipod would like to move file blocks one at a time from the end of the disk to the leftmost free space block
 * (until there are no gaps remaining between file blocks). For the disk map 12345, the process looks like this:
 * 0..111....22222
 * 02.111....2222.
 * 022111....222..
 * 0221112...22...
 * 02211122..2....
 * 022111222......
 *
 * The first example requires a few more steps:
 * 00...111...2...333.44.5555.6666.777.888899
 * 009..111...2...333.44.5555.6666.777.88889.
 * 0099.111...2...333.44.5555.6666.777.8888..
 * 00998111...2...333.44.5555.6666.777.888...
 * 009981118..2...333.44.5555.6666.777.88....
 * 0099811188.2...333.44.5555.6666.777.8.....
 * 009981118882...333.44.5555.6666.777.......
 * 0099811188827..333.44.5555.6666.77........
 * 00998111888277.333.44.5555.6666.7.........
 * 009981118882777333.44.5555.6666...........
 * 009981118882777333644.5555.666............
 * 00998111888277733364465555.66.............
 * 0099811188827773336446555566..............
 *
 * The final step of this file-compacting process is to update the filesystem checksum. To calculate the checksum, add
 * up the result of multiplying each of these blocks' position with the file ID number it contains. The leftmost block
 * is in position 0. If a block contains free space, skip it instead.
 *
 * Continuing the first example, the first few blocks' position multiplied by its file ID number are 0 * 0 = 0,
 * 1 * 0 = 0, 2 * 9 = 18, 3 * 9 = 27, 4 * 8 = 32, and so on. In this example, the checksum is the sum of these, 1928.
 *
 * Compact the amphipod's hard drive using the process he requested. What is the resulting filesystem checksum?
 * (Be careful copy/pasting the input for this puzzle; it is a single, very long line.)
 * ANSWER: 6262891638328
 *
 *
 *
 * --- Part Two ---
 * Upon completion, two things immediately become clear. First, the disk definitely has a lot more contiguous free
 * space, just like the amphipod hoped. Second, the computer is running much more slowly! Maybe introducing all of that
 * file system fragmentation was a bad idea?
 *
 * The eager amphipod already has a new plan: rather than move individual blocks, he'd like to try compacting the files
 * on his disk by moving whole files instead.
 *
 * This time, attempt to move whole files to the leftmost span of free space blocks that could fit the file. Attempt to
 * move each file exactly once in order of decreasing file ID number starting with the file with the highest file
 * ID number. If there is no span of free space to the left of a file that is large enough to fit the file, the file
 * does not move.
 *
 * The first example from above now proceeds differently:
 * 00...111...2...333.44.5555.6666.777.888899
 * 0099.111...2...333.44.5555.6666.777.8888..
 * 0099.1117772...333.44.5555.6666.....8888..
 * 0099.111777244.333....5555.6666.....8888..
 * 00992111777.44.333....5555.6666.....8888..
 *
 * The process of updating the filesystem checksum is the same; now, this example's checksum would be 2858.
 *
 * Start over, now compacting the amphipod's hard drive using this new method instead. What is the resulting filesystem
 * checksum?
 * ANSWER: 6287317016845
 */

import templates.Day;
import tools.Convert;
import tools.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

public class Day09 extends Day {

    static final int DAY = 9 ;
    static final boolean DEBUG = false ;
    //static final String FILE_NAME = "Example" ;
    static final String FILE_NAME = "Input" ;

    public Day09(Log log) {
        super(log, DAY, FILE_NAME);
    }

    public static void main(String[] args) {
        Day d = new Day09(new Log());
        d.loadInput();
        d.executePart1();
        d.resetInput();
        d.executePart2();
        d.execTime();
    }

    public void executePart1() {
        super.executePart1();

        // Declare Variables:
        HardDrive disk = new HardDrive();

        // Process Disk Map
        char[] diskMap = input.next().toCharArray();
        int size;
        int fileId = 0;
        boolean isFile = true;
        File file;
        for(char c: diskMap) {
            file = null;
            size = Convert.toInt(c);
            if(isFile) {
                file = new File(fileId++, size);
                disk.addFile(file);
            }
            while(size-- > 0) disk.addBlock(file);
            isFile = !isFile;
        }

        // Print Disk Blocks:
        if(DEBUG) log.write(disk.print());

        // Compact Disk:
        disk.compactA();

        // Calculate Checksum:
        log.write("Disk Checksum: " + disk.getCheckSum());
    }

    public void executePart2() {
        super.executePart2();

        // Declare Variables:
        HardDrive disk = new HardDrive();

        // Process Disk Map
        char[] diskMap = input.next().toCharArray();
        int size;
        int fileId = 0;
        boolean isFile = true;
        File file;
        for(char c: diskMap) {
            file = null;
            size = Convert.toInt(c);
            if(isFile) {
                file = new File(fileId++, size);
                disk.addFile(file);
            }
            while(size-- > 0) disk.addBlock(file);
            isFile = !isFile;
        }

        // Print Disk Blocks:
        if(DEBUG) log.write(disk.print());

        // Compact Disk:
        disk.compactB();

        // Calculate Checksum:
        log.write("Disk Checksum: " + disk.getCheckSum());
    }

    class HardDrive {
        ArrayList<DiskBlock> blocks;
        ArrayList<File> files;

        public HardDrive() {
            blocks = new ArrayList<>();
            files = new ArrayList<>();
        }

        public void addBlock(File file) {
            int index = blocks.size();
            blocks.add(new DiskBlock(index));
            if(file != null) {
                blocks.get(index).setFile(file);
                file.addBlock(blocks.get(index));
            }
        }

        public void addFile(File file) {
            files.add(file);
        }
        public ArrayList<File> getFilesInReverse() {
            ArrayList<File> reversedFiles = new ArrayList<>();
            ArrayList<File> tempFiles = (ArrayList<File>) files.clone();
            while(!tempFiles.isEmpty()) reversedFiles.add(tempFiles.remove(tempFiles.size()-1));
            return reversedFiles;
        }

        public String print() {
            String s = "";
            Iterator<DiskBlock> i = blocks.iterator();
            while(i.hasNext()) s += i.next().getBlockChar();
            return s;
        }

        public void compactA() {
            int iWrite = getNextWriteIndex(-1);
            int iRead = getNextReadIndex(blocks.size());
            File file;
            while(iWrite > 0 && iRead > 0 && iWrite < iRead) {
                file = blocks.get(iRead).read();
                blocks.get(iWrite).write(file);
                iWrite = getNextWriteIndex(iWrite);
                iRead = getNextReadIndex(iRead);
                if(DEBUG) log.write(print());
            }
        }
        public void compactB() {
            ArrayList<File> reversedFiles = getFilesInReverse();
            File file;
            while(!reversedFiles.isEmpty()) {
                file = reversedFiles.remove(0);
                int iWrite = getNextWriteIndex(-1);
                int writeBlockSize = getWriteSize(iWrite);
                while(iWrite > 0 && iWrite <= file.getFirstBlock().getIndex()) {
                    if(writeBlockSize >= file.getSize()) {
                        file.read();
                        for(int index=iWrite; index<iWrite+file.getSize(); index++) {
                            blocks.get(index).write(file);
                        }
                        break;
                    } else {
                        iWrite = getNextWriteIndex(iWrite);
                        writeBlockSize = getWriteSize(iWrite);
                    }
                }
                if(DEBUG) log.write(print());
            }
        }
        private int getNextWriteIndex(int index) {
            index++;
            while(index < blocks.size()) {
                if (blocks.get(index).isEmpty()) return index;
                index++;
            }
            return -1;
        }
        private int getNextReadIndex(int index) {
            index--;
            while(index >= 0) {
                if (!blocks.get(index).isEmpty()) return index;
                index--;
            }
            return -2;
        }
        private int getWriteSize(int index) {
            if(index < 0) return -1;
            int size = 0;
            while(index < blocks.size() && blocks.get(index).isEmpty()) {
                size++;
                index++;
            }
            return size;
        }

        public String getCheckSum() {
            BigInteger checksum = new BigInteger("0");
            BigInteger multi;
            int index = 0;
            while(index < blocks.size()) {
                if(!blocks.get(index).isEmpty()) {
                    multi = new BigInteger(Convert.toString(blocks.get(index).getFile().getId()));
                    multi = multi.multiply(new BigInteger(Convert.toString(index)));
                    checksum = checksum.add(multi);
                }
                index++;
            }
            return checksum.toString();
        }
    }

    class DiskBlock {
        private int index;
        private File file;

        public DiskBlock(int index) {
            this.index = index;
            file = null;
        }

        public void setFile(File file) { this.file = file; }

        public char getBlockChar() {
            if(file == null) return '.';
            return Convert.toString(file.getId()).charAt(0);
        }

        public int getIndex() { return index; }
        public boolean isEmpty() { return file == null; }
        public File getFile() { return file; }

        public File read() {
            if(file == null) log.error("Attempting to move empty block!");
            File out = file;
            file = null;
            return out;
        }
        public void write(File file) {
            if(this.file != null) log.error("File block rewritten!");
            this.file = file;
            file.addBlock(this);
        }
    }

    class File {
        private int id;
        private int size;
        private ArrayList<DiskBlock> blocks;

        public File(int id, int size) {
            this.id = id;
            this.size = size;
            blocks = new ArrayList<>();
        }

        public int getId() { return id; }
        public int getSize() { return size; }

        public void addBlock(DiskBlock block) {
            blocks.add(block);
        }

        public DiskBlock getFirstBlock() { return blocks.get(0); }

        public void read() {
            Iterator<DiskBlock> i = blocks.iterator();
            while(i.hasNext()) i.next().read();
        }
    }
}
