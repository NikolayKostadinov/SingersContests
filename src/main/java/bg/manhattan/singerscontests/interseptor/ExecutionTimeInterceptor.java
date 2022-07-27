package bg.manhattan.singerscontests.interseptor;

import bg.manhattan.singerscontests.events.PerformanceIssueEvent;
import bg.manhattan.singerscontests.model.view.PerformanceIssueViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class ExecutionTimeInterceptor implements HandlerInterceptor {

    private static final String START_TIME_ATTR_NAME = "startTime";
    private static final String EXECUTION_TIME_ATTR_NAME = "executionTime";
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTimeInterceptor.class);

    private final ApplicationEventPublisher eventPublisher;
    public final Long maxExecutionTime;

    public ExecutionTimeInterceptor(ApplicationEventPublisher eventPublisher,
                                    @Value("${max_execution_time:500}") Long maxExecutionTime) {
        this.eventPublisher = eventPublisher;

        this.maxExecutionTime = maxExecutionTime;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTR_NAME, startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute(START_TIME_ATTR_NAME);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String source = handler.toString();
        if (handler instanceof HandlerMethod) {
            source = ((HandlerMethod) handler).getMethod().toString();
        }

        if (LOGGER.isInfoEnabled() && handler instanceof HandlerMethod) {
            LOGGER.info("[" + source + "] executeTime : " + executionTime + "ms");
        }

        if (executionTime > maxExecutionTime) {
            this.eventPublisher.publishEvent(
                    new PerformanceIssueEvent(this,
                            new PerformanceIssueViewModel(source, executionTime)));
        }
    }
}
