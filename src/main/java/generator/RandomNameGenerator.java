package generator;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import utility.FileUtil;

public class RandomNameGenerator {

    private String[] firstNames;
    private String[] lastNames;

    private final Random random = new Random();

    private final String[] connectors = new String[] { "_", "#", "*", "!" };

    public RandomNameGenerator(String firstNameFilePath, String lastNameFilePath) throws IOException {
        firstNames = FileUtil.listyFile(new File(firstNameFilePath)).toArray(new String[] {});
        lastNames = FileUtil.listyFile(new File(lastNameFilePath)).toArray(new String[] {});
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
