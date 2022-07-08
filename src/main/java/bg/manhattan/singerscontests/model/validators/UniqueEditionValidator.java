package bg.manhattan.singerscontests.model.validators;

import bg.manhattan.singerscontests.repositories.EditionRepository;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEditionValidator implements ConstraintValidator<UniqueEdition, Object> {
    private final EditionRepository editionRepository;
    private String numberField;
    private String contestIdField;
    private String message;

    public UniqueEditionValidator(EditionRepository editionRepository) {
        this.editionRepository = editionRepository;
    }

    @Override
    public void initialize(UniqueEdition constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.numberField = constraintAnnotation.numberField();
        this.contestIdField = constraintAnnotation.contestIdField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        Integer number = (Integer) beanWrapper.getPropertyValue(this.numberField);
        Long contestId = (Long) beanWrapper.getPropertyValue(this.contestIdField);
        boolean isValid = this.editionRepository.findOneByContestIdAndNumber(contestId, number).isEmpty();

        if (!isValid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(this.numberField)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
