package bg.manhattan.singerscontests.model.binding;


import bg.manhattan.singerscontests.model.validators.GreaterThanOrEqual;
import bg.manhattan.singerscontests.model.validators.PasswordComplexity;
import bg.manhattan.singerscontests.model.validators.UniqueUserName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static bg.manhattan.singerscontests.model.ModelConstants.*;

@GreaterThanOrEqual(first = "password", second = "confirmPassword", message = "Passwords not match!")
public class UserRegisterBindingModel {

    @NotBlank(message = "First name is required")
    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String firstName;

    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String lastName;

    @NotNull(message = "Username is required")
    @Size(min=USER_NAME_MIN_LENGTH,  max = USER_NAME_MAX_LENGTH,
            message = "User name length must be between {min} and {max} characters!")
    @UniqueUserName
    private String username;

    @Email(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Enter valid email address")
    private String email; // username of the user.

    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;

    @NotNull
    @PasswordComplexity
    private String password; // password of the user.

    @NotNull
    @PasswordComplexity
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserRegisterBindingModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegisterBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
