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
package conf;

public class PartitionMetrics {

    private final long numOfGBookUsers;
    private final long numOfChirpUsers;
    private final int avgMsgPerGBookUser;
    private final int avgMsgPerChirpUser;
    private final long seed;

    public PartitionMetrics(long seed, long numOfGBookUsers, long numOfChirpUsers, int avgMsgGBookUser,
            int avgMsgChirpUser, long numOfPartitions) {
        this.numOfGBookUsers = numOfGBookUsers / numOfPartitions;
        this.numOfChirpUsers = numOfChirpUsers / numOfPartitions;
        this.avgMsgPerGBookUser = avgMsgGBookUser;
        this.avgMsgPerChirpUser = avgMsgChirpUser;
        this.seed = seed;
    }

    public long getNumOfGBookUsers() {
        return numOfGBookUsers;
    }

    public long getNumOfChirpUsers() {
        return numOfChirpUsers;
    }

    public int getAvgMsgPerGBookUser() {
        return avgMsgPerGBookUser;
    }

    public int getAvgMsgPerChirpUser() {
        return avgMsgPerChirpUser;
    }

    public long getSeed() {
        return seed;
    }
}
