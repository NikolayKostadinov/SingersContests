package bg.manhattan.singerscontests.model.binding;


import bg.manhattan.singerscontests.model.IHaveNames;
import bg.manhattan.singerscontests.model.validators.FieldMatch;
import bg.manhattan.singerscontests.model.validators.UniqueUserName;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static bg.manhattan.singerscontests.model.ModelConstants.*;

@FieldMatch(first = "password", second = "confirmPassword", message = "Password and Confirm password must be the same!")
public class UserRegisterBindingModel implements IHaveNames {

    @NotBlank(message = "First name is required")
    @Size(max = NAME_MAX_LENGTH, message = "First name must be shorter than {max} characters!")
    private String firstName;

    @Size(max = NAME_MAX_LENGTH, message = "Middle name must be shorter than {max} characters!")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = NAME_MAX_LENGTH, message = "Last name must be shorter than{max} characters!")
    private String lastName;

    @NotNull(message = "Username is required")
    @Size(min=USER_NAME_MIN_LENGTH,  max = USER_NAME_MAX_LENGTH,
            message = "Username length must be between {min} and {max} characters!")
    @UniqueUserName
    private String username;

    @Email(regexp = "^(\\w+@\\w+)(.\\w+){2,}$", message = "Enter valid email address")
    private String email; // username of the user.

    private String password; // password of the user.

    private String confirmPassword;

    private String imageUrl; // an url of user's picture.

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

    public String getImageUrl() {
        return imageUrl;
    }

    public UserRegisterBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
