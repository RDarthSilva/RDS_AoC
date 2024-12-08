package problems;

/*
 * --- Day 7: Bridge Repair ---
 * The Historians take you to a familiar rope bridge over a river in the middle of a jungle. The Chief isn't on this
 * side of the bridge, though; maybe he's on the other side?
 *
 * When you go to cross the bridge, you notice a group of engineers trying to repair it. (Apparently, it breaks pretty
 * frequently.) You won't be able to cross until it's fixed.
 *
 * You ask how long it'll take; the engineers tell you that it only needs final calibrations, but some young elephants
 * were playing nearby and stole all the operators from their calibration equations! They could finish the calibrations
 * if only someone could determine which test values could possibly be produced by placing any combination of operators
 * into their calibration equations (your puzzle input).
 *
 * For example:
 * 190: 10 19
 * 3267: 81 40 27
 * 83: 17 5
 * 156: 15 6
 * 7290: 6 8 6 15
 * 161011: 16 10 13
 * 192: 17 8 14
 * 21037: 9 7 18 13
 * 292: 11 6 16 20
 *
 * Each line represents a single equation. The test value appears before the colon on each line; it is your job to
 * determine whether the remaining numbers can be combined with operators to produce the test value.
 *
 * Operators are always evaluated left-to-right, not according to precedence rules. Furthermore, numbers in the
 * equations cannot be rearranged. Glancing into the jungle, you can see elephants holding two different types of
 * operators: add (+) and multiply (*).
 *
 * Only three of the above equations can be made true by inserting operators:
 * - 190: 10 19 has only one position that accepts an operator: between 10 and 19. Choosing + would give 29, but
 * choosing * would give the test value (10 * 19 = 190).
 * - 3267: 81 40 27 has two positions for operators. Of the four possible configurations of the operators, two cause
 * the right side to match the test value: 81 + 40 * 27 and 81 * 40 + 27 both equal 3267 (when evaluated left-to-right)!
 * - 292: 11 6 16 20 can be solved in exactly one way: 11 + 6 * 16 + 20.
 *
 * The engineers just need the total calibration result, which is the sum of the test values from just the equations
 * that could possibly be true. In the above example, the sum of the test values for the three equations listed above
 * is 3749.
 *
 * Determine which equations could possibly be true. What is their total calibration result?
 * ANSWER: 4364915411363
 *
 *
 *
 * --- Part Two ---
 * The engineers seem concerned; the total calibration result you gave them is nowhere close to being within safety
 * tolerances. Just then, you spot your mistake: some well-hidden elephants are holding a third type of operator.
 *
 * The concatenation operator (||) combines the digits from its left and right inputs into a single number.
 * For example, 12 || 345 would become 12345. All operators are still evaluated left-to-right.
 *
 * Now, apart from the three equations that could be made true using only addition and multiplication, the above example
 * has three more equations that can be made true by inserting operators:
 * - 156: 15 6 can be made true through a single concatenation: 15 || 6 = 156.
 * - 7290: 6 8 6 15 can be made true using 6 * 8 || 6 * 15.
 * - 192: 17 8 14 can be made true using 17 || 8 + 14.
 *
 * Adding up all six test values (the three that could be made before using only + and * plus the new three that can
 * now be made by also using ||) produces the new total calibration result of 11387.
 *
 * Using your new knowledge of elephant hiding spots, determine which equations could possibly be true. What is their
 * total calibration result?
 * ANSWER: 38322057216320
 */

