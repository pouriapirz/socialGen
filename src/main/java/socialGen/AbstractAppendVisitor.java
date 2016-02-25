package socialGen;

public abstract class AbstractAppendVisitor implements IAppendVisitor {
    protected final StringBuilder builder = new StringBuilder();

    public IAppendVisitor reset() {
        builder.setLength(0);
        return this;
    }

    public String toString() {
        return builder.toString();
    }

    public IAppendVisitor append(String s) {
        builder.append(s);
        return this;
    }
}
