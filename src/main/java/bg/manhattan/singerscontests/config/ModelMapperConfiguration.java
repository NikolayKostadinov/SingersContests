package bg.manhattan.singerscontests.config;

import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.model.entity.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        return mapper;
    }

}