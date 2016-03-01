package conf;

public class PartitionMetrics {

    private final long numOfGBookUsers;
    private final long numOfChirpUsers;
    private final int avgMsgPerGBookUser;
    private final int avgMsgPerChirpUser;
    private final long seed;

    public PartitionMetrics(long seed, long numOfGBookUsers, long numOfChirpUsers, int avgMsgGBookUser,
            int avgMsgChirpUser, long numOfPartitions) {
        this.numOfGBookUsers = numOfGBookUsers / numOfPartitions;
        this.numOfChirpUsers = numOfChirpUsers / numOfPartitions;
        this.avgMsgPerGBookUser = avgMsgGBookUser;
        this.avgMsgPerChirpUser = avgMsgChirpUser;
        this.seed = seed;
    }

    public long getNumOfGBookUsers() {
        return numOfGBookUsers;
    }

    public long getNumOfChirpUsers() {
        return numOfChirpUsers;
    }

    public int getAvgMsgPerGBookUser() {
        return avgMsgPerGBookUser;
    }

    public int getAvgMsgPerChirpUser() {
        return avgMsgPerChirpUser;
    }

    public long getSeed() {
        return seed;
    }
}
