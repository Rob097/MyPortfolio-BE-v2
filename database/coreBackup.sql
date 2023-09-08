
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
DROP TABLE IF EXISTS `diaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diaries` (
  `id` int NOT NULL AUTO_INCREMENT,
  `entry_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_diary_fk` (`user_id`),
  CONSTRAINT `user_diary_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `diaries` WRITE;
/*!40000 ALTER TABLE `diaries` DISABLE KEYS */;
INSERT INTO `diaries` VALUES (1,'2023-09-08 11:42:16',1),(2,'2023-09-08 11:42:16',2),(3,'2023-09-08 11:42:16',3),(4,'2023-09-08 11:42:16',4);
/*!40000 ALTER TABLE `diaries` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `educations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `educations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `degree` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `end_date` date DEFAULT NULL,
  `field` varchar(255) DEFAULT NULL,
  `grade` double DEFAULT NULL,
  `school` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `user_id` int NOT NULL,
  `entry_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `slug` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_education_fk` (`user_id`),
  CONSTRAINT `user_education_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `educations` WRITE;
/*!40000 ALTER TABLE `educations` DISABLE KEYS */;
INSERT INTO `educations` VALUES (1,'Bachelor of Science','<h2>Computer Science</h2><p>This is a blog about my educational journey in computer science. It all began with a passion for technology and coding. I pursued my bachelor\'s degree at the University of XYZ, where I learned the fundamentals of algorithms, data structures, and software development. Throughout my academic journey, I worked on exciting projects and gained hands-on experience in programming...</p>','2019-09-30','Computer Science',100,'University of XYZ','2020-09-01',1,'2023-09-08 11:42:16','computer-science-university-of-XYZ'),(2,'Master of Engineering','<h2>Software Engineering</h2><p>In this blog, I delve deep into the world of software engineering. From coding challenges to team projects, I share my experiences and insights into building robust and scalable software solutions. My master\'s degree in software engineering from ABC University has equipped me with advanced knowledge and problem-solving skills...</p>','2019-09-30','Software Engineering',80,'ABC University','2018-09-01',2,'2023-09-08 11:42:16','software-engineering-ABC-university'),(3,'Bachelor of Computer Science','<h2>Computer Science Journey</h2><p>My educational journey in computer science has been remarkable. I studied computer science at XYZ University, where I honed my programming skills and explored various technologies. This blog documents my path from a curious student to a proficient software engineer...</p>','2019-09-30','Computer Science',96,'XYZ University','2019-08-01',3,'2023-09-08 11:42:16','computer-science-XYZ-university'),(4,'Master of Web Development','<h2>Web Development Adventures</h2><p>My passion for web development led me to pursue a master\'s degree in this field. Throughout my studies at WebDev Institute, I learned to create stunning websites and web applications. This blog showcases my web development journey and the projects that have shaped my skills...</p>','2019-09-30','Web Development',78,'WebDev Institute','2017-09-01',4,'2023-09-08 11:42:16','web-development-webdev-institute');
/*!40000 ALTER TABLE `educations` ENABLE KEYS */;
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
INSERT INTO `experience_skills` VALUES (1,1),(2,2),(4,2),(3,3);
/*!40000 ALTER TABLE `experience_skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `experiences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `experiences` (
  `id` int NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `description` text NOT NULL,
  `employment_type` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `start_date` date NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int NOT NULL,
  `entry_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `slug` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_experience_fk` (`user_id`),
  CONSTRAINT `user_experience_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `experiences` WRITE;
/*!40000 ALTER TABLE `experiences` DISABLE KEYS */;
INSERT INTO `experiences` VALUES (1,'Samsung','<h2>Software Developer</h2><p>My journey as a software developer has been filled with exciting projects. I\'ve learned to write clean and efficient code, collaborate with cross-functional teams, and deliver high-quality software solutions. My experience includes working on Java and Spring Boot applications, as well as React frontends...</p>',NULL,NULL,NULL,'2022-01-15','Software Developer',1,'2023-09-08 11:42:16','software-developer-samsung'),(2,'Netflix','<h2>Frontend Developer</h2><p>As a frontend developer, I\'ve had the opportunity to create user-friendly interfaces. From responsive designs to interactive web apps, I\'ve contributed to enhancing the user experience. My skills include React, Next.js, and CSS frameworks...</p>',NULL,NULL,NULL,'2021-06-01','Frontend Developer',2,'2023-09-08 11:42:16','frontend-developer-netflix'),(3,'Apple','<h2>Database Administrator</h2><p>I have a strong background in database management and optimization. My role as a database administrator involves ensuring data integrity, performance tuning, and maintaining data security. I am proficient in SQL, database design, and cloud-based databases...</p>',NULL,NULL,NULL,'2020-03-10','Database Administrator',3,'2023-09-08 11:42:16','database-administrator-apple'),(4,'Amazon','<h2>UI/UX Designer</h2><p>I am passionate about UI/UX design and crafting delightful user experiences. From wireframing to prototyping, I focus on user-centered design principles. My design toolkit includes Adobe XD, Figma, and a keen eye for detail...</p>',NULL,NULL,NULL,'2019-07-20','UI/UX Designer',4,'2023-09-08 11:42:16','ui-ux-designer-amazon');
/*!40000 ALTER TABLE `experiences` ENABLE KEYS */;
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
INSERT INTO `project_skills` VALUES (1,1),(2,2),(4,2),(3,3);
/*!40000 ALTER TABLE `project_skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `user_id` int NOT NULL,
  `description` text NOT NULL,
  `entry_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `slug` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_project_fk` (`user_id`),
  CONSTRAINT `user_project_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,'E-commerce Platform',1,'<h2>Building an E-commerce Platform</h2><p>Our team embarked on the ambitious project to build an e-commerce platform that would revolutionize online shopping. I played a key role in developing the backend using Java and Spring Boot, as well as integrating payment gateways...</p>','2023-09-08 11:42:16','e-commerce-platform'),(2,'Personal Portfolio',2,'<h2>Creating a Personal Portfolio</h2><p>Designing and developing my personal portfolio was a journey filled with creativity. I wanted to showcase my skills and projects in a visually appealing way. This project allowed me to experiment with the latest web development technologies...</p>','2023-09-08 11:42:16','personal-portfolio'),(3,'Database Optimization',3,'<h2>Database Optimization Project</h2><p>I led a database optimization project to enhance query performance and reduce response times. This project involved SQL query tuning, indexing strategies, and implementing caching mechanisms. The result was a significant improvement in application speed...</p>','2023-09-08 11:42:16','database-optimization'),(4,'UI Redesign',4,'<h2>User Interface Redesign</h2><p>Redesigning the user interface of our flagship product was an exciting challenge. I worked closely with the design team to create a modern and intuitive UI. The project included creating responsive layouts and implementing user feedback...</p>','2023-09-08 11:42:16','ui-redesign');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `skill_category_fk` (`category_id`),
  CONSTRAINT `skill_category_fk` FOREIGN KEY (`category_id`) REFERENCES `skills_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (1,'Java',1),(2,'React',2),(3,'SQL',3);
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `skills_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `skills_category` WRITE;
/*!40000 ALTER TABLE `skills_category` DISABLE KEYS */;
INSERT INTO `skills_category` VALUES (1,'Programming'),(2,'Web Development'),(3,'Database');
/*!40000 ALTER TABLE `skills_category` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `stories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `entry_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `from_date` date DEFAULT NULL,
  `is_primary_story` tinyint(1) NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL,
  `to_date` date DEFAULT NULL,
  `diary_id` int NOT NULL,
  `first_relevant_section` text,
  `second_relevant_section` text,
  `slug` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `diary_story_fk` (`diary_id`),
  CONSTRAINT `diary_story_fk` FOREIGN KEY (`diary_id`) REFERENCES `diaries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `stories` WRITE;
/*!40000 ALTER TABLE `stories` DISABLE KEYS */;
INSERT INTO `stories` VALUES (1,'<h2>My Journey in Software Development</h2><p>From writing my first line of code to becoming a seasoned software developer, this is my story. Join me on this exciting journey as I share the challenges, triumphs, and lessons learned along the way...</p>','2023-09-08 11:42:16',NULL,0,'Software Development Journey',NULL,1,NULL,NULL,'software-development-journey'),(2,'<h2>Frontend Redesign Project</h2><p>Exploring the challenges and triumphs of redesigning our frontend. From wireframing to user testing, every step was crucial to success. This blog delves into the details of the project and the impact it had on our users...</p>','2023-09-08 11:42:16',NULL,0,'Frontend Redesign Project',NULL,2,NULL,NULL,'frontend-redesign-project'),(3,'<h2>Database Optimization Story</h2><p>Optimizing our database was a critical project that improved our application\'s performance. In this blog, I share the strategies and techniques used to achieve remarkable results. Database administrators and developers will find valuable insights...</p>','2023-09-08 11:42:16',NULL,0,'Database Optimization Story',NULL,3,NULL,NULL,'database-optimization-story'),(4,'<h2>Designing User-Focused Interfaces</h2><p>As a UI/UX designer, my goal is to create interfaces that users love. Join me on a journey of designing user-focused interfaces, from wireframes to interactive prototypes. This blog showcases my passion for creating visually appealing and user-friendly designs...</p>','2023-09-08 11:42:16',NULL,0,'UI/UX Design Chronicles',NULL,4,NULL,NULL,'ui-ux-design-chronicles');
/*!40000 ALTER TABLE `stories` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `story_educations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `story_educations` (
  `story_id` int NOT NULL,
  `education_id` int NOT NULL,
  PRIMARY KEY (`story_id`,`education_id`),
  KEY `FKrcyjyu195igjrqya17c0sffw9` (`education_id`),
  CONSTRAINT `FKhf6vaxdrv4019tb4dsmsrp0pg` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`),
  CONSTRAINT `FKrcyjyu195igjrqya17c0sffw9` FOREIGN KEY (`education_id`) REFERENCES `educations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `story_educations` WRITE;
/*!40000 ALTER TABLE `story_educations` DISABLE KEYS */;
INSERT INTO `story_educations` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `story_educations` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `story_experiences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `story_experiences` (
  `story_id` int NOT NULL,
  `experience_id` int NOT NULL,
  PRIMARY KEY (`story_id`,`experience_id`),
  KEY `FKra239v5kjks2x77lstemninyy` (`experience_id`),
  CONSTRAINT `FKp9gk65qfobgucf0h9fuw7yxxp` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`),
  CONSTRAINT `FKra239v5kjks2x77lstemninyy` FOREIGN KEY (`experience_id`) REFERENCES `experiences` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `story_experiences` WRITE;
