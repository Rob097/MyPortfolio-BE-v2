use core;

-- Insert new skill categories
INSERT INTO skills_category (id, name) VALUES (1, 'Programming');
INSERT INTO skills_category (id, name) VALUES (5, 'Design');
INSERT INTO skills_category (id, name) VALUES (14, 'Marketing');
INSERT INTO skills_category (id, name) VALUES (15, 'General skills');
INSERT INTO skills_category (id, name) VALUES (16, 'Software Engineering');
INSERT INTO skills_category (id, name) VALUES (17, 'Architecture');
INSERT INTO skills_category (id, name) VALUES (18, 'Art');
INSERT INTO skills_category (id, name) VALUES (19, 'Data Science');
INSERT INTO skills_category (id, name) VALUES (20, 'Healthcare');
INSERT INTO skills_category (id, name) VALUES (21, 'Finance');
INSERT INTO skills_category (id, name) VALUES (22, 'Education');
INSERT INTO skills_category (id, name) VALUES (23, 'Engineering');
INSERT INTO skills_category (id, name) VALUES (24, 'Human Resources');
INSERT INTO skills_category (id, name) VALUES (25, 'Business');
INSERT INTO skills_category (id, name) VALUES (26, 'Legal');
INSERT INTO skills_category (id, name) VALUES (27, 'Hospitality');
INSERT INTO skills_category (id, name) VALUES (28, 'Science');
INSERT INTO skills_category (id, name) VALUES (29, 'Writing');
INSERT INTO skills_category (id, name) VALUES (30, 'Music');
INSERT INTO skills_category (id, name) VALUES (31, 'Sports');

SET @next_val := (SELECT next_val FROM skill_category_seq LIMIT 1);
UPDATE skill_category_seq SET next_val = 32 WHERE next_val = @next_val;

-- Insert new skills for the "Software Engineering" category
INSERT INTO skills (id, name, category_id) VALUES (1, 'Java', 1);
INSERT INTO skills (id, name, category_id) VALUES (2, 'React', 1);
INSERT INTO skills (id, name, category_id) VALUES (3, 'SQL', 1);
INSERT INTO skills (id, name, category_id) VALUES (12, 'HTML', 1);
INSERT INTO skills (id, name, category_id) VALUES (13, 'CSS', 1);
INSERT INTO skills (id, name, category_id) VALUES (14, 'JavaScript', 1);
INSERT INTO skills (id, name, category_id) VALUES (15, 'PHP', 1);
INSERT INTO skills (id, name, category_id) VALUES (16, 'Python', 1);
INSERT INTO skills (id, name, category_id) VALUES (17, 'Angular', 1);
INSERT INTO skills (id, name, category_id) VALUES (18, 'Node.js', 1);
INSERT INTO skills (id, name, category_id) VALUES (19, 'Django', 1);
INSERT INTO skills (id, name, category_id) VALUES (20, 'Ruby on Rails', 1);
INSERT INTO skills (id, name, category_id) VALUES (21, 'NextJs', 1);
INSERT INTO skills (id, name, category_id) VALUES (22, 'SEO', 14);
INSERT INTO skills (id, name, category_id) VALUES (23, 'Link Building', 14);
INSERT INTO skills (id, name, category_id) VALUES (24, 'Social media', 14);
INSERT INTO skills (id, name, category_id) VALUES (25, 'Email marketing', 14);
INSERT INTO skills (id, name, category_id) VALUES (26, 'Content marketing', 14);
INSERT INTO skills (id, name, category_id) VALUES (27, 'Copywriting', 14);
INSERT INTO skills (id, name, category_id) VALUES (28, 'Branding', 14);
INSERT INTO skills (id, name, category_id) VALUES (29, 'Content strategy', 14);
INSERT INTO skills (id, name, category_id) VALUES (30, 'Customer relationship management', 14);
INSERT INTO skills (id, name, category_id) VALUES (31, 'Public relations', 14);
INSERT INTO skills (id, name, category_id) VALUES (32, 'Storytelling', 14);
INSERT INTO skills (id, name, category_id) VALUES (33, 'Data analysis', 15);
INSERT INTO skills (id, name, category_id) VALUES (34, 'Software development', 15);
INSERT INTO skills (id, name, category_id) VALUES (35, 'Project management', 15);
INSERT INTO skills (id, name, category_id) VALUES (36, 'Communication', 15);
INSERT INTO skills (id, name, category_id) VALUES (37, 'Business analysis', 15);
INSERT INTO skills (id, name, category_id) VALUES (38, 'Database design', 15);
INSERT INTO skills (id, name, category_id) VALUES (39, 'Web development', 15);
INSERT INTO skills (id, name, category_id) VALUES (40, 'User interface design', 15);
INSERT INTO skills (id, name, category_id) VALUES (41, 'Information security', 15);
INSERT INTO skills (id, name, category_id) VALUES (42, 'Innovation', 15);
INSERT INTO skills (id, name, category_id) VALUES (43, 'Marketing', 15);
INSERT INTO skills (id, name, category_id) VALUES (44, 'JSP', 1);
INSERT INTO skills (id, name, category_id) VALUES (45, 'JSTL', 1);
INSERT INTO skills (id, name, category_id) VALUES (46, 'NoSql', 1);
INSERT INTO skills (id, name, category_id) VALUES (47, 'Multi-tenancy', 1);
INSERT INTO skills (id, name, category_id) VALUES (48, 'DevOps', 16);
INSERT INTO skills (id, name, category_id) VALUES (49, 'Cloud Computing', 16);
INSERT INTO skills (id, name, category_id) VALUES (50, 'Microservices', 16);
INSERT INTO skills (id, name, category_id) VALUES (51, 'Version Control', 16);
INSERT INTO skills (id, name, category_id) VALUES (52, 'Agile Methodologies', 16);

