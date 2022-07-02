package bg.manhattan.singerscontests.model.service;

public class UserServiceProfileDetailsModel {
    private String firstName;

    private String middleName;

    private String lastName;

    private String username;

    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public UserServiceProfileDetailsModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserServiceProfileDetailsModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserServiceProfileDetailsModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserServiceProfileDetailsModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserServiceProfileDetailsModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
