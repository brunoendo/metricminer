-- MySQL dump 10.13  Distrib 5.1.56, for slackware-linux-gnu (x86_64)
--
-- Host: localhost    Database: MetricMiner
-- ------------------------------------------------------
-- Server version	5.1.56

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Artifact`
--

DROP TABLE IF EXISTS `Artifact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artifact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kind` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBA9C69F2783885CC` (`project_id`),
  KEY `project_index` (`project_id`),
  KEY `name_index` (`name`),
  CONSTRAINT `FKBA9C69F2783885CC` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artifact`
--

LOCK TABLES `Artifact` WRITE;
/*!40000 ALTER TABLE `Artifact` DISABLE KEYS */;
/*!40000 ALTER TABLE `Artifact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Author`
--

DROP TABLE IF EXISTS `Author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `secret_email` varchar(255) DEFAULT NULL,
  `secret_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `author_name` (`secret_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Author`
--

LOCK TABLES `Author` WRITE;
/*!40000 ALTER TABLE `Author` DISABLE KEYS */;
/*!40000 ALTER TABLE `Author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BlamedLine`
--

DROP TABLE IF EXISTS `BlamedLine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BlamedLine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `line` int(11) NOT NULL,
  `author_id` int(11) DEFAULT NULL,
  `sourceCode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1C2602C97FD44CE8` (`sourceCode_id`),
  KEY `FK1C2602C998C6CCE8` (`author_id`),
  CONSTRAINT `FK1C2602C998C6CCE8` FOREIGN KEY (`author_id`) REFERENCES `Author` (`id`),
  CONSTRAINT `FK1C2602C97FD44CE8` FOREIGN KEY (`sourceCode_id`) REFERENCES `SourceCode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BlamedLine`
--

