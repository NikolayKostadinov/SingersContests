# SingersContests
System for organizing of singers contests

  To be able to run all the integration tests on Windows you have to make changes to you UTF-8 settings as in picture
  ![Figure](https://github.com/NikolayKostadinov/SingersContests/blob/main/UTF-8%20Settings.png)

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
