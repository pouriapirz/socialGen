package socialGen;

import java.util.List;

import datatype.Date;
import datatype.DateTime;
import datatype.Message;
import datatype.Point;
import entity.Employments;
import entity.FacebookMessage;
import entity.FacebookUser;
import entity.TweetMessage;
import entity.TwitterUser;

public class AdmAppendVisitor extends AbstractAppendVisitor implements IAppendVisitor {

    public IAppendVisitor visit(String s) {
        builder.append('"').append(s).append('"');
        return this;
    }

    public IAppendVisitor visit(List<String> strs) {
        builder.append("{{");
        final int size = strs.size();
        for (int i = 0; i < size - 1; i++) {
            visit(strs.get(i));
            builder.append(", ");
        }
        if (size > 0) {
            visit(strs.get(size - 1));
        }
        builder.append("}}");
        return this;
    }

    public IAppendVisitor visit(int i) {
        builder.append(i);
        return this;
    }

    public IAppendVisitor visit(long l) {
        builder.append("int64(\"").append(l).append("\")");
        return this;
    }

    public IAppendVisitor visit(long[] l) {
        builder.append("{{");
        for (int i = 0; i < l.length - 1; i++) {
            visit(l[i]);
            builder.append(", ");
        }
        if (l.length > 0) {
            visit(l[l.length - 1]);
        }
        builder.append("}}");
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

    public IAppendVisitor visit(Message message) {
        builder.append('"');
        for (int i = 0; i < message.getLength(); i++) {
            builder.append(message.charAt(i));
        }
        builder.append('"');
        return this;
    }

    public IAppendVisitor visit(Point point) {
        builder.append("point(\"").append(point.getLatitude()).append(",").append(point.getLongitude()).append("\")");
        return this;
    }

    public IAppendVisitor visit(Employments employment) {
        builder.append("[");
        int empCount = employment.size();
        for (int i = 0; i < empCount - 1; i++) {
            employment.get(i).accept(this);
            builder.append(", ");
        }
        employment.get(empCount - 1).accept(this);
        builder.append("]");
        return this;
    }

    public IAppendVisitor visit(FacebookMessage fbMessage) {
        return fbMessage.accept(this);
    }

    public IAppendVisitor visit(FacebookUser fbUser) {
        return fbUser.accept(this);
    }

    public IAppendVisitor visit(TweetMessage twMessage) {
        return twMessage.accept(this);
    }

    public IAppendVisitor visit(TwitterUser twUser) {
        return twUser.accept(this);
    }
}