-- Insert new skills for the "Architecture" category
INSERT INTO skills (id, name, category_id) VALUES (53, 'Structural Analysis', 17);
INSERT INTO skills (id, name, category_id) VALUES (54, 'AutoCAD', 17);
INSERT INTO skills (id, name, category_id) VALUES (55, 'Revit', 17);
INSERT INTO skills (id, name, category_id) VALUES (56, 'Building Information Modeling (BIM)', 17);
INSERT INTO skills (id, name, category_id) VALUES (57, 'Sustainable Design', 17);

-- Insert new skills for the "Art" category
INSERT INTO skills (id, name, category_id) VALUES (58, 'Digital Painting', 18);
INSERT INTO skills (id, name, category_id) VALUES (59, 'Sculpture', 18);
INSERT INTO skills (id, name, category_id) VALUES (60, 'Art History', 18);
INSERT INTO skills (id, name, category_id) VALUES (61, 'Graphic Design', 18);
INSERT INTO skills (id, name, category_id) VALUES (62, 'Photography', 18);

-- Insert new skills for the "Data Science" category
INSERT INTO skills (id, name, category_id) VALUES (63, 'Machine Learning', 19);
INSERT INTO skills (id, name, category_id) VALUES (64, 'Deep Learning', 19);
INSERT INTO skills (id, name, category_id) VALUES (65, 'Data Visualization', 19);
INSERT INTO skills (id, name, category_id) VALUES (66, 'Big Data', 19);
INSERT INTO skills (id, name, category_id) VALUES (67, 'Statistical Analysis', 19);

-- Insert new skills for the "Healthcare" category
INSERT INTO skills (id, name, category_id) VALUES (68, 'Patient Care', 20);
INSERT INTO skills (id, name, category_id) VALUES (69, 'Medical Coding', 20);
INSERT INTO skills (id, name, category_id) VALUES (70, 'Healthcare Management', 20);
INSERT INTO skills (id, name, category_id) VALUES (71, 'Nursing', 20);
INSERT INTO skills (id, name, category_id) VALUES (72, 'Public Health', 20);

-- Insert new skills for the "Finance" category
INSERT INTO skills (id, name, category_id) VALUES (73, 'Financial Analysis', 21);
INSERT INTO skills (id, name, category_id) VALUES (74, 'Investment Banking', 21);
INSERT INTO skills (id, name, category_id) VALUES (75, 'Risk Management', 21);
INSERT INTO skills (id, name, category_id) VALUES (76, 'Accounting', 21);
INSERT INTO skills (id, name, category_id) VALUES (77, 'Taxation', 21);

