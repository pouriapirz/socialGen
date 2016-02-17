package entity;

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
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"message_id\": int64(\"");
        builder.append(messageId).append("\")");
        builder.append(",");
        builder.append("\"author_id\": int64(\"");
        builder.append(authorId);
        builder.append("\"), ");
        builder.append("\"in_response_to\": int64(\"");
        builder.append(inResponseTo);
        builder.append("\"),");
        builder.append("\"sender_location\":");
        builder.append(senderLocation);
        builder.append(",");
        builder.append("\"send_time\":" + sendTime);
        builder.append(",");
        builder.append("\"message\":");
        builder.append("\"");
        for (int i = 0; i < message.getLength(); i++) {
            builder.append(message.charAt(i));
        }
        builder.append("\"");
        builder.append("}");
        return new String(builder);
    }
}
