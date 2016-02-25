package socialGen;

import datatype.Date;
import datatype.DateTime;
import datatype.Point;

public class JsonAppendVisitor extends AbstractAppendVisitor implements IAppendVisitor {

    String startList() {
        return "[";
    }

    String endList() {
        return "]";
    }

    String startBag() {
        return "[";
    }

    String endBag() {
        return "]";
    }

    public IAppendVisitor visit(long l) {
        builder.append(l);
        return this;
    }

    public IAppendVisitor visit(Date date) {
        builder.append("\"");
        date.accept(this);
        builder.append("\"");
        return this;
    }

    public IAppendVisitor visit(DateTime datetime) {
        builder.append("\"");
        datetime.accept(this);
        builder.append("\"");
        return this;
    }

    public IAppendVisitor visit(Point point) {
        builder.append("\"").append(point.getLatitude()).append(",").append(point.getLongitude()).append("\"");
        return this;
    }
}
