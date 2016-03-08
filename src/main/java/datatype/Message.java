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
package datatype;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private char[] message = new char[500];
    private List<String> referredTopics;
    private int length;

    public Message(char[] m, List<String> referredTopics) {
        System.arraycopy(m, 0, message, 0, m.length);
        length = m.length;
        this.referredTopics = referredTopics;
    }

    public Message() {
        referredTopics = new ArrayList<String>();
        length = 0;
    }

    public char[] getMessage() {
        return message;
    }

    public List<String> getReferredTopics() {
        return referredTopics;
    }

    public void reset(char[] m, int offset, int length, List<String> referredTopics) {
        System.arraycopy(m, offset, message, 0, length);
        this.length = length;
        this.referredTopics = referredTopics;
    }

    public int getLength() {
        return length;
    }

    public char charAt(int index) {
        return message[index];
    }
}
