package entity;

import java.util.ArrayList;
import java.util.List;

public class FacebookUser {

    private long id;
    private String alias;
    private String name;
    private String userSince;
    private long[] friendIds;
    private List<Employment> employment;

    public FacebookUser() {

    }

    public FacebookUser(long id, String alias, String name, String userSince, long[] friendIds,
            List<Employment> employment) {
        this.id = id;
        this.alias = alias;
        this.name = name;
        this.userSince = userSince;
        this.friendIds = friendIds;
        setEmployment(employment);
    }

    public long getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getUserSince() {
        return userSince;
    }

    public long[] getFriendIds() {
        return friendIds;
    }

    public List<Employment> getEmployment() {
        return employment;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"id\": int64(\"").append(id).append("\")");
        builder.append(", ");
        builder.append("\"alias\":" + "\"" + alias + "\"");
        builder.append(", ");
        builder.append("\"name\":" + "\"" + name + "\"");
        builder.append(", ");
        builder.append("\"user_since\":" + userSince);
        builder.append(", ");
        builder.append("\"friend_ids\":");
        builder.append("{{");
        for (int i = 0; i < friendIds.length; i++) {
            builder.append("int64(\"").append(friendIds[i] + "\")");
            builder.append(", ");
        }
        if (friendIds.length > 0) {
            builder.deleteCharAt(builder.lastIndexOf(","));
        }
        builder.append("}}");
        builder.append(", ");
        builder.append("\"employment\":");
        builder.append("[");
        int empCount = employment.size();
        for (int i = 0; i < empCount - 1; i++) {
            builder.append(employment.get(i).toString());
            builder.append(", ");
        }
        builder.append(employment.get(empCount - 1).toString());
        builder.append("]");
        builder.append("}");
        return builder.toString();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserSince(String userSince) {
        this.userSince = userSince;
    }

    public void setFriendIds(long[] friendIds) {
        this.friendIds = friendIds;
    }

    public void setEmployment(List<Employment> employment) {
        if (this.employment == null) {
            this.employment = new ArrayList<Employment>();
        }
        this.employment.clear();
        for (Employment e : employment) {
            this.employment.add(e);
        }
    }

    public void reset(long id, String alias, String name, String userSince, long[] friendIds,
            List<Employment> employment) {
        this.id = id;
        this.alias = alias;
        this.name = name;
        this.userSince = userSince;
        this.friendIds = friendIds;
        setEmployment(employment);
    }
}
