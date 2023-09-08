-- Crea l'utente "myportfolio"
CREATE USER 'myportfolio'@'%' IDENTIFIED BY 'password';

-- Assegna tutti i privilegi all'utente "myportfolio"
GRANT ALL PRIVILEGES ON *.* TO 'myportfolio'@'%';

-- Crea i database "auth" e "core"
CREATE DATABASE IF NOT EXISTS auth;
CREATE DATABASE IF NOT EXISTS core;

-- Seleziona il database "auth"
USE auth;

-- Crea la tabella "permissions"
CREATE TABLE IF NOT EXISTS permissions (
                                           id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           description VARCHAR(255) NULL,
    name        VARCHAR(255) NULL
    );

-- Crea la tabella "roles"
CREATE TABLE IF NOT EXISTS roles (
                                     id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NULL
    );

-- Crea la tabella "role_permissions"
CREATE TABLE IF NOT EXISTS role_permissions (
                                                role_id       BIGINT NOT NULL,
                                                permission_id BIGINT NOT NULL,
                                                PRIMARY KEY (role_id, permission_id),
    CONSTRAINT FKegdk29eiy7mdtefy5c7eirr6e FOREIGN KEY (permission_id) REFERENCES permissions (id),
    CONSTRAINT FKn5fotdgk8d1xvo8nav9uv3muc FOREIGN KEY (role_id) REFERENCES roles (id)
    );

-- Popola la tabella "roles"
INSERT INTO roles (id, name)
VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_BASIC');

-- Popola la tabella "permissions"
INSERT INTO permissions (id, name, description)
VALUES
    (1, 'users_read', 'users_read'),
    (2, 'users_write', 'users_write');

-- Popola la tabella "role_permissions"
INSERT INTO role_permissions (role_id, permission_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);


##############################
##  DUMMY DATA (08/09/2023) ##
##############################
ALTER TABLE skills AUTO_INCREMENT = 1;
ALTER TABLE skills_category AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE diaries AUTO_INCREMENT = 1;
ALTER TABLE educations AUTO_INCREMENT = 1;
ALTER TABLE experiences AUTO_INCREMENT = 1;
ALTER TABLE experience_skills AUTO_INCREMENT = 1;
ALTER TABLE projects AUTO_INCREMENT = 1;
ALTER TABLE project_skills AUTO_INCREMENT = 1;
ALTER TABLE stories AUTO_INCREMENT = 1;
ALTER TABLE story_educations AUTO_INCREMENT = 1;
ALTER TABLE story_experiences AUTO_INCREMENT = 1;
ALTER TABLE story_projects AUTO_INCREMENT = 1;
ALTER TABLE story_skills AUTO_INCREMENT = 1;
ALTER TABLE user_skills AUTO_INCREMENT = 1;
-- Insert new mock data into the 'skills_category' table
INSERT INTO skills_category (name)
VALUES
    ('Programming'),
    ('Web Development'),
    ('Database');

-- Insert new mock data into the 'skills' table
INSERT INTO skills (name, category_id)
VALUES
    ('Java', 1),
    ('React', 2),
    ('SQL', 3);

-- Insert mock data into the 'users' table
INSERT INTO users (address, age, cap, city, email, first_name, last_name, nation, nationality, province, sex, slug, description, phone, title)
VALUES
    ('123 Main St', 28, '12345', 'Milan', 'user1@example.com', 'John', 'Doe', 'Italy', 'Italian', 'MI', 'Male', 'john-doe', 'I am a software engineer with a passion for creating innovative solutions...', '+1234567890', 'Software Engineer'),
    ('456 Elm St', 25, '54321', 'Rome', 'user2@example.com', 'Jane', 'Smith', 'Italy', 'Italian', 'RM', 'Female', 'jane-smith', 'I specialize in web development and love turning ideas into interactive websites...', '+9876543210', 'Web Developer'),
    ('789 Oak St', 30, '67890', 'Naples', 'user3@example.com', 'Michael', 'Johnson', 'Italy', 'Italian', 'NA', 'Male', 'michael-johnson', 'I have a strong background in database management and optimization...', '+1122334455', 'Database Administrator'),
    ('101 Pine St', 32, '11223', 'Turin', 'user4@example.com', 'Emily', 'Brown', 'Italy', 'Italian', 'TO', 'Female', 'emily-brown', 'I am passionate about UI/UX design and crafting delightful user experiences...', '+9988776655', 'UI/UX Designer');

INSERT INTO diaries (user_id)
VALUES
    (1),
    (2),
    (3),
    (4);


-- Insert new mock data into the 'educations' table with descriptions
-- Use a random HTML description between 500 and 1500 characters for each row
INSERT INTO educations (degree, description, school, start_date, user_id, entry_date_time)
VALUES
    ('Bachelor of Science', '<h2>Computer Science</h2><p>This is a blog about my educational journey in computer science. It all began with a passion for technology and coding. I pursued my bachelor\'s degree at the University of XYZ, where I learned the fundamentals of algorithms, data structures, and software development. Throughout my academic journey, I worked on exciting projects and gained hands-on experience in programming...</p>', 'University of XYZ', '2020-09-01', 1, NOW()),
    ('Master of Engineering', '<h2>Software Engineering</h2><p>In this blog, I delve deep into the world of software engineering. From coding challenges to team projects, I share my experiences and insights into building robust and scalable software solutions. My master\'s degree in software engineering from ABC University has equipped me with advanced knowledge and problem-solving skills...</p>', 'ABC University', '2018-09-01', 2, NOW()),
    ('Bachelor of Computer Science', '<h2>Computer Science Journey</h2><p>My educational journey in computer science has been remarkable. I studied computer science at XYZ University, where I honed my programming skills and explored various technologies. This blog documents my path from a curious student to a proficient software engineer...</p>', 'XYZ University', '2019-08-01', 3, NOW()),
    ('Master of Web Development', '<h2>Web Development Adventures</h2><p>My passion for web development led me to pursue a master\'s degree in this field. Throughout my studies at WebDev Institute, I learned to create stunning websites and web applications. This blog showcases my web development journey and the projects that have shaped my skills...</p>', 'WebDev Institute', '2017-09-01', 4, NOW());

