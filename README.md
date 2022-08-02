# SingersContests
System for organizing of singers contests

## Rest controllers
- JuryDetailsRestController
- FileTransferRestController 

## Interceptors
- ExecutionTimeInterceptor

## Events
- PerformanceIssueEvent
- UserChangeEmailEvent
- UserRegisteredEvent

## Schedulers
- FinalizeEditionRegistrationScheduler
   - It starts every night at 00:00 and generates the scenario order for all contests for which registration has closed the previous day.
