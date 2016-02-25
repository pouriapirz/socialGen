package socialGen;

import datatype.Date;
import datatype.DateTime;
import datatype.Message;
import datatype.Point;
import entity.Employments;
import entity.FacebookMessage;
import entity.FacebookUser;
import entity.TweetMessage;
import entity.TwitterUser;

import java.util.List;

public interface IAppendVisitor {
    IAppendVisitor reset();

    // appends the string
    IAppendVisitor append(String s);

    // appends a string value (e.g. a string with quotes)
    IAppendVisitor visit(String s);

    IAppendVisitor visit(List<String> strs);

    IAppendVisitor visit(int i);

    IAppendVisitor visit(long l);

    IAppendVisitor visit(long[] l);

    IAppendVisitor visit(Date date);

    IAppendVisitor visit(DateTime datetime);

    IAppendVisitor visit(Message message);

    IAppendVisitor visit(Point point);

    IAppendVisitor visit(Employments employments);

    IAppendVisitor visit(FacebookMessage fbMessage);

    IAppendVisitor visit(FacebookUser fbUser);

    IAppendVisitor visit(TweetMessage twMessage);

    IAppendVisitor visit(TwitterUser twUser);
}
