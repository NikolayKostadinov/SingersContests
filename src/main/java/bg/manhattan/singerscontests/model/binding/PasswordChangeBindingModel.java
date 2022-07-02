package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.validators.FieldMatch;
import bg.manhattan.singerscontests.model.validators.PasswordComplexity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FieldMatch(first = "newPassword", second = "confirmPassword", message = "Passwords not match!")
public class PasswordChangeBindingModel {
    @NotBlank(message = "Curren password is required!")
    private String currentPassword;

    @NotBlank(message = "New password is required!")
    @PasswordComplexity
    private String newPassword; // password of the user.

    @NotBlank(message = "Confirm password is required!")
    @PasswordComplexity
    private String confirmPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public PasswordChangeBindingModel setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public PasswordChangeBindingModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public PasswordChangeBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
