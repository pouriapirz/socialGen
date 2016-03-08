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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import datatype.Message;
import utility.FileUtil;

public class RandomMessageGenerator {
    //Params' Tags in Config File
    private final String posAdj = "POS_ADJ";
    private final String negAdj = "NEG_ADJ";
    private final String posFeel = "POS_FEEL";
    private final String negFeel = "NEG_FEEL";

    private final String serviceActivityVerbs = "SERV_VERB";
    private final String jargon = "JARGON";
    private final String location_rare = "RARE_LOC";
    private final String location_med = "MED_LOC";
    private final String location_freq = "FREQ_LOC";
    private final String vendor_rare = "RARE_VENDOR";
    private final String vendor_med = "MED_VENDOR";
    private final String vendor_freq = "FREQ_VENDOR";

    private final String deviceActivityVerbs = "DEV_VERB";
    private final String time = "TIME";
    private final String device_rare = "RARE_DEV";
    private final String device_med = "MED_DEV";
    private final String device_freq = "FREQ_DEV";
    private final String part_rare = "RARE_PART";
    private final String part_med = "MED_PART";
    private final String part_freq = "FREQ_PART";
    private final Random rand;
    private ServiceRelatedMessage serviceMsgGen;
    private DeviceRelatedMessage deviceMsgGen;
    private MessageSettings settings;

    public RandomMessageGenerator(String configFile, String parentDir, long seed) {
        this.settings = new MessageSettings(configFile, parentDir);
        this.rand = new Random(seed);
        setServiceMsgGen(seed);
        setDeviceMsgGen(seed);
    }

    public Message getNextRandomMessage(boolean needTopics) {
        boolean msgType = rand.nextBoolean();
        if (msgType) { //service-related
            return serviceMsgGen.getNextRandomMessage(needTopics);
        } else { //device-related
            return deviceMsgGen.getNextRandomMessage(needTopics);
        }
    }

    private void setServiceMsgGen(long seed) {
        String posAdjFile = settings.getParam(posAdj);
        String negAdjFile = settings.getParam(negAdj);
        String posFeelFile = settings.getParam(posFeel);
        String negFeelFile = settings.getParam(negFeel);

        String serviceActivityVerbsFile = settings.getParam(serviceActivityVerbs);
        String jargonFile = settings.getParam(jargon);
        String location_rareFile = settings.getParam(location_rare);
        String location_medFile = settings.getParam(location_med);
        String location_freqFile = settings.getParam(location_freq);
        String vendor_rareFile = settings.getParam(vendor_rare);
        String vendor_medFile = settings.getParam(vendor_med);
        String vendor_freqFile = settings.getParam(vendor_freq);

        this.serviceMsgGen = new ServiceRelatedMessage(posAdjFile, negAdjFile, posFeelFile, negFeelFile,
                serviceActivityVerbsFile, jargonFile, seed);
        serviceMsgGen.setLocs(location_rareFile, location_medFile, location_freqFile);
        serviceMsgGen.setVendors(vendor_rareFile, vendor_medFile, vendor_freqFile);
    }

    private void setDeviceMsgGen(long seed) {
        String posAdjFile = settings.getParam(posAdj);
        String negAdjFile = settings.getParam(negAdj);
        String posFeelFile = settings.getParam(posFeel);
        String negFeelFile = settings.getParam(negFeel);

        String deviceActivityVerbsFile = settings.getParam(deviceActivityVerbs);
        String timeFile = settings.getParam(time);
        String device_rareFile = settings.getParam(device_rare);
        String device_medFile = settings.getParam(device_med);
        String device_freqFile = settings.getParam(device_freq);
        String part_rareFile = settings.getParam(part_rare);
        String part_medFile = settings.getParam(part_med);
        String part_freqFile = settings.getParam(part_freq);

        this.deviceMsgGen = new DeviceRelatedMessage(posAdjFile, negAdjFile, posFeelFile, negFeelFile,
                deviceActivityVerbsFile, timeFile, seed);
        deviceMsgGen.setDevices(device_rareFile, device_medFile, device_freqFile);
        deviceMsgGen.setParts(part_rareFile, part_medFile, part_freqFile);
    }
}

abstract class MessageTemplate {
    protected final double RARE = 0.05; //[0, 0.05) => RARE Messages (5%)
    protected final double MED = 0.25; //[0.05, 0.25) => MED Messages (20%)
                                       //[0.25, 1.0) => FREQ Messages (75%)
    protected final double NOISE = 0.05; //(5%)
    protected final Random random;

