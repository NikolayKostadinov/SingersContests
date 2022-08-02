package bg.manhattan.singerscontests.config.cutom_security;

import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.services.EditionService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public class SingersContestsSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private final ContestService contestService;

    private final EditionService editionService;
    private final ContestantService contestantService;
    private Object filterObject;
    private Object returnObject;

    public SingersContestsSecurityExpressionRoot(Authentication authentication,
                                                 ContestService contestService,
                                                 EditionService editionService,
                                                 ContestantService contestantService) {
        super(authentication);
        this.contestService = contestService;
        this.editionService = editionService;
        this.contestantService = contestantService;
    }

    public boolean isOwner(Long id) {
        if ( super.authentication.getPrincipal() == null) {
            return false;
        }

        String userName = super.authentication.getName();

        return contestService.isOwner(userName, id);
    }

    public boolean isEditionOwner(Long id) {
        if ( super.authentication.getPrincipal() == null) {
            return false;
        }

        String userName = super.authentication.getName();

        return editionService.isEditionOwner(userName, id);
    }



    public boolean isRegistrar(Long id) {
        if ( super.authentication.getPrincipal() == null) {
            return false;
        }

        Principal principal = (Principal) super.authentication.getPrincipal();

        return contestantService.isRegistrar(principal, id);
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

}
