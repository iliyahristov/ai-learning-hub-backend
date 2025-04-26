CREATE
DATABASE IF NOT EXISTS ai_learning_hub CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE
ai_learning_hub;

-- Test users (the password is "password123" for everyone)
INSERT INTO users (name, email, password, role, created_at, updated_at)
VALUES ('Admin User', 'admin@example.com', '$2a$10$VPXLmf5D1M5pCZvJEQ5iUeGTN0MXZyvWYH5XFbkOWGNcxgPGKKHCi', 'ADMIN',
        NOW(), NOW()),
       ('Teacher One', 'teacher1@example.com', '$2a$10$VPXLmf5D1M5pCZvJEQ5iUeGTN0MXZyvWYH5XFbkOWGNcxgPGKKHCi',
        'TEACHER', NOW(), NOW()),
       ('Teacher Two', 'teacher2@example.com', '$2a$10$VPXLmf5D1M5pCZvJEQ5iUeGTN0MXZyvWYH5XFbkOWGNcxgPGKKHCi',
        'TEACHER', NOW(), NOW()),
       ('Student One', 'student1@example.com', '$2a$10$VPXLmf5D1M5pCZvJEQ5iUeGTN0MXZyvWYH5XFbkOWGNcxgPGKKHCi',
        'STUDENT', NOW(), NOW()),
       ('Student Two', 'student2@example.com', '$2a$10$VPXLmf5D1M5pCZvJEQ5iUeGTN0MXZyvWYH5XFbkOWGNcxgPGKKHCi',
        'STUDENT', NOW(), NOW()),
       ('Student Three', 'student3@example.com', '$2a$10$VPXLmf5D1M5pCZvJEQ5iUeGTN0MXZyvWYH5XFbkOWGNcxgPGKKHCi',
        'STUDENT', NOW(), NOW());

INSERT INTO courses (title, subject, description, difficulty_level, created_by, is_public, created_at, updated_at)
VALUES ('Introduction to Java Programming', 'Programming',
        'Learn the fundamentals of Java programming language including OOP concepts, data structures, and more',
        'BEGINNER', 2, true, NOW(), NOW()),
       ('Advanced JavaScript Concepts', 'Programming',
        'Deep dive into JavaScript advanced features like closures, promises, async/await, and modern ES6+ syntax',
        'ADVANCED', 2, true, NOW(), NOW()),
       ('Database Design with MySQL', 'Database',
        'Master database design principles, normalization, and SQL queries with practical examples', 'INTERMEDIATE', 3,
        true, NOW(), NOW()),
       ('Web Development with React', 'Web Development',
        'Build modern web applications with React, including hooks, state management, and best practices',
        'INTERMEDIATE', 2, true, NOW(), NOW()),
       ('Machine Learning Fundamentals', 'Artificial Intelligence',
        'Introduction to machine learning concepts, algorithms, and practical implementations', 'ADVANCED', 3, true,
        NOW(), NOW());

INSERT INTO materials (course_id, title, content, content_type, difficulty_level, estimated_time_minutes, created_at,
                       updated_at)
VALUES (1, 'Java Basics', 'Content about Java basics including variables, data types, and control structures...',
        'TEXT', 'BEGINNER', 30, NOW(), NOW()),
       (1, 'Object-Oriented Programming in Java',
        'Learn about classes, objects, inheritance, polymorphism, and encapsulation...', 'TEXT', 'INTERMEDIATE', 45,
        NOW(), NOW()),
       (2, 'JavaScript Closures', 'In-depth look at closures, lexical scope, and practical use cases...', 'TEXT',
        'ADVANCED', 40, NOW(), NOW()),
       (2, 'Promises and Async Programming',
        'Master asynchronous JavaScript with promises, async/await, and error handling...', 'TEXT', 'ADVANCED', 60,
        NOW(), NOW()),
       (3, 'Database Normalization', 'Understanding database normalization forms (1NF, 2NF, 3NF) with examples...',
        'TEXT', 'INTERMEDIATE', 35, NOW(), NOW()),
       (4, 'React Components and Props', 'Building reusable components with React and managing component props...',
        'TEXT', 'INTERMEDIATE', 40, NOW(), NOW()),
       (4, 'State Management with Hooks', 'Using useState, useEffect, and custom hooks in React applications...',
        'TEXT', 'INTERMEDIATE', 50, NOW(), NOW()),
       (5, 'Introduction to Neural Networks', 'Basic concepts of neural networks, neurons, and activation functions...',
        'TEXT', 'ADVANCED', 55, NOW(), NOW());

INSERT INTO progress (user_id, material_id, status, score, completion_time, last_interaction, created_at, updated_at)
VALUES (4, 1, 'COMPLETED', 95.00, 28, NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 5 DAY),
       (4, 2, 'IN_PROGRESS', NULL, NULL, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 3 DAY, NOW() - INTERVAL 1 DAY),
       (4, 6, 'COMPLETED', 88.50, 35, NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 4 DAY, NOW() - INTERVAL 2 DAY),
       (5, 1, 'COMPLETED', 90.00, 30, NOW() - INTERVAL 7 DAY, NOW() - INTERVAL 7 DAY, NOW() - INTERVAL 7 DAY),
       (5, 3, 'COMPLETED', 92.00, 38, NOW() - INTERVAL 3 DAY, NOW() - INTERVAL 6 DAY, NOW() - INTERVAL 3 DAY),
       (5, 4, 'IN_PROGRESS', NULL, NULL, NOW(), NOW() - INTERVAL 1 DAY, NOW());