-- Insert new skills for the "Education" category
INSERT INTO skills (id, name, category_id) VALUES (78, 'Curriculum Development', 22);
INSERT INTO skills (id, name, category_id) VALUES (79, 'Educational Technology', 22);
INSERT INTO skills (id, name, category_id) VALUES (80, 'Classroom Management', 22);
INSERT INTO skills (id, name, category_id) VALUES (81, 'Special Education', 22);
INSERT INTO skills (id, name, category_id) VALUES (82, 'Educational Psychology', 22);

-- Insert new skills for the "Engineering" category
INSERT INTO skills (id, name, category_id) VALUES (83, 'Mechanical Engineering', 23);
INSERT INTO skills (id, name, category_id) VALUES (84, 'Electrical Engineering', 23);
INSERT INTO skills (id, name, category_id) VALUES (85, 'Civil Engineering', 23);
INSERT INTO skills (id, name, category_id) VALUES (86, 'Chemical Engineering', 23);
INSERT INTO skills (id, name, category_id) VALUES (87, 'Environmental Engineering', 23);

-- Insert new skills for the "Human Resources" category
INSERT INTO skills (id, name, category_id) VALUES (88, 'Talent Acquisition', 24);
INSERT INTO skills (id, name, category_id) VALUES (89, 'Employee Relations', 24);
INSERT INTO skills (id, name, category_id) VALUES (90, 'Performance Management', 24);
INSERT INTO skills (id, name, category_id) VALUES (91, 'Training and Development', 24);
INSERT INTO skills (id, name, category_id) VALUES (92, 'Compensation and Benefits', 24);

-- Insert new skills for the "Business" category
INSERT INTO skills (id, name, category_id) VALUES (93, 'Business Strategy', 25);
INSERT INTO skills (id, name, category_id) VALUES (94, 'Entrepreneurship', 25);
INSERT INTO skills (id, name, category_id) VALUES (95, 'Business Development', 25);
INSERT INTO skills (id, name, category_id) VALUES (96, 'Negotiation', 25);
INSERT INTO skills (id, name, category_id) VALUES (97, 'Operations Management', 25);

-- Insert new skills for the "Legal" category
INSERT INTO skills (id, name, category_id) VALUES (98, 'Contract Law', 26);
INSERT INTO skills (id, name, category_id) VALUES (99, 'Corporate Law', 26);
INSERT INTO skills (id, name, category_id) VALUES (100, 'Intellectual Property Law', 26);
INSERT INTO skills (id, name, category_id) VALUES (101, 'Criminal Law', 26);
INSERT INTO skills (id, name, category_id) VALUES (102, 'Legal Research', 26);

-- Insert new skills for the "Hospitality" category
INSERT INTO skills (id, name, category_id) VALUES (103, 'Event Planning', 27);
INSERT INTO skills (id, name, category_id) VALUES (104, 'Hotel Management', 27);
INSERT INTO skills (id, name, category_id) VALUES (105, 'Customer Service', 27);
INSERT INTO skills (id, name, category_id) VALUES (106, 'Food and Beverage Management', 27);
INSERT INTO skills (id, name, category_id) VALUES (107, 'Tourism Management', 27);

-- Insert new skills for the "Science" category
INSERT INTO skills (id, name, category_id) VALUES (108, 'Biology', 28);
INSERT INTO skills (id, name, category_id) VALUES (109, 'Chemistry', 28);
INSERT INTO skills (id, name, category_id) VALUES (110, 'Physics', 28);
INSERT INTO skills (id, name, category_id) VALUES (111, 'Environmental Science', 28);
INSERT INTO skills (id, name, category_id) VALUES (112, 'Astronomy', 28);

-- Insert new skills for the "Writing" category
INSERT INTO skills (id, name, category_id) VALUES (113, 'Creative Writing', 29);
INSERT INTO skills (id, name, category_id) VALUES (114, 'Technical Writing', 29);
INSERT INTO skills (id, name, category_id) VALUES (115, 'Blogging', 29);
INSERT INTO skills (id, name, category_id) VALUES (116, 'Editing', 29);
INSERT INTO skills (id, name, category_id) VALUES (117, 'Copywriting', 29);

-- Insert new skills for the "Music" category
INSERT INTO skills (id, name, category_id) VALUES (118, 'Music Theory', 30);
INSERT INTO skills (id, name, category_id) VALUES (119, 'Instrumental Performance', 30);
INSERT INTO skills (id, name, category_id) VALUES (120, 'Music Production', 30);
INSERT INTO skills (id, name, category_id) VALUES (121, 'Composition', 30);
INSERT INTO skills (id, name, category_id) VALUES (122, 'Music History', 30);

