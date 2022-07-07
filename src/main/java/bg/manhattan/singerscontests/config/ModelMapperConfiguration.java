package bg.manhattan.singerscontests.config;

import bg.manhattan.singerscontests.model.binding.ContestCreateBindingModel;
import bg.manhattan.singerscontests.model.binding.ContestEditBindingModel;
import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.service.ContestEditServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModelWithEditions;
import bg.manhattan.singerscontests.model.view.ContestEditionsViewModel;
import bg.manhattan.singerscontests.model.view.EditionListViewModel;
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
        mapper.createTypeMap(UserRegisterBindingModel.class, User.class)
                .addMappings(mpr -> mpr.using(passwordHash)
                        .map(UserRegisterBindingModel::getPassword, User::setPassword));

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

//        Converter<List<Long>, List<ManagerBindingModel>> toManagerList = ctx -> ctx.getSource() == null ? null :
//                ctx.getSource()
//                        .stream()
//                        .map(usrId -> new ManagerBindingModel()
//                                .setId(usrId))
//                        .toList();
//
//        mapper.createTypeMap(ContestServiceModel.class, ContestEditBindingModel.class)
//                .addMappings(mpr -> mpr.using(toManagerList)
//                        .map(ContestServiceModel::getManagers, ContestCreateBindingModel::setManagers));
//
//        Converter<List<ManagerBindingModel>, List<Long>> toManagerIdList = ctx -> (ctx.getSource() == null) ? null :
//                ctx.getSource()
//                        .stream()
//                        .filter(m -> !m.isDeleted())
//                        .map(ManagerBindingModel::getId)
//                        .toList();

//        mapper.createTypeMap(ContestCreateBindingModel.class, ContestCreateServiceModel.class)
//                .addMappings(mpr -> mpr.using(toManagerIdList)
//                        .map(ContestCreateBindingModel::getManagers, ContestCreateServiceModel::setManagers));

//        mapper.createTypeMap(ContestEditBindingModel.class, ContestEditServiceModel.class)
//                .addMappings(mpr -> mpr.using(toManagerIdList)
//                        .map(ContestEditBindingModel::getManagers, ContestEditServiceModel::setManagers));

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
        return mapper;
    }

}
