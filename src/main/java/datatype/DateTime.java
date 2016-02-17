package datatype;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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
        StringBuilder builder = new StringBuilder();
        builder.append("datetime");
        builder.append("(\"");
        builder.append(super.getYear());
        builder.append("-");
        builder.append(super.getMonth() < 10 ? "0" + super.getMonth() : super.getMonth());
        builder.append("-");
        builder.append(super.getDay() < 10 ? "0" + super.getDay() : super.getDay());
        builder.append("T");

        String hourString = (hour < 10) ? ("0" + hour) : ("" + hour);
        String minString = (minute < 10) ? ("0" + minute) : ("" + minute);
        String secString = (second < 10) ? ("0" + second) : ("" + second);

        builder.append(hourString + ":" + minString + ":" + secString);
        builder.append("\")");
        return builder.toString();
    }

    public long toTimestamp() {
        c.set(year, (month - 1), day, hour, minute, second);
        return c.getTimeInMillis();
    }

    public void reset(long ts) {
        c.setTimeInMillis(ts);
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH) + 1;
        this.day = c.get(Calendar.DAY_OF_MONTH);
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);
        this.second = c.get(Calendar.SECOND);
    }
}
