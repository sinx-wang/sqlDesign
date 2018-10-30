-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: hibernate
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `call_history`
--

DROP TABLE IF EXISTS `call_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `call_history` (
  `chid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `created_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `all_time` double NOT NULL,
  `money` double DEFAULT NULL,
  `money_this_time` double DEFAULT NULL,
  PRIMARY KEY (`chid`),
  KEY `call_history_customer_cid_fk` (`cid`),
  CONSTRAINT `call_history_customer_cid_fk` FOREIGN KEY (`cid`) REFERENCES `customer` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通话消费历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `call_history`
--

LOCK TABLES `call_history` WRITE;
/*!40000 ALTER TABLE `call_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `call_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `call_standard`
--

DROP TABLE IF EXISTS `call_standard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `call_standard` (
  `call_id` int(11) NOT NULL AUTO_INCREMENT,
  `free_time` double DEFAULT NULL,
  `standard` double DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='通话资费标准';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `call_standard`
--

LOCK TABLES `call_standard` WRITE;
/*!40000 ALTER TABLE `call_standard` DISABLE KEYS */;
INSERT INTO `call_standard` VALUES (1,0,0.5),(2,100,0.5),(3,50,0.25),(4,0,0.5),(5,100,0.5),(6,50,0.25);
/*!40000 ALTER TABLE `call_standard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'zhangsan'),(2,'lisi'),(3,'wangwu'),(4,'ZhangSan'),(5,'LiSi'),(6,'WangWu');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flow`
--

DROP TABLE IF EXISTS `flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flow` (
  `flow_id` int(11) NOT NULL AUTO_INCREMENT,
  `local_free_num` double DEFAULT NULL,
  `local_standard` double DEFAULT NULL,
  `other_free_num` double DEFAULT NULL,
  `other_standard` double DEFAULT NULL,
  PRIMARY KEY (`flow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flow`
--

LOCK TABLES `flow` WRITE;
/*!40000 ALTER TABLE `flow` DISABLE KEYS */;
INSERT INTO `flow` VALUES (1,0,2,0,5),(2,2048,3,0,3),(3,0,3,2048,3),(4,5120,3,4096,3),(5,0,2,0,5),(6,2048,3,0,3),(7,0,3,2048,3),(8,5120,3,4096,3);
/*!40000 ALTER TABLE `flow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flow_history`
--

DROP TABLE IF EXISTS `flow_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flow_history` (
  `fhid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `consume_local_all` double NOT NULL,
  `consume_other_all` double NOT NULL,
  `money` double DEFAULT NULL,
  `money_this_time` double DEFAULT NULL,
  PRIMARY KEY (`fhid`),
  KEY `flow_history_customer_cid_fk` (`cid`),
  CONSTRAINT `flow_history_customer_cid_fk` FOREIGN KEY (`cid`) REFERENCES `customer` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flow_history`
--

LOCK TABLES `flow_history` WRITE;
/*!40000 ALTER TABLE `flow_history` DISABLE KEYS */;
INSERT INTO `flow_history` VALUES (1,3,10,500,0,0,0),(2,3,10,500,2,0,0);
/*!40000 ALTER TABLE `flow_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(255) DEFAULT NULL,
  `call_id` int(11) DEFAULT NULL,
  `flow_id` int(11) DEFAULT NULL,
  `sms_id` int(11) DEFAULT NULL,
  `base` double DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `product_call_standard_call_id_fk` (`call_id`),
  KEY `product_flow_flow_id_fk` (`flow_id`),
  KEY `product_sms_sms_id_fk` (`sms_id`),
  CONSTRAINT `product_call_standard_call_id_fk` FOREIGN KEY (`call_id`) REFERENCES `call_standard` (`call_id`),
  CONSTRAINT `product_flow_flow_id_fk` FOREIGN KEY (`flow_id`) REFERENCES `flow` (`flow_id`),
  CONSTRAINT `product_sms_sms_id_fk` FOREIGN KEY (`sms_id`) REFERENCES `sms` (`sms_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'base_plan',1,1,1,0),(2,'call_plan',2,1,1,20),(3,'sms_plan',1,1,2,10),(4,'local_data_plan',1,2,1,20),(5,'other_data_plan',1,3,1,30),(6,'mix_plan',3,4,3,50),(7,'base_plan',1,1,1,0),(8,'call_plan',2,1,1,20),(9,'sms_plan',1,1,2,10),(10,'local_data_plan',1,2,1,20),(11,'other_data_plan',1,3,1,30),(12,'mix_plan',3,4,3,50);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_history`
--

DROP TABLE IF EXISTS `product_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_history` (
  `phid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `pid` int(11) DEFAULT NULL,
  `p_next_id` int(11) DEFAULT NULL,
  `month` int(11) NOT NULL,
  `be_using` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`phid`),
  KEY `product_history_product_pid_fk` (`pid`),
  KEY `product_history_product_pid_fk_2` (`p_next_id`),
  KEY `product_history_customer_cid_fk` (`cid`),
  CONSTRAINT `product_history_customer_cid_fk` FOREIGN KEY (`cid`) REFERENCES `customer` (`cid`),
  CONSTRAINT `product_history_product_pid_fk` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`),
  CONSTRAINT `product_history_product_pid_fk_2` FOREIGN KEY (`p_next_id`) REFERENCES `product` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='订购套餐记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_history`
--

LOCK TABLES `product_history` WRITE;
/*!40000 ALTER TABLE `product_history` DISABLE KEYS */;
INSERT INTO `product_history` VALUES (1,1,2,2,10,1),(2,1,3,1,10,1),(3,1,4,4,10,1),(4,2,6,6,10,1),(5,2,4,6,10,1),(6,3,6,6,10,1);
/*!40000 ALTER TABLE `product_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sms`
--

DROP TABLE IF EXISTS `sms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sms` (
  `sms_id` int(11) NOT NULL AUTO_INCREMENT,
  `free_num` int(11) DEFAULT NULL,
  `standard` double DEFAULT NULL,
  PRIMARY KEY (`sms_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sms`
--

LOCK TABLES `sms` WRITE;
/*!40000 ALTER TABLE `sms` DISABLE KEYS */;
INSERT INTO `sms` VALUES (1,0,0.1),(2,200,0.1),(3,100,0.1),(4,0,0.1),(5,200,0.1),(6,100,0.1);
/*!40000 ALTER TABLE `sms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sms_history`
--

DROP TABLE IF EXISTS `sms_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sms_history` (
  `shid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `send_num_all` int(11) NOT NULL,
  `money` double DEFAULT NULL,
  `money_this_time` double DEFAULT NULL,
  PRIMARY KEY (`shid`),
  KEY `sms_history_customer_cid_fk` (`cid`),
  CONSTRAINT `sms_history_customer_cid_fk` FOREIGN KEY (`cid`) REFERENCES `customer` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sms_history`
--

LOCK TABLES `sms_history` WRITE;
/*!40000 ALTER TABLE `sms_history` DISABLE KEYS */;
INSERT INTO `sms_history` VALUES (1,2,10,40,0,0),(2,2,10,90,0,0),(3,2,10,105,0.5,0.5);
/*!40000 ALTER TABLE `sms_history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-30 22:32:59
