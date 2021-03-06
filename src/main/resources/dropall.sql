SET foreign_key_checks = 0;

DROP TABLE 
Artifact                  ,
Author                    ,
BlamedLine                ,
CCResult                  ,
CalculatedMetric          ,
Commit                    ,
CommitMessage             ,
Diff                      ,
FanOutResult              ,
LComResult                ,
LinesOfCodeResult         ,
MethodsCountResult        ,
MethodsInvocationResult   ,
Modification              ,
Project                   ,
ProjectConfigurationEntry ,
Project_Tag               ,
Query                     ,
QueryResult               ,
SourceCode                ,
StatisticalTest           ,
StatisticalTestResult     ,
Tag                       ,
Task                      ,
TaskConfigurationEntry    ,
Task_Task                 ,
TestedMethodFinderResult  ,
TruckFactorResult         ,
User                      ;

SET foreign_key_checks = 1;
