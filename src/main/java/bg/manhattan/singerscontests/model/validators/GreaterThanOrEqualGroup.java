package bg.manhattan.singerscontests.model.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GreaterThanOrEqualGroup {
    GreaterThanOrEqual[] value();
}
