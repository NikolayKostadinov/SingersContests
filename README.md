# <u>Singers Contests</u>

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

## Self management
  Implemented self-management functionality. When you click on greeting message at the top of the screen, you will be redirected to ***"Manage your account"***

&nbsp;
&nbsp;
&nbsp;

> ***Note 1***
> ______
> 
> **To be able to run project you have to run MailHog on ports :**
>   - smtp: 65000 - instead of default 1025
>   - http: 8025
>
> ***Note 2***
> _____
>To be able to run all the integration tests on Windows you have to make changes to you UTF-8 settings as in picture
> 
>![Figure](/UTF-8%20Settings.png)
>
