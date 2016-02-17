package conf;

public class PartitionConfiguration {

    private final TargetPartition targetPartition;
    private final SourcePartition sourcePartition;

    public PartitionConfiguration(SourcePartition sourcePartition, TargetPartition targetPartition) {
        this.sourcePartition = sourcePartition;
        this.targetPartition = targetPartition;
    }

    public TargetPartition getTargetPartition() {
        return targetPartition;
    }

    public SourcePartition getSourcePartition() {
        return sourcePartition;
    }
}
