package bg.manhattan.singerscontests.config;

import bg.manhattan.singerscontests.interseptor.ExecutionTimeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfigurator implements WebMvcConfigurer {
    private final ExecutionTimeInterceptor executionTimeInterceptor;

    public InterceptorConfigurator(ExecutionTimeInterceptor executionTimeInterceptor) {
        this.executionTimeInterceptor = executionTimeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(executionTimeInterceptor);
    }
}
