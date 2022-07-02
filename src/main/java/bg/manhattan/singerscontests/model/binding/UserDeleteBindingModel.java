package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotBlank;

public class UserDeleteBindingModel {
    @NotBlank(message = "Password is required!")
    private String password;

    public String getPassword() {
        return password;
    }

    public UserDeleteBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
