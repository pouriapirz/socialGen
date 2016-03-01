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
