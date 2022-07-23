package bg.manhattan.singerscontests.model.view;

import bg.manhattan.singerscontests.model.enums.UserRoleEnum;

import java.util.ArrayList;
import java.util.List;

public class RoleEditViewModel {
    private UserRoleEnum userRole;
    private List<UserSelectViewModel> members;
    private List<UserSelectViewModel> nonMembers;

    public RoleEditViewModel() {
        this.members = new ArrayList<>();
        this.nonMembers = new ArrayList<>();
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public RoleEditViewModel setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }

    public List<UserSelectViewModel> getMembers() {
        return members;
    }

    public RoleEditViewModel setMembers(List<UserSelectViewModel> members) {
        this.members = members;
        return this;
    }

    public List<UserSelectViewModel> getNonMembers() {
        return nonMembers;
    }

    public RoleEditViewModel setNonMembers(List<UserSelectViewModel> nonMembers) {
        this.nonMembers = nonMembers;
        return this;
    }
}
