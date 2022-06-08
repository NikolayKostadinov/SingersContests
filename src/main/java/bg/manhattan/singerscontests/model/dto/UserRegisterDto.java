package bg.manhattan.singerscontests.model.dto;


import bg.manhattan.singerscontests.model.validators.PasswordComplexity;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static bg.manhattan.singerscontests.model.ModelConstants.*;

public class UserRegisterDto {



    @NotBlank
    @Size(max = NAME_MAX_LENGTH)
    private String firstName;

    @Size(max = NAME_MAX_LENGTH)
    private String middleName;

    @NotBlank
    @Size(max = NAME_MAX_LENGTH)
    private String lastName;

    @NotBlank
    @Size(min=USER_NAME_MIN_LENGTH,  max = USER_NAME_MAX_LENGTH)
    private String username;

    @Column(nullable = false, unique = true)
    private String email; // username of the user.

    @PasswordComplexity
    private String password; // password of the user.


    private String confirmPassword;

    private boolean isActive;

    private String imageUrl; // an url of user's picture.

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserRegisterDto setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserRegisterDto setActive(boolean active) {
        isActive = active;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserRegisterDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