import templates.Day;
import tools.Convert;
import tools.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Day07 extends Day {

    static final int DAY = 7 ;
    static final boolean DEBUG = false ;
    //static final String FILE_NAME = "Example" ;
    static final String FILE_NAME = "Input" ;

    public Day07(Log log) {
        super(log, DAY, FILE_NAME);
    }

    public static void main(String[] args) {
        Day d = new Day07(new Log());
        d.loadInput();
        d.executePart1();
        d.resetInput();
        d.executePart2();
        d.execTime();
    }

    public void executePart1() {
        super.executePart1();

        // Declare Variables:
        ArrayList<Equation> equations = new ArrayList<>();

        // Load Equations:
        while(input.hasNext()) {
            equations.add(new Equation(input.next()));
        }

        // Get Valid Equations:
        Iterator<Equation> i = equations.iterator();
        Equation equation;
        BigInteger sum = new BigInteger("0");
        while(i.hasNext()) {
            equation = i.next();
            if(DEBUG) log.write(equation.toString());
            if(equation.isValid(false)) sum = sum.add(equation.getTestValue());
        }
        log.write("Sum of valid calibrations: " + sum);
    }

    public void executePart2() {
        super.executePart2();

        // Declare Variables:
        ArrayList<Equation> equations = new ArrayList<>();

        // Load Equations:
        while(input.hasNext()) {
            equations.add(new Equation(input.next()));
        }

        // Get Valid Equations:
        Iterator<Equation> i = equations.iterator();
        Equation equation;
        BigInteger sum = new BigInteger("0");
        while(i.hasNext()) {
            equation = i.next();
            if(DEBUG) log.write(equation.toString());
            if(equation.isValid(true)) sum = sum.add(equation.getTestValue());
        }
        log.write("Sum of valid calibrations: " + sum);
    }

    class Equation {
        private BigInteger testValue;
        private ArrayList<BigInteger> equation;

        public Equation(String line) {
            testValue = new BigInteger(line.substring(0,line.indexOf(':')));
            equation = new ArrayList<>();
            StringTokenizer t = new StringTokenizer(line.substring(line.indexOf(':')+2));
            while(t.hasMoreTokens()) equation.add(new BigInteger(t.nextToken()));
        }

        public BigInteger getTestValue() { return testValue; }

        public String toString() {
            String s = "";
            s += testValue + ":";
            Iterator<BigInteger> i = equation.iterator();
            while(i.hasNext()) s += " " + i.next().toString() + ",";
            return s.substring(0,s.length()-1);
        }

        public boolean isValid(boolean useConcat) {
            ArrayList<EquationExecution> executions = new ArrayList<>();
            EquationExecution executionSum, executionMultiply, executionConcat;
            ArrayList<BigInteger> nextEquation = (ArrayList<BigInteger>) equation.clone();
            nextEquation.remove(0);
            executionSum = new EquationExecution(equation.get(0), nextEquation);
            executions.add(executionSum);
            while(executions.size() > 0) {
                executionSum = executions.remove(0);
                executionMultiply = executionSum.clone();
                executionConcat = executionSum.clone();

                executionSum = executionSum.executeSum();
                if(executionSum.complete()) {
                    if(executionSum.isValid(testValue)) return true;
                } else executions.add(executionSum);

                executionMultiply = executionMultiply.executeMultiply();
                if(executionMultiply.complete()) {
                    if(executionMultiply.isValid(testValue)) return true;
                } else executions.add(executionMultiply);

                if(useConcat) {
                    executionConcat = executionConcat.executeConcat();
                    if(executionConcat.complete()) {
                        if(executionConcat.isValid(testValue)) return true;
                    } else executions.add(executionConcat);
                }
            }
            return false;
        }
    }

    class EquationExecution {
        private BigInteger currentValue;
        private ArrayList<BigInteger> equationNumbers;

        public EquationExecution(BigInteger currentValue, ArrayList<BigInteger> equationNumbers) {
            this.currentValue = currentValue;
            this.equationNumbers = equationNumbers;
        }

        public boolean complete() { return equationNumbers.isEmpty(); }
        public boolean isValid(BigInteger testValue) { return currentValue.compareTo(testValue) == 0; }

        public EquationExecution executeSum() {
            currentValue = currentValue.add(equationNumbers.remove(0));
            return new EquationExecution(currentValue, equationNumbers);
        }
        public EquationExecution executeMultiply() {
            currentValue = currentValue.multiply(equationNumbers.remove(0));
            return new EquationExecution(currentValue, equationNumbers);
        }
        public EquationExecution executeConcat() {
            currentValue = new BigInteger(currentValue.toString() +  equationNumbers.remove(0).toString());
            return new EquationExecution(currentValue, equationNumbers);
        }

        public EquationExecution clone() {
            return new EquationExecution(currentValue, (ArrayList<BigInteger>) equationNumbers.clone());
        }
    }
}