    List<String> positiveFeeling;
    List<String> negativeFeeling;
    List<String> positiveAdj;
    List<String> negativeAdj;

    public MessageTemplate(String posAdjFile, String negAdjFile, String posFeelFile, String negFeelFile, long seed) {
        this.random = new Random(seed);
        try {
            positiveAdj = FileUtil.listyFile(new File(posAdjFile));
            negativeAdj = FileUtil.listyFile(new File(negAdjFile));
            positiveFeeling = FileUtil.listyFile(new File(posFeelFile));
            negativeFeeling = FileUtil.listyFile(new File(negFeelFile));
        } catch (IOException e) {
            System.err.println("Problem in loading dictionary files for message generator");
            e.printStackTrace();
        }
    }

    public abstract Message getNextRandomMessage(boolean needTopics);

    protected String getRandomFeelingVerb(boolean isPositive) {
        int rix = isPositive ? random.nextInt(positiveFeeling.size()) : random.nextInt(negativeFeeling.size());
        if (isPositive) {
            return new String(positiveFeeling.get(rix));
        }
        return new String(negativeFeeling.get(rix));
    }

    protected String getRandomAdj(boolean isPositive) {
        int rix = isPositive ? random.nextInt(positiveAdj.size()) : random.nextInt(negativeAdj.size());
        if (isPositive) {
            return new String(positiveAdj.get(rix));
        }
        return new String(negativeAdj.get(rix));
    }

    protected String addNoise(String orig) {
        Boolean swap = random.nextBoolean();
        char[] chars = orig.toCharArray();
        int l = chars.length;
        int r1 = random.nextInt(l);
        if (swap) { //swap characters (2 changes)
            int r2 = random.nextInt(l);
            while (chars[r1] == chars[r2]) {
                r2 = random.nextInt(l);
            }
            char c = chars[r1];
            chars[r1] = chars[r2];
            chars[r2] = c;
        } else { //change one character (1 change)
            if (chars[r1] == 'a') {
                chars[r1] = 'b';
            } else {
                chars[r1] = 'a';
            }
        }
        return new String(chars);
    }

}

class ServiceRelatedMessage extends MessageTemplate {
    protected final String[] con1 = { "the", "my" };
    protected final String[] con2 = { "is simply", "is really", "is just" };
    final String[] otherTags = { "tech", "service", "internet", "communication", "app" }; //General tags that could be assigned to messages of this family (To make ref-topics longer (sometimes) )

    List<String> jargon;
    List<String> activityVerbs;

    List<String> location_rare;
    List<String> location_med;
    List<String> location_freq;

    List<String> vendor_rare;
    List<String> vendor_med;
    List<String> vendor_freq;

    String[] baseParts = new String[6];

    public ServiceRelatedMessage(String posAdjFile, String negAdjFile, String posFeelFile, String negFeelFile,
            String actVerbFile, String jargonFile, long seed) {
        super(posAdjFile, negAdjFile, posFeelFile, negFeelFile, seed);
        try {
            jargon = FileUtil.listyFile(new File(jargonFile));
            activityVerbs = FileUtil.listyFile(new File(actVerbFile));
        } catch (IOException e) {
            System.err.println("Problem in loading dictionary files for service realted message generator");
            e.printStackTrace();
        }
    }

    public void setLocs(String rareFile, String medFile, String freqFile) {
        try {
            location_rare = FileUtil.listyFile(new File(rareFile));
            location_med = FileUtil.listyFile(new File(medFile));
            location_freq = FileUtil.listyFile(new File(freqFile));
        } catch (IOException e) {
            System.err.println("Problem in parsing location files for the message generator ! ");
            e.printStackTrace();
        }
    }

    public void setVendors(String rareFile, String medFile, String freqFile) {
        try {
            vendor_rare = FileUtil.listyFile(new File(rareFile));
            vendor_med = FileUtil.listyFile(new File(medFile));
            vendor_freq = FileUtil.listyFile(new File(freqFile));
        } catch (IOException e) {
            System.err.println("Problem in parsing vendor files for the message generator ! ");
            e.printStackTrace();
        }
    }

    @Override
    public Message getNextRandomMessage(boolean needTopics) {
        boolean isPositive = random.nextBoolean();
        boolean isNoisy = (random.nextDouble()) < NOISE ? true : false;
        double msgClass = random.nextDouble();
        String[] locVendor;
        if (msgClass < RARE) {
            locVendor = getRareLocVendor();
        } else if (msgClass < MED) {
            locVendor = getMedLocVendor();
        } else {
            locVendor = getFreqLocVendor();
        }
        return getNextMessage(locVendor[0], locVendor[1], isPositive, isNoisy, needTopics);
    }