-- Insert new skills for the "Sports" category
INSERT INTO skills (id, name, category_id) VALUES (123, 'Coaching', 31);
INSERT INTO skills (id, name, category_id) VALUES (124, 'Sports Medicine', 31);
INSERT INTO skills (id, name, category_id) VALUES (125, 'Fitness Training', 31);
INSERT INTO skills (id, name, category_id) VALUES (126, 'Sports Management', 31);
INSERT INTO skills (id, name, category_id) VALUES (127, 'Nutrition', 31);

SET @next_val := (SELECT next_val FROM skill_seq LIMIT 1);
UPDATE skill_seq SET next_val = 128 WHERE next_val = @next_val;


##### SECOND PART #####
-- Insert new skills for the existing skills categories
-- Programming (ID: 1)
INSERT INTO skills (id, name, category_id) VALUES (128, 'Spring Framework', 1);
INSERT INTO skills (id, name, category_id) VALUES (129, 'Vue.js', 1);
INSERT INTO skills (id, name, category_id) VALUES (130, 'Docker Swarm', 1);
INSERT INTO skills (id, name, category_id) VALUES (131, 'Rust', 1);
INSERT INTO skills (id, name, category_id) VALUES (132, 'GraphQL', 1);

-- Design (ID: 5)
INSERT INTO skills (id, name, category_id) VALUES (133, 'User Experience (UX) Design', 5);
INSERT INTO skills (id, name, category_id) VALUES (134, 'Typography', 5);
INSERT INTO skills (id, name, category_id) VALUES (135, 'Motion Design', 5);
INSERT INTO skills (id, name, category_id) VALUES (136, 'Illustrator', 5);
INSERT INTO skills (id, name, category_id) VALUES (137, 'UI Prototyping', 5);

-- Marketing (ID: 14)
INSERT INTO skills (id, name, category_id) VALUES (138, 'Search Engine Marketing (SEM)', 14);
INSERT INTO skills (id, name, category_id) VALUES (139, 'Conversion Rate Optimization (CRO)', 14);
INSERT INTO skills (id, name, category_id) VALUES (140, 'Influencer Marketing', 14);
INSERT INTO skills (id, name, category_id) VALUES (141, 'Affiliate Marketing', 14);
INSERT INTO skills (id, name, category_id) VALUES (142, 'Marketing Automation', 14);

-- General skills (ID: 15)
INSERT INTO skills (id, name, category_id) VALUES (143, 'Time Management', 15);
INSERT INTO skills (id, name, category_id) VALUES (144, 'Critical Thinking', 15);
INSERT INTO skills (id, name, category_id) VALUES (145, 'Problem Solving', 15);
INSERT INTO skills (id, name, category_id) VALUES (146, 'Adaptability', 15);
INSERT INTO skills (id, name, category_id) VALUES (147, 'Emotional Intelligence', 15);

-- Newly generated skills categories
-- Software Engineering (ID: 16)
INSERT INTO skills (id, name, category_id) VALUES (148, 'Kotlin', 16);
INSERT INTO skills (id, name, category_id) VALUES (149, 'AWS Lambda', 16);
INSERT INTO skills (id, name, category_id) VALUES (150, 'Continuous Integration (CI)', 16);
INSERT INTO skills (id, name, category_id) VALUES (151, 'Test-Driven Development (TDD)', 16);
INSERT INTO skills (id, name, category_id) VALUES (152, 'GraphQL', 16);

-- Architecture (ID: 17)
INSERT INTO skills (id, name, category_id) VALUES (153, 'Urban Design', 17);
INSERT INTO skills (id, name, category_id) VALUES (154, 'Facade Design', 17);
INSERT INTO skills (id, name, category_id) VALUES (155, 'Interior Design', 17);
INSERT INTO skills (id, name, category_id) VALUES (156, 'Sustainability in Architecture', 17);
INSERT INTO skills (id, name, category_id) VALUES (157, '3D Modeling', 17);

-- Art (ID: 18)
INSERT INTO skills (id, name, category_id) VALUES (158, 'Oil Painting', 18);
INSERT INTO skills (id, name, category_id) VALUES (159, 'Calligraphy', 18);
INSERT INTO skills (id, name, category_id) VALUES (160, 'Street Art', 18);
INSERT INTO skills (id, name, category_id) VALUES (161, 'Installation Art', 18);
INSERT INTO skills (id, name, category_id) VALUES (162, 'Visual Arts Management', 18);