INSERT INTO quizzes (material_id, title, description, ai_generated, difficulty_level, created_at, updated_at)
VALUES (1, 'Java Basics Quiz', 'Test your knowledge of Java fundamentals', false, 'BEGINNER', NOW(), NOW()),
       (3, 'JavaScript Closures Quiz', 'Test your understanding of closures', true, 'ADVANCED', NOW(), NOW()),
       (6, 'React Components Quiz', 'Quiz on React components and props', false, 'INTERMEDIATE', NOW(), NOW());

INSERT INTO questions (quiz_id, question_text, question_type, difficulty_level, ai_generated, created_at, updated_at)
VALUES (1, 'What is the correct way to declare a variable in Java?', 'MULTIPLE_CHOICE', 'BEGINNER', false, NOW(),
        NOW()),
       (1, 'Which of the following is a primitive data type in Java?', 'MULTIPLE_CHOICE', 'BEGINNER', false, NOW(),
        NOW()),
       (1, 'Java is a platform-independent language.', 'TRUE_FALSE', 'BEGINNER', false, NOW(), NOW()),
       (2, 'What is a closure in JavaScript?', 'MULTIPLE_CHOICE', 'ADVANCED', true, NOW(), NOW()),
       (2, 'Which statement about closures is true?', 'MULTIPLE_CHOICE', 'ADVANCED', true, NOW(), NOW()),
       (3, 'Which method is used to pass data to a React component?', 'MULTIPLE_CHOICE', 'INTERMEDIATE', false, NOW(),
        NOW());

INSERT INTO answers (question_id, answer_text, is_correct, explanation, created_at, updated_at)
VALUES (1, 'int number = 10;', true, 'This is the correct syntax for declaring a variable in Java', NOW(), NOW()),
       (1, 'var number = 10;', false, 'var can only be used in Java 10+ with type inference', NOW(), NOW()),
       (1, 'number = 10;', false, 'Variable type must be specified', NOW(), NOW()),
       (1, 'let number = 10;', false, 'let is used in JavaScript, not Java', NOW(), NOW()),
       (2, 'String', false, 'String is an object, not a primitive type', NOW(), NOW()),
       (2, 'int', true, 'int is a primitive data type in Java', NOW(), NOW()),
       (2, 'ArrayList', false, 'ArrayList is a collection class, not a primitive type', NOW(), NOW()),
       (2, 'Object', false, 'Object is a class, not a primitive type', NOW(), NOW()),
       (3, 'true', true, 'Java is platform-independent due to JVM', NOW(), NOW()),
       (3, 'false', false, 'Java is platform-independent', NOW(), NOW()),
       (4, 'A function with access to its lexical scope', true, 'Closure includes function and its environment', NOW(),
        NOW()),
       (4, 'A way to close the browser window', false, 'This is not related to closures', NOW(), NOW()),
       (4, 'A JavaScript library', false, 'Closure is a concept, not a library', NOW(), NOW()),
       (4, 'A method to end a loop', false, 'This is not related to closures', NOW(), NOW()),
       (5, 'Closures retain access to outer function variables', true, 'This is a key characteristic of closures',
        NOW(), NOW()),
       (5, 'Closures cannot access outer scope variables', false, 'Closures can access outer scope', NOW(), NOW()),
       (5, 'Closures are only created with arrow functions', false, 'Closures work with all function types', NOW(),
        NOW()),
       (5, 'Closures are garbage collected immediately', false, 'Closures persist as long as referenced', NOW(), NOW()),
       (6, 'props', true, 'Props are used to pass data to components', NOW(), NOW()),
       (6, 'state', false, 'State is for internal component data', NOW(), NOW()),
       (6, 'context', false, 'Context is for global data sharing', NOW(), NOW()),
       (6, 'redux', false, 'Redux is an external state management library', NOW(), NOW());

INSERT INTO ai_interactions (user_id, ai_model, interaction_type, prompt, tokens_used, result_satisfaction, created_at)
VALUES (4, 'gpt-4', 'MATERIAL_GENERATION', 'Create a lesson about Java inheritance', 1250, 'EXCELLENT',
        NOW() - INTERVAL 3 DAY),
       (4, 'gpt-4', 'QUIZ_GENERATION', 'Generate quiz questions for React hooks', 850, 'GOOD', NOW() - INTERVAL 2 DAY),
       (5, 'gpt-4', 'PROGRESS_ANALYSIS', 'Analyze my learning progress', 650, 'AVERAGE', NOW() - INTERVAL 1 DAY),
       (5, 'gpt-4', 'VISUALIZATION', 'Create a diagram for database relationships', 450, 'EXCELLENT', NOW());