package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.model.entity.*;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.model.enums.EditionType;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.repositories.*;
import bg.manhattan.singerscontests.services.AgeGroupService;
import bg.manhattan.singerscontests.services.SeedService;
import bg.manhattan.singerscontests.util.DateTimeProvider;
import bg.manhattan.singerscontests.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

@Service
@Transactional
public class SeedServiceImpl implements SeedService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeedServiceImpl.class);
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ContestRepository contestRepository;
    private final EditionRepository editionRepository;
    private final JuryMemberRepository juryMemberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AgeGroupService ageGroupService;

    private final String adminPass;

    private final ContestantRepository contestantRepository;

    private final Properties seedProps;

    public SeedServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           ContestRepository contestRepository,
                           EditionRepository editionRepository,
                           JuryMemberRepository juryMemberRepository,
                           PasswordEncoder passwordEncoder,
                           AgeGroupService ageGroupService,
                           @Value("${app.default.admin.password}") String adminPass,
                           ContestantRepository contestantRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.contestRepository = contestRepository;
        this.editionRepository = editionRepository;
        this.juryMemberRepository = juryMemberRepository;
        this.passwordEncoder = passwordEncoder;
        this.ageGroupService = ageGroupService;
        this.adminPass = adminPass;
        this.contestantRepository = contestantRepository;


        this.seedProps = new Properties();
        try {
            ClassPathResource classPathResource = new ClassPathResource("seed.properties");
            seedProps.load(classPathResource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void seed() {
        LOGGER.info("----------------- Begin DB Initialization ------------------");
        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
            LOGGER.info("-----------------      Seed Roles         ------------------");
            UserRole adminRole = new UserRole().setUserRole(UserRoleEnum.ADMIN);
            UserRole contestManagerRole = new UserRole().setUserRole(UserRoleEnum.CONTEST_MANAGER);
            UserRole juryMemberRole = new UserRole().setUserRole(UserRoleEnum.JURY_MEMBER);

            adminRole = userRoleRepository.save(adminRole);
            contestManagerRole = userRoleRepository.save(contestManagerRole);
            juryMemberRole = userRoleRepository.save(juryMemberRole);

            seedAdmin(List.of(adminRole, contestManagerRole, juryMemberRole));
            seedContestManagers(List.of(contestManagerRole));
            seedJuryMembers(List.of(juryMemberRole));
            seedUsers();
        }

        if (contestRepository.count() == 0) {
            List<Contest> contests = seedContests();
            List<Edition> editions = seedEditions(contests);
            seedContestants(editions);
        }
        LOGGER.info("----------------- DB Initialized and ready -----------------");
    }

    @Override
    @Transactional
    public void seedRatingsForFirstEdition() {
        Edition edition = this.editionRepository.findById(1L).orElse(new Edition());
        changeEditionDate(edition);
        Set<JuryMember> juryMembers = edition.getJuryMembers();
        BinaryOperator<BigDecimal> increment = getBigDecimalBinaryIncrementOperator();

        for (JuryMember juryMember : juryMembers) {
            AtomicReference<BigDecimal> rating = new AtomicReference<>(BigDecimal.ONE);
            edition.getContestants().forEach(
                    contestant -> contestant.getSongs()
                            .forEach(song -> {
                                BigDecimal artistry = rating.getAndAccumulate(BigDecimal.valueOf(0.5), increment);
                                BigDecimal intonation = rating.getAndAccumulate(BigDecimal.valueOf(0.5), increment);
                                BigDecimal repertoire = rating.getAndAccumulate(BigDecimal.valueOf(0.5), increment);
                                BigDecimal avgScore = artistry
                                        .add(intonation)
                                        .add(repertoire)
                                        .divide(BigDecimal.valueOf(3), MathContext.DECIMAL128);

                                song.getRatings().add(new Rating()
                                        .setSong(song)
                                        .setArtistry(artistry)
                                        .setIntonation(intonation)
                                        .setRepertoire(repertoire)
                                        .setAverageScore(avgScore)
                                        .setJuryMember(juryMember.getUser())
                                );
                            })
            );
        }

        this.editionRepository.save(edition);
    }

    private void seedContestants(List<Edition> editions) {
        LOGGER.info("-----------------      Seed Contestants   ------------------");
        User user = this.userRepository.findByUsername("user1").orElse(null);
        ClassPathResource classPathResource = new ClassPathResource("seed_contestants.txt");
        editions.forEach(edition -> {
                    List<Contestant> contestants = new ArrayList<>();
                    try {
                        InputStreamReader in = new InputStreamReader(classPathResource.getInputStream());//reads the file
                        BufferedReader br = new BufferedReader(in);  //creates a buffering character input stream
                        char[] bom = new char[1];
                        int bomBytes = br.read(bom);
                        String line;
                        while ((line = br.readLine()) != null) {
                            contestants.add(parseContestant(line).setRegistrar(user));
                        }
                        in.close();    //closes the stream and release the resources
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    contestants.forEach(contestant -> {
                        List<PerformanceCategory> categories = edition.getPerformanceCategories().stream().toList();
                        contestant.getSongs().get(0).setCategory(categories.get(0)).setContestant(contestant);
                        contestant.getSongs().get(1).setCategory(categories.get(0)).setContestant(contestant);
                        contestant.setEdition(edition);
                        contestant.setAgeGroup(this.ageGroupService.getAgeGroupEntity(edition,
                                contestant.getBirthDay()));
                    });
                    this.contestantRepository.saveAll(contestants);
                }
        );
    }

    private Contestant parseContestant(String line) {
        String[] tokens = Arrays.stream(line.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        return ((Contestant) new Contestant()
                .setFirstName(tokens[0])
                .setMiddleName(tokens[1].isBlank() ? null : tokens[1])
                .setLastName(tokens[2]))
                .setImageUrl(this.seedProps.getProperty("anonymous.picture"))
                .setBirthDay(LocalDate.of(Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8])))
                .setCity(tokens[3])
                .setCountry(tokens[4])
                .setInstitution(tokens[5])
                .setSongs(List.of(
                        new Song()
                                .setName(tokens[9])
                                .setComposerFullName(tokens[10])
                                .setArrangerFullName(tokens[11])
                                .setLyricistFullName(tokens[12])
                                .setInstrumentalUrl("https://res.cloudinary.com/dsylyy9iz/raw/upload/v1659174204/1.%20%D0%96%D0%B0%D0%BA%D0%BB%D0%B8%D0%BD%20%D0%9D%D0%B8%D0%BA%D0%BE%D0%BB%D0%B0%D0%B5%D0%B2%D0%B0%20%D0%9A%D0%BE%D1%81%D1%82%D0%B0%D0%B4%D0%B8%D0%BD%D0%BE%D0%B2%D0%B0%20%D0%9E%D1%81%D1%82%D0%B0%D0%BD%D0%B8%20%D1%82%D0%B0%D0%B7%D0%B8%20%D0%BD%D0%BE%D1%89.mp3")
                                .setDuration(Utils.getRandomNumberInRange(150, 240)),
                        new Song()
                                .setName(tokens[13])
                                .setComposerFullName(tokens[14])
                                .setArrangerFullName(tokens[15])
                                .setLyricistFullName(tokens[16])
                                .setInstrumentalUrl("https://res.cloudinary.com/dsylyy9iz/raw/upload/v1659186305/2.%20%D0%96%D0%B0%D0%BA%D0%BB%D0%B8%D0%BD%20%D0%9D%D0%B8%D0%BA%D0%BE%D0%BB%D0%B0%D0%B5%D0%B2%D0%B0%20%D0%9A%D0%BE%D1%81%D1%82%D0%B0%D0%B4%D0%B8%D0%BD%D0%BE%D0%B2%D0%B0%20And%20I%20am%20Telling%20You.mp3")
                                .setDuration(Utils.getRandomNumberInRange(150, 240))));
    }

    private List<Edition> seedEditions(List<Contest> contests) {
        LOGGER.info("-----------------      Seed Editions      ------------------");
        LocalDate today = DateTimeProvider.getCurrent().utcNow().toLocalDate();
        List<JuryMember> juryMembers = this.juryMemberRepository.findAll();
        List<Edition> editions = new ArrayList<>();
        contests.forEach(contest -> {
            for (int i = 0; i < 14; i++) {
                Edition edition = new Edition()
                        .setEditionType(i % 2 == 0 ? EditionType.ATTENDING : EditionType.ONLINE)
                        .setNumber(i + 1)
                        .setBeginDate(today.plusDays(i + 15))
                        .setEndDate(today.plusDays(i + 17))
                        .setBeginOfSubscriptionDate(today.plusDays(i - 5))
                        .setEndOfSubscriptionDate(today.plusDays(i - 3))
                        .setAgeCalculationType(
                                switch (i % 3) {
                                    case 0 -> AgeCalculationType.START_OF_CONTEST;
                                    case 1 -> AgeCalculationType.START_OF_YEAR;
                                    default -> AgeCalculationType.YEAR_OF_BIRTH;
                                }
                        )
                        .setRegulations(seedProps.getProperty("regulations"))
                        .setContest(contest)
                        .setJuryMembers(new HashSet<>(getRandomJuryMembersManagers(juryMembers)));
                edition.setPerformanceCategories(
                                new HashSet<>(Arrays.asList(
                                        new PerformanceCategory().setName("Song in native language").setDisplayNumber(1).setRequired(true).setEdition(edition),
                                        new PerformanceCategory().setName("Song of choice").setDisplayNumber(2).setRequired(true).setEdition(edition)
                                )))
                        .setAgeGroups(
                                new HashSet<>(Arrays.asList(
                                        new AgeGroup().setName("I").setMinAge(5).setMaxAge(8).setDisplayNumber(1).setEdition(edition),
                                        new AgeGroup().setName("II").setMinAge(9).setMaxAge(12).setDisplayNumber(2).setEdition(edition),
                                        new AgeGroup().setName("III").setMinAge(13).setMaxAge(16).setDisplayNumber(3).setEdition(edition),
                                        new AgeGroup().setName("IV").setMinAge(17).setMaxAge(25).setDisplayNumber(4).setEdition(edition)
                                )));
                editions.add(edition);
            }
        });

        this.editionRepository.saveAll(editions);
        return editions;
    }

    private List<Contest> seedContests() {
        LOGGER.info("-----------------      Seed Contests      ------------------");
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
        return contests;
    }

    private List<JuryMember> getRandomJuryMembersManagers(List<JuryMember> juryMembers) {
        int managersCount = Utils.getRandomNumberInRange(2, 4);
        List<JuryMember> result = new ArrayList<>();
        for (int i = 0; i < managersCount; i++) {
            result.add(juryMembers.get(Utils.getRandomNumberInRange(0, juryMembers.size() - 1)));
        }
        return result;
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
        LOGGER.info("-----------------      Seed Admin         ------------------");
        LOGGER.info("----------------- u/n: admin; pass: P@ssw0rd ---------------");
        LOGGER.info("!!!!!!!! Please change admin password immediately !!!!!!!!!!");
        User admin = new User()
                .setRoles(new HashSet<>(roles))
                .setUsername("admin")
                .setEmail("kostadinov.nikolay.d@gmail.com")
                .setPassword(passwordEncoder.encode(adminPass))
                .setPhoneNumber("+359 888 888 888");
        admin.setFirstName("Admin");
        admin.setMiddleName("Admin");
        admin.setLastName("Admin");

        userRepository.save(admin);
    }

    private void seedContestManagers(List<UserRole> roles) {
        LOGGER.info("-----------------      Seed Contest Managers ---------------");
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
        LOGGER.info("-----------------      Seed Jury Members  ------------------");
        List<User> users = new ArrayList<>();
        User jury = new User()
                .setUsername("jury")
                .setRoles(new HashSet<>(roles))
                .setEmail("jury@example.com")
                .setPassword(passwordEncoder.encode(adminPass))
                .setPhoneNumber("123123123");
        jury.setFirstName("Jury");
        jury.setMiddleName("Jury");
        jury.setLastName("Jury");
        users.add(jury);

        User haygo = new User()
                .setUsername("haygo")
                .setRoles(new HashSet<>(roles))
                .setEmail("haygo@example.com")
                .setPassword(passwordEncoder.encode(adminPass))
                .setPhoneNumber("123123123");
        haygo.setFirstName("Haigashod");
        haygo.setMiddleName("Azad");
        haygo.setLastName("Agasyan");
        users.add(haygo);

        User loboshki = new User()
                .setUsername("loboshki")
                .setRoles(new HashSet<>(roles))
                .setEmail("loboshki@example.com")
                .setPassword(passwordEncoder.encode(adminPass))
                .setPhoneNumber("123123123");
        loboshki.setFirstName("Svetoslav");
        loboshki.setLastName("Loboshki");
        users.add(loboshki);

        User krasi = new User()
                .setUsername("krasi")
                .setRoles(new HashSet<>(roles))
                .setEmail("krasi@example.com")
                .setPassword(passwordEncoder.encode(adminPass))
                .setPhoneNumber("123123123");
        krasi.setFirstName("Krasimir");
        krasi.setLastName("Gyulmezov");
        users.add(krasi);

        User etien = new User()
                .setUsername("etien")
                .setRoles(new HashSet<>(roles))
                .setEmail("etien@example.com")
                .setPassword(passwordEncoder.encode(adminPass))
                .setPhoneNumber("123123123");
        etien.setFirstName("Etienne");
        etien.setMiddleName("Herzel");
        etien.setLastName("Levi");
        users.add(etien);
        userRepository.saveAll(users);

        List<JuryMember> juryMembers = new ArrayList<>();

        juryMembers.add(new JuryMember()
                .setUser(jury)
                .setDetails(this.seedProps.getProperty("anonymous.biography"))
                .setImageUrl(this.seedProps.getProperty("anonymous.picture")));

        juryMembers.add(new JuryMember()
                .setUser(haygo)
                .setDetails(this.seedProps.getProperty("haygo.biography"))
                .setImageUrl(this.seedProps.getProperty("haygo.picture")));

        juryMembers.add(new JuryMember()
                .setUser(loboshki)
                .setDetails(this.seedProps.getProperty("loboshki.biography"))
                .setImageUrl(this.seedProps.getProperty("loboshki.picture")));

        juryMembers.add(new JuryMember()
                .setUser(krasi)
                .setDetails(this.seedProps.getProperty("krasi.biography"))
                .setImageUrl(this.seedProps.getProperty("krasi.picture")));

        juryMembers.add(new JuryMember()
                .setUser(etien)
                .setDetails(this.seedProps.getProperty("etien.biography"))
                .setImageUrl(this.seedProps.getProperty("etien.picture")));

        juryMemberRepository.saveAll(juryMembers);
    }

    private void seedUsers() {
        LOGGER.info("-----------------      Seed Users         ------------------");
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User()
                    .setUsername("user" + i)
                    .setEmail("user" + i + "@example.com")
                    .setPassword(passwordEncoder.encode(adminPass))
                    .setPhoneNumber("123123123");
            user.setFirstName("User");
            user.setMiddleName("User");
            user.setLastName("User " + i);
            users.add(user);
        }

        userRepository.saveAll(users);
    }

    private BinaryOperator<BigDecimal> getBigDecimalBinaryIncrementOperator() {
        BinaryOperator<BigDecimal> increment = (s, step) -> {
            BigDecimal result = s.add(step);
            if (result.compareTo(BigDecimal.valueOf(10)) > 0) {
                result = BigDecimal.ONE;
            }
            return result;
        };
        return increment;
    }

    private void changeEditionDate(Edition edition) {
        LocalDateTime today = DateTimeProvider.getCurrent().utcNow();
        edition.setBeginOfSubscriptionDate(today.minusDays(3).toLocalDate());
        edition.setEndOfSubscriptionDate(today.minusDays(2).toLocalDate());
        edition.setBeginDate(today.minusDays(1).toLocalDate());
        edition.setEndDate(today.toLocalDate());
    }
}
