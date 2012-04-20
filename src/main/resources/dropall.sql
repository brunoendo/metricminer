SET foreign_key_checks = 0;


DROP TABLE  `Artifact` ,
`Author` ,
`Commit` ,
`Commit_Artifact` ,
`ConfigurationEntry` ,
`Modification` ,
`Project` ,
`SourceCode` ,
`Task` ,
`Task_Task` ,
`TaskConfigurationEntry`,
`CCResult`,
`FanOutResult`, 
`LComResult`,
`MethodsInvocationResult`,
`LinesOfCodeResult`,
`TestedMethodFinderResult`,
`MethodsCountResult`;

SET foreign_key_checks = 1;
