package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.validators.UniqueEmail;

import javax.validation.constraints.Email;

public class EmailChangeBindingModel {

    private String email;

    @Email(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Enter valid email address")
    @UniqueEmail
    private String newEmail;

    public String getEmail() {
        return email;
    }

    public EmailChangeBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public EmailChangeBindingModel setNewEmail(String newEmail) {
        this.newEmail = newEmail;
        return this;
    }
}
