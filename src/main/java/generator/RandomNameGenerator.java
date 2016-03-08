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
package generator;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import utility.FileUtil;

public class RandomNameGenerator {

    private String[] firstNames;
    private String[] lastNames;
    private final Random random;
    private final String[] connectors = new String[] { "_", "#", "*", "!" };

    public RandomNameGenerator(String firstNameFilePath, String lastNameFilePath, long seed) throws IOException {
        this.firstNames = FileUtil.listyFile(new File(firstNameFilePath)).toArray(new String[] {});
        this.lastNames = FileUtil.listyFile(new File(lastNameFilePath)).toArray(new String[] {});
        this.random = new Random(seed);
    }

    public String getRandomName() {
        String name;
        name = getSuggestedName();
        return name;

    }

    private String getSuggestedName() {
        int firstNameIndex = random.nextInt(firstNames.length);
        int lastNameIndex = random.nextInt(lastNames.length);
        String suggestedName = firstNames[firstNameIndex] + " " + lastNames[lastNameIndex];
        return suggestedName;
    }

    public String getRandomNameSuffix() {
        return connectors[random.nextInt(connectors.length)] + random.nextInt(1000);
    }

}
