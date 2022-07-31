package bg.manhattan.singerscontests.model.validators;

import bg.manhattan.singerscontests.services.AgeGroupService;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ExistingAgeGroupValidator implements ConstraintValidator<ExistingAgeGroup, Object> {
    private AgeGroupService ageGroupService;

    private String birthDateField;
    private String editionIdField;
    private String message;

    public ExistingAgeGroupValidator(AgeGroupService ageGroupService) {
        this.ageGroupService = ageGroupService;
    }

    @Override
    public void initialize(ExistingAgeGroup constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.birthDateField = constraintAnnotation.birthDate();
        this.editionIdField = constraintAnnotation.editionId();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        LocalDate birthDate = LocalDate.parse(beanWrapper.getPropertyValue(this.birthDateField).toString());
        Long etitionId = (Long) beanWrapper.getPropertyValue(this.editionIdField);
        boolean isValid = this.ageGroupService.isBirthDateInRange(etitionId, birthDate);
        if (!isValid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(this.birthDateField)
                    .addConstraintViolation();
        }
        return isValid;
    }
}
