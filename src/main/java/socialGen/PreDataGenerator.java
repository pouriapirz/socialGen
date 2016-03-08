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
