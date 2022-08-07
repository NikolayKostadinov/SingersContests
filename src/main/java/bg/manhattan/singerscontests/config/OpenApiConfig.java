package bg.manhattan.singerscontests.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .version("1.0.0")
                        .contact(new Contact().name("Nikolay Kostadinov")
                                .email("kostainov.nikolay.d@gmail.com"))
                        .description("Singers Contests API"));
    }
}