LOCK TABLES `BlamedLine` WRITE;
/*!40000 ALTER TABLE `BlamedLine` DISABLE KEYS */;
/*!40000 ALTER TABLE `BlamedLine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CCResult`
--

DROP TABLE IF EXISTS `CCResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CCResult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avgCc` double NOT NULL,
  `cc` int(11) NOT NULL,
  `sourceCode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK99E2F49D7FD44CE8` (`sourceCode_id`),
  CONSTRAINT `FK99E2F49D7FD44CE8` FOREIGN KEY (`sourceCode_id`) REFERENCES `SourceCode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CCResult`
--

LOCK TABLES `CCResult` WRITE;
/*!40000 ALTER TABLE `CCResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `CCResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CalculatedMetric`
--

DROP TABLE IF EXISTS `CalculatedMetric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CalculatedMetric` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metricFactoryClass` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDDFFC92E783885CC` (`project_id`),
  CONSTRAINT `FKDDFFC92E783885CC` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CalculatedMetric`
--

LOCK TABLES `CalculatedMetric` WRITE;
/*!40000 ALTER TABLE `CalculatedMetric` DISABLE KEYS */;
/*!40000 ALTER TABLE `CalculatedMetric` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Commit`
--

DROP TABLE IF EXISTS `Commit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Commit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commitId` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `priorCommitId` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `diff_id` int(11) DEFAULT NULL,
  `message_id` int(11) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK78A41557783885CC` (`project_id`),
  KEY `FK78A4155798C6CCE8` (`author_id`),
  KEY `FK78A41557296EDC55` (`message_id`),
  KEY `FK78A4155719316E28` (`diff_id`),
  CONSTRAINT `FK78A4155719316E28` FOREIGN KEY (`diff_id`) REFERENCES `Diff` (`id`),
  CONSTRAINT `FK78A41557296EDC55` FOREIGN KEY (`message_id`) REFERENCES `CommitMessage` (`id`),
  CONSTRAINT `FK78A41557783885CC` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FK78A4155798C6CCE8` FOREIGN KEY (`author_id`) REFERENCES `Author` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Commit`
--

LOCK TABLES `Commit` WRITE;
/*!40000 ALTER TABLE `Commit` DISABLE KEYS */;
/*!40000 ALTER TABLE `Commit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommitMessage`
--

DROP TABLE IF EXISTS `CommitMessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CommitMessage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommitMessage`
--

LOCK TABLES `CommitMessage` WRITE;
/*!40000 ALTER TABLE `CommitMessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `CommitMessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Diff`
--

DROP TABLE IF EXISTS `Diff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Diff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `diff` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Diff`
--

LOCK TABLES `Diff` WRITE;
/*!40000 ALTER TABLE `Diff` DISABLE KEYS */;
/*!40000 ALTER TABLE `Diff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FanOutResult`
--

DROP TABLE IF EXISTS `FanOutResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FanOutResult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fanOut` int(11) NOT NULL,
  `sourceCode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41D6DF587FD44CE8` (`sourceCode_id`),
  CONSTRAINT `FK41D6DF587FD44CE8` FOREIGN KEY (`sourceCode_id`) REFERENCES `SourceCode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FanOutResult`
--

LOCK TABLES `FanOutResult` WRITE;
/*!40000 ALTER TABLE `FanOutResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `FanOutResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LComResult`
--

DROP TABLE IF EXISTS `LComResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LComResult` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lcom` double NOT NULL,
  `source_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK76130612400AE875` (`source_id`),
  CONSTRAINT `FK76130612400AE875` FOREIGN KEY (`source_id`) REFERENCES `SourceCode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LComResult`
--

LOCK TABLES `LComResult` WRITE;
/*!40000 ALTER TABLE `LComResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `LComResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LinesOfCodeResult`
--

DROP TABLE IF EXISTS `LinesOfCodeResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LinesOfCodeResult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `linesOfCode` int(11) NOT NULL,
  `methodName` varchar(1024) DEFAULT NULL,
  `sourceCode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4539A2207FD44CE8` (`sourceCode_id`),
  CONSTRAINT `FK4539A2207FD44CE8` FOREIGN KEY (`sourceCode_id`) REFERENCES `SourceCode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LinesOfCodeResult`
--

LOCK TABLES `LinesOfCodeResult` WRITE;
/*!40000 ALTER TABLE `LinesOfCodeResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `LinesOfCodeResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MethodsCountResult`
--

DROP TABLE IF EXISTS `MethodsCountResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MethodsCountResult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `constructorMethods` int(11) NOT NULL,
  `crotectedMethods` int(11) NOT NULL,
  `defaultAttributes` int(11) NOT NULL,
  `defaultMethods` int(11) NOT NULL,
  `privateAttributes` int(11) NOT NULL,
  `privateMethods` int(11) NOT NULL,
  `protectedAttributes` int(11) NOT NULL,
  `publicAttributes` int(11) NOT NULL,
  `publicMethods` int(11) NOT NULL,
  `sourceCode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD07F31A7FD44CE8` (`sourceCode_id`),
  CONSTRAINT `FKD07F31A7FD44CE8` FOREIGN KEY (`sourceCode_id`) REFERENCES `SourceCode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MethodsCountResult`
--

LOCK TABLES `MethodsCountResult` WRITE;
/*!40000 ALTER TABLE `MethodsCountResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `MethodsCountResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MethodsInvocationResult`
--

DROP TABLE IF EXISTS `MethodsInvocationResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MethodsInvocationResult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `methodName` varchar(255) DEFAULT NULL,
  `methodsInvocation` double NOT NULL,
  `sourceCode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK17C8447F7FD44CE8` (`sourceCode_id`),
  CONSTRAINT `FK17C8447F7FD44CE8` FOREIGN KEY (`sourceCode_id`) REFERENCES `SourceCode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MethodsInvocationResult`
--

LOCK TABLES `MethodsInvocationResult` WRITE;
/*!40000 ALTER TABLE `MethodsInvocationResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `MethodsInvocationResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Modification`
--

DROP TABLE IF EXISTS `Modification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Modification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `diff` longtext,
  `kind` varchar(255) DEFAULT NULL,
  `artifact_id` int(11) DEFAULT NULL,
  `commit_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE0FD25BCEB039D68` (`commit_id`),
  KEY `FKE0FD25BC75C60808` (`artifact_id`),
  CONSTRAINT `FKE0FD25BC75C60808` FOREIGN KEY (`artifact_id`) REFERENCES `Artifact` (`id`),
  CONSTRAINT `FKE0FD25BCEB039D68` FOREIGN KEY (`commit_id`) REFERENCES `Commit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Modification`
--

LOCK TABLES `Modification` WRITE;
/*!40000 ALTER TABLE `Modification` DISABLE KEYS */;
/*!40000 ALTER TABLE `Modification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `projectPath` varchar(255) DEFAULT NULL,
  `scmUrl` varchar(255) DEFAULT NULL,
  `totalCommiters` bigint(20) DEFAULT NULL,
  `totalCommits` bigint(20) DEFAULT NULL,
  `firstCommit_id` int(11) DEFAULT NULL,
  `lastCommit_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK50C8E2F989D9392` (`lastCommit_id`),
  KEY `FK50C8E2F9BE3ED0B8` (`firstCommit_id`),
  CONSTRAINT `FK50C8E2F9BE3ED0B8` FOREIGN KEY (`firstCommit_id`) REFERENCES `Commit` (`id`),
  CONSTRAINT `FK50C8E2F989D9392` FOREIGN KEY (`lastCommit_id`) REFERENCES `Commit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES (1,'2012-11-29 00:03:26','tubaina','/var/tmp/repositories/projects/','git://github.com/caelum/tubaina.git',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectConfigurationEntry`
--

DROP TABLE IF EXISTS `ProjectConfigurationEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectConfigurationEntry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `entry_key` varchar(255) DEFAULT NULL,
  `entry_value` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC02E7FF5783885CC` (`project_id`),
  CONSTRAINT `FKC02E7FF5783885CC` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectConfigurationEntry`
--

LOCK TABLES `ProjectConfigurationEntry` WRITE;
/*!40000 ALTER TABLE `ProjectConfigurationEntry` DISABLE KEYS */;
INSERT INTO `ProjectConfigurationEntry` VALUES (1,'scm','org.metricminer.scm.git.GitFactory',1),(2,'scm.repository','/var/tmp/repositories/projects/1',1),(3,'changesets','org.metricminer.changesets.AllChangeSetsFactory',1);
/*!40000 ALTER TABLE `ProjectConfigurationEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project_Tag`
--

DROP TABLE IF EXISTS `Project_Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project_Tag` (
  `Project_id` bigint(20) NOT NULL,
  `tags_id` int(11) NOT NULL,
  KEY `FK2B693534783885CC` (`Project_id`),
  KEY `FK2B693534257A9A4D` (`tags_id`),
  CONSTRAINT `FK2B693534257A9A4D` FOREIGN KEY (`tags_id`) REFERENCES `Tag` (`id`),
  CONSTRAINT `FK2B693534783885CC` FOREIGN KEY (`Project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project_Tag`
--

LOCK TABLES `Project_Tag` WRITE;
/*!40000 ALTER TABLE `Project_Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `Project_Tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Query`
--

DROP TABLE IF EXISTS `Query`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Query` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sqlQuery` longtext,
  `submitDate` datetime DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4AC28A8E4402A8` (`author_id`),
  CONSTRAINT `FK4AC28A8E4402A8` FOREIGN KEY (`author_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Query`
--

LOCK TABLES `Query` WRITE;
/*!40000 ALTER TABLE `Query` DISABLE KEYS */;
INSERT INTO `Query` VALUES (1,'select name','select name from Project;\r\n','2012-11-29 00:12:23',1);
/*!40000 ALTER TABLE `Query` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QueryResult`
--

DROP TABLE IF EXISTS `QueryResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QueryResult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `csvFilename` varchar(255) DEFAULT NULL,
  `executedDate` datetime DEFAULT NULL,
  `message` longtext,
  `status` varchar(255) DEFAULT NULL,
  `query_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK15B7BEE5CDF2F7EC` (`query_id`),
  CONSTRAINT `FK15B7BEE5CDF2F7EC` FOREIGN KEY (`query_id`) REFERENCES `Query` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QueryResult`
--

LOCK TABLES `QueryResult` WRITE;
/*!40000 ALTER TABLE `QueryResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `QueryResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SourceCode`
--

DROP TABLE IF EXISTS `SourceCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SourceCode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source` longtext,
  `sourceSize` bigint(20) DEFAULT NULL,
  `modification_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK348AE0C8802384C8` (`modification_id`),
  KEY `sourceSize_index` (`sourceSize`),
  CONSTRAINT `FK348AE0C8802384C8` FOREIGN KEY (`modification_id`) REFERENCES `Modification` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SourceCode`
--

LOCK TABLES `SourceCode` WRITE;
/*!40000 ALTER TABLE `SourceCode` DISABLE KEYS */;
/*!40000 ALTER TABLE `SourceCode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StatisticalTest`
--

DROP TABLE IF EXISTS `StatisticalTest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StatisticalTest` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `algorithm` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1098AC8DAD8B1068` (`user_id`),
  CONSTRAINT `FK1098AC8DAD8B1068` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StatisticalTest`
--

LOCK TABLES `StatisticalTest` WRITE;
/*!40000 ALTER TABLE `StatisticalTest` DISABLE KEYS */;
INSERT INTO `StatisticalTest` VALUES (1,'set1 <- #set1#\nset2 <- #set2#\nt1 = wilcox.test(set1, set2, paired = TRUE)\nt1','wilcoxon',NULL),(2,'set1 <- #set1#\nset2 <- #set2#\nt1 = wilcox.test(set1, set2, paired = TRUE)\nt1','wilcoxon',NULL),(3,'set1 <- #set1#\nset2 <- #set2#\nt1 = wilcox.test(set1, set2, paired = TRUE)\nt1','wilcoxon',NULL);
/*!40000 ALTER TABLE `StatisticalTest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StatisticalTestResult`
--

DROP TABLE IF EXISTS `StatisticalTestResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StatisticalTestResult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `output` varchar(255) DEFAULT NULL,
  `q1_id` bigint(20) DEFAULT NULL,
  `q2_id` bigint(20) DEFAULT NULL,
  `test_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA2E9030AC2C31C91` (`q1_id`),
  KEY `FKA2E9030AC2C390F0` (`q2_id`),
  KEY `FKA2E9030A64EFC547` (`test_id`),
  CONSTRAINT `FKA2E9030A64EFC547` FOREIGN KEY (`test_id`) REFERENCES `StatisticalTest` (`id`),
  CONSTRAINT `FKA2E9030AC2C31C91` FOREIGN KEY (`q1_id`) REFERENCES `QueryResult` (`id`),
  CONSTRAINT `FKA2E9030AC2C390F0` FOREIGN KEY (`q2_id`) REFERENCES `QueryResult` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StatisticalTestResult`
--

LOCK TABLES `StatisticalTestResult` WRITE;
/*!40000 ALTER TABLE `StatisticalTestResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `StatisticalTestResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tag`
--

DROP TABLE IF EXISTS `Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tag`
--

LOCK TABLES `Tag` WRITE;
/*!40000 ALTER TABLE `Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `Tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Task`
--

DROP TABLE IF EXISTS `Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `endDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `runnableTaskFactoryClass` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `submitDate` datetime DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK27A9A5783885CC` (`project_id`),
  CONSTRAINT `FK27A9A5783885CC` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Task`
--

LOCK TABLES `Task` WRITE;
/*!40000 ALTER TABLE `Task` DISABLE KEYS */;
INSERT INTO `Task` VALUES (1,'2012-11-29 00:12:22','Clone SCM',0,'org.metricminer.tasks.scm.SCMCloneTaskFactory','2012-11-29 00:12:10','QUEUED','2012-11-29 00:03:26',1),(2,NULL,'Parse SCM logs',1,'org.metricminer.tasks.scm.ParseSCMLogTaskFactory',NULL,'QUEUED','2012-11-29 00:03:26',1),(3,NULL,'Remove source code directory',2,'org.metricminer.tasks.scm.RemoveSourceDirectoryTaskFactory',NULL,'QUEUED','2012-11-29 00:03:26',1),(4,NULL,'Calculate all metrics',3,'org.metricminer.tasks.metric.CalculateAllMetricsTaskFactory',NULL,'QUEUED','2012-11-29 00:03:26',1),(5,NULL,'Caculate truck factor metric',4,'org.metricminer.tasks.projectmetric.CalculateProjectMetricTaskFactory',NULL,'QUEUED','2012-11-29 00:03:26',1),(6,NULL,'Execute query select name',0,'org.metricminer.tasks.query.ExecuteQueryTaskFactory',NULL,'QUEUED','2012-11-29 00:12:23',NULL);
/*!40000 ALTER TABLE `Task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TaskConfigurationEntry`
--

DROP TABLE IF EXISTS `TaskConfigurationEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TaskConfigurationEntry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8A2173A15AAEFE28` (`task_id`),
  CONSTRAINT `FK8A2173A15AAEFE28` FOREIGN KEY (`task_id`) REFERENCES `Task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TaskConfigurationEntry`
--

LOCK TABLES `TaskConfigurationEntry` WRITE;
/*!40000 ALTER TABLE `TaskConfigurationEntry` DISABLE KEYS */;
INSERT INTO `TaskConfigurationEntry` VALUES (1,'PROJECT_METRIC_FACTORY_CLASS','org.metricminer.tasks.projectmetric.truckfactor.TruckFactorFactory',5),(2,'QUERY_ID','1',6);
/*!40000 ALTER TABLE `TaskConfigurationEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Task_Task`
--

DROP TABLE IF EXISTS `Task_Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Task_Task` (
  `Task_id` bigint(20) NOT NULL,
  `depends_id` bigint(20) NOT NULL,
  KEY `FK822BF7FF5AAEFE28` (`Task_id`),
  KEY `FK822BF7FFB4C464E6` (`depends_id`),
  CONSTRAINT `FK822BF7FFB4C464E6` FOREIGN KEY (`depends_id`) REFERENCES `Task` (`id`),
  CONSTRAINT `FK822BF7FF5AAEFE28` FOREIGN KEY (`Task_id`) REFERENCES `Task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Task_Task`
--

LOCK TABLES `Task_Task` WRITE;
/*!40000 ALTER TABLE `Task_Task` DISABLE KEYS */;
INSERT INTO `Task_Task` VALUES (2,1),(3,2),(4,2),(5,4);
/*!40000 ALTER TABLE `Task_Task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestedMethodFinderResult`
--

DROP TABLE IF EXISTS `TestedMethodFinderResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestedMethodFinderResult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productionMethod` varchar(255) DEFAULT NULL,
  `testMethod` varchar(255) DEFAULT NULL,
  `sourceCode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC212BE157FD44CE8` (`sourceCode_id`),
  CONSTRAINT `FKC212BE157FD44CE8` FOREIGN KEY (`sourceCode_id`) REFERENCES `SourceCode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestedMethodFinderResult`
--

LOCK TABLES `TestedMethodFinderResult` WRITE;
/*!40000 ALTER TABLE `TestedMethodFinderResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `TestedMethodFinderResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TruckFactorResult`
--

DROP TABLE IF EXISTS `TruckFactorResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TruckFactorResult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `artifactId` bigint(20) DEFAULT NULL,
  `authorId` bigint(20) DEFAULT NULL,
  `percentage` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TruckFactorResult`
--

LOCK TABLES `TruckFactorResult` WRITE;
/*!40000 ALTER TABLE `TruckFactorResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `TruckFactorResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cvUrl` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `twitter` varchar(255) DEFAULT NULL,
  `university` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'','chico.sokol@gmail.com','Francisco Sokol','9c2f5ce0472220c016a8a77822c22d211ab9233a7083bbe009b0db86380b6135','ADMINISTRATOR','','IME USP'),(2,'','seixasfelipe@gmail.com','Felipe Seixas','ae3784314f375645157903c0ce188a2b0bef88b5e9476f4c72513c1015f0b875','ADMINISTRATOR','','VISIONAIRES');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-11-29  0:14:46
