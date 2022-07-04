package bg.manhattan.singerscontests.model.validators;

import bg.manhattan.singerscontests.model.binding.ManagerBindingModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class AtLeastOneMangerValidator implements ConstraintValidator<AtLeastOneNotDeleted,
        List<ManagerBindingModel>> {
    @Override
    public void initialize(AtLeastOneNotDeleted constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<ManagerBindingModel> records, ConstraintValidatorContext context) {
        return records.stream().anyMatch(d -> !d.isDeleted());
    }
}
