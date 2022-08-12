package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static bg.manhattan.singerscontests.model.ModelConstants.*;

public class ProfileDetailsBindingModel {
    @NotNull(message = "Username is required")
    @Size(min=USER_NAME_MIN_LENGTH,  max = USER_NAME_MAX_LENGTH,
            message = "User name length must be between {min} and {max} characters!")
    private String username;

    @NotBlank(message = "First name is required")
    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String firstName;

    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String lastName;

    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;

    public String getUsername() {
        return username;
    }

    public ProfileDetailsBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getFirstName() {
        return firstName;
    }

    public ProfileDetailsBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ProfileDetailsBindingModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileDetailsBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ProfileDetailsBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
