package entity;

import java.util.List;

import datatype.DateTime;
import datatype.Message;
import datatype.Point;

public class ChirpMessage {

    private long chirpId;
    private ChirpUser user;
    private Point senderLocation;
    private DateTime sendTime;
    private List<String> referredTopics;
    private Message messageText;

    public ChirpMessage() {

    }

    public ChirpMessage(long chirpId, ChirpUser user, Point senderLocation, DateTime sendTime,
            List<String> referredTopics, Message messageText) {
        this.chirpId = chirpId;
        this.user = user;
        this.senderLocation = senderLocation;
        this.sendTime = sendTime;
        this.referredTopics = referredTopics;
        this.messageText = messageText;
    }

    public void reset(long chirpId, ChirpUser user, Point senderLocation, DateTime sendTime,
            List<String> referredTopics, Message messageText) {
        this.chirpId = chirpId;
        this.user = user;
        this.senderLocation = senderLocation;
        this.sendTime = sendTime;
        this.referredTopics = referredTopics;
        this.messageText = messageText;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"chirpid\":");
        builder.append(" int64(\"").append(chirpId).append("\")");
        builder.append(", ");
        builder.append("\"user\":");
        builder.append(user);
        builder.append(",");
        builder.append("\"sender_location\":");
        builder.append(senderLocation);
        builder.append(",");
        builder.append("\"send_time\":");
        builder.append(sendTime);
        builder.append(",");
        builder.append("\"referred_topics\":");
        builder.append("{{");
        for (String topic : referredTopics) {
            builder.append("\"" + topic + "\"");
            builder.append(",");
        }
        if (referredTopics.size() > 0) {
            builder.deleteCharAt(builder.lastIndexOf(","));
        }
        builder.append("}}");
        builder.append(",");
        builder.append("\"message_text\":");
        builder.append("\"");
        for (int i = 0; i < messageText.getLength(); i++) {
            builder.append(messageText.charAt(i));
        }
        builder.append("\"");
        builder.append("}");
        return new String(builder);
    }
}