-- Insert new mock data into the 'experiences' table with descriptions
-- Use a random HTML description between 500 and 1500 characters for each row
INSERT INTO experiences (description, start_date, user_id, entry_date_time)
VALUES
    ('<h2>Software Developer</h2><p>My journey as a software developer has been filled with exciting projects. I\'ve learned to write clean and efficient code, collaborate with cross-functional teams, and deliver high-quality software solutions. My experience includes working on Java and Spring Boot applications, as well as React frontends...</p>', '2022-01-15', 1, NOW()),
    ('<h2>Frontend Developer</h2><p>As a frontend developer, I\'ve had the opportunity to create user-friendly interfaces. From responsive designs to interactive web apps, I\'ve contributed to enhancing the user experience. My skills include React, Next.js, and CSS frameworks...</p>', '2021-06-01', 2, NOW()),
    ('<h2>Database Administrator</h2><p>I have a strong background in database management and optimization. My role as a database administrator involves ensuring data integrity, performance tuning, and maintaining data security. I am proficient in SQL, database design, and cloud-based databases...</p>', '2020-03-10', 3, NOW()),
    ('<h2>UI/UX Designer</h2><p>I am passionate about UI/UX design and crafting delightful user experiences. From wireframing to prototyping, I focus on user-centered design principles. My design toolkit includes Adobe XD, Figma, and a keen eye for detail...</p>', '2019-07-20', 4, NOW());

-- Insert new mock data into the 'projects' table with descriptions
-- Use a random HTML description between 500 and 1500 characters for each row
INSERT INTO projects (title, description, user_id, entry_date_time)
VALUES
    ('E-commerce Platform', '<h2>Building an E-commerce Platform</h2><p>Our team embarked on the ambitious project to build an e-commerce platform that would revolutionize online shopping. I played a key role in developing the backend using Java and Spring Boot, as well as integrating payment gateways...</p>', 1, NOW()),
    ('Personal Portfolio', '<h2>Creating a Personal Portfolio</h2><p>Designing and developing my personal portfolio was a journey filled with creativity. I wanted to showcase my skills and projects in a visually appealing way. This project allowed me to experiment with the latest web development technologies...</p>', 2, NOW()),
    ('Database Optimization', '<h2>Database Optimization Project</h2><p>I led a database optimization project to enhance query performance and reduce response times. This project involved SQL query tuning, indexing strategies, and implementing caching mechanisms. The result was a significant improvement in application speed...</p>', 3, NOW()),
    ('UI Redesign', '<h2>User Interface Redesign</h2><p>Redesigning the user interface of our flagship product was an exciting challenge. I worked closely with the design team to create a modern and intuitive UI. The project included creating responsive layouts and implementing user feedback...</p>', 4, NOW());

-- Insert new mock data into the 'stories' table with descriptions
-- Use a random HTML description between 500 and 1500 characters for each row
INSERT INTO stories (description, title, diary_id, entry_date_time)
VALUES
    ('<h2>My Journey in Software Development</h2><p>From writing my first line of code to becoming a seasoned software developer, this is my story. Join me on this exciting journey as I share the challenges, triumphs, and lessons learned along the way...</p>', 'Software Development Journey', 1, NOW()),
    ('<h2>Frontend Redesign Project</h2><p>Exploring the challenges and triumphs of redesigning our frontend. From wireframing to user testing, every step was crucial to success. This blog delves into the details of the project and the impact it had on our users...</p>', 'Frontend Redesign Project', 2, NOW()),
    ('<h2>Database Optimization Story</h2><p>Optimizing our database was a critical project that improved our application\'s performance. In this blog, I share the strategies and techniques used to achieve remarkable results. Database administrators and developers will find valuable insights...</p>', 'Database Optimization Story', 3, NOW()),
    ('<h2>Designing User-Focused Interfaces</h2><p>As a UI/UX designer, my goal is to create interfaces that users love. Join me on a journey of designing user-focused interfaces, from wireframes to interactive prototypes. This blog showcases my passion for creating visually appealing and user-friendly designs...</p>', 'UI/UX Design Chronicles', 4, NOW());

-- Insert new mock data into the 'experience_skills' table
INSERT INTO experience_skills (experience_id, skill_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 2);

-- Insert new mock data into the 'project_skills' table
INSERT INTO project_skills (project_id, skill_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 2);

-- Insert new mock data into the 'story_skills' table
INSERT INTO story_skills (story_id, skill_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 2);

INSERT INTO user_skills (is_main, order_id, skill_id, user_id)
VALUES
    (1, 1, 1, 1), -- User 1 with skill Java as the main skill
    (1, 1, 2, 2), -- User 2 with skill React as the main skill
    (1, 1, 3, 3), -- User 3 with skill SQL as the main skill
    (1, 1, 2, 4); -- User 4 with skill React as the main skill

-- Insert new mock data into the 'story_educations' table
INSERT INTO story_educations (story_id, education_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);

-- Insert new mock data into the 'story_experiences' table
INSERT INTO story_experiences (story_id, experience_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);

-- Insert new mock data into the 'story_projects' table
INSERT INTO story_projects (story_id, project_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);