-- Data Science (ID: 19)
INSERT INTO skills (id, name, category_id) VALUES (163, 'Natural Language Processing (NLP)', 19);
INSERT INTO skills (id, name, category_id) VALUES (164, 'Time Series Analysis', 19);
INSERT INTO skills (id, name, category_id) VALUES (165, 'Data Engineering', 19);
INSERT INTO skills (id, name, category_id) VALUES (166, 'Data Warehousing', 19);
INSERT INTO skills (id, name, category_id) VALUES (167, 'Predictive Analytics', 19);

-- Healthcare (ID: 20)
INSERT INTO skills (id, name, category_id) VALUES (168, 'Medical Imaging', 20);
INSERT INTO skills (id, name, category_id) VALUES (169, 'Epidemiology', 20);
INSERT INTO skills (id, name, category_id) VALUES (170, 'Health Informatics', 20);
INSERT INTO skills (id, name, category_id) VALUES (171, 'Healthcare Analytics', 20);
INSERT INTO skills (id, name, category_id) VALUES (172, 'Healthcare Policy', 20);

-- Finance (ID: 21)
INSERT INTO skills (id, name, category_id) VALUES (173, 'Financial Modeling', 21);
INSERT INTO skills (id, name, category_id) VALUES (174, 'Personal Finance', 21);
INSERT INTO skills (id, name, category_id) VALUES (175, 'Financial Planning', 21);
INSERT INTO skills (id, name, category_id) VALUES (176, 'Investment Management', 21);
INSERT INTO skills (id, name, category_id) VALUES (177, 'Corporate Finance', 21);

-- Education (ID: 22)
INSERT INTO skills (id, name, category_id) VALUES (178, 'Online Teaching', 22);
INSERT INTO skills (id, name, category_id) VALUES (179, 'Learning Management Systems (LMS)', 22);
INSERT INTO skills (id, name, category_id) VALUES (180, 'Student Assessment', 22);
INSERT INTO skills (id, name, category_id) VALUES (181, 'Classroom Technology Integration', 22);
INSERT INTO skills (id, name, category_id) VALUES (182, 'Adult Education', 22);

-- Engineering (ID: 23)
INSERT INTO skills (id, name, category_id) VALUES (183, 'Robotics', 23);
INSERT INTO skills (id, name, category_id) VALUES (184, 'Industrial Engineering', 23);
INSERT INTO skills (id, name, category_id) VALUES (185, 'Systems Engineering', 23);
INSERT INTO skills (id, name, category_id) VALUES (186, 'Materials Science', 23);
INSERT INTO skills (id, name, category_id) VALUES (187, 'Aerospace Engineering', 23);

-- Human Resources (ID: 24)
INSERT INTO skills (id, name, category_id) VALUES (188, 'Employer Branding', 24);
INSERT INTO skills (id, name, category_id) VALUES (189, 'Diversity and Inclusion', 24);
INSERT INTO skills (id, name, category_id) VALUES (190, 'Human Resource Information Systems (HRIS)', 24);
INSERT INTO skills (id, name, category_id) VALUES (191, 'Labor Relations', 24);
INSERT INTO skills (id, name, category_id) VALUES (192, 'Workforce Planning', 24);

-- Business (ID: 25)
INSERT INTO skills (id, name, category_id) VALUES (193, 'Strategic Planning', 25);
INSERT INTO skills (id, name, category_id) VALUES (194, 'International Business', 25);
INSERT INTO skills (id, name, category_id) VALUES (195, 'Market Entry Strategy', 25);
INSERT INTO skills (id, name, category_id) VALUES (196, 'Supply Chain Management', 25);
INSERT INTO skills (id, name, category_id) VALUES (197, 'Business Analytics', 25);

-- Legal (ID: 26)
INSERT INTO skills (id, name, category_id) VALUES (198, 'Immigration Law', 26);
INSERT INTO skills (id, name, category_id) VALUES (199, 'Family Law', 26);
INSERT INTO skills (id, name, category_id) VALUES (200, 'Bankruptcy Law', 26);
INSERT INTO skills (id, name, category_id) VALUES (201, 'Real Estate Law', 26);
INSERT INTO skills (id, name, category_id) VALUES (202, 'Privacy Law', 26);

