package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.JuryMember;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;
import bg.manhattan.singerscontests.repositories.JuryMemberRepository;
import bg.manhattan.singerscontests.services.JuryMemberService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JuryMemberServiceImpl implements JuryMemberService {
    private final JuryMemberRepository repository;
    private final ModelMapper mapper;

    public JuryMemberServiceImpl(UserService userService, JuryMemberRepository repository,
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

    @Override
    public JuryMemberServiceModel getJuryMemberById(Long id) {
        JuryMember juryMember = this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("JuryMember", id));
        return this.mapper.map(juryMember, JuryMemberServiceModel.class);
    }
}
