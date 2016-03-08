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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import socialGen.ADMAppendVisitor;
import socialGen.IAppendVisitor;

public class DateTime extends Date {

    private int hour;
    private int minute;
    private int second;

    private Calendar c;

    public DateTime() {
        this.c = new GregorianCalendar();
        c.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public DateTime(int month, int day, int year, int hour, int min, int sec) {
        super(month, day, year);
        this.hour = hour;
        this.minute = min;
        this.second = sec;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return minute;
    }

    public int getSec() {
        return second;
    }

    public void reset(int month, int day, int year, int hour, int min, int sec) {
        super.setMonth(month);
        super.setDay(day);
        super.setYear(year);
        this.hour = hour;
        this.minute = min;
        this.second = sec;
    }

    public String toString() {
        return accept(new ADMAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        super.accept(visitor);
        visitor.append("T");
        visitor.append(toDigits(hour) + ":" + toDigits(minute) + ":" + toDigits(second));
        return visitor;
    }

    public long toTimestamp() {
        c.clear();
        c.set(year, (month - 1), day, hour, minute, second);
        return c.getTimeInMillis();
    }

    public void reset(long ts) {
        c.clear();
        c.setTimeInMillis(ts);
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH) + 1;
        this.day = c.get(Calendar.DAY_OF_MONTH);
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);
        this.second = c.get(Calendar.SECOND);
    }
}
