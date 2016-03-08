/*
 * Copyright by The Regents of the University of California
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
