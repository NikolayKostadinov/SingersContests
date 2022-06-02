package bg.manhattan.singerscontests.common;

import java.time.LocalDateTime;

public abstract class DateTimeProvider {
    private static DateTimeProvider current = new DefaultTimeProvider();

    public static DateTimeProvider getCurrent() {
        return current;
    }

    public static void setCurrent(DateTimeProvider current) {
        DateTimeProvider.current = current;
    }
    public abstract LocalDateTime utcNow ();

    public static void ResetToDefault()
    {
        DateTimeProvider.current = new DefaultTimeProvider();
    }
}
