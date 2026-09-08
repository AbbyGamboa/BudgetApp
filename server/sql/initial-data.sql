use budget_app;

USE budget_app;

-- =========================================================
-- USERS
-- =========================================================

INSERT INTO user (email, password) VALUES
('alex.johnson@college.edu', 'password123'),
('maya.williams@college.edu', 'password123'),
('ethan.brown@college.edu', 'password123'),
('sophia.davis@college.edu', 'password123'),
('liam.miller@college.edu', 'password123'),
('olivia.wilson@college.edu', 'password123'),
('noah.moore@college.edu', 'password123'),
('emma.taylor@college.edu', 'password123'),
('james.anderson@college.edu', 'password123'),
('ava.thomas@college.edu', 'password123');


-- =========================================================
-- BUDGETS
-- =========================================================

INSERT INTO budget (userId, name) VALUES
(1, 'Monthly College Budget'),
(2, 'Fall Semester Budget'),
(3, 'Monthly Student Expenses'),
(4, 'College Living Budget'),
(5, 'Monthly Spending Plan'),
(6, 'Fall 2026 Budget'),
(7, 'Student Monthly Budget'),
(8, 'College Expenses'),
(9, 'Monthly Budget'),
(10, 'Semester Spending');


-- =========================================================
-- CATEGORIES
-- userId = NULL means preset/global category
-- =========================================================

INSERT INTO categories (name, userId) VALUES
-- Preset categories
('Food', NULL),
('Transportation', NULL),
('Housing', NULL),
('Entertainment', NULL),
('School', NULL),
('Groceries', NULL),
('Subscriptions', NULL),
('Shopping', NULL),
('Health', NULL),
('Personal', NULL),

-- Custom categories created by users
('Coffee', 1),
('Campus Events', 2),
('Gaming', 3),
('Concerts', 4),
('Study Materials', 5),
('Pet Expenses', 6),
('Gym', 7),
('Clubs', 8),
('Hobbies', 9),
('Dining Out', 10);


-- =========================================================
-- ACCOUNTS
-- =========================================================

INSERT INTO account (userId, subtype) VALUES
(1, 'checking'),
(2, 'checking'),
(3, 'checking'),
(4, 'checking'),
(5, 'checking'),
(6, 'checking'),
(7, 'checking'),
(8, 'checking'),
(9, 'checking'),
(10, 'checking');


-- =========================================================
-- BUDGET CATEGORIES
--
-- User 1
-- Budget 1
-- Categories:
-- 1 Food
-- 2 Transportation
-- 3 Housing
-- 4 Entertainment
-- 5 School
-- 11 Coffee
-- =========================================================

INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(1, 1, 20.00),
(1, 2, 10.00),
(1, 3, 35.00),
(1, 4, 10.00),
(1, 5, 15.00),
(1, 11, 10.00);


-- User 2
INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(2, 1, 15.00),
(2, 2, 10.00),
(2, 3, 30.00),
(2, 5, 20.00),
(2, 6, 15.00),
(2, 12, 10.00);


-- User 3
INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(3, 1, 20.00),
(3, 2, 10.00),
(3, 4, 15.00),
(3, 5, 20.00),
(3, 7, 10.00),
(3, 13, 25.00);


-- User 4
INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(4, 1, 15.00),
(4, 2, 10.00),
(4, 3, 35.00),
(4, 4, 15.00),
(4, 5, 15.00),
(4, 14, 10.00);


-- User 5
INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(5, 1, 15.00),
(5, 2, 10.00),
(5, 3, 30.00),
(5, 5, 20.00),
(5, 6, 15.00),
(5, 15, 10.00);


-- User 6
INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(6, 1, 15.00),
(6, 2, 10.00),
(6, 3, 30.00),
(6, 4, 10.00),
(6, 5, 15.00),
(6, 6, 10.00),
(6, 16, 10.00);


-- User 7
INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(7, 1, 20.00),
(7, 2, 10.00),
(7, 3, 30.00),
(7, 4, 10.00),
(7, 5, 15.00),
(7, 17, 15.00);


