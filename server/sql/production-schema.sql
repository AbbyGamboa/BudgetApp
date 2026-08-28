drop database if exists budget_app;
create database budget_app;
use budget_app;

create table user(
	userId int primary key auto_increment,
	email text not NULL , 
	password text not null
);

create table budget(
	budgetId int primary key auto_increment, 
	userId int, 
	income decimal(10, 2),
	 constraint fk_budget_user_id
        foreign key (userId)
        references user(userId)
        on delete cascade
);

create table categories(
	categoryId int primary key auto_increment, 
	name text, 
	userId int NULL, 
	constraint fk_user_cat_id
		foreign key (userId)
		references user(userId)
		on delete cascade
);

create table budget_category(
	budgetCategoryId int primary key auto_increment, 
	budgetId int, 
	categoryId int, 
	percentage decimal, 
	constraint fk_budget_id
        foreign key (budgetId)
        references budget(budgetId)
        on delete cascade, 
    constraint fk_budget_category_id
    	foreign key (categoryId)
    	references categories(categoryId)
    	on delete cascade
);

create table account(
	accountId int primary key auto_increment,
	userId int,
    subtype varchar(50),
	constraint fk_user_account_id
		foreign key (userId)
		references user(userId)
		on delete cascade
);

create table transaction(
	transactionId int primary key auto_increment,
	accountId int,
	amount decimal(10,2), 
	date date, 
	merchantName text NULL,
	description text NULL,
	constraint fk_account_id
		foreign key (accountId)
		references account(accountId)
		on delete cascade
);

create table transaction_categories(
	transactionId int,
	categoryId int,
	primary key (transactionId, categoryId),
	constraint fk_cat_transaction_id
		foreign key (transactionId)
		references transaction(transactionId)
		on delete cascade,
	constraint fk_trans_category_id
		foreign key (categoryId)
		references categories(categoryId)
		on delete cascade
);

