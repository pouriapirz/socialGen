package socialGen;

import java.util.List;

import datatype.Date;
import datatype.DateTime;
import datatype.Message;
import datatype.Point;
import entity.ChirpMessage;
import entity.ChirpUser;
import entity.Employments;
import entity.GleambookMessage;
import entity.GleambookUser;

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

    IAppendVisitor visit(GleambookMessage gleambookMessage);

    IAppendVisitor visit(GleambookUser gleambookUser);

    IAppendVisitor visit(ChirpMessage chirpMessage);

    IAppendVisitor visit(ChirpUser chirpUser);
}
