package conf;

public class TargetPartition {
    private final String name;
    private final String host;
    private final String path;
    private final long fbUserKeyMin;
    private final long fbUserKeyMax;
    private final long twUserKeyMin;
    private final long twUserKeyMax;
    private final long fbMessageIdMin;
    private final long fbMessageIdMax;
    private final long twMessageIdMin;
    private final long twMessageIdMax;
    private final int avgMsgPerFBU;
    private final int avgTweetPerTWU;

    public TargetPartition(String partitionName, String host, String path, long fbUserKeyMin, long fbUserKeyMax,
            long twUserKeyMin, long twUserKeyMax, long fbMessageIdMin, long fbMessageIdMax, long twMessageIdMin,
            long twMessageIdMax, int avgMsgPerFBU, int avgTweetPerTWU) {
        this.name = partitionName;
        this.host = host;
        this.path = path;
        this.fbUserKeyMin = fbUserKeyMin;
        this.fbUserKeyMax = fbUserKeyMax;
        this.twUserKeyMin = twUserKeyMin;
        this.twUserKeyMax = twUserKeyMax;
        this.twMessageIdMin = twMessageIdMin;
        this.twMessageIdMax = twMessageIdMax;
        this.fbMessageIdMin = fbMessageIdMin;
        this.fbMessageIdMax = fbMessageIdMax;
        this.avgMsgPerFBU = avgMsgPerFBU;
        this.avgTweetPerTWU = avgTweetPerTWU;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(" ");
        builder.append(host);
        builder.append("\n");
        builder.append(path);
        builder.append("\n");
        builder.append("fbUser:key:min");
        builder.append(fbUserKeyMin);

        builder.append("\n");
        builder.append("fbUser:key:max");
        builder.append(fbUserKeyMax);

        builder.append("\n");
        builder.append("twUser:key:min");
        builder.append(twUserKeyMin);

        builder.append("\n");
        builder.append("twUser:key:max");
        builder.append(twUserKeyMax);

        builder.append("\n");
        builder.append("fbMessage:key:min");
        builder.append(fbMessageIdMin);

        builder.append("\n");
        builder.append("fbMessage:key:max");
        builder.append(fbMessageIdMax);

        builder.append("\n");
        builder.append("twMessage:key:min");
        builder.append(twMessageIdMin);

        builder.append("\n");
        builder.append("twMessage:key:max");
        builder.append(twMessageIdMax);

        builder.append("\n");
        builder.append("twMessage:key:max");
        builder.append(twMessageIdMax);

        return new String(builder);
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public long getFbUserKeyMin() {
        return fbUserKeyMin;
    }

    public long getFbUserKeyMax() {
        return fbUserKeyMax;
    }

    public long getTwUserKeyMin() {
        return twUserKeyMin;
    }

    public long getTwUserKeyMax() {
        return twUserKeyMax;
    }

    public long getFbMessageIdMin() {
        return fbMessageIdMin;
    }

    public long getFbMessageIdMax() {
        return fbMessageIdMax;
    }

    public long getTwMessageIdMin() {
        return twMessageIdMin;
    }

    public long getTwMessageIdMax() {
        return twMessageIdMax;
    }

    public int getAvgMsgPerFBU() {
        return avgMsgPerFBU;
    }

    public int getAvgTweetPerTWU() {
        return avgTweetPerTWU;
    }

    public String getPath() {
        return path;
    }
}