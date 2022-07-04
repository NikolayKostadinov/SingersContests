package bg.manhattan.singerscontests.model.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AtLeastOneMangerValidator.class)
public @interface AtLeastOneNotDeleted {
    String message() default "There must be at least one record!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