/*!40000 ALTER TABLE `story_experiences` DISABLE KEYS */;
INSERT INTO `story_experiences` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `story_experiences` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `story_projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `story_projects` (
  `story_id` int NOT NULL,
  `project_id` int NOT NULL,
  PRIMARY KEY (`story_id`,`project_id`),
  KEY `FK9n1r47s5vpjp2x3nri3wcelns` (`project_id`),
  CONSTRAINT `FK5ia6tbg6lvnb2s5g80yxa63pm` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`),
  CONSTRAINT `FK9n1r47s5vpjp2x3nri3wcelns` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `story_projects` WRITE;
/*!40000 ALTER TABLE `story_projects` DISABLE KEYS */;
INSERT INTO `story_projects` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `story_projects` ENABLE KEYS */;
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
INSERT INTO `story_skills` VALUES (1,1),(2,2),(4,2),(3,3);
/*!40000 ALTER TABLE `story_skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `user_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_skills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_main` bit(1) DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `skill_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh223y61gwijpgqt6nlsuti07g` (`skill_id`),
  KEY `FKro13if9r7fwkr5115715127ai` (`user_id`),
  CONSTRAINT `FKh223y61gwijpgqt6nlsuti07g` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`),
  CONSTRAINT `FKro13if9r7fwkr5115715127ai` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `user_skills` WRITE;
/*!40000 ALTER TABLE `user_skills` DISABLE KEYS */;
INSERT INTO `user_skills` VALUES (1,_binary '',1,1,1),(2,_binary '',1,2,2),(3,_binary '',1,3,3),(4,_binary '',1,2,4);
/*!40000 ALTER TABLE `user_skills` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `cap` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `slug` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'123 Main St',28,'12345','Milan','user1@example.com','John','Doe','Italy','Italian','MI','MALE','john-doe','I am a software engineer with a passion for creating innovative solutions...','+1234567890','Software Engineer'),(2,'456 Elm St',25,'54321','Rome','user2@example.com','Jane','Smith','Italy','Italian','RM','FEMALE','jane-smith','I specialize in web development and love turning ideas into interactive websites...','+9876543210','Web Developer'),(3,'789 Oak St',30,'67890','Naples','user3@example.com','Michael','Johnson','Italy','Italian','NA','MALE','michael-johnson','I have a strong background in database management and optimization...','+1122334455','Database Administrator'),(4,'101 Pine St',32,'11223','Turin','user4@example.com','Emily','Brown','Italy','Italian','TO','FEMALE','emily-brown','I am passionate about UI/UX design and crafting delightful user experiences...','+9988776655','UI/UX Designer');
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

