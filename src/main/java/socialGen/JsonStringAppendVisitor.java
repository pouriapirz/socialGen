package socialGen;

/**
 * a variant of the JsonAppendVisitor that writes identifiers (longs) as quoted strings
 */
public class JsonStringAppendVisitor extends JsonAppendVisitor {
    public IAppendVisitor visit(long l) {
        builder.append('"').append(l).append('"');
        return this;
    }
}
