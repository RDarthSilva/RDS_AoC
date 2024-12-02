package templates;

import tools.Convert;
import tools.InputFile;
import tools.Log;

import java.util.Iterator;
import java.util.LinkedList;

public class Day {
    static final String INPUT_FOLDER = "D:/Workspace/GitHub/RDS_AoC/AoC_2024/src/input/" ;
    protected Log log ;
    protected int day ;
    protected String inputFile ;
    private LinkedList<String> inputList ;
    protected Iterator<String> input ;

    public Day(Log log, int day, String inputFile) {
        this.log = log ;
        this.day = day;
        this.inputFile =  INPUT_FOLDER + "Day" + Convert.toString(day,2) + "_" + inputFile ;
        inputList = new LinkedList<>() ;
        log.write("--- Advent of Code 2024 ---") ;
        log.write("--------- Day " + Convert.toString(day,2) + " ----------") ;
    }

    public void loadInput() {
        if(inputFile == null) {
            log.error("Unabe to load input, file has not been initialized!") ;
            return ;
        }
        InputFile input = new InputFile(inputFile) ;
        String line = input.getLine() ;
        while(line != null) {
            inputList.add(line) ;
            line = input.getLine() ;
        }
        input.close() ;
        resetInput() ;
    }

    public void resetInput() { input = inputList.iterator() ; }

    public void executePart1() {
        log.write("--------- Part  1 ---------") ;
    }

    public void executePart2() {
        log.write("--------- Part  2 ---------") ;
    }

    public void execTime() {
        log.writeExecTime("Total") ;
    }

}
