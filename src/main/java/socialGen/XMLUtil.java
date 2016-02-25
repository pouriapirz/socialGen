package socialGen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import conf.Configuration;
import conf.PartitionConfiguration;
import conf.PartitionMetrics;
import conf.SourcePartition;
import conf.TargetPartition;

public class XMLUtil {

    final static String PARTITIONS = "partitions";
    final static String PARTITION = "partition";
    final static String NAME = "name";
    final static String HOST = "host";
    final static String PATH = "path";
    final static String GBOOK_USERS = "gleambook.users.count";
    final static String CHIRP_USERS = "chirp.users.count";
    final static String GBOOK_USER_KEY_MIN = "gbook.users.key.min";
    final static String GBOOK_USER_KEY_MAX = "gbook.users.key.max";
    final static String CHIRP_USER_KEY_MIN = "chirp.users.key.min";
    final static String CHIRP_USER_KEY_MAX = "chirp.users.key.max";
    final static String GBOOK_MSG_KEY_MIN = "gbook.messages.key.min";
    final static String GBOOK_MSG_KEY_MAX = "gbook.messages.key.max";
    final static String CHIRP_MSG_KEY_MIN = "chirp.messages.key.min";
    final static String CHIRP_MSG_KEY_MAX = "chirp.messages.key.max";
    final static String AVG_MSG_PER_GBOOK_USER = "avg.message.count.per.gleambook.user";
    final static String AVG_MSG_PER_CHIRP_USER = "avg.message.count.per.chirp.user";

    public static void writeToXML(Configuration conf, String filePath)
            throws IOException, ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(PARTITIONS);
        doc.appendChild(rootElement);

