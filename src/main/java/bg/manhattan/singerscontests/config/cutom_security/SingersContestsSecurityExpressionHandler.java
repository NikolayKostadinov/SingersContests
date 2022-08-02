package bg.manhattan.singerscontests.config.cutom_security;

import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.services.EditionService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class SingersContestsSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

  private final ContestService contestService;

  private final EditionService editionService;
  private final ContestantService contestantService;

  public SingersContestsSecurityExpressionHandler(ContestService contestService,
                                                  EditionService editionService,
                                                  ContestantService contestantService) {
    this.contestService = contestService;
    this.editionService = editionService;
    this.contestantService = contestantService;
  }

  @Override
  protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
          Authentication authentication,
          MethodInvocation invocation) {

    SingersContestsSecurityExpressionRoot root =
            new SingersContestsSecurityExpressionRoot(authentication,
                    contestService,
                    editionService,
                    contestantService);

    root.setPermissionEvaluator(getPermissionEvaluator());
    root.setTrustResolver(new AuthenticationTrustResolverImpl());
    root.setRoleHierarchy(getRoleHierarchy());

    return root;
  }
}
