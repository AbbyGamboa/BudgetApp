drop database if exists budget_app_test;
create database budget_app_test;
use budget_app_test;

create table user(
	userId int primary key auto_increment,
	email text not NULL , 
	password text not null
);

create table budget(
	budgetId int primary key auto_increment, 
	userId int, 
	income decimal,
	 constraint fk_user_id
        foreign key (userId)
        references user(userId)
);

create table categories(
	categoryId int primary key auto_increment, 
	name text, 
	userId int NULL, 
	constraint fk_user_cat_id
		foreign key (userId)
		references user(userId)
);

create table budget_category(
	budgetCategoryId int primary key auto_increment, 
	budgetId int, 
	categoryId int, 
	percentage decimal, 
	constraint fk_budget_id
        foreign key (budgetId)
        references budget(budgetId), 
    constraint fk_budget_category_id
    	foreign key (categoryId)
    	references categories(categoryId)
);

create table plaid_items(
	plaidItemId varchar(50) primary key, 
	userId int, 
	accessToken text, 
	institutionName text,
	constraint fk_user_item_id
		foreign key (userId)
		references user(userId)
);

create table account(
	plaidAccountId varchar(50) primary key, 
	plaidItemId varchar(50), 
	name text, 
	subtype text,
	constraint fk_plaid_item_id
		foreign key (plaidItemId)
		references plaid_items(plaidItemId)
);

create table transaction(
	plaidTransactionId varchar(50) primary key, 
	plaidAccountId varchar(50), 
	amount decimal, 
	date date, 
	merchantName text, 
	description text, 
	pending boolean, 
	constraint fk_plaid_account_id
		foreign key (plaidAccountId)
		references account(plaidAccountId)
);

create table transaction_categories(
	plaidTransactionId varchar(50), 
	categoryId int,
	primary key (plaidTransactionId, categoryId),
	constraint fk_plaid_transaction_id
		foreign key (plaidTransactionId)
		references transaction(plaidTransactionId),
	constraint fk_trans_category_id
		foreign key (categoryId)
		references categories(categoryId)
);

delimiter //
create procedure set_known_good_state()
begin
	delete from transaction_categories;
	delete from budget_category;
	alter table budget_category auto_increment = 1;
	delete from budget;
	alter table budget auto_increment = 1;
	delete from transaction;
	delete from account;
	delete from plaid_items;
	delete from categories;
	alter table categories auto_increment = 1;
	delete from user;
    alter table user auto_increment = 1;

    insert into user (email, password) values
        ("a@a.com","a"),
        ("b@b.com","b");

	insert into categories (name, userId)
		values
		("Tuition", null),
		("Groceries", 1),
		("Gym", 2)
		;
	
	insert into plaid_items(plaidItemId, userId, accessToken, institutionName) values
	("123", 1, "abc123", "US Bank"),
	("987", 2, "xyz987", "Chase");
	
	insert into account(plaidAccountId, plaidItemId, name, subtype) values
	("123", "123", "usbank account", "Checkings"),
	("987", "987", "Chase account", "Savings");
	
	insert into transaction(plaidTransactionId, plaidAccountId, amount, date, merchantName, description, pending) values
	("123", "123", 100.00, '2026-06-08', "Target", "Online buy", false),
	("124", "123", 10.00, '2026-06-09', "Speedway", "Gas", false),
	("125", "987", 300.50, '2026-08-23', "Ikea", "Home Improvement", true);
	
	insert into budget(userId, income) values
	(1, 4000), 
	(2, 15000);
	
	insert into budget_category(budgetId, categoryId, percentage) values 
	(1,1,15),
	(1,2,30),
	(2,1,30),
	(2,3,10);
	
	insert into transaction_categories(plaidTransactionId, categoryId) values
	("123",2),("124",1), ("125",3);
	

end //
delimiter ;

