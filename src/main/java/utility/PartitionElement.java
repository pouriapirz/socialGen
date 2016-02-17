package utility;

public class PartitionElement implements ILibraryElement {
    private final String name;
    private final String host;
    private final long fbUserKeyMin;
    private final long fbUserKeyMax;
    private final long twUserKeyMin;
    private final long twUserKeyMax;
    private final long fbMessageIdMin;
    private final long fbMessageIdMax;
    private final long twMessageIdMin;
    private final long twMessageIdMax;

    public PartitionElement(String partitionName, String host, long fbUserKeyMin, long fbUserKeyMax, long twUserKeyMin,
            long twUserKeyMax, long fbMessageIdMin, long fbMessageIdMax, long twMessageIdMin, long twMessageIdMax) {
        this.name = partitionName;
        this.host = host;
        this.fbUserKeyMin = fbUserKeyMin;
        this.fbUserKeyMax = fbUserKeyMax;
        this.twUserKeyMin = twUserKeyMax;
        this.twUserKeyMax = twUserKeyMax;
        this.twMessageIdMin = twMessageIdMin;
        this.twMessageIdMax = twMessageIdMax;
        this.fbMessageIdMin = fbMessageIdMin;
        this.fbMessageIdMax = fbMessageIdMax;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(" ");
        builder.append(host);
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
        builder.append(twUserKeyMin);

        return new String(builder);
    }

    public String getName() {
        return "Partition";
    }

}

interface ILibraryElement {

    public enum ElementType {
        PARTITION
    }

    public String getName();

}