-- User 8
INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(8, 1, 15.00),
(8, 2, 10.00),
(8, 3, 35.00),
(8, 5, 20.00),
(8, 6, 10.00),
(8, 18, 10.00);


-- User 9
INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(9, 1, 20.00),
(9, 2, 10.00),
(9, 3, 30.00),
(9, 4, 10.00),
(9, 5, 15.00),
(9, 19, 15.00);


-- User 10
INSERT INTO budget_category
(budgetId, categoryId, percentage)
VALUES
(10, 1, 20.00),
(10, 2, 10.00),
(10, 3, 30.00),
(10, 4, 10.00),
(10, 5, 15.00),
(10, 20, 15.00);


-- =========================================================
-- TRANSACTIONS
-- =========================================================

INSERT INTO transaction
(accountId, amount, date, merchantName, description)
VALUES

-- =========================================================
-- USER 1
-- =========================================================

(1, 12.50, '2026-08-01', 'Campus Cafe', 'Coffee and breakfast'),
(1, 8.75,  '2026-08-03', 'McDonalds', 'Lunch'),
(1, 45.00, '2026-08-05', 'Target', 'School supplies'),
(1, 32.40, '2026-08-07', 'Jewel-Osco', 'Groceries'),
(1, 18.00, '2026-08-09', 'Uber', 'Ride to campus'),
(1, 14.25, '2026-08-12', 'Campus Cafe', 'Coffee'),
(1, 55.00, '2026-08-15', 'Amazon', 'Textbook accessories'),
(1, 22.50, '2026-08-18', 'Chipotle', 'Dinner'),
(1, 11.00, '2026-08-20', 'Campus Cafe', 'Coffee'),
(1, 35.00, '2026-08-23', 'AMC Theatres', 'Movie with friends'),

-- =========================================================
-- USER 2
-- =========================================================

(2, 10.25, '2026-08-01', 'Starbucks', 'Coffee'),
(2, 42.50, '2026-08-03', 'Walmart', 'Groceries'),
(2, 15.00, '2026-08-05', 'Campus Dining', 'Lunch'),
(2, 25.00, '2026-08-08', 'Uber', 'Transportation'),
(2, 75.00, '2026-08-10', 'Barnes & Noble', 'Textbooks'),
(2, 20.00, '2026-08-13', 'Campus Event', 'Student event'),
(2, 31.75, '2026-08-16', 'Target', 'Dorm supplies'),
(2, 12.50, '2026-08-19', 'Campus Dining', 'Dinner'),
(2, 9.75,  '2026-08-22', 'Starbucks', 'Coffee'),
(2, 40.00, '2026-08-25', 'Campus Event', 'Football game'),

-- =========================================================
-- USER 3
-- =========================================================

(3, 15.00, '2026-08-02', 'Chipotle', 'Lunch'),
(3, 12.00, '2026-08-04', 'Steam', 'Game purchase'),
(3, 60.00, '2026-08-06', 'Amazon', 'Computer accessories'),
(3, 22.00, '2026-08-09', 'Uber', 'Ride home'),
(3, 35.50, '2026-08-11', 'Jewel-Osco', 'Groceries'),
(3, 14.00, '2026-08-14', 'Netflix', 'Monthly subscription'),
(3, 45.00, '2026-08-17', 'Best Buy', 'Gaming accessories'),
(3, 18.50, '2026-08-20', 'McDonalds', 'Dinner'),
(3, 25.00, '2026-08-23', 'Steam', 'Game purchase'),
(3, 65.00, '2026-08-26', 'Amazon', 'School equipment'),

-- =========================================================
-- USER 4
-- =========================================================

(4, 11.50, '2026-08-01', 'Starbucks', 'Coffee'),
(4, 28.00, '2026-08-04', 'Target', 'Dorm supplies'),
(4, 18.75, '2026-08-06', 'Chipotle', 'Lunch'),
(4, 20.00, '2026-08-08', 'Uber', 'Transportation'),
(4, 65.00, '2026-08-11', 'Ticketmaster', 'Concert ticket'),
(4, 30.00, '2026-08-14', 'Amazon', 'School supplies'),
(4, 16.50, '2026-08-17', 'Campus Dining', 'Dinner'),
(4, 50.00, '2026-08-20', 'Concert Venue', 'Concert merchandise'),
(4, 13.25, '2026-08-23', 'Starbucks', 'Coffee'),
(4, 25.00, '2026-08-26', 'Target', 'Personal items'),

