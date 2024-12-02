package tools;

import java.io.*;

public class InputFile {
    private File file ;
    private BufferedReader br ;

    public InputFile(String filePath) {
        try {
            file = new File(filePath) ;
            br = new BufferedReader(new FileReader(file)) ;
        } catch(FileNotFoundException e) { e.printStackTrace() ; }
    }

    public String getLine() {
        String line = null ;
        try { line = br.readLine() ; }
        catch(IOException e) { e.printStackTrace() ; }
        return line ;
    }

    public void close() {
        try { br.close() ; }
        catch(IOException e) { e.printStackTrace() ; }
    }

}
