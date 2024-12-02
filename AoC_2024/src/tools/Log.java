package tools;

import java.text.DecimalFormat;

public class Log {

    private long startTime ;

    public Log() {
        startTime = System.currentTimeMillis() ;
    }

    public void write(String message) { System.out.println(message) ; }
    public void error(String message) { System.err.println(message) ; }
    public void writeExecTime(String object) {
        long execTime = System.currentTimeMillis() - startTime ;
        DecimalFormat milis = new DecimalFormat("### ###") ;
        String time ;
        if(execTime < 1000) time = milis.format(execTime) + "ms" ;
        else {
            if(execTime < 60000) time = (execTime / 1000) + " seconds" ;
            else time = "Too long!" ;
            time += " (" + milis.format(execTime) + "ms)" ;
        }
        write(object + " Execution Time: " + time) ;
    }

}