-- Hospitality (ID: 27)
INSERT INTO skills (id, name, category_id) VALUES (203, 'Resort Management', 27);
INSERT INTO skills (id, name, category_id) VALUES (204, 'Cruise Ship Management', 27);
INSERT INTO skills (id, name, category_id) VALUES (205, 'Event Catering', 27);
INSERT INTO skills (id, name, category_id) VALUES (206, 'Hospitality Technology', 27);
INSERT INTO skills (id, name, category_id) VALUES (207, 'Leisure Management', 27);

-- Science (ID: 28)
INSERT INTO skills (id, name, category_id) VALUES (208, 'Geology', 28);
INSERT INTO skills (id, name, category_id) VALUES (209, 'Biochemistry', 28);
INSERT INTO skills (id, name, category_id) VALUES (210, 'Astrophysics', 28);
INSERT INTO skills (id, name, category_id) VALUES (211, 'Genetics', 28);
INSERT INTO skills (id, name, category_id) VALUES (212, 'Neuroscience', 28);

-- Writing (ID: 29)
INSERT INTO skills (id, name, category_id) VALUES (213, 'Screenwriting', 29);
INSERT INTO skills (id, name, category_id) VALUES (214, 'Grant Writing', 29);
INSERT INTO skills (id, name, category_id) VALUES (215, 'Resume Writing', 29);
INSERT INTO skills (id, name, category_id) VALUES (216, 'Ghostwriting', 29);
INSERT INTO skills (id, name, category_id) VALUES (217, 'Academic Writing', 29);

-- Music (ID: 30)
INSERT INTO skills (id, name, category_id) VALUES (218, 'Music Composition', 30);
INSERT INTO skills (id, name, category_id) VALUES (219, 'Music Therapy', 30);
INSERT INTO skills (id, name, category_id) VALUES (220, 'Sound Engineering', 30);
INSERT INTO skills (id, name, category_id) VALUES (221, 'Music Education', 30);
INSERT INTO skills (id, name, category_id) VALUES (222, 'Songwriting', 30);

-- Sports (ID: 31)
INSERT INTO skills (id, name, category_id) VALUES (223, 'Strength and Conditioning', 31);
INSERT INTO skills (id, name, category_id) VALUES (224, 'Athletic Training', 31);
INSERT INTO skills (id, name, category_id) VALUES (225, 'Sports Psychology', 31);
INSERT INTO skills (id, name, category_id) VALUES (226, 'Coach Development', 31);
INSERT INTO skills (id, name, category_id) VALUES (227, 'Scouting and Recruitment', 31);

SET @next_val := (SELECT next_val FROM skill_seq LIMIT 1);
UPDATE skill_seq SET next_val = 228 WHERE next_val = @next_val;


##### THIRD PART #####
-- Insert new skills categories
INSERT INTO skills_category (id, name) VALUES (32, 'Technology');
INSERT INTO skills_category (id, name) VALUES (33, 'Fashion');
INSERT INTO skills_category (id, name) VALUES (34, 'Cooking');
INSERT INTO skills_category (id, name) VALUES (35, 'Automotive');
INSERT INTO skills_category (id, name) VALUES (36, 'Language');
INSERT INTO skills_category (id, name) VALUES (37, 'Travel');
INSERT INTO skills_category (id, name) VALUES (38, 'Photography');
INSERT INTO skills_category (id, name) VALUES (39, 'Gardening');
INSERT INTO skills_category (id, name) VALUES (40, 'Finance & Investment');

SET @next_val := (SELECT next_val FROM skill_category_seq LIMIT 1);
UPDATE skill_category_seq SET next_val = 41 WHERE next_val = @next_val;

-- Insert new skills for the "Technology" category
INSERT INTO skills (id, name, category_id) VALUES (228, 'Blockchain', 32);
INSERT INTO skills (id, name, category_id) VALUES (229, 'Augmented Reality (AR)', 32);
INSERT INTO skills (id, name, category_id) VALUES (230, 'Virtual Reality (VR)', 32);
INSERT INTO skills (id, name, category_id) VALUES (231, 'Internet of Things (IoT)', 32);
INSERT INTO skills (id, name, category_id) VALUES (232, 'Cybersecurity', 32);
INSERT INTO skills (id, name, category_id) VALUES (233, 'Data Mining', 32);
INSERT INTO skills (id, name, category_id) VALUES (234, 'Cloud Security', 32);
INSERT INTO skills (id, name, category_id) VALUES (235, 'Mobile App Development', 32);
INSERT INTO skills (id, name, category_id) VALUES (236, 'Game Development', 32);
INSERT INTO skills (id, name, category_id) VALUES (237, 'Embedded Systems', 32);

