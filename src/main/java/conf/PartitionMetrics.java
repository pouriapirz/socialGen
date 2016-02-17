package conf;

public class PartitionMetrics {

    private final long numOfFBU;
    private final long numOfTWU;
    private final int avgMsgPerFBU;
    private final int avgTweetPerTWU;

    public PartitionMetrics(long numOfFBU, long numOfTWU, int avgMsgFBU, int avgMsgTWU, long numOfPartitions) {
        this.numOfFBU = numOfFBU / numOfPartitions;
        this.numOfTWU = numOfTWU / numOfPartitions;
        this.avgMsgPerFBU = avgMsgFBU;
        this.avgTweetPerTWU = avgMsgTWU;
    }

    public long getNumOfFBU() {
        return numOfFBU;
    }

    public long getNumOfTWU() {
        return numOfTWU;
    }

    public int getAvgMsgPerFBU() {
        return avgMsgPerFBU;
    }

    public int getAvgTweetPerTWU() {
        return avgTweetPerTWU;
    }
}
