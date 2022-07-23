package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.enums.UserRoleEnum;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class RoleEditBindingModel {

    @NotNull(message = "Role is required!")
    private UserRoleEnum userRole;
    private List<Long> idsToAdd;
    private List<Long> idsToDelete;

    public RoleEditBindingModel() {
        this.idsToAdd = new ArrayList<>();
        this.idsToDelete = new ArrayList<>();
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public RoleEditBindingModel setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }

    public List<Long> getIdsToAdd() {
        return idsToAdd;
    }

    public RoleEditBindingModel setIdsToAdd(List<Long> idsToAdd) {
        this.idsToAdd = idsToAdd;
        return this;
    }

    public List<Long> getIdsToDelete() {
        return idsToDelete;
    }

    public RoleEditBindingModel setIdsToDelete(List<Long> idsToDelete) {
        this.idsToDelete = idsToDelete;
        return this;
    }
}
