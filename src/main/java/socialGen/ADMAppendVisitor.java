package socialGen;

import datatype.Date;
import datatype.DateTime;
import datatype.Point;

public class ADMAppendVisitor extends AbstractAppendVisitor implements IAppendVisitor {

    String startList() {
        return "[";
    }

    String endList() {
        return "]";
    }

    String startBag() {
        return "{{";
    }

    String endBag() {
        return "}}";
    }

    public IAppendVisitor visit(long l) {
        builder.append("int64(\"").append(l).append("\")");
        return this;
    }

    public IAppendVisitor visit(Date date) {
        builder.append("date(\"");
        date.accept(this);
        builder.append("\")");
        return this;
    }

    public IAppendVisitor visit(DateTime datetime) {
        builder.append("datetime(\"");
        datetime.accept(this);
        builder.append("\")");
        return this;
    }

    public IAppendVisitor visit(Point point) {
        builder.append("point(\"").append(point.getLatitude()).append(",").append(point.getLongitude()).append("\")");
        return this;
    }
}
