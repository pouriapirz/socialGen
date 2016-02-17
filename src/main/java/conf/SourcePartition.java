package conf;

public class SourcePartition {

    private final String name;
    private final String host;
    private final String path;

    public SourcePartition(String name, String host, String path) {
        this.name = name;
        this.host = host;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

}
