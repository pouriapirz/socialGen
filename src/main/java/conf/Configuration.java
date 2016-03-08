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

import java.io.IOException;
import java.util.List;

public class Configuration {

    private final long numOfGBookUsers;
    private final long numOfChirpUsers;
    private final int avgMsgPerGBookUser;
    private final int avgMsgPerChirpUser;
    private final long seed;

    private final List<SourcePartition> sourcePartitions;
    private List<TargetPartition> targetPartitions;

    public Configuration(long seed, long numOfGBookUsers, long numOfChirpUsers, int avgMsgGBookUser,
            int avgMsgChirpUser, List<SourcePartition> partitions) throws IOException {
        this.seed = seed;
        this.numOfGBookUsers = numOfGBookUsers;
        this.numOfChirpUsers = numOfChirpUsers;
        this.avgMsgPerGBookUser = avgMsgGBookUser;
        this.avgMsgPerChirpUser = avgMsgChirpUser;
        this.sourcePartitions = partitions;
    }

    public long getNumOfGBookUsers() {
        return numOfGBookUsers;
    }

    public long getNumOfChirpUsers() {
        return numOfChirpUsers;
    }

    public int getAvgMsgGBookUser() {
        return avgMsgPerGBookUser;
    }

    public int getAvgMsgChirpUser() {
        return avgMsgPerChirpUser;
    }

    public long getSeed() {
        return seed;
    }

    public List<SourcePartition> getSourcePartitions() {
        return sourcePartitions;
    }

    public List<TargetPartition> getTargetPartitions() {
        return targetPartitions;
    }

    public void setTargetPartitions(List<TargetPartition> targetPartitions) {
        this.targetPartitions = targetPartitions;
    }

}
