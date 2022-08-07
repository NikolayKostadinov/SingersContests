package bg.manhattan.singerscontests.model.view;

import bg.manhattan.singerscontests.model.enums.UserRoleEnum;

import java.util.ArrayList;
import java.util.List;

public class JuryDemoteViewModel {
    private UserRoleEnum userRole;
    private List<UserSelectViewModel> members;

    public JuryDemoteViewModel() {
        this.members = new ArrayList<>();
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public JuryDemoteViewModel setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }

    public List<UserSelectViewModel> getMembers() {
        return members;
    }

    public JuryDemoteViewModel setMembers(List<UserSelectViewModel> members) {
        this.members = members;
        return this;
    }
}
