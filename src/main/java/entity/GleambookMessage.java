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

import datatype.DateTime;
import datatype.Message;
import datatype.Point;
import socialGen.ADMAppendVisitor;
import socialGen.IAppendVisitor;

public class GleambookMessage {

    private long messageId;
    private long authorId;
    private long inResponseTo;
    private Point senderLocation;
    private DateTime sendTime;
    private Message message;

    public long getMessageId() {
        return messageId;
    }

    public long getAuthorID() {
        return authorId;
    }

    public Point getSenderLocation() {
        return senderLocation;
    }

    public Message getMessage() {
        return message;
    }

    public long getInResponseTo() {
        return inResponseTo;
    }

    public GleambookMessage() {
    }

    public GleambookMessage(long messageId, long authorId, long inResponseTo, Point senderLocation, DateTime sendTime,
            Message message, boolean pcf) {
        this.messageId = messageId;
        this.authorId = authorId;
        this.inResponseTo = inResponseTo;
        this.senderLocation = senderLocation;
        this.sendTime = sendTime;
        this.message = message;
    }

    public void reset(long messageId, long authorId, long inResponseTo, Point senderLocation, DateTime sendTime,
            Message message) {
        this.messageId = messageId;
        this.authorId = authorId;
        this.inResponseTo = inResponseTo;
        this.senderLocation = senderLocation;
        this.sendTime = sendTime;
        this.message = message;
    }

    public String toString() {
        return accept(new ADMAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        visitor.append("{\"message_id\": ").visit(messageId);
        visitor.append(", \"author_id\": ").visit(authorId);
        visitor.append(", \"in_response_to\": ").visit(inResponseTo);
        visitor.append(", \"sender_location\": ").visit(senderLocation);
        visitor.append(", \"send_time\": ").visit(sendTime);
        visitor.append(", \"message\": ").visit(message);
        return visitor.append("}");
    }
}
