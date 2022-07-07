package bg.manhattan.singerscontests.model.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = GreaterThanOrEqualValidator.class)
@Repeatable(GreaterThanOrEqualGroup.class)
public @interface GreaterThanOrEqual {

    String first();
    String second();

    String message() default "Invalid Date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}