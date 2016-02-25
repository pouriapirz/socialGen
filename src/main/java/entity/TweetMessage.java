package entity;

import java.util.List;

import socialGen.AdmAppendVisitor;
import socialGen.IAppendVisitor;
import datatype.DateTime;
import datatype.Message;
import datatype.Point;

public class TweetMessage {

    private long tweetid;
    private TwitterUser user;
    private Point senderLocation;
    private DateTime sendTime;
    private List<String> referredTopics;
    private Message messageText;

    public TweetMessage() {

    }

    public TweetMessage(long tweetid, TwitterUser user, Point senderLocation, DateTime sendTime,
            List<String> referredTopics, Message messageText) {
        this.tweetid = tweetid;
        this.user = user;
        this.senderLocation = senderLocation;
        this.sendTime = sendTime;
        this.referredTopics = referredTopics;
        this.messageText = messageText;
    }

    public void reset(long tweetid, TwitterUser user, Point senderLocation, DateTime sendTime,
            List<String> referredTopics, Message messageText) {
        this.tweetid = tweetid;
        this.user = user;
        this.senderLocation = senderLocation;
        this.sendTime = sendTime;
        this.referredTopics = referredTopics;
        this.messageText = messageText;
    }

    public String toString() {
        return accept(new AdmAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        visitor.append("{\"tweetid\": ").visit(tweetid);
        visitor.append(", \"user\": ").visit(user);
        visitor.append(", \"sender_location\": ").visit(senderLocation);
        visitor.append(", \"send_time\": ").visit(sendTime);
        visitor.append(", \"referred_topics\": ").visit(referredTopics);
        visitor.append(", \"message_text\": ").visit(messageText);
        return visitor.append("}");
    }

}
