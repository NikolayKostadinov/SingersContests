package bg.manhattan.singerscontests.model.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ExistingAgeGroupValidator.class)
public @interface ExistingAgeGroup {

    String birthDate();

    String editionId();

    String message() default "You must choose existing Age Group!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
