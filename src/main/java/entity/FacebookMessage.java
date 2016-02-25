package entity;

import socialGen.AdmAppendVisitor;
import socialGen.IAppendVisitor;
import datatype.DateTime;
import datatype.Message;
import datatype.Point;

public class FacebookMessage {

    private long messageId;
    private long authorId;
    private long inResponseTo;
    private Point senderLocation;
    private DateTime sendTime;
    private Message message;

    public long getMessageId() {
        return messageId;
    }

    public long getAuthorID() {
        return authorId;
    }

    public Point getSenderLocation() {
        return senderLocation;
    }

    public Message getMessage() {
        return message;
    }

    public long getInResponseTo() {
        return inResponseTo;
    }

    public FacebookMessage() {
    }

    public FacebookMessage(long messageId, long authorId, long inResponseTo, Point senderLocation, DateTime sendTime,
            Message message, boolean pcf) {
        this.messageId = messageId;
        this.authorId = authorId;
        this.inResponseTo = inResponseTo;
        this.senderLocation = senderLocation;
        this.sendTime = sendTime;
        this.message = message;
    }

    public void reset(long messageId, long authorId, long inResponseTo, Point senderLocation, DateTime sendTime,
            Message message/*, boolean pcf*/) {
        this.messageId = messageId;
        this.authorId = authorId;
        this.inResponseTo = inResponseTo;
        this.senderLocation = senderLocation;
        this.sendTime = sendTime;
        this.message = message;
    }

    public String toString() {
        return accept(new AdmAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        visitor.append("{\"message_id\": ").visit(messageId);
        visitor.append(", \"author_id\": ").visit(authorId);
        visitor.append(", \"in_response_to\": ").visit(inResponseTo);
        visitor.append(", \"sender_location\": ").visit(senderLocation);
        visitor.append(", \"send_time\": ").visit(sendTime);
        visitor.append(", \"message\": ").visit(message);
        return visitor.append("}");
    }
}
