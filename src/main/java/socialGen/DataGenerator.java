package socialGen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import conf.PartitionConfiguration;
import datatype.Date;
import datatype.DateTime;
import datatype.Message;
import datatype.Point;
import entity.ChirpMessage;
import entity.ChirpUser;
import entity.Employment;
import entity.GleambookMessage;
import entity.GleambookUser;
import generator.RandomDateGenerator;
import generator.RandomEmploymentGenerator;
import generator.RandomIdSelector;
import generator.RandomLocationGenerator;
import generator.RandomMessageGenerator;
import generator.RandomNameGenerator;
import utility.FileAppender;
import utility.FileUtil;

public class DataGenerator {

    private final static int MAX_DIGIT = 15;
    private final static int EMPLOYMENT_LAST_START_YEAR = 2012;
    private final static int EMPLOYED_USERS_RATIO = 60;
    private final static int START_YEAR = 2000;
    private final static int START_MONTH = 1;
    private final static int START_DAY = 1;
    private final static int END_YEAR = 2014;
    private final static int END_MONTH = 8;
    private final static int END_DAY = 30;
    private final static int START_LATITUDE = 24;
    private final static int END_LATITUDE = 49;
    private final static int START_LONGITUDE = 66;
    private final static int END_LONGITUDE = 98;
    private final static int MAX_FRIENDS = 400;
    private final static int MAX_STATUS_COUNT = 500;

    private static RandomDateGenerator randDateGen;
    private static RandomNameGenerator randNameGen;
    private static RandomEmploymentGenerator randEmpGen;
    private static RandomMessageGenerator randMessageGen;
    private static RandomLocationGenerator randLocationGen;
    private static long numOfGBookUsers;
    private static long numOfChirpUsers;
    private static int avgMsgPerGBookUser;
    private static int avgMsgPerChirpUser;
    private static long gBookUserId;
    private static long gBookMsgId;
    private static long chirpMsgId;

    private static Random random = new Random(System.currentTimeMillis());
    private static String outputDir;
    private static PartitionConfiguration partition;

    private static GleambookUser gBookUser = new GleambookUser();
    private static ChirpUser chirpUser = new ChirpUser();
    private static GleambookMessage gBookMessage = new GleambookMessage();
    private static ChirpMessage chirpMessage = new ChirpMessage();

    private static void generateGbookUsers(long numGBookUsers) throws IOException {
        FileAppender appender = FileUtil.getFileAppender(outputDir + "/" + "gbook_users.adm", true, true);
        FileAppender messageAppender = FileUtil.getFileAppender(outputDir + "/" + "gbook_messages.adm", true, true);
        for (long i = 0; i < numGBookUsers; i++) {
            generateGBookUser(null);
            appender.appendToFile(gBookUser.toString());
            int numOfMsg = random.nextInt(2 * avgMsgPerGBookUser + 1);
            generateGBookMessages(gBookUser, messageAppender, numOfMsg);
        }
        appender.close();
        messageAppender.close();
    }

    private static void generateChirpUsers(long numChirpUsers) throws IOException {
        FileAppender messageAppender = FileUtil.getFileAppender(outputDir + "/" + "chirp_messages.adm", true, true);
        for (long i = 0; i < numChirpUsers; i++) {
            generateChirpUser(null);
            int numOfMsg = random.nextInt(2 * avgMsgPerChirpUser + 1);
            generateChirpMessages(chirpUser, messageAppender, numOfMsg);
        }
        messageAppender.close();
    }

    private static void generateGBookMessages(GleambookUser user, FileAppender appender, int numMsg)
            throws IOException {
        Message message;
        for (int i = 0; i < numMsg; i++) {
            message = randMessageGen.getNextRandomMessage(false);
            Point location = randLocationGen.getRandomPoint();
            DateTime sendTime = randDateGen.getNextRandomDatetime();
            gBookMessage.reset(gBookMsgId++, user.getId(),
                    generateRandomLong(1, (numOfGBookUsers * avgMsgPerGBookUser)), location, sendTime, message);
            appender.appendToFile(gBookMessage.toString());
        }
    }

    private static void generateChirpMessages(ChirpUser user, FileAppender appender, long numMsg) throws IOException {
        Message message;
        for (int i = 0; i < numMsg; i++) {
            message = randMessageGen.getNextRandomMessage(true);
            Point location = randLocationGen.getRandomPoint();
            DateTime sendTime = randDateGen.getNextRandomDatetime();
            chirpMessage.reset(chirpMsgId, user, location, sendTime, message.getReferredTopics(), message);
            chirpMsgId++;
            appender.appendToFile(chirpMessage.toString());
        }
    }

