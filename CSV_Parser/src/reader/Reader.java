package reader;
import jdk.jfr.Name;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader extends InputStreamReader {

    /**
     * @param fileName - name of input file. If it null will be used default name: in.txt
     */
    Reader(String fileName) {
        if (fileName == null) {
            fileName = "in.txt";
        }
        try {
            this.buffer = new InputStreamReader(new FileInputStream(fileName));
        } catch (IOException exception) {
            System.err.println("![ERR] Error while reading file: " + exception.getLocalizedMessage());
        }
    }


}
