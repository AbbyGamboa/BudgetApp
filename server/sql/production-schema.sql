drop database if exists budget_app;
create database budget_app;
use budget_app;

create table user(
	userId int primary key auto_increment,
	email text not NULL , 
	username text not null, 
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
	plaidItemId int primary key, 
	userId int, 
	accessToken text, 
	institutionName text,
	constraint fk_user_item_id
		foreign key (userId)
		references user(userId)
);

create table account(
	plaidAccountId int primary key, 
	plaidItemId int, 
	name text, 
	subtype text,
	constraint fk_plaid_item_id
		foreign key (plaidItemId)
		references plaid_items(plaidItemId)
);

create table transaction(
	plaidTransactionId int primary key, 
	plaidAccountId int, 
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
	plaidTransactionId int, 
	categoryId int,
	primary key (plaidTransactionId, categoryId),
	constraint fk_plaid_transaction_id
		foreign key (plaidTransactionId)
		references transaction(plaidTransactionId),
	constraint fk_trans_category_id
		foreign key (categoryId)
		references categories(categoryId)
);

