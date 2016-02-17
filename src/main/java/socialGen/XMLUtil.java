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

    public static void writeToXML(Configuration conf, String filePath)
            throws IOException, ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("Partitions");
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
        PartitionMetrics metrics = new PartitionMetrics(conf.getNumOfFBU(), conf.getNumOfTWU(), conf.getAvgMsgPerFBU(),
                conf.getAvgTweetPerTWU(), conf.getSourcePartitions().size());
        List<TargetPartition> targetPartitions = getTargetPartitions(metrics, conf.getSourcePartitions());
        conf.setTargetPartitions(targetPartitions);
        return conf;
    }

    private static Configuration getConfiguration(Document document) throws IOException {
        Element rootEle = document.getDocumentElement();
        NodeList nodeList = rootEle.getChildNodes();
        long fbuCount = Long.parseLong(getStringValue((Element) nodeList, "facebookUsers"));
        long twuCount = Long.parseLong(getStringValue((Element) nodeList, "twitterUsers"));
        int avgMsgFBU = Integer.parseInt(getStringValue((Element) nodeList, "avgMsg"));
        int avgTweetTWU = Integer.parseInt(getStringValue((Element) nodeList, "avgTweet"));

        List<SourcePartition> sourcePartitions = getSourcePartitions(document);
        return new Configuration(fbuCount, twuCount, avgMsgFBU, avgTweetTWU, sourcePartitions);
    }

    private static List<SourcePartition> getSourcePartitions(Document document) {
        Element rootEle = document.getDocumentElement();
        NodeList nodeList = rootEle.getElementsByTagName("partition");
        List<SourcePartition> sourcePartitions = new ArrayList<SourcePartition>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            sourcePartitions.add(getSourcePartition((Element) node));
        }
        return sourcePartitions;
    }

    private static SourcePartition getSourcePartition(Element functionElement) {
        String name = getStringValue(functionElement, "name");
        String host = getStringValue(functionElement, "host");
        String path = getStringValue(functionElement, "path");
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
        NodeList nodeList = rootEle.getElementsByTagName("Partition");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element nodeElement = (Element) node;
            String name = getStringValue(nodeElement, "name");
            if (!name.equalsIgnoreCase(partitionName)) {
                continue;
            }
            String host = getStringValue(nodeElement, "host");
            String path = getStringValue(nodeElement, "path");

            String fbUserKeyMin = getStringValue(nodeElement, "fbUserKeyMin");
            String fbUserKeyMax = getStringValue(nodeElement, "fbUserKeyMax");
            String twUserKeyMin = getStringValue(nodeElement, "twUserKeyMin");
            String twUserKeyMax = getStringValue(nodeElement, "twUserKeyMax");
            String fbMessageKeyMin = getStringValue(nodeElement, "fbMessageKeyMin");

            String fbMessageKeyMax = getStringValue(nodeElement, "fbMessageKeyMax");
            String twMessageKeyMin = getStringValue(nodeElement, "twMessageKeyMin");
            String twMessageKeyMax = getStringValue(nodeElement, "twMessageKeyMax");
            String avgMsgPerFBU = getStringValue(nodeElement, "avgMsgPerFBU");
            String avgTweetPerTWU = getStringValue(nodeElement, "avgTweetPerTWU");

            SourcePartition sp = new SourcePartition(name, host, path);

            TargetPartition tp = new TargetPartition(partitionName, host, path, Long.parseLong(fbUserKeyMin),
                    Long.parseLong(fbUserKeyMax), Long.parseLong(twUserKeyMin), Long.parseLong(twUserKeyMax),
                    Long.parseLong(fbMessageKeyMin), Long.parseLong(fbMessageKeyMax), Long.parseLong(twMessageKeyMin),
                    Long.parseLong(twMessageKeyMax), Integer.parseInt(avgMsgPerFBU), Integer.parseInt(avgTweetPerTWU));
            PartitionConfiguration pc = new PartitionConfiguration(sp, tp);
            return pc;
        }
        return null;
    }

    private static List<TargetPartition> getTargetPartitions(PartitionMetrics metrics,
            List<SourcePartition> sourcePartitions) {
        List<TargetPartition> partitions = new ArrayList<TargetPartition>();
        long fbUserKeyMin = 1;
        long twUserKeyMin = 1;
        long fbMessageIdMin = 1;
        long twMessageIdMin = 1;

        int avgMsgPerFBU = metrics.getAvgMsgPerFBU();
        int avgTweetPerTWU = metrics.getAvgTweetPerTWU();

        for (SourcePartition sp : sourcePartitions) {
            long fbUserKeyMax = fbUserKeyMin + metrics.getNumOfFBU() - 1;
            long twUserKeyMax = twUserKeyMin + metrics.getNumOfTWU() - 1;

            long maxPossibleMsgs = metrics.getNumOfFBU() * (2 * avgMsgPerFBU); //worst case (every user gets exactly double of average messages)
            long maxPossibleTweets = metrics.getNumOfTWU() * (2 * avgTweetPerTWU); //same as above (worst case assumption)

            long fbMessageIdMax = fbMessageIdMin + maxPossibleMsgs - 1;
            long twMessageIdMax = twMessageIdMin + maxPossibleTweets - 1;
            TargetPartition pe = new TargetPartition(sp.getName(), sp.getHost(), sp.getPath(), fbUserKeyMin,
                    fbUserKeyMax, twUserKeyMin, twUserKeyMax, fbMessageIdMin, fbMessageIdMax, twMessageIdMin,
                    twMessageIdMax, avgMsgPerFBU, avgTweetPerTWU);
            partitions.add(pe);

            fbUserKeyMin = fbUserKeyMax + 1;
            twUserKeyMin = twUserKeyMax + 1;

            fbMessageIdMin = fbMessageIdMax + 1;
            twMessageIdMin = twMessageIdMax + 1;
        }

        return partitions;
    }

    private static void writePartitionElement(SourcePartition sourcePartition, TargetPartition partition,
            Element rootElement, Document doc) {
        // staff elements
        Element pe = doc.createElement("Partition");
        rootElement.appendChild(pe);

        // name element
        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode("" + partition.getName()));
        pe.appendChild(name);

        // host element
        Element host = doc.createElement("host");
        host.appendChild(doc.createTextNode("" + partition.getHost()));
        pe.appendChild(host);

        // path element
        Element path = doc.createElement("path");
        path.appendChild(doc.createTextNode("" + partition.getPath()));
        pe.appendChild(path);

        // fbUserKeyMin element
        Element fbUserKeyMin = doc.createElement("fbUserKeyMin");
        fbUserKeyMin.appendChild(doc.createTextNode("" + partition.getFbUserKeyMin()));
        pe.appendChild(fbUserKeyMin);

        // fbUserKeyMax element
        Element fbUserKeyMax = doc.createElement("fbUserKeyMax");
        fbUserKeyMax.appendChild(doc.createTextNode("" + partition.getFbUserKeyMax()));
        pe.appendChild(fbUserKeyMax);

        // twUserKeyMin element
        Element twUserKeyMin = doc.createElement("twUserKeyMin");
        twUserKeyMin.appendChild(doc.createTextNode("" + partition.getTwUserKeyMin()));
        pe.appendChild(twUserKeyMin);

        // twUserKeyMax element
        Element twUserKeyMax = doc.createElement("twUserKeyMax");
        twUserKeyMax.appendChild(doc.createTextNode("" + partition.getTwUserKeyMax()));
        pe.appendChild(twUserKeyMax);

        // fbMessgeKeyMin element
        Element fbMessageKeyMin = doc.createElement("fbMessageKeyMin");
        fbMessageKeyMin.appendChild(doc.createTextNode("" + partition.getFbMessageIdMin()));
        pe.appendChild(fbMessageKeyMin);

        // fbMessgeKeyMin element
        Element fbMessageKeyMax = doc.createElement("fbMessageKeyMax");
        fbMessageKeyMax.appendChild(doc.createTextNode("" + partition.getFbMessageIdMax()));
        pe.appendChild(fbMessageKeyMax);

        // twMessgeKeyMin element
        Element twMessageKeyMin = doc.createElement("twMessageKeyMin");
        twMessageKeyMin.appendChild(doc.createTextNode("" + partition.getTwMessageIdMin()));
        pe.appendChild(twMessageKeyMin);

        // twMessgeKeyMin element
        Element twMessageKeyMax = doc.createElement("twMessageKeyMax");
        twMessageKeyMax.appendChild(doc.createTextNode("" + partition.getTwMessageIdMax()));
        pe.appendChild(twMessageKeyMax);

        Element avgMsg = doc.createElement("avgMsgPerFBU");
        avgMsg.appendChild(doc.createTextNode("" + partition.getAvgMsgPerFBU()));
        pe.appendChild(avgMsg);

        Element avgTweet = doc.createElement("avgTweetPerTWU");
        avgTweet.appendChild(doc.createTextNode("" + partition.getAvgTweetPerTWU()));
        pe.appendChild(avgTweet);

    }
}