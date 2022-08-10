package bg.manhattan.singerscontests.test_utility;

import bg.manhattan.singerscontests.util.DateTimeProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FakeDateTimeProvider extends DateTimeProvider {
    private final int year;
    private final int month;
    private final int dayOfMonth;
    private final int hour;
    private final int minute;

    public FakeDateTimeProvider(int year, int month, int dayOfMonth, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hour = hour;
        this.minute = minute;
    }

    public FakeDateTimeProvider(LocalDate date) {
        this(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),0,0);
    }

    public FakeDateTimeProvider(LocalDateTime date) {
        this(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute());
    }

    @Override
    public LocalDateTime utcNow() {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }
}
