package bg.manhattan.singerscontests.config.cutom_security;

import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.services.EditionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SingersContestsMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    private final ContestService contestService;
    private final EditionService editionService;
    private final ContestantService contestantService;

    public SingersContestsMethodSecurityConfig(ContestService contestService,
                                               EditionService editionService,
                                               ContestantService contestantService) {
        this.contestService = contestService;
        this.editionService = editionService;
        this.contestantService = contestantService;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new SingersContestsSecurityExpressionHandler(contestService, editionService, contestantService);
    }
}