    public static void main(String args[]) throws Exception {
        String controllerInstallDir = null;
        if (args.length < 2) {
            printUsage();
            System.exit(1);
        } else {
            controllerInstallDir = args[0];
            String partitionConfXML = controllerInstallDir + "/output/partition-conf.xml";
            String partitionName = args[1];
            partition = XMLUtil.getPartitionConfiguration(partitionConfXML, partitionName);
        }

        randDateGen = new RandomDateGenerator(new Date(START_MONTH, START_DAY, START_YEAR),
                new Date(END_MONTH, END_DAY, END_YEAR));

        String firstNameFile = controllerInstallDir + "/metadata/firstNames.txt";
        String lastNameFile = controllerInstallDir + "/metadata/lastNames.txt";
        String msgGenConfigFile = controllerInstallDir + "/metadata/config.txt";
        String orgList = controllerInstallDir + "/metadata/org_list.txt";

        randNameGen = new RandomNameGenerator(firstNameFile, lastNameFile);
        randEmpGen = new RandomEmploymentGenerator(EMPLOYED_USERS_RATIO, new Date(START_MONTH, START_DAY, START_YEAR),
                new Date(END_MONTH, END_DAY, EMPLOYMENT_LAST_START_YEAR), orgList);
        randLocationGen = new RandomLocationGenerator(START_LATITUDE, END_LATITUDE, START_LONGITUDE, END_LONGITUDE);
        String parentDir = controllerInstallDir + "/metadata";
        randMessageGen = new RandomMessageGenerator(msgGenConfigFile, parentDir);
        numOfGBookUsers = (partition.getTargetPartition().getgBookUserKeyMax()
                - partition.getTargetPartition().getgBookUserKeyMin()) + 1;
        numOfChirpUsers = (partition.getTargetPartition().getChirpUserKeyMax()
                - partition.getTargetPartition().getChirpUserKeyMin()) + 1;
        avgMsgPerGBookUser = partition.getTargetPartition().getAvgMsgPerGBookUser();
        avgMsgPerChirpUser = partition.getTargetPartition().getAvgMsgPerChirpUser();
        gBookUserId = partition.getTargetPartition().getgBookUserKeyMin();
        gBookMsgId = partition.getTargetPartition().getGBookMsgIdMin();
        chirpMsgId = partition.getTargetPartition().getChirpMsgIdMin();
        outputDir = partition.getSourcePartition().getPath();

        generateData();
    }

    private static void printUsage() {
        System.out.println(" Error: Invalid number of arguments ");
        System.out.println(" Usage :" + " DataGenerator <path to configuration file> <partition name> ");
    }

    private static void generateData() throws IOException {
        generateGbookUsers(numOfGBookUsers);
        generateChirpUsers(numOfChirpUsers);
        System.out.println("\nData generation in partition " + partition.getTargetPartition().getName() + " finished");
    }

    private static void generateGBookUser(String usernameSuffix) {
        String suggestedName = randNameGen.getRandomName();
        String[] nameComponents = suggestedName.split(" ");
        String name = nameComponents[0] + " " + nameComponents[1];
        if (usernameSuffix != null) {
            name = name + usernameSuffix;
        }
        long id = gBookUserId++;
        String alias = getUniqueAlias(nameComponents[0], id, MAX_DIGIT);
        String userSince = randDateGen.getNextRandomDatetime().toString();
        int numFriends = random.nextInt(11);
        long[] friendIds = RandomIdSelector.getKFromN(numFriends, (numOfGBookUsers));
        int empCount = 1 + random.nextInt(3);
        ArrayList<Employment> emp = new ArrayList<Employment>(empCount);
        for (int i = 0; i < empCount; i++) {
            Employment e = randEmpGen.getRandomEmployment();
            emp.add(new Employment(e.getOrganization(), e.getStartDate(), e.getEndDate()));
        }
        gBookUser.reset(id, alias, name, userSince, friendIds, emp);
    }

    private static void generateChirpUser(String usernameSuffix) {
        String suggestedName = randNameGen.getRandomName();
        String[] nameComponents = suggestedName.split(" ");
        String screenName = nameComponents[0] + nameComponents[1] + randNameGen.getRandomNameSuffix();
        String name = suggestedName;
        if (usernameSuffix != null) {
            name = name + usernameSuffix;
        }
        int numFriends = random.nextInt(MAX_FRIENDS);
        int statusesCount = random.nextInt(MAX_STATUS_COUNT);
        int followersCount = (numFriends - 5) + random.nextInt(200);
        chirpUser.reset(screenName, numFriends, statusesCount, name, followersCount);
    }

    private static long generateRandomLong(long x, long y) {
        return (x + ((long) (random.nextDouble() * (y - x))));
    }

    private static String getUniqueAlias(String name, long id, int maxDigit) {
        int digits = maxDigit;
        if (id != 0) {
            digits -= ((int) (Math.log10(id) + 1));
        }
        String pad = "";
        for (int i = 0; i < digits; i++) {
            pad = "0" + pad;
        }
        return name + pad + id;
    }
}
