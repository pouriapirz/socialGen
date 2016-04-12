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
package socialGen;

import java.io.IOException;
import java.util.Random;

import utility.FileAppender;
import utility.FileUtil;
import conf.PartitionConfiguration;
import datatype.Date;
import datatype.DateTime;
import datatype.Message;
import datatype.Point;
import entity.ChirpMessage;
import entity.ChirpUser;
import entity.Employment;
import entity.Employments;
import entity.GleambookMessage;
import entity.GleambookUser;
import generator.RandomDateGenerator;
import generator.RandomEmploymentGenerator;
import generator.RandomIdSelector;
import generator.RandomLocationGenerator;
import generator.RandomMessageGenerator;
import generator.RandomNameGenerator;

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

    public static final String EXT_ADM = "adm";
    public static final String EXT_JSON = "json";

    enum Output {
        ADM,
        JSON
    }

    enum KeyType {
        LONG,
        STRING
    }

    private static class OutputDesc {
        OutputDesc(IAppendVisitor visitor, String extension) {
            this.visitor = visitor;
            this.extension = extension;
        }

        IAppendVisitor visitor;
        String extension;
    }

    private static RandomDateGenerator randDateGen;
    private static RandomNameGenerator randNameGen;
    private static RandomEmploymentGenerator randEmpGen;
    private static RandomMessageGenerator randMessageGen;
    private static RandomLocationGenerator randLocationGen;
    private static RandomIdSelector randIdSelector;
    private static Random random;
    private static long numOfGBookUsers;
    private static long numOfChirpUsers;
    private static int avgMsgPerGBookUser;
    private static int avgMsgPerChirpUser;
    private static long gBookUserId;
    private static long gBookMsgId;
    private static long chirpMsgId;

    private static String outputDir;
    private static PartitionConfiguration partition;

    private static GleambookUser gBookUser = new GleambookUser();
    private static ChirpUser chirpUser = new ChirpUser();
    private static GleambookMessage gBookMessage = new GleambookMessage();
    private static ChirpMessage chirpMessage = new ChirpMessage();

    private static void generateGbookUsers(long numGBookUsers, IAppendVisitor visitor, String ext) throws IOException {
        FileAppender appender = FileUtil.getFileAppender(outputDir + "/" + "gbook_users." + ext, true, true);
        FileAppender messageAppender = FileUtil.getFileAppender(outputDir + "/" + "gbook_messages." + ext, true, true);
        for (long i = 0; i < numGBookUsers; i++) {
            generateGBookUser(null);
            appender.appendToFile(visitor.reset().visit(gBookUser).toString());
            int numOfMsg = random.nextInt(2 * avgMsgPerGBookUser + 1);
            generateGBookMessages(gBookUser, messageAppender, numOfMsg, visitor);
        }
        appender.close();
        messageAppender.close();
    }

    private static void generateChirpUsers(long numChirpUsers, IAppendVisitor visitor, String ext) throws IOException {
        FileAppender messageAppender = FileUtil.getFileAppender(outputDir + "/" + "chirp_messages." + ext, true, true);
        for (long i = 0; i < numChirpUsers; i++) {
            generateChirpUser(null);
            int numOfMsg = random.nextInt(2 * avgMsgPerChirpUser + 1);
            generateChirpMessages(chirpUser, messageAppender, numOfMsg, visitor);
        }
        messageAppender.close();
    }

    private static void generateGBookMessages(GleambookUser user, FileAppender appender, int numMsg,
            IAppendVisitor visitor) throws IOException {
        Message message;
        for (int i = 0; i < numMsg; i++) {
            message = randMessageGen.getNextRandomMessage(false);
            Point location = randLocationGen.getRandomPoint();
            DateTime sendTime = randDateGen.getNextRandomDatetime();
            gBookMessage.reset(gBookMsgId++, user.getId(),
                    generateRandomLong(1, (numOfGBookUsers * avgMsgPerGBookUser)), location, sendTime, message);
            appender.appendToFile(visitor.reset().visit(gBookMessage).toString());
        }
    }

    private static void generateChirpMessages(ChirpUser user, FileAppender appender, long numMsg,
            IAppendVisitor visitor) throws IOException {
        Message message;
        for (int i = 0; i < numMsg; i++) {
            message = randMessageGen.getNextRandomMessage(true);
            Point location = randLocationGen.getRandomPoint();
            DateTime sendTime = randDateGen.getNextRandomDatetime();
            chirpMessage.reset(chirpMsgId, user, location, sendTime, message.getReferredTopics(), message);
            chirpMsgId++;
            appender.appendToFile(visitor.reset().visit(chirpMessage).toString());
        }
    }

    public static void main(String args[]) throws Exception {
        String controllerInstallDir = null;
        if (args.length < 2) {
            printUsage(" Error: Invalid number of arguments ");
        }
        controllerInstallDir = args[0];
        String partitionConfXML = controllerInstallDir + "/output/partition-conf.xml";
        long partitionId = Long.parseLong(args[1]);
        OutputDesc od = outputFormat(args);
        partition = XMLUtil.getPartitionConfiguration(partitionConfXML, partitionId);

        String firstNameFile = controllerInstallDir + "/metadata/firstNames.txt";
        String lastNameFile = controllerInstallDir + "/metadata/lastNames.txt";
        String msgGenConfigFile = controllerInstallDir + "/metadata/config.txt";
        String orgList = controllerInstallDir + "/metadata/org_list.txt";

        long seed = partition.getTargetPartition().getSeed();
        random = new Random(seed);
        randDateGen = new RandomDateGenerator(new Date(START_MONTH, START_DAY, START_YEAR),
                new Date(END_MONTH, END_DAY, END_YEAR), seed);
        randNameGen = new RandomNameGenerator(firstNameFile, lastNameFile, seed);
        randEmpGen = new RandomEmploymentGenerator(EMPLOYED_USERS_RATIO, new Date(START_MONTH, START_DAY, START_YEAR),
                new Date(END_MONTH, END_DAY, EMPLOYMENT_LAST_START_YEAR), orgList, seed);
        randLocationGen = new RandomLocationGenerator(START_LATITUDE, END_LATITUDE, START_LONGITUDE, END_LONGITUDE,
                seed);
        randIdSelector = new RandomIdSelector(seed);
        String parentDir = controllerInstallDir + "/metadata";
        randMessageGen = new RandomMessageGenerator(msgGenConfigFile, parentDir, seed);
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

        generateData(od.visitor, od.extension);
    }

    private static OutputDesc outputFormat(String[] args) {
        if (args.length < 3) {
            return new OutputDesc(new ADMAppendVisitor(), EXT_ADM);
        }
        int i = 2;
        Output output = Output.ADM;
        KeyType keyType = KeyType.LONG;
        while (i < args.length) {
            String arg = args[i];
            if (!arg.startsWith("-")) {
                printUsage("unsupported argument " + arg);
            }
            switch (arg) {
                case "-f":
                    if (i + 1 >= args.length) {
                        printUsage("missing parameter for " + arg);
                    }
                    String outputFormat = args[++i];
                    if (outputFormat.equalsIgnoreCase("json")) {
                        output = Output.JSON;
                    } else if (outputFormat.equalsIgnoreCase("adm")) {
                        output = Output.ADM;
                    } else {
                        printUsage("Illegal output format " + outputFormat);
                    }
                    break;
                case "-k":
                    if (i + 1 >= args.length) {
                        printUsage("missing parameter for " + arg);
                    }
                    String keyFormat = args[++i];
                    if (keyFormat.equalsIgnoreCase("long")) {
                        keyType = KeyType.LONG;
                    } else if (keyFormat.equalsIgnoreCase("string")) {
                        keyType = KeyType.STRING;
                    } else {
                        printUsage("Illegal key format " + keyFormat);
                    }
                    break;
                default:
                    printUsage("unknown option " + arg);
            }
            ++i;
        }

        IAppendVisitor visitor;
        switch (output) {
            case ADM:
                visitor = (keyType == KeyType.LONG ? new ADMAppendVisitor() : new ADMAppendVisitor() {
                    public IAppendVisitor visit(long l) {
                        builder.append('"').append(l).append('"');
                        return this;
                    }
                });
                return new OutputDesc(visitor, EXT_ADM);
            case JSON:
                visitor = (keyType == KeyType.LONG ? new JsonAppendVisitor() : new JsonAppendVisitor() {
                    public IAppendVisitor visit(long l) {
                        builder.append('"').append(l).append('"');
                        return this;
                    }
                });
                return new OutputDesc(visitor, EXT_JSON);
            default:
                throw new IllegalArgumentException(output.toString());
        }

    }

    private static void printUsage(String msg) {
        System.out.println(msg);
        System.out.println("Usage : DataGenerator <path to configuration file> <partition name> [<options>]");
        System.out.println("Options : -f [ADM|JSON]    (format - default: ADM)");
        System.out.println("          -k [LONG|STRING] (key type - default: LONG)");
        System.exit(1);
    }

    private static void generateData(IAppendVisitor visitor, String extension) throws IOException {
        generateGbookUsers(numOfGBookUsers, visitor, extension);
        generateChirpUsers(numOfChirpUsers, visitor, extension);
        System.out.println("\nData generation in partition " + partition.getTargetPartition().getId() + " finished");
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
        DateTime userSince = randDateGen.getNextRandomDatetime();
        int numFriends = random.nextInt(11);
        long[] friendIds = randIdSelector.getKFromN(numFriends, (numOfGBookUsers));
        int empCount = 1 + random.nextInt(3);
        Employments emp = new Employments(empCount);
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
