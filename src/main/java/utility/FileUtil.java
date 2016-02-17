package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> listyFile(File file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        List<String> list = new ArrayList<String>();
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            list.add(line);
        }
        reader.close();
        return list;
    }

    public static FileAppender getFileAppender(String filePath, boolean createIfNotExists, boolean overwrite)
            throws IOException {
        return new FileAppender(filePath, createIfNotExists, overwrite);
    }
}
