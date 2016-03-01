package conf;

public class SourcePartition {

    private final long id;
    private final String host;
    private final String path;

    public SourcePartition(long id, String host, String path) {
        this.id = id;
        this.host = host;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

}
