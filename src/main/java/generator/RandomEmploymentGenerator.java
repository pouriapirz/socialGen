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
import java.util.List;
import java.util.Random;

import datatype.Date;
import entity.Employment;
import utility.FileUtil;

public class RandomEmploymentGenerator {

    private final int percentEmployed;
    private final Random random;
    private final RandomDateGenerator randDateGen;
    private final List<String> organizations;
    private Employment emp;

    public RandomEmploymentGenerator(int percentEmployed, Date beginEmpDate, Date endEmpDate, String orgListPath,
            long seed) throws IOException {
        this.percentEmployed = percentEmployed;
        this.randDateGen = new RandomDateGenerator(beginEmpDate, endEmpDate, seed);
        organizations = FileUtil.listyFile(new File(orgListPath));
        emp = new Employment();
        random = new Random(seed);
    }

    public Employment getRandomEmployment() {
        int empployed = random.nextInt(100) + 1;
        boolean isEmployed = false;
        if (empployed <= percentEmployed) {
            isEmployed = true;
        }
        Date startDate = randDateGen.getNextRandomDate();
        Date endDate = null;
        if (!isEmployed) {
            endDate = randDateGen.getNextRecentDate(startDate);
        }
        String org = organizations.get(random.nextInt(organizations.size()));
        emp.reset(org, startDate, endDate);
        return emp;
    }
}
