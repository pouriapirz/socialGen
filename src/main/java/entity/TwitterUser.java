package entity;

import socialGen.AdmAppendVisitor;
import socialGen.IAppendVisitor;

public class TwitterUser {

    private String screenName;
    private String lang = "en";
    private int friendsCount;
    private int statusesCount;
    private String name;
    private int followersCount;

    public TwitterUser() {

    }

    public TwitterUser(String screenName, int friendsCount, int statusesCount, String name, int followersCount) {
        this.screenName = screenName;
        this.friendsCount = friendsCount;
        this.statusesCount = statusesCount;
        this.name = name;
        this.followersCount = followersCount;
    }

    public void reset(String screenName, int friendsCount, int statusesCount, String name, int followersCount) {
        this.screenName = screenName;
        this.friendsCount = friendsCount;
        this.statusesCount = statusesCount;
        this.name = name;
        this.followersCount = followersCount;
    }

    public String getScreenName() {
        return screenName;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public String getName() {
        return name;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public String toString() {
        return accept(new AdmAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        visitor.append("{\"screen_name\": ").visit(screenName);
        visitor.append(", \"lang\": ").visit(lang);
        visitor.append(", \"friends_count\": ").visit(friendsCount);
        visitor.append(", \"statuses_count\": ").visit(statusesCount);
        visitor.append(", \"name\": ").visit(name);
        visitor.append(", \"followers_count\": ").visit(followersCount);
        return visitor.append("}");
    }
}
