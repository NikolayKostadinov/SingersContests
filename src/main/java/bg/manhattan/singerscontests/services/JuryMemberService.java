package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.entity.JuryMember;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface JuryMemberService {
    Set<JuryMember> getAllById(Set<Long> juryMembers);

    List<JuryMemberServiceModel> getAll();
}
