package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.model.entity.JuryMember;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;
import bg.manhattan.singerscontests.repositories.JuryMemberRepository;
import bg.manhattan.singerscontests.services.JuryMemberService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JuryMemberServiceImpl implements JuryMemberService {
    private final JuryMemberRepository repository;
    private final ModelMapper mapper;

    public JuryMemberServiceImpl(JuryMemberRepository repository,
                                 ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Set<JuryMember> getAllById(Set<Long> juryMembers) {
        return new HashSet<>(this.repository.findAllById(juryMembers));
    }

    @Override
    public List<JuryMemberServiceModel> getAll() {
        return this.repository.findAll()
                .stream()
                .map(member -> this.mapper.map(member, JuryMemberServiceModel.class))
                .toList();
    }
}
