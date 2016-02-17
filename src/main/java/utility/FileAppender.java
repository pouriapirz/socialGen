package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppender {

    private final BufferedWriter writer;

    public FileAppender(String filePath, boolean createIfNotExists, boolean overwrite) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            if (createIfNotExists) {
                new File(file.getParent()).mkdirs();
            } else {
                throw new IOException("path " + filePath + " does not exists");
            }
        }
        this.writer = new BufferedWriter(new FileWriter(file, !overwrite));
    }

    public void appendToFile(String content) throws IOException {
        writer.append(content);
        writer.append("\n");
    }

    public void close() throws IOException {
        writer.close();
    }
}
