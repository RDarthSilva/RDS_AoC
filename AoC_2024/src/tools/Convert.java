package tools;

public class Convert {

    public static int toInt(String s) { return Integer.parseInt(s) ; }

    public static int toInt(char c) { return Character.getNumericValue(c); }
    public static long toLong(String s) { return Long.parseLong(s) ; }

    public static char toChar(int n) { return (char) n ; }
    public static String toString(int n) {
        return "" + n ;
    }
    public static String toString(int n, int padding) { return String.format("%0" + padding + "d", n) ; }
    public static String toString(long l) { return "" + l ; }
    public static String toString(char c) { return Character.toString(c) ; }
    public static int absolute(int n) {if(n<0) return n*-1 ;  else return n ; }

}
