package bg.manhattan.singerscontests.model.validators;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GreaterThanOrEqualValidator implements ConstraintValidator<GreaterThanOrEqual, Object> {
    private String first;
    private String second;
    private String message;

    @Override
    public void initialize(GreaterThanOrEqual constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
        Comparable firstValue = (Comparable) beanWrapper.getPropertyValue(this.first);
        Comparable secondValue = (Comparable) beanWrapper.getPropertyValue(this.second);

        boolean isValid;

        if (firstValue == null) {
            isValid = secondValue == null;
        } else {
            isValid = firstValue.compareTo(secondValue) <= 0;
        }

        if (!isValid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(this.first)
                    .addConstraintViolation();

            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(this.second)
                    .addConstraintViolation();
        }
        return isValid;
    }
}