-- =========================================================
-- USER 5
-- =========================================================

(5, 38.50, '2026-08-01', 'Jewel-Osco', 'Groceries'),
(5, 16.25, '2026-08-03', 'Campus Dining', 'Lunch'),
(5, 45.00, '2026-08-05', 'Barnes & Noble', 'Course materials'),
(5, 20.00, '2026-08-08', 'Uber', 'Transportation'),
(5, 12.00, '2026-08-11', 'Starbucks', 'Coffee'),
(5, 27.75, '2026-08-14', 'Target', 'School supplies'),
(5, 55.00, '2026-08-17', 'PetSmart', 'Pet supplies'),
(5, 19.50, '2026-08-20', 'Chipotle', 'Dinner'),
(5, 42.00, '2026-08-23', 'Jewel-Osco', 'Groceries'),
(5, 15.00, '2026-08-26', 'Campus Dining', 'Lunch'),

-- =========================================================
-- USER 6
-- =========================================================

(6, 30.00, '2026-08-01', 'Jewel-Osco', 'Groceries'),
(6, 12.50, '2026-08-03', 'Starbucks', 'Coffee'),
(6, 20.00, '2026-08-05', 'Uber', 'Transportation'),
(6, 55.00, '2026-08-08', 'PetSmart', 'Pet food'),
(6, 25.00, '2026-08-11', 'Target', 'Dorm supplies'),
(6, 18.00, '2026-08-14', 'Campus Dining', 'Lunch'),
(6, 14.00, '2026-08-17', 'Spotify', 'Music subscription'),
(6, 48.00, '2026-08-20', 'Amazon', 'School supplies'),
(6, 33.50, '2026-08-23', 'Jewel-Osco', 'Groceries'),
(6, 16.75, '2026-08-26', 'Chipotle', 'Dinner'),

-- =========================================================
-- USER 7
-- =========================================================

(7, 14.00, '2026-08-01', 'Campus Dining', 'Lunch'),
(7, 25.00, '2026-08-03', 'Uber', 'Transportation'),
(7, 40.00, '2026-08-05', 'Target', 'School supplies'),
(7, 12.50, '2026-08-07', 'Starbucks', 'Coffee'),
(7, 35.00, '2026-08-10', 'Jewel-Osco', 'Groceries'),
(7, 30.00, '2026-08-13', 'Campus Gym', 'Gym membership'),
(7, 18.75, '2026-08-16', 'Chipotle', 'Dinner'),
(7, 45.00, '2026-08-19', 'Campus Gym', 'Fitness class'),
(7, 22.00, '2026-08-22', 'Target', 'Personal items'),
(7, 15.50, '2026-08-25', 'Campus Dining', 'Lunch'),

-- =========================================================
-- USER 8
-- =========================================================

(8, 40.00, '2026-08-01', 'Jewel-Osco', 'Groceries'),
(8, 15.00, '2026-08-03', 'Campus Dining', 'Lunch'),
(8, 20.00, '2026-08-05', 'Uber', 'Transportation'),
(8, 35.00, '2026-08-08', 'Barnes & Noble', 'Books'),
(8, 18.00, '2026-08-11', 'Campus Club', 'Club dues'),
(8, 12.75, '2026-08-14', 'Starbucks', 'Coffee'),
(8, 28.50, '2026-08-17', 'Target', 'Dorm supplies'),
(8, 50.00, '2026-08-20', 'Campus Club', 'Club event'),
(8, 17.25, '2026-08-23', 'Chipotle', 'Dinner'),
(8, 42.00, '2026-08-26', 'Jewel-Osco', 'Groceries'),

-- =========================================================
-- USER 9
-- =========================================================

