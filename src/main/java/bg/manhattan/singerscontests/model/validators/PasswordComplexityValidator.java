package bg.manhattan.singerscontests.model.validators;


import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordComplexityValidator implements ConstraintValidator<PasswordComplexity, String> {


    @Override
    public void initialize(PasswordComplexity constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                //Rule 1: Password length should be in between 8 and 16 characters
                new LengthRule(8, 16),
                //Rule 2: No whitespace allowed
                new WhitespaceRule(),
                //Rule 3.a: At least one Upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                //Rule 3.b: At least one Lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                //Rule 3.c: At least one digit
                new CharacterRule(EnglishCharacterData.Digit, 1),
                //Rule 3.d: At least one special character
                new CharacterRule(EnglishCharacterData.Special, 1)));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        validator.getMessages(result)
                .forEach(message ->
                        context.buildConstraintViolationWithTemplate(message).addConstraintViolation());
        return false;
    }
}
