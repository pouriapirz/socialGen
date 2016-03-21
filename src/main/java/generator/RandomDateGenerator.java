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
package generator;

import java.util.Random;

import datatype.Date;
import datatype.DateTime;

public class RandomDateGenerator {

    protected final long sDate;
    protected final long eDate;
    protected long diff;
    private final Random random;
    private Date date;
    private DateTime dateTime;

    public RandomDateGenerator(Date startDate, Date endDate, long seed) {
        this.sDate = startDate.toTimestamp();
        this.eDate = endDate.toTimestamp();
        this.diff = (eDate - sDate) + 1;
        this.date = new Date();
        this.dateTime = new DateTime();
        this.random = new Random(seed);
    }

    public Date getNextRandomDate() {
        long newTs = sDate + (long) (random.nextDouble() * diff);
        date.reset(newTs);
        return new Date(date.getMonth(), date.getDay(), date.getYear());
    }

    public DateTime getNextRandomDatetime() {
        long newTs = sDate + (long) (random.nextDouble() * diff);
        dateTime.reset(newTs);
        return new DateTime(dateTime.getMonth(), dateTime.getDay(), dateTime.getYear(), dateTime.getHour(),
                dateTime.getMin(), dateTime.getSec());
    }

    public Date getNextRecentDate(Date base) {
        long b = base.toTimestamp();
        long d = (eDate - b) + 1;
        long newTs = b + (long) (random.nextDouble() * d);
        date.reset(newTs);
        return new Date(date.getMonth(), date.getDay(), date.getYear());
    }
}
