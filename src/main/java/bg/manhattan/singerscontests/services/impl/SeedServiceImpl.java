package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.JuryMember;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.entity.UserRole;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.repositories.UserRepository;
import bg.manhattan.singerscontests.repositories.UserRoleRepository;
import bg.manhattan.singerscontests.services.SeedService;
import bg.manhattan.singerscontests.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class SeedServiceImpl implements SeedService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ContestRepository contestRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService appUserDetailsService;
    private final String adminPass;

    public SeedServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           ContestRepository contestRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsService appUserDetailsService,
                           @Value("${app.default.admin.password}") String adminPass) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.contestRepository = contestRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
        this.adminPass = adminPass;
    }

    @Override
    @Transactional
    public void seed() {

        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
            UserRole adminRole = new UserRole().setUserRole(UserRoleEnum.ADMIN);
            UserRole contestManagerRole = new UserRole().setUserRole(UserRoleEnum.CONTEST_MANAGER);
            UserRole juryMemberRole = new UserRole().setUserRole(UserRoleEnum.JURY_MEMBER);

            adminRole = userRoleRepository.save(adminRole);
            contestManagerRole = userRoleRepository.save(contestManagerRole);
            juryMemberRole = userRoleRepository.save(juryMemberRole);

            seedAdmin(List.of(adminRole, contestManagerRole, juryMemberRole));
            seedContestManagers(List.of(contestManagerRole));
            seedJuryMembers(List.of(juryMemberRole));
            //todo: create Users

        }

        if (contestRepository.count() == 0) {
            seedContests();
        }
    }

    private void seedContests() {
        List<User> managers = this.userRepository.findByRole(UserRoleEnum.CONTEST_MANAGER);
        managers.addAll(this.userRepository.findByRole(UserRoleEnum.ADMIN));

        List<Contest> contests = new ArrayList<>();

        List.of("Silver Yantra",
                        "Flower tuning fork",
                        "Sarandev",
                        "River notes",
                        "Danube stars",
                        "Fifteen tulips",
                        "The road to the stars",
                        "Artship Festival Pomorie",
                        "Art Stars",
                        "Coast Happiness",
                        "Abanico",
                        "Sofia Grandprix",
                        "Tallents Of Tomorrow",
                        "Bravo festival",
                        "Slavic bazaar in Vitebsk",
                        "Song pallet")
                .forEach(contestName ->
                        contests.add(new Contest()
                                .setName(contestName)
                                .setManagers(new HashSet<>(getRandomContestManagers(managers)))));
        contestRepository.saveAll(contests);
    }

    private List<User> getRandomContestManagers(List<User> managers) {
        int managersCount = Utils.getRandomNumberInRange(1, 3);
        List<User> result = new ArrayList<>();
        for (int i = 0; i < managersCount; i++) {
            result.add(managers.get(Utils.getRandomNumberInRange(0, managers.size() - 1)));
        }

        return result;
    }

    private void seedAdmin(List<UserRole> roles) {
        User admin = new User()
                .setRoles(new HashSet<>(roles))
                .setUsername("admin")
                .setEmail("kostadinov.nikolay.d@gmail.com")
                .setPassword(passwordEncoder.encode(adminPass))
                .setPhoneNumber("+359 886 630 111");
        admin.setFirstName("Nikolay");
        admin.setMiddleName("Dimitrov");
        admin.setLastName("Kostadinov");

        userRepository.save(admin);
    }

    private void seedContestManagers(List<UserRole> roles) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User()
                    .setUsername("manager" + i)
                    .setRoles(new HashSet<>(roles))
                    .setEmail("manager" + i + "@example.com")
                    .setPassword(passwordEncoder.encode(adminPass))
                    .setPhoneNumber("123123123");
            user.setFirstName("Manager");
            user.setMiddleName("Manager");
            user.setLastName("Manager " + i);
            users.add(user);
        }

        userRepository.saveAll(users);
    }


    private void seedJuryMembers(List<UserRole> roles) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User()
                    .setUsername("jury" + i)
                    .setRoles(new HashSet<>(roles))
                    .setEmail("jury" + i + "@example.com")
                    .setPassword(passwordEncoder.encode(adminPass))
                    .setPhoneNumber("123123123")
                    .setJuryMember(new JuryMember()
                            .setDetails("Details for jury member " + i)
                            .setImageUrl("https://theatre.peakview.bg/theatre/photos/BIG1371021236haigashot-agasqn.jpg"));
            user.setFirstName("Jury");
            user.setMiddleName("Jury");
            user.setLastName("Jury " + i);
            users.add(user);
        }

        userRepository.saveAll(users);


    }


//    private void initJuryMembers(List<UserRoleEntity> roles) {
//        UserEntity user = new UserEntity().
//                setUserRoles(roles).
//                setFirstName("User").
//                setLastName("Userov").
//                setEmail("user@example.com").
//                setPassword(passwordEncoder.encode(adminPass));
//
//        userRepository.save(user);
//    }
//
//    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {
//        UserEntity newUser =
//                new UserEntity().
//                        setEmail(userRegisterDTO.getEmail()).
//                        setFirstName(userRegisterDTO.getFirstName()).
//                        setLastName(userRegisterDTO.getLastName()).
//                        setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
//
//        userRepository.save(newUser);
//
//        UserDetails userDetails =
//                appUserDetailsService.loadUserByUsername(newUser.getEmail());
//
//        Authentication auth =
//                new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        userDetails.getPassword(),
//                        userDetails.getAuthorities()
//                );
//
//        SecurityContextHolder.clearContext();
//                setAuthentication(auth);
//    }
}
