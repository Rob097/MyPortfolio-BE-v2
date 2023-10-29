
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `core` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `core`;
DROP TABLE IF EXISTS `default_gen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `default_gen` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `default_gen` WRITE;
/*!40000 ALTER TABLE `default_gen` DISABLE KEYS */;
/*!40000 ALTER TABLE `default_gen` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `diaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diaries` (
  `id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_main` bit(1) DEFAULT NULL,
  `main_story_id` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_diary_fk` (`user_id`),
  CONSTRAINT `user_diary_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `diaries` WRITE;
/*!40000 ALTER TABLE `diaries` DISABLE KEYS */;
INSERT INTO `diaries` VALUES (1,'2023-10-24 07:09:37.814000','2023-10-24 07:09:37.814000','This is my personal diary',_binary '',NULL,'My Diary',1);
/*!40000 ALTER TABLE `diaries` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `diary_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diary_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `diary_seq` WRITE;
/*!40000 ALTER TABLE `diary_seq` DISABLE KEYS */;
INSERT INTO `diary_seq` VALUES (24);
/*!40000 ALTER TABLE `diary_seq` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `education_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `education_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `education_seq` WRITE;
/*!40000 ALTER TABLE `education_seq` DISABLE KEYS */;
INSERT INTO `education_seq` VALUES (15);
/*!40000 ALTER TABLE `education_seq` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `education_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `education_skills` (
  `education_id` int NOT NULL,
  `skill_id` int NOT NULL,
  PRIMARY KEY (`education_id`,`skill_id`),
  KEY `FK6vpx2b1vcbb8ke1lbotm0np3` (`skill_id`),
  CONSTRAINT `FK6vpx2b1vcbb8ke1lbotm0np3` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`),
  CONSTRAINT `FKlrw2ko1e3q4d7lyixg5t5w232` FOREIGN KEY (`education_id`) REFERENCES `educations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `education_skills` WRITE;
/*!40000 ALTER TABLE `education_skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `education_skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `educations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `educations` (
  `id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `degree` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `field` varchar(255) NOT NULL,
  `from_date` date DEFAULT NULL,
  `grade` double DEFAULT NULL,
  `main_story_id` int DEFAULT NULL,
  `school` varchar(255) NOT NULL,
  `to_date` date DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmhmwthgffhv3jvscm2w9iytsd` (`user_id`,`slug`),
  CONSTRAINT `user_education_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `educations` WRITE;
/*!40000 ALTER TABLE `educations` DISABLE KEYS */;
INSERT INTO `educations` VALUES (1,'2023-10-24 07:11:32.574000','2023-10-24 07:11:32.574000','information-engineering-and-business-organization-university-of-trento','Bachelor Degree','My only degree and one of my proudest achivements. The description should be a little longer.','Information Engineering and Business Organization',NULL,97,NULL,'University of Trento',NULL,1);
/*!40000 ALTER TABLE `educations` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `experience_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `experience_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `experience_seq` WRITE;
/*!40000 ALTER TABLE `experience_seq` DISABLE KEYS */;
INSERT INTO `experience_seq` VALUES (15);
/*!40000 ALTER TABLE `experience_seq` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `experience_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `experience_skills` (
  `experience_id` int NOT NULL,
  `skill_id` int NOT NULL,
  PRIMARY KEY (`experience_id`,`skill_id`),
  KEY `FKo88ck6rbx84b1jntrarjgnfuc` (`skill_id`),
  CONSTRAINT `FKa3xn3uy2mhqrvv57pb5ik0h0b` FOREIGN KEY (`experience_id`) REFERENCES `experiences` (`id`),
  CONSTRAINT `FKo88ck6rbx84b1jntrarjgnfuc` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `experience_skills` WRITE;
/*!40000 ALTER TABLE `experience_skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `experience_skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `experiences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `experiences` (
  `id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `description` text NOT NULL,
  `employment_type` enum('FULL_TIME','PART_TIME','SELF_EMPLOYED','FREELANCE','CONTRACT','INTERNSHIP','APPRENTICESHIP','SEASONAL') DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `main_story_id` int DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `to_date` date DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9voqkqq65alkrlwgh9kgcp1ve` (`user_id`,`slug`),
  CONSTRAINT `user_experience_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `experiences` WRITE;
/*!40000 ALTER TABLE `experiences` DISABLE KEYS */;
INSERT INTO `experiences` VALUES (1,'2023-10-24 07:13:07.071000','2023-10-24 07:13:07.071000','full-stack-developer','GPI s.p.a.','My First experience as a full stack developer for a big corporate in the healthcare business.','FULL_TIME',NULL,'Trento',NULL,'Full Stack Developer',NULL,1);
/*!40000 ALTER TABLE `experiences` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `project_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `project_seq` WRITE;
/*!40000 ALTER TABLE `project_seq` DISABLE KEYS */;
INSERT INTO `project_seq` VALUES (15);
/*!40000 ALTER TABLE `project_seq` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `project_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_skills` (
  `project_id` int NOT NULL,
  `skill_id` int NOT NULL,
  PRIMARY KEY (`project_id`,`skill_id`),
  KEY `FKoik2usavkhwhi78q9wj6e3lg2` (`skill_id`),
  CONSTRAINT `FK57v4gsyik9h1a1xu92sp1f3wj` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
  CONSTRAINT `FKoik2usavkhwhi78q9wj6e3lg2` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `project_skills` WRITE;
/*!40000 ALTER TABLE `project_skills` DISABLE KEYS */;
INSERT INTO `project_skills` VALUES (1,1),(14,1),(1,2),(14,2),(1,3),(14,3);
/*!40000 ALTER TABLE `project_skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects` (
  `id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `main_story_id` int DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKi9tp7rf57y1s8iekb9cv2sjxm` (`user_id`,`slug`),
  CONSTRAINT `user_project_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,'2023-10-24 05:14:39.513000','2023-10-25 08:35:18.287000','myportfolio','A Personal project of developing a web application to allow people telling themself through stories',2,'MyPortfolio',1),(14,'2023-10-25 08:37:57.090000','2023-10-25 08:42:09.290000','ourlists','An innovative web applications for shared shopping lists.',61,'OurLists',1);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `skill_category_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill_category_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `skill_category_seq` WRITE;
/*!40000 ALTER TABLE `skill_category_seq` DISABLE KEYS */;
INSERT INTO `skill_category_seq` VALUES (14);
/*!40000 ALTER TABLE `skill_category_seq` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `skill_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `skill_seq` WRITE;
/*!40000 ALTER TABLE `skill_seq` DISABLE KEYS */;
INSERT INTO `skill_seq` VALUES (12);
/*!40000 ALTER TABLE `skill_seq` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `skill_category_fk` (`category_id`),
  CONSTRAINT `skill_category_fk` FOREIGN KEY (`category_id`) REFERENCES `skills_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (1,'Java',2),(2,'React',1),(3,'MySql',3);
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `skills_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills_category` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `skills_category` WRITE;
/*!40000 ALTER TABLE `skills_category` DISABLE KEYS */;
INSERT INTO `skills_category` VALUES (1,'Programmazione FE'),(2,'Programmazione BE'),(3,'DBMS'),(4,'Programmazione SQL'),(5,'Design');
/*!40000 ALTER TABLE `skills_category` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `stories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stories` (
  `id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `first_relevant_section` text,
  `from_date` date DEFAULT NULL,
  `order_in_education` int DEFAULT NULL,
  `order_in_experience` int DEFAULT NULL,
  `order_in_project` int DEFAULT NULL,
  `second_relevant_section` text,
  `title` varchar(255) NOT NULL,
  `to_date` date DEFAULT NULL,
  `diary_id` int NOT NULL,
  `education_id` int DEFAULT NULL,
  `experience_id` int DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKorsqpe9h3ftdtes9mn69m11nf` (`diary_id`,`slug`),
  KEY `education_story_fk` (`education_id`),
  KEY `experience_story_fk` (`experience_id`),
  KEY `project_story_fk` (`project_id`),
  CONSTRAINT `diary_story_fk` FOREIGN KEY (`diary_id`) REFERENCES `diaries` (`id`),
  CONSTRAINT `education_story_fk` FOREIGN KEY (`education_id`) REFERENCES `educations` (`id`),
  CONSTRAINT `experience_story_fk` FOREIGN KEY (`experience_id`) REFERENCES `experiences` (`id`),
  CONSTRAINT `project_story_fk` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `stories` WRITE;
/*!40000 ALTER TABLE `stories` DISABLE KEYS */;
INSERT INTO `stories` VALUES (1,'2023-10-24 07:20:24.279000','2023-10-24 07:20:24.279000','hi-everyone','This is me. I\'m a full stack developer who loves spending time alone coding and learning new things. This story will probably become longere with time passing.',NULL,NULL,NULL,NULL,NULL,NULL,'Hi everyone!',NULL,1,NULL,NULL,NULL),(2,'2023-10-24 07:23:23.045000','2023-10-25 08:34:53.561000','why-spring-boot','With all the options out there why did I chose spring boot for all my personal projects? Well this is a good question.','Did you know that stories can have highligted sections?','2022-01-01',NULL,NULL,1,'It can have only two... for now ;)','Why Spring Boot?',NULL,1,NULL,NULL,1),(60,'2023-10-25 08:34:53.557000','2023-10-26 08:34:53.557000','why-nextjs','With all the options out there why did I chose NextJs for all my personal projects? Well this is a good question.','Did you know that stories can have highligted sections?','2022-01-01',NULL,NULL,2,'It can have only two... for now ;)','Why NextJs?',NULL,1,NULL,NULL,1),(61,'2023-10-25 08:41:38.164000','2023-10-25 08:41:38.164000','what-is-it','OriLists is a web application that allows multiple people to create a shared shopping lists. It creates alerts when you are close to a shop that could sell the products you need and have a lot more fun features!','Did you know that stories can have highligted sections?','2015-01-01',NULL,NULL,1,'It can have only two... for now ;)','What is it?','2015-06-01',1,NULL,NULL,14),(62,'2023-10-25 10:09:20.420000','2023-10-25 10:09:20.420000','the-biggest-difficulties','The biggest difficulties of developing this web application was the lack in our knoledge of programming.','Did you know that stories can have highligted sections?','2015-01-01',NULL,NULL,2,'It can have only two... for now ;)','The biggest difficulties','2015-06-01',1,NULL,NULL,14);
/*!40000 ALTER TABLE `stories` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `story_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `story_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `story_seq` WRITE;
/*!40000 ALTER TABLE `story_seq` DISABLE KEYS */;
INSERT INTO `story_seq` VALUES (63);
/*!40000 ALTER TABLE `story_seq` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `story_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `story_skills` (
  `story_id` int NOT NULL,
  `skill_id` int NOT NULL,
  PRIMARY KEY (`story_id`,`skill_id`),
  KEY `FKbff55eb8wsakgg14kjxwun8v3` (`skill_id`),
  CONSTRAINT `FKbff55eb8wsakgg14kjxwun8v3` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`),
  CONSTRAINT `FKtp1kk1dphrmgydrrffnq3n1jb` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `story_skills` WRITE;
/*!40000 ALTER TABLE `story_skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `story_skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `user_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (15);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `user_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_skills` (
  `is_main` bit(1) DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `skill_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`skill_id`,`user_id`),
  KEY `FKro13if9r7fwkr5115715127ai` (`user_id`),
  CONSTRAINT `FKh223y61gwijpgqt6nlsuti07g` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`),
  CONSTRAINT `FKro13if9r7fwkr5115715127ai` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `user_skills` WRITE;
/*!40000 ALTER TABLE `user_skills` DISABLE KEYS */;
INSERT INTO `user_skills` VALUES (_binary '',1,1,1),(_binary '',2,2,1),(_binary '',3,3,1);
/*!40000 ALTER TABLE `user_skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `cap` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `main_story_id` int DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `sex` enum('MALE','FEMALE') DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK82kvqupf9ax45leg8b0nofwh2` (`slug`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2023-10-23 17:09:56.341000','2023-10-24 07:06:36.635000','roberto-dellantonio','Via Garibaldi 74',NULL,'38037','Predazzo',NULL,'dellantonio47@gmail.com','Roberto','Dellantonio',NULL,'Italia','Italiana',NULL,'Trento',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

