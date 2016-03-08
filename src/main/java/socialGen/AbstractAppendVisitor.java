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
package socialGen;

import java.util.List;

import datatype.Message;
import entity.ChirpMessage;
import entity.ChirpUser;
import entity.Employments;
import entity.GleambookMessage;
import entity.GleambookUser;

public abstract class AbstractAppendVisitor implements IAppendVisitor {
    protected final StringBuilder builder = new StringBuilder();

    public IAppendVisitor reset() {
        builder.setLength(0);
        return this;
    }

    public String toString() {
        return builder.toString();
    }

    abstract String startList();

    abstract String endList();

    abstract String startBag();

    abstract String endBag();

    public IAppendVisitor append(String s) {
        builder.append(s);
        return this;
    }

    public IAppendVisitor visit(String s) {
        builder.append('"').append(s).append('"');
        return this;
    }

    public IAppendVisitor visit(List<String> strs) {
        builder.append(startBag());
        final int size = strs.size();
        for (int i = 0; i < size - 1; i++) {
            visit(strs.get(i));
            builder.append(", ");
        }
        if (size > 0) {
            visit(strs.get(size - 1));
        }
        builder.append(endBag());
        return this;
    }

    public IAppendVisitor visit(int i) {
        builder.append(i);
        return this;
    }

    public IAppendVisitor visit(long[] l) {
        builder.append(startBag());
        for (int i = 0; i < l.length - 1; i++) {
            visit(l[i]);
            builder.append(", ");
        }
        if (l.length > 0) {
            visit(l[l.length - 1]);
        }
        builder.append(endBag());
        return this;
    }

    public IAppendVisitor visit(Message message) {
        builder.append('"');
        for (int i = 0; i < message.getLength(); i++) {
            builder.append(message.charAt(i));
        }
        builder.append('"');
        return this;
    }

    public IAppendVisitor visit(Employments employment) {
        builder.append(startList());
        int empCount = employment.size();
        for (int i = 0; i < empCount - 1; i++) {
            employment.get(i).accept(this);
            builder.append(", ");
        }
        employment.get(empCount - 1).accept(this);
        builder.append(endList());
        return this;
    }

    public IAppendVisitor visit(GleambookMessage gleambookMessage) {
        return gleambookMessage.accept(this);
    }

    public IAppendVisitor visit(GleambookUser gleambookUser) {
        return gleambookUser.accept(this);
    }

    public IAppendVisitor visit(ChirpMessage chirpMessage) {
        return chirpMessage.accept(this);
    }

    public IAppendVisitor visit(ChirpUser chirpUser) {
        return chirpUser.accept(this);
    }
}