-- Insert new skills for the "Fashion" category
INSERT INTO skills (id, name, category_id) VALUES (238, 'Fashion Design', 33);
INSERT INTO skills (id, name, category_id) VALUES (239, 'Textile Design', 33);
INSERT INTO skills (id, name, category_id) VALUES (240, 'Pattern Making', 33);
INSERT INTO skills (id, name, category_id) VALUES (241, 'Fashion Styling', 33);
INSERT INTO skills (id, name, category_id) VALUES (242, 'Fashion Merchandising', 33);
INSERT INTO skills (id, name, category_id) VALUES (243, 'Fashion Marketing', 33);
INSERT INTO skills (id, name, category_id) VALUES (244, 'Fashion Photography', 33);
INSERT INTO skills (id, name, category_id) VALUES (245, 'Modeling', 33);
INSERT INTO skills (id, name, category_id) VALUES (246, 'Accessories Design', 33);
INSERT INTO skills (id, name, category_id) VALUES (247, 'Makeup Artistry', 33);

-- Insert new skills for the "Cooking" category
INSERT INTO skills (id, name, category_id) VALUES (248, 'Culinary Techniques', 34);
INSERT INTO skills (id, name, category_id) VALUES (249, 'Baking', 34);
INSERT INTO skills (id, name, category_id) VALUES (250, 'Pastry Making', 34);
INSERT INTO skills (id, name, category_id) VALUES (251, 'International Cuisine', 34);
INSERT INTO skills (id, name, category_id) VALUES (252, 'Food Presentation', 34);
INSERT INTO skills (id, name, category_id) VALUES (253, 'Gourmet Cooking', 34);
INSERT INTO skills (id, name, category_id) VALUES (254, 'Knife Skills', 34);
INSERT INTO skills (id, name, category_id) VALUES (255, 'Sous Vide Cooking', 34);
INSERT INTO skills (id, name, category_id) VALUES (256, 'Fermentation', 34);
INSERT INTO skills (id, name, category_id) VALUES (257, 'Vegan Cooking', 34);

-- Insert new skills for the "Automotive" category
INSERT INTO skills (id, name, category_id) VALUES (258, 'Car Mechanics', 35);
INSERT INTO skills (id, name, category_id) VALUES (259, 'Auto Body Repair', 35);
INSERT INTO skills (id, name, category_id) VALUES (260, 'Auto Detailing', 35);
INSERT INTO skills (id, name, category_id) VALUES (261, 'Car Painting', 35);
INSERT INTO skills (id, name, category_id) VALUES (262, 'Automotive Electronics', 35);
INSERT INTO skills (id, name, category_id) VALUES (263, 'Car Tuning', 35);
INSERT INTO skills (id, name, category_id) VALUES (264, 'Vehicle Wrapping', 35);
INSERT INTO skills (id, name, category_id) VALUES (265, 'Motorcycle Maintenance', 35);
INSERT INTO skills (id, name, category_id) VALUES (266, 'Diesel Engine Repair', 35);
INSERT INTO skills (id, name, category_id) VALUES (267, 'Auto Upholstery', 35);

-- Insert new skills for the "Language" category
INSERT INTO skills (id, name, category_id) VALUES (268, 'Spanish', 36);
INSERT INTO skills (id, name, category_id) VALUES (269, 'French', 36);
INSERT INTO skills (id, name, category_id) VALUES (270, 'German', 36);
INSERT INTO skills (id, name, category_id) VALUES (271, 'Mandarin Chinese', 36);
INSERT INTO skills (id, name, category_id) VALUES (272, 'Japanese', 36);
INSERT INTO skills (id, name, category_id) VALUES (273, 'Korean', 36);
INSERT INTO skills (id, name, category_id) VALUES (274, 'Russian', 36);
INSERT INTO skills (id, name, category_id) VALUES (275, 'Italian', 36);
INSERT INTO skills (id, name, category_id) VALUES (276, 'Arabic', 36);
INSERT INTO skills (id, name, category_id) VALUES (277, 'Portuguese', 36);

