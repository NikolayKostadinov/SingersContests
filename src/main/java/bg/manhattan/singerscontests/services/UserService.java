package bg.manhattan.singerscontests.services;


import bg.manhattan.singerscontests.model.dto.UserLoginDto;
import bg.manhattan.singerscontests.model.dto.UserRegisterDto;

public interface UserService {
    boolean login(UserLoginDto loginDTO);

    void logout();

    void register(UserRegisterDto registerDto);
}
