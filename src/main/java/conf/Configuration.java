package conf;

import java.io.IOException;
import java.util.List;

public class Configuration {

    private final long numOfFBU;
    private final long numOfTWU;
    private final int avgMsgPerFBU;
    private final int avgTweetPerTWU;

    private final List<SourcePartition> sourcePartitions;
    private List<TargetPartition> targetPartitions;

    public Configuration(long numOfFBU, long numOfTWU, int avgMsgFBU, int avgMsgTWU, List<SourcePartition> partitions)
            throws IOException {
        this.numOfFBU = numOfFBU;
        this.numOfTWU = numOfTWU;
        this.avgMsgPerFBU = avgMsgFBU;
        this.avgTweetPerTWU = avgMsgTWU;
        this.sourcePartitions = partitions;
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

    public List<SourcePartition> getSourcePartitions() {
        return sourcePartitions;
    }

    public List<TargetPartition> getTargetPartitions() {
        return targetPartitions;
    }

    public void setTargetPartitions(List<TargetPartition> targetPartitions) {
        this.targetPartitions = targetPartitions;
    }

}
