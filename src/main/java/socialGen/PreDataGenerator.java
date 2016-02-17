package socialGen;

import conf.Configuration;

public class PreDataGenerator {

    private static Configuration configuration;

    public static void main(String args[]) throws Exception {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        } else {
            String inputConf = args[0] + "/conf/conf.xml";
            configuration = XMLUtil.getConfiguration(inputConf);
        }

        String outputConf = args[0] + "/output/partition-conf.xml";
        XMLUtil.writeToXML(configuration, outputConf);

        String outputPartitionInfo = args[0] + "/output/partitions";
        XMLUtil.writePartitionInfo(configuration, outputPartitionInfo);
    }

    private static void printUsage() {
        System.out.println(" Invalid number of arguments!");
        System.out.println(" Usage: " + "PreDataGenerator <path to configuration file>"
                + "<output conf dir>  <output partition info dir>");
    }
}
