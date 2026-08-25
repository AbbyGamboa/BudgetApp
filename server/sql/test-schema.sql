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
	 constraint fk_budget_user_id
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

create table account(
	accountId int primary key auto_increment,
	userId int,
    subtype varchar(50),
	constraint fk_user_account_id
		foreign key (userId)
		references user(userId)
);

create table transaction(
	transactionId int primary key auto_increment,
	accountId int,
	amount decimal, 
	date date, 
	merchantName text NULL,
	description text NULL,
	constraint fk_account_id
		foreign key (accountId)
		references account(accountId)
);

create table transaction_categories(
	transactionId int,
	categoryId int,
	primary key (transactionId, categoryId),
	constraint fk_cat_transaction_id
		foreign key (transactionId)
		references transaction(transactionId),
	constraint fk_trans_category_id
		foreign key (categoryId)
		references categories(categoryId)
);



delimiter //
create procedure set_known_good_state()
begin
	delete from transaction_categories;
	alter table transaction_categories auto_increment = 1;
	delete from transactions;
	alter table transactions auto_increment = 1;
	delete from account;
	alter table account auto_increment = 1;
	delete from budget_categoryt;
	alter table budget_category auto_increment = 1;
	delete from budget;
	alter table budget auto_increment = 1;
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
		("Gym", 2);
	
	insert into budget(userId, income) values
	(1, 4000), 
	(2, 15000);
	
	insert into budget_category(budgetId, categoryId, percentage) values 
	(1,1,15),
	(1,2,30),
	(2,1,30),
	(2,3,10);
	
	insert into account( userId, subtype) values
	(1, "Checkings"),
	(2, "Savings");
	
	insert into transaction(accountId, amount, date, merchantName, description) values
	(1, 100.00, '2026-06-08', "Target", "Online buy"),
	(1, 10.00, '2026-06-09', "Speedway", "Gas"),
	(2, 300.50, '2026-08-23', "Ikea", null);
	
	
	insert into transaction_categories(transactionId, categoryId) values
	(1,2),(2,1), (3,3);
	

end //
delimiter ;

