package bg.manhattan.singerscontests.model.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserServiceModel {

    private String firstName;

    private String middleName;

    private String lastName;

    private String fullName;

    private String username;

    private String email;

    private String password;

    private final Set<String> roles;

    public UserServiceModel() {
        this.roles = new HashSet<>();
    }

    private boolean isActive;


    public String getFirstName() {
        return firstName;
    }

    public UserServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserServiceModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserServiceModel setActive(boolean active) {
        isActive = active;
        return this;
    }

    public String getFullName() {
        if (this.fullName == null) {
            this.fullName = Stream.of(this.getFirstName(), this.getMiddleName(), this.getLastName())
                    .filter(n -> n != null && !n.isEmpty())
                    .collect(Collectors.joining(" "));
        }
        return this.fullName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void addRole(String role){
        this.roles.add(role);
    }
}
