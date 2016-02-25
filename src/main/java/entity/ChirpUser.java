package entity;

public class ChirpUser {

    private String screenName;
    private String lang = "en";
    private int friendsCount;
    private int statusesCount;
    private String name;
    private int followersCount;

    public ChirpUser() {

    }

    public ChirpUser(String screenName, int friendsCount, int statusesCount, String name, int followersCount) {
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
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"screen_name\":" + "\"" + screenName + "\"");
        builder.append(",");
        builder.append("\"lang\":" + "\"" + lang + "\"");
        builder.append(",");
        builder.append("\"friends_count\":" + friendsCount);
        builder.append(",");
        builder.append("\"statuses_count\":" + statusesCount);
        builder.append(",");
        builder.append("\"name\":" + "\"" + name + "\"");
        builder.append(",");
        builder.append("\"followers_count\":" + followersCount);
        builder.append("}");
        return builder.toString();
    }
}
