/*
 * Copyright by The Regents of the University of California
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package entity;

import socialGen.ADMAppendVisitor;
import socialGen.IAppendVisitor;

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
        return accept(new ADMAppendVisitor()).toString();
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