(9, 13.50, '2026-08-01', 'Starbucks', 'Coffee'),
(9, 35.00, '2026-08-03', 'Jewel-Osco', 'Groceries'),
(9, 20.00, '2026-08-06', 'Uber', 'Transportation'),
(9, 25.00, '2026-08-09', 'Target', 'Personal items'),
(9, 14.00, '2026-08-12', 'Netflix', 'Streaming subscription'),
(9, 18.75, '2026-08-15', 'Chipotle', 'Dinner'),
(9, 30.00, '2026-08-18', 'Amazon', 'Hobby supplies'),
(9, 15.00, '2026-08-21', 'Campus Dining', 'Lunch'),
(9, 45.00, '2026-08-24', 'Amazon', 'Art supplies'),
(9, 22.50, '2026-08-27', 'Jewel-Osco', 'Groceries'),

-- =========================================================
-- USER 10
-- =========================================================

(10, 15.00, '2026-08-01', 'Campus Dining', 'Lunch'),
(10, 40.00, '2026-08-03', 'Jewel-Osco', 'Groceries'),
(10, 20.00, '2026-08-05', 'Uber', 'Transportation'),
(10, 30.00, '2026-08-08', 'Target', 'School supplies'),
(10, 55.00, '2026-08-11', 'Restaurant', 'Dinner with friends'),
(10, 12.50, '2026-08-14', 'Starbucks', 'Coffee'),
(10, 35.00, '2026-08-17', 'Amazon', 'School materials'),
(10, 25.00, '2026-08-20', 'Restaurant', 'Dining out'),
(10, 18.00, '2026-08-23', 'Campus Dining', 'Lunch'),
(10, 45.00, '2026-08-26', 'Restaurant', 'Dinner');


-- =========================================================
-- TRANSACTION CATEGORIES
-- =========================================================

-- User 1
INSERT INTO transaction_categories VALUES
(1, 11),
(2, 1),
(3, 5),
(4, 1),
(5, 2),
(6, 11),
(7, 5),
(8, 1),
(9, 11),
(10, 4);

-- User 2
INSERT INTO transaction_categories VALUES
(11, 12),
(12, 6),
(13, 1),
(14, 2),
(15, 5),
(16, 12),
(17, 6),
(18, 1),
(19, 12),
(20, 12);

-- User 3
INSERT INTO transaction_categories VALUES
(21, 1),
(22, 13),
(23, 13),
(24, 2),
(25, 6),
(26, 7),
(27, 13),
(28, 1),
(29, 13),
(30, 5);

-- User 4
INSERT INTO transaction_categories VALUES
(31, 14),
(32, 4),
(33, 1),
(34, 2),
(35, 14),
(36, 5),
(37, 1),
(38, 14),
(39, 14),
(40, 4);

-- User 5
INSERT INTO transaction_categories VALUES
(41, 6),
(42, 1),
(43, 5),
(44, 2),
(45, 15),
(46, 5),
(47, 15),
(48, 1),
(49, 6),
(50, 1);

-- User 6
INSERT INTO transaction_categories VALUES
(51, 6),
(52, 1),
(53, 2),
(54, 16),
(55, 3),
(56, 1),
(57, 7),
(58, 5),
(59, 6),
(60, 1);

-- User 7
INSERT INTO transaction_categories VALUES
(61, 1),
(62, 2),
(63, 5),
(64, 1),
(65, 6),
(66, 17),
(67, 1),
(68, 17),
(69, 10),
(70, 1);

-- User 8
INSERT INTO transaction_categories VALUES
(71, 6),
(72, 1),
(73, 2),
(74, 5),
(75, 18),
(76, 1),
(77, 3),
(78, 18),
(79, 1),
(80, 6);

-- User 9
INSERT INTO transaction_categories VALUES
(81, 19),
(82, 6),
(83, 2),
(84, 10),
(85, 7),
(86, 1),
(87, 19),
(88, 1),
(89, 19),
(90, 6);

-- User 10
INSERT INTO transaction_categories VALUES
(91, 1),
(92, 6),
(93, 2),
(94, 5),
(95, 20),
(96, 1),
(97, 5),
(98, 20),
(99, 1),
(100, 20);


select * from user;
select * from transaction_categories;
