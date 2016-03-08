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

public class SourcePartition {

    private final long id;
    private final String host;
    private final String path;

    public SourcePartition(long id, String host, String path) {
        this.id = id;
        this.host = host;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

}
