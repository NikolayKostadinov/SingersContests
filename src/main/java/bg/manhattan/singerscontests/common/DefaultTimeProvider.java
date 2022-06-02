package bg.manhattan.singerscontests.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DefaultTimeProvider extends DateTimeProvider {

    @Override
    public LocalDateTime utcNow() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