    private Message getNextMessage(String location, String vendor, boolean isPositive, boolean isNoisy,
            boolean needTopics) {
        getBasicMsgParts(isPositive);
        List<String> topics = null;
        if (needTopics) {
            topics = getReferredTopics(location, vendor);
        }
        if (isNoisy) {
            vendor = addNoise(vendor);
        }
        StringBuilder sb = new StringBuilder(baseParts[0]);
        sb.append(" ").append(location).append(", ").append(baseParts[1]);
        sb.append(" ").append(vendor).append(", ").append(baseParts[2]).append(" ");
        sb.append(baseParts[3]).append(" ").append(baseParts[4]).append(" ").append(baseParts[5]);
        return new Message(sb.toString().toCharArray(), topics);
    }

    private void getBasicMsgParts(boolean isPositive) { //[0]=Activity_Verb [1]=Feeling_Verb [2]=Con1 [3]=Jargon [4]=Con2 [5]=Adj
        baseParts[0] = getRandomActivity();
        baseParts[1] = getRandomFeelingVerb(isPositive);
        baseParts[2] = con1[random.nextInt(con1.length)];
        baseParts[3] = getRandomJargon();
        baseParts[4] = con2[random.nextInt(con2.length)];
        baseParts[5] = getRandomAdj(isPositive);
    }

    private String[] getRareLocVendor() {
        String location = location_rare.get(random.nextInt(location_rare.size()));
        String vendor = vendor_rare.get(random.nextInt(vendor_rare.size()));
        return new String[] { location, vendor };
    }

    private String[] getMedLocVendor() {
        String location = location_med.get(random.nextInt(location_med.size()));
        String vendor = vendor_med.get(random.nextInt(vendor_med.size()));
        return new String[] { location, vendor };
    }

    private String[] getFreqLocVendor() {
        String location = location_freq.get(random.nextInt(location_freq.size()));
        String vendor = vendor_freq.get(random.nextInt(vendor_freq.size()));
        return new String[] { location, vendor };
    }

    private String getRandomActivity() {
        return new String(activityVerbs.get(random.nextInt(activityVerbs.size())));
    }

    private String getRandomJargon() {
        return new String(jargon.get(random.nextInt(jargon.size())));
    }

    private List<String> getReferredTopics(String loc, String vendor) {
        ArrayList<String> topics = new ArrayList<String>();
        topics.add(loc);
        topics.add(vendor);
        int moreTags = 1 + random.nextInt(otherTags.length);
        for (int i = 0; i < moreTags; i++) {
            String tag = otherTags[random.nextInt(otherTags.length)];
            while (topics.contains(tag)) {
                tag = otherTags[random.nextInt(otherTags.length)];
            }
            topics.add(tag);
        }
        return topics;
    }
}

class DeviceRelatedMessage extends MessageTemplate {
    protected final String[] con1 = { "its", "the" };
    protected final String[] con2 = { "it is", "it is really", "it is just" };
    final String[] otherTags = { "gadget", "device", "technology", "electronics", "hardware" }; //General tags that could be assigned to messages of this family (To make ref-topics longer (sometimes) )

    List<String> time;
    List<String> activityVerbs;

    List<String> device_rare;
    List<String> device_med;
    List<String> device_freq;

    List<String> part_rare;
    List<String> part_med;
    List<String> part_freq;

    String[] baseParts = new String[5]; //Keep reusing it

    public DeviceRelatedMessage(String posAdjFile, String negAdjFile, String posFeelFile, String negFeelFile,
            String actVerbFile, String timeFile, long seed) {
        super(posAdjFile, negAdjFile, posFeelFile, negFeelFile, seed);
        try {
            time = FileUtil.listyFile(new File(timeFile));
            activityVerbs = FileUtil.listyFile(new File(actVerbFile));
        } catch (IOException e) {
            System.err.println("Problem in loading dictionary files for device realted message generator");
            e.printStackTrace();
        }
    }

    public void setDevices(String rareFile, String medFile, String freqFile) {
        try {
            device_rare = FileUtil.listyFile(new File(rareFile));
            device_med = FileUtil.listyFile(new File(medFile));
            device_freq = FileUtil.listyFile(new File(freqFile));
        } catch (IOException e) {
            System.err.println("Problem in parsing device files for message generator ! ");
            e.printStackTrace();
        }
    }