-- Insert new skills for the "Travel" category
INSERT INTO skills (id, name, category_id) VALUES (278, 'Travel Planning', 37);
INSERT INTO skills (id, name, category_id) VALUES (279, 'Adventure Travel', 37);
INSERT INTO skills (id, name, category_id) VALUES (280, 'Ecotourism', 37);
INSERT INTO skills (id, name, category_id) VALUES (281, 'Cultural Immersion', 37);
INSERT INTO skills (id, name, category_id) VALUES (282, 'Solo Travel', 37);
INSERT INTO skills (id, name, category_id) VALUES (283, 'Budget Travel', 37);
INSERT INTO skills (id, name, category_id) VALUES (284, 'Luxury Travel', 37);
INSERT INTO skills (id, name, category_id) VALUES (285, 'Travel Photography', 37);
INSERT INTO skills (id, name, category_id) VALUES (286, 'Digital Nomad Lifestyle', 37);
INSERT INTO skills (id, name, category_id) VALUES (287, 'Travel Blogging', 37);

-- Insert new skills for the "Photography" category
INSERT INTO skills (id, name, category_id) VALUES (288, 'Portrait Photography', 38);
INSERT INTO skills (id, name, category_id) VALUES (289, 'Landscape Photography', 38);
INSERT INTO skills (id, name, category_id) VALUES (290, 'Street Photography', 38);
INSERT INTO skills (id, name, category_id) VALUES (291, 'Product Photography', 38);
INSERT INTO skills (id, name, category_id) VALUES (292, 'Fashion Photography', 38);
INSERT INTO skills (id, name, category_id) VALUES (293, 'Event Photography', 38);
INSERT INTO skills (id, name, category_id) VALUES (294, 'Wildlife Photography', 38);
INSERT INTO skills (id, name, category_id) VALUES (295, 'Night Photography', 38);
INSERT INTO skills (id, name, category_id) VALUES (296, 'Astrophotography', 38);
INSERT INTO skills (id, name, category_id) VALUES (297, 'Documentary Photography', 38);

-- Insert new skills for the "Gardening" category
INSERT INTO skills (id, name, category_id) VALUES (298, 'Vegetable Gardening', 39);
INSERT INTO skills (id, name, category_id) VALUES (299, 'Urban Gardening', 39);
INSERT INTO skills (id, name, category_id) VALUES (300, 'Flower Arrangement', 39);
INSERT INTO skills (id, name, category_id) VALUES (301, 'Permaculture', 39);
INSERT INTO skills (id, name, category_id) VALUES (302, 'Indoor Gardening', 39);
INSERT INTO skills (id, name, category_id) VALUES (303, 'Garden Design', 39);
INSERT INTO skills (id, name, category_id) VALUES (304, 'Succulent Care', 39);
INSERT INTO skills (id, name, category_id) VALUES (305, 'Bonsai Cultivation', 39);
INSERT INTO skills (id, name, category_id) VALUES (306, 'Herb Gardening', 39);
INSERT INTO skills (id, name, category_id) VALUES (307, 'Landscaping', 39);

-- Insert new skills for the "Finance & Investment" category
INSERT INTO skills (id, name, category_id) VALUES (308, 'Stock Trading', 40);
INSERT INTO skills (id, name, category_id) VALUES (309, 'Real Estate Investing', 40);
INSERT INTO skills (id, name, category_id) VALUES (310, 'Cryptocurrency Investing', 40);
INSERT INTO skills (id, name, category_id) VALUES (311, 'Retirement Planning', 40);
INSERT INTO skills (id, name, category_id) VALUES (312, 'Financial Risk Management', 40);
INSERT INTO skills (id, name, category_id) VALUES (313, 'Tax Planning', 40);
INSERT INTO skills (id, name, category_id) VALUES (314, 'Portfolio Management', 40);
INSERT INTO skills (id, name, category_id) VALUES (315, 'Venture Capital', 40);
INSERT INTO skills (id, name, category_id) VALUES (316, 'Angel Investing', 40);
INSERT INTO skills (id, name, category_id) VALUES (317, 'Forex Trading', 40);

SET @next_val := (SELECT next_val FROM skill_seq LIMIT 1);
UPDATE skill_seq SET next_val = 318 WHERE next_val = @next_val;