package entity;

import java.util.List;

import socialGen.ADMAppendVisitor;
import socialGen.IAppendVisitor;
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
        return accept(new ADMAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        visitor.append("{\"chirpid\": ").visit(chirpId);
        visitor.append(", \"user\": ").visit(user);
        visitor.append(", \"sender_location\": ").visit(senderLocation);
        visitor.append(", \"send_time\": ").visit(sendTime);
        visitor.append(", \"referred_topics\": ").visit(referredTopics);
        visitor.append(", \"message_text\": ").visit(messageText);
        return visitor.append("}");
    }

}