    public void setParts(String rareFile, String medFile, String freqFile) {
        try {
            part_rare = FileUtil.listyFile(new File(rareFile));
            part_med = FileUtil.listyFile(new File(medFile));
            part_freq = FileUtil.listyFile(new File(freqFile));
        } catch (IOException e) {
            System.err.println("Problem in parsing part files for message generator ! ");
            e.printStackTrace();
        }
    }

    @Override
    public Message getNextRandomMessage(boolean needTopics) {
        boolean isPositive = random.nextBoolean();
        boolean isNoisy = (random.nextDouble()) < NOISE ? true : false;
        double msgClass = random.nextDouble();
        String[] devPart;
        if (msgClass < RARE) {
            devPart = getRareDevPart();
        } else if (msgClass < MED) {
            devPart = getMedDevPart();
        } else {
            devPart = getFreqDevPart();
        }
        return getNextMessage(devPart[0], devPart[1], isPositive, isNoisy, needTopics);
    }

    private Message getNextMessage(String device, String part, boolean isPositive, boolean isNoisy,
            boolean needTopics) {
        getBasicMsgParts(isPositive);
        String rTime = getRandomTime();
        List<String> topics = null;
        if (needTopics) {
            topics = getReferredTopics(device, part);
        }
        if (isNoisy) {
            device = addNoise(device);
        }
        StringBuilder sb = new StringBuilder(baseParts[0]);
        sb.append(" ").append(device).append(" ").append(rTime).append(", ").append(baseParts[1]);
        sb.append(" ").append(baseParts[2]).append(" ");
        sb.append(part).append(", ").append(baseParts[3]).append(" ").append(baseParts[4]);
        return new Message(sb.toString().toCharArray(), topics);
    }

    private void getBasicMsgParts(boolean isPositive) { //[0]=Activity_Verb [1]=Feeling_Verb [2]=Con1 [3]=Con2 [4]=Adj
        baseParts[0] = getRandomActivity();
        baseParts[1] = getRandomFeelingVerb(isPositive);
        baseParts[2] = con1[random.nextInt(con1.length)];
        baseParts[3] = con2[random.nextInt(con2.length)];
        baseParts[4] = getRandomAdj(isPositive);
    }

    private String[] getRareDevPart() {
        String device = device_rare.get(random.nextInt(device_rare.size()));
        String part = part_rare.get(random.nextInt(part_rare.size()));
        return new String[] { device, part };
    }

    private String[] getMedDevPart() {
        String device = device_med.get(random.nextInt(device_med.size()));
        String part = part_med.get(random.nextInt(part_med.size()));
        return new String[] { device, part };
    }

    private String[] getFreqDevPart() {
        String device = device_freq.get(random.nextInt(device_freq.size()));
        String part = part_freq.get(random.nextInt(part_freq.size()));
        return new String[] { device, part };
    }

    private String getRandomActivity() {
        return new String(activityVerbs.get(random.nextInt(activityVerbs.size())));
    }

    private String getRandomTime() {
        return new String(time.get(random.nextInt(time.size())));
    }

    private List<String> getReferredTopics(String device, String part) {
        ArrayList<String> topics = new ArrayList<String>();
        topics.add(device);
        topics.add(part);
        int moreTags = 1 + random.nextInt(otherTags.length);
        for (int i = 0; i < moreTags; i++) {
            String tag = otherTags[random.nextInt(otherTags.length)];
            while (topics.contains(tag)) {
                tag = otherTags[random.nextInt(otherTags.length)];
            }
            topics.add(tag);
        }
        return topics;
    }
}

class MessageSettings {
    /*
     * As MessageGenerator has many files, we only pass one file (as the config file)
     * to it that contains paths to all files, and this class parses that file and sets the args
     */

    HashMap<String, String> params;

    public MessageSettings(String configFilePath, String parentDir) {
        params = parse(configFilePath, parentDir);
    }

    public String getParam(String paramName) {
        return params.get(paramName);
    }

    //Format per line: [TAG],[Name of File]
    //You should remember that all these files are under parent directory
    //Lines starting with # are comments and will be ignored
    private HashMap<String, String> parse(String path, String parentDir) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.startsWith("#")) {
                    continue;
                }
                StringTokenizer st = new StringTokenizer(str.trim(), ",");
                String name = st.nextToken();
                String fullPath = parentDir + "/" + st.nextToken();
                params.put(name, fullPath);
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Problem in parsing config file for Message Generator");
            e.printStackTrace();
        }
        return params;
    }
}