        int index = 0;
        for (TargetPartition partition : conf.getTargetPartitions()) {
            writePartitionElement(conf.getSourcePartitions().get(index), partition, rootElement, doc);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));

        transformer.transform(source, result);

    }

    public static void writePartitionInfo(Configuration conf, String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (SourcePartition sp : conf.getSourcePartitions()) {
            bw.write(sp.getHost() + ":" + sp.getName() + ":" + sp.getPath());
            bw.write("\n");
        }
        bw.close();
    }

    private static Document getDocument(String filePath) throws Exception {
        File inputFile = new File(filePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(inputFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static Configuration getConfiguration(String filePath) throws Exception {
        Configuration conf = getConfiguration(getDocument(filePath));
        PartitionMetrics metrics = new PartitionMetrics(conf.getNumOfGBookUsers(), conf.getNumOfChirpUsers(),
                conf.getAvgMsgGBookUser(), conf.getAvgMsgChirpUser(), conf.getSourcePartitions().size());
        List<TargetPartition> targetPartitions = getTargetPartitions(metrics, conf.getSourcePartitions());
        conf.setTargetPartitions(targetPartitions);
        return conf;
    }

    private static Configuration getConfiguration(Document document) throws IOException {
        Element rootEle = document.getDocumentElement();
        NodeList nodeList = rootEle.getChildNodes();
        long gBookUserCount = Long.parseLong(getStringValue((Element) nodeList, GBOOK_USERS));
        long chirpUserCount = Long.parseLong(getStringValue((Element) nodeList, CHIRP_USERS));
        int avgMsgPerGBookUser = Integer.parseInt(getStringValue((Element) nodeList, AVG_MSG_PER_GBOOK_USER));
        int avgMsgPerChirpUser = Integer.parseInt(getStringValue((Element) nodeList, AVG_MSG_PER_CHIRP_USER));

        List<SourcePartition> sourcePartitions = getSourcePartitions(document);
        return new Configuration(gBookUserCount, chirpUserCount, avgMsgPerGBookUser, avgMsgPerChirpUser,
                sourcePartitions);
    }

    private static List<SourcePartition> getSourcePartitions(Document document) {
        Element rootEle = document.getDocumentElement();
        NodeList nodeList = rootEle.getElementsByTagName(PARTITION);
        List<SourcePartition> sourcePartitions = new ArrayList<SourcePartition>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            sourcePartitions.add(getSourcePartition((Element) node));
        }
        return sourcePartitions;
    }

    private static SourcePartition getSourcePartition(Element functionElement) {
        String name = getStringValue(functionElement, NAME);
        String host = getStringValue(functionElement, HOST);
        String path = getStringValue(functionElement, PATH);
        SourcePartition sp = new SourcePartition(name, host, path);
        return sp;
    }

    private static String getStringValue(Element element, String tagName) {
        String textValue = null;
        NodeList nl = element.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textValue = el.getFirstChild().getNodeValue();
        }
        return textValue;
    }

    public static PartitionConfiguration getPartitionConfiguration(String filePath, String partitionName)
            throws Exception {
        PartitionConfiguration pconf = getPartitionConfigurations(getDocument(filePath), partitionName);
        return pconf;
    }

    private static PartitionConfiguration getPartitionConfigurations(Document document, String partitionName)
            throws IOException {

        Element rootEle = document.getDocumentElement();
        NodeList nodeList = rootEle.getElementsByTagName(PARTITION);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element nodeElement = (Element) node;
            String name = getStringValue(nodeElement, NAME);
            if (!name.equalsIgnoreCase(partitionName)) {
                continue;
            }
            String host = getStringValue(nodeElement, HOST);
            String path = getStringValue(nodeElement, PATH);

            String gBookUserKeyMin = getStringValue(nodeElement, GBOOK_USER_KEY_MIN);
            String gBUserKeyMax = getStringValue(nodeElement, GBOOK_USER_KEY_MAX);
            String chirpUserKeyMin = getStringValue(nodeElement, CHIRP_USER_KEY_MIN);
            String chirpUserKeyMax = getStringValue(nodeElement, CHIRP_USER_KEY_MAX);
            String gBookMsgKeyMin = getStringValue(nodeElement, GBOOK_MSG_KEY_MIN);
            String gBookMsgKeyMax = getStringValue(nodeElement, GBOOK_MSG_KEY_MAX);
            String chirpMsgKeyMin = getStringValue(nodeElement, CHIRP_MSG_KEY_MIN);
            String chirpMsgKeyMax = getStringValue(nodeElement, CHIRP_MSG_KEY_MAX);
            String avgMsgPerGBookUser = getStringValue(nodeElement, AVG_MSG_PER_GBOOK_USER);
            String avgMsgPerChirpUser = getStringValue(nodeElement, AVG_MSG_PER_CHIRP_USER);

            SourcePartition sp = new SourcePartition(name, host, path);

            TargetPartition tp = new TargetPartition(partitionName, host, path, Long.parseLong(gBookUserKeyMin),
                    Long.parseLong(gBUserKeyMax), Long.parseLong(chirpUserKeyMin), Long.parseLong(chirpUserKeyMax),
                    Long.parseLong(gBookMsgKeyMin), Long.parseLong(gBookMsgKeyMax), Long.parseLong(chirpMsgKeyMin),
                    Long.parseLong(chirpMsgKeyMax), Integer.parseInt(avgMsgPerGBookUser),
                    Integer.parseInt(avgMsgPerChirpUser));
            PartitionConfiguration pc = new PartitionConfiguration(sp, tp);
            return pc;
        }
        return null;
    }

    private static List<TargetPartition> getTargetPartitions(PartitionMetrics metrics,
            List<SourcePartition> sourcePartitions) {
        List<TargetPartition> partitions = new ArrayList<TargetPartition>();
        long gBookUserKeyMin = 1;
        long chirpUserKeyMin = 1;
        long gBookMsgIdMin = 1;
        long chirpMsgIdMin = 1;

        int avgMsgPerGBookUser = metrics.getAvgMsgPerGBookUser();
        int avgMsgPerChirpUser = metrics.getAvgMsgPerChirpUser();

        for (SourcePartition sp : sourcePartitions) {
            long gBookUserKeyMax = gBookUserKeyMin + metrics.getNumOfGBookUsers() - 1;
            long chirpUserKeyMax = chirpUserKeyMin + metrics.getNumOfChirpUsers() - 1;

            long maxPossibleGBookMsgs = metrics.getNumOfGBookUsers() * (2 * avgMsgPerGBookUser); //worst case (every user gets exactly double of average messages)
            long maxPossibleChirpMsgs = metrics.getNumOfChirpUsers() * (2 * avgMsgPerChirpUser); //same as above (worst case assumption)

            long gBookMsgIdMax = gBookMsgIdMin + maxPossibleGBookMsgs - 1;
            long chirpMsgIdMax = chirpMsgIdMin + maxPossibleChirpMsgs - 1;
            TargetPartition pe = new TargetPartition(sp.getName(), sp.getHost(), sp.getPath(), gBookUserKeyMin,
                    gBookUserKeyMax, chirpUserKeyMin, chirpUserKeyMax, gBookMsgIdMin, gBookMsgIdMax, chirpMsgIdMin,
                    chirpMsgIdMax, avgMsgPerGBookUser, avgMsgPerChirpUser);
            partitions.add(pe);

            gBookUserKeyMin = gBookUserKeyMax + 1;
            chirpUserKeyMin = chirpUserKeyMax + 1;

            gBookMsgIdMin = gBookMsgIdMax + 1;
            chirpMsgIdMin = chirpMsgIdMax + 1;
        }

        return partitions;
    }

    private static void writePartitionElement(SourcePartition sourcePartition, TargetPartition partition,
            Element rootElement, Document doc) {

        Element pe = doc.createElement(PARTITION);
        rootElement.appendChild(pe);

        Element name = doc.createElement(NAME);
        name.appendChild(doc.createTextNode("" + partition.getName()));
        pe.appendChild(name);

        Element host = doc.createElement(HOST);
        host.appendChild(doc.createTextNode("" + partition.getHost()));
        pe.appendChild(host);

        Element path = doc.createElement(PATH);
        path.appendChild(doc.createTextNode("" + partition.getPath()));
        pe.appendChild(path);

        Element gBookUserKeyMin = doc.createElement(GBOOK_USER_KEY_MIN);
        gBookUserKeyMin.appendChild(doc.createTextNode("" + partition.getgBookUserKeyMin()));
        pe.appendChild(gBookUserKeyMin);

        Element gBookUserKeyMax = doc.createElement(GBOOK_USER_KEY_MAX);
        gBookUserKeyMax.appendChild(doc.createTextNode("" + partition.getgBookUserKeyMax()));
        pe.appendChild(gBookUserKeyMax);

        Element chirpUserKeyMin = doc.createElement(CHIRP_USER_KEY_MIN);
        chirpUserKeyMin.appendChild(doc.createTextNode("" + partition.getChirpUserKeyMin()));
        pe.appendChild(chirpUserKeyMin);

        Element chirpUserKeyMax = doc.createElement(CHIRP_USER_KEY_MAX);
        chirpUserKeyMax.appendChild(doc.createTextNode("" + partition.getChirpUserKeyMax()));
        pe.appendChild(chirpUserKeyMax);

        Element gBookMsgKeyMin = doc.createElement(GBOOK_MSG_KEY_MIN);
        gBookMsgKeyMin.appendChild(doc.createTextNode("" + partition.getGBookMsgIdMin()));
        pe.appendChild(gBookMsgKeyMin);

        Element gBookMsgKeyMax = doc.createElement(GBOOK_MSG_KEY_MAX);
        gBookMsgKeyMax.appendChild(doc.createTextNode("" + partition.getGBookMsgIdMax()));
        pe.appendChild(gBookMsgKeyMax);

        Element chirpMsgKeyMin = doc.createElement(CHIRP_MSG_KEY_MIN);
        chirpMsgKeyMin.appendChild(doc.createTextNode("" + partition.getChirpMsgIdMin()));
        pe.appendChild(chirpMsgKeyMin);

        Element chirpMsgKeyMax = doc.createElement(CHIRP_MSG_KEY_MAX);
        chirpMsgKeyMax.appendChild(doc.createTextNode("" + partition.getChirpMsgIdMax()));
        pe.appendChild(chirpMsgKeyMax);

        Element avgMsgPerGBookUser = doc.createElement(AVG_MSG_PER_GBOOK_USER);
        avgMsgPerGBookUser.appendChild(doc.createTextNode("" + partition.getAvgMsgPerGBookUser()));
        pe.appendChild(avgMsgPerGBookUser);

        Element avgMsgPerChirpUser = doc.createElement(AVG_MSG_PER_CHIRP_USER);
        avgMsgPerChirpUser.appendChild(doc.createTextNode("" + partition.getAvgMsgPerChirpUser()));
        pe.appendChild(avgMsgPerChirpUser);

    }
}