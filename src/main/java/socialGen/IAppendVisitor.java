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

import datatype.Date;
import datatype.DateTime;
import datatype.Message;
import datatype.Point;
import entity.ChirpMessage;
import entity.ChirpUser;
import entity.Employments;
import entity.GleambookMessage;
import entity.GleambookUser;

public interface IAppendVisitor {
    IAppendVisitor reset();

    // appends the string
    IAppendVisitor append(String s);

    // appends a string value (e.g. a string with quotes)
    IAppendVisitor visit(String s);

    IAppendVisitor visit(List<String> strs);

    IAppendVisitor visit(int i);

    IAppendVisitor visit(long l);

    IAppendVisitor visit(long[] l);

    IAppendVisitor visit(Date date);

    IAppendVisitor visit(DateTime datetime);

    IAppendVisitor visit(Message message);

    IAppendVisitor visit(Point point);

    IAppendVisitor visit(Employments employments);

    IAppendVisitor visit(GleambookMessage gleambookMessage);

    IAppendVisitor visit(GleambookUser gleambookUser);

    IAppendVisitor visit(ChirpMessage chirpMessage);

    IAppendVisitor visit(ChirpUser chirpUser);
}
