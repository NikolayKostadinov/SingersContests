package bg.manhattan.singerscontests.config;

import bg.manhattan.singerscontests.model.binding.*;
import bg.manhattan.singerscontests.model.entity.*;
import bg.manhattan.singerscontests.model.service.*;
import bg.manhattan.singerscontests.model.view.ContestEditionsViewModel;
import bg.manhattan.singerscontests.model.view.EditionListViewModel;
import bg.manhattan.singerscontests.model.view.JuryDetailsViewModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ModelMapperConfiguration {
    private final PasswordEncoder passwordEncoder;

    public ModelMapperConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.class, LocalDate.class);


        Converter<String, String> passwordHash = ctx -> ctx.getSource() == null ? null :
                passwordEncoder.encode(ctx.getSource());

        // encode password on the fly
        mapper.createTypeMap(UserRegisterBindingModel.class, UserServiceModel.class)
                .addMappings(mpr -> mpr.using(passwordHash)
                        .map(UserRegisterBindingModel::getPassword, UserServiceModel::setPassword));

        Converter<User, Long> toUserId = ctx -> ctx.getSource() == null ? null :
                ctx.getSource().getId();

        mapper.addMappings(new PropertyMap<Contest, ContestServiceModel>() {
            @Override
            protected void configure() {
                skip(destination.getManagers());
            }
        });

        Converter<List<User>, List<Long>> toUserIdList = ctx -> ctx.getSource() == null ? null :
                ctx.getSource()
                        .stream()
                        .map(User::getId)
                        .toList();

        Converter<List<PerformanceCategoryBindingModel>, Set<PerformanceCategoryServiceModel>> toIndexedPerformanceCategory =
                ctx -> {
                    if (ctx.getSource() == null) {
                        return null;
                    }
                    AtomicInteger pcIx = new AtomicInteger();

                    return ctx.getSource()
                            .stream()
                            .map(pcbm -> {
                                PerformanceCategoryServiceModel pc = mapper.map(pcbm, PerformanceCategoryServiceModel.class);
                                pc.setDisplayNumber(pcIx.getAndIncrement());
                                return pc;
                            }).collect(Collectors.toSet());
                };

        Converter<Set<PerformanceCategoryServiceModel>, List<PerformanceCategoryBindingModel>> toIndexedPerformanceCategoryBindingModel =
                ctx -> ctx.getSource() == null ? null :
                        ctx.getSource()
                                .stream()
                                .sorted(Comparator.comparing(PerformanceCategoryServiceModel::getDisplayNumber))
                                .map(pc -> mapper.map(pc, PerformanceCategoryBindingModel.class))
                                .toList();


        Converter<List<AgeGroupBindingModel>, Set<AgeGroupServiceModel>> toIndexedAgeGroup =
                ctx -> {
                    if (ctx.getSource() == null) {
                        return null;
                    }
                    AtomicInteger pcIx = new AtomicInteger();

                    return ctx.getSource()
                            .stream()
                            .map(agbm -> {
                                AgeGroupServiceModel ag = mapper.map(agbm, AgeGroupServiceModel.class);
                                ag.setDisplayNumber(pcIx.getAndIncrement());
                                return ag;
                            }).collect(Collectors.toSet());
                };

        Converter<Set<AgeGroupServiceModel>, List<AgeGroupBindingModel>> toIndexedAgeGroupBindingModel =
                ctx -> ctx.getSource() == null ? null :
                        ctx.getSource()
                                .stream()
                                .sorted(Comparator.comparing(AgeGroupServiceModel::getDisplayNumber))
                                .map(ag -> mapper.map(ag, AgeGroupBindingModel.class))
                                .toList();


        mapper.addMappings(new PropertyMap<PerformanceCategoryBindingModel, PerformanceCategoryServiceModel>() {
            @Override
            protected void configure() {
                skip(destination.getDisplayNumber());
            }
        });

        mapper.addMappings(new PropertyMap<AgeGroupBindingModel, AgeGroupServiceModel>() {
            @Override
            protected void configure() {
                skip(destination.getDisplayNumber());
            }
        });

        mapper.createTypeMap(EditionCreateBindingModel.class, EditionServiceModel.class)
                .addMappings(mpr -> mpr.using(toIndexedPerformanceCategory)
                        .map(EditionCreateBindingModel::getPerformanceCategories, EditionServiceModel::setPerformanceCategories))
                .addMappings(mpr -> mpr.using(toIndexedAgeGroup)
                        .map(EditionCreateBindingModel::getAgeGroups, EditionServiceModel::setAgeGroups));

        mapper.createTypeMap(EditionEditBindingModel.class, EditionServiceModel.class)
                .addMappings(mpr -> mpr.using(toIndexedPerformanceCategory)
                        .map(EditionEditBindingModel::getPerformanceCategories, EditionServiceModel::setPerformanceCategories))
                .addMappings(mpr -> mpr.using(toIndexedAgeGroup)
                        .map(EditionEditBindingModel::getAgeGroups, EditionServiceModel::setAgeGroups));

        mapper.createTypeMap(EditionServiceModel.class, EditionCreateBindingModel.class)
                .addMappings(mpr -> mpr.using(toIndexedPerformanceCategoryBindingModel)
                        .map(EditionServiceModel::getPerformanceCategories,
                                EditionCreateBindingModel::setPerformanceCategories))
                .addMappings(mpr -> mpr.using(toIndexedAgeGroupBindingModel)
                        .map(EditionServiceModel::getAgeGroups, EditionCreateBindingModel::setAgeGroups));

        mapper.createTypeMap(EditionServiceModel.class, EditionEditBindingModel.class)
                .addMappings(mpr -> mpr.using(toIndexedPerformanceCategoryBindingModel)
                        .map(EditionServiceModel::getPerformanceCategories,
                                EditionEditBindingModel::setPerformanceCategories))
                .addMappings(mpr -> mpr.using(toIndexedAgeGroupBindingModel)
                        .map(EditionServiceModel::getAgeGroups, EditionEditBindingModel::setAgeGroups));


        Converter<Set<JuryMember>, Set<Long>> toJuryMemberIdList = ctx -> (ctx.getSource() == null) ? null :
                ctx.getSource()
                        .stream()
                        .map(JuryMember::getId)
                        .collect(Collectors.toSet());

        mapper.createTypeMap(Edition.class, EditionServiceModel.class)
                .addMappings(mpr -> mpr.using(toJuryMemberIdList)
                        .map(Edition::getJuryMembers, EditionServiceModel::setJuryMembers));

        mapper.createTypeMap(JuryMemberServiceModel.class, JuryDetailsViewModel.class)
                .addMapping(src->src.getUser().getFullName(), JuryDetailsViewModel::setFullName);


        Converter<List<Edition>, Collection<EditionListViewModel>> toEditionList = ctx -> (ctx.getSource() == null) ?
                null :
                ctx.getSource()
                        .stream()
                        .sorted(Comparator.comparing(Edition::getBeginDate).reversed())
                        .map(e -> mapper.map(e, EditionListViewModel.class))
                        .toList();


        mapper.createTypeMap(ContestServiceModelWithEditions.class, ContestEditionsViewModel.class)
                .addMappings(mpr -> mpr.using(toEditionList)
                        .map(ContestServiceModelWithEditions::getEditions, ContestEditionsViewModel::setEditions));

//        Converter<Set<Song>, List<SongServiceModel>> toSongServiceModel = ctx -> (ctx.getSource() == null) ?
//                null :
//                ctx.getSource()
//                        .stream()
//                        .sorted(Comparator.comparing(s -> s.getCategory().getDisplayNumber()))
//                        .map(s -> mapper.map(s, SongServiceModel.class))
//                        .toList();
//
//        mapper.createTypeMap(Contestant.class, ContestantServiceModel.class)
//                .addMappings(mpr -> mpr.using(toSongServiceModel)
//                        .map(Contestant::getSongs, ContestantServiceModel::setSongs));
        return mapper;
    }

}
