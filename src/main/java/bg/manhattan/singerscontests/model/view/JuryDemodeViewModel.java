package bg.manhattan.singerscontests.model.view;

import bg.manhattan.singerscontests.model.enums.UserRoleEnum;

import java.util.ArrayList;
import java.util.List;

public class JuryDemodeViewModel {
    private UserRoleEnum userRole;
    private List<UserSelectViewModel> members;

    public JuryDemodeViewModel() {
        this.members = new ArrayList<>();
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public JuryDemodeViewModel setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }

    public List<UserSelectViewModel> getMembers() {
        return members;
    }

    public JuryDemodeViewModel setMembers(List<UserSelectViewModel> members) {
        this.members = members;
        return this;
    }
}
