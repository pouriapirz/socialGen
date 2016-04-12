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

public class Date {

    protected int day;
    protected int month;
    protected int year;

    protected Calendar c;

    public Date() {
        this.c = new GregorianCalendar();
        c.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.c = new GregorianCalendar();
        c.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public void reset(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return accept(new ADMAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        return visitor.append(year + "-" + toDigits(month) + "-" + toDigits(day));
    }

    protected String toDigits(int i) {
        return (i < 10 ? "0" + i : "" + i);
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long toTimestamp() {
        c.clear();
        c.set(year, (month - 1), day); //In the Gregorian Calendar, first month is 0
        return c.getTimeInMillis();
    }

    public void reset(long ts) {
        c.clear();
        c.setTimeInMillis(ts);
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH) + 1;
        this.day = c.get(Calendar.DAY_OF_MONTH);
    }

    public void reset(Date d) {
        this.year = d.getYear();
        this.month = d.getMonth();
        this.day = d.getDay();
        c.clear();
        c.set(year, (month - 1), day);
    }
}
