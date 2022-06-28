package bg.manhattan.singerscontests.model.service;

import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.entity.UserRole;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

import static bg.manhattan.singerscontests.model.ModelConstants.NAME_MAX_LENGTH;
import static bg.manhattan.singerscontests.model.ModelConstants.USER_NAME_MAX_LENGTH;

public class UserServiceModel {

    private String firstName;

    private String middleName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private boolean isActive;

}
