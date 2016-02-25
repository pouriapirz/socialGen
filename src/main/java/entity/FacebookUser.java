package entity;

import java.util.ArrayList;
import java.util.List;

import datatype.DateTime;
import socialGen.AdmAppendVisitor;
import socialGen.IAppendVisitor;

public class FacebookUser {

    private long id;
    private String alias;
    private String name;
    private DateTime userSince;
    private long[] friendIds;
    private Employments employment;

    public FacebookUser() {

    }

    public FacebookUser(long id, String alias, String name, DateTime userSince, long[] friendIds,
                        Employments employment) {
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

    public DateTime getUserSince() {
        return userSince;
    }

    public long[] getFriendIds() {
        return friendIds;
    }

    public Employments getEmployment() {
        return employment;
    }

    public String toString() {
        return accept(new AdmAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        visitor.append("{\"id\": ").visit(id);
        visitor.append(", \"alias\": ").visit(alias);
        visitor.append(", \"name\": ").visit(name);
        visitor.append(", \"user_since\": ").visit(userSince);
        visitor.append(", \"friend_ids\": ").visit(friendIds);
        visitor.append(", \"employment\": ").visit(employment);
        return visitor.append("}");
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

    public void setUserSince(DateTime userSince) {
        this.userSince = userSince;
    }

    public void setFriendIds(long[] friendIds) {
        this.friendIds = friendIds;
    }

    public void setEmployment(Employments employment) {
        if (this.employment == null) {
            this.employment = new Employments(employment.size());
        }
        this.employment.set(employment);
    }

    public void reset(long id, String alias, String name, DateTime userSince, long[] friendIds,
                      Employments employment) {
        this.id = id;
        this.alias = alias;
        this.name = name;
        this.userSince = userSince;
        this.friendIds = friendIds;
        setEmployment(employment);
    }
}
