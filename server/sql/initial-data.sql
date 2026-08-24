use budget_app;

insert into user (email, password) values
        ("a@a.com", "a"),
        ("b@b.com", "b");

	insert into categories (name, userId)
		values
		("Tuition", null),
		("Groceries", 1),
		("Gym", 2)
		;
	
	insert into plaid_items(plaidItemId, userId, accessToken, institutionName) values
	(123, 1, "abc123", "US Bank"),
	(987, 2, "xyz987", "Chase");
	
	insert into account(plaidAccountId, plaidItemId, name, subtype) values
	(123, 123, "usbank account", "Checkings"),
	(987, 987, "Chase account", "Savings");
	
	insert into transaction(plaidTransactionId, plaidAccountId, amount, date, merchantName, description, pending) values
	(123, 123, 100.00, '2026-06-08', "Target", "Online buy", false),
	(124, 123, 10.00, '2026-06-09', "Speedway", "Gas", false),
	(125, 987, 300.50, '2026-08-23', "Ikea", "Home Improvement", true);
	
	insert into budget(userId, income) values
	(1, 4000), 
	(2, 15000);
	
	insert into budget_category(budgetId, categoryId, percentage) values 
	(1,1,15),
	(1,2,30),
	(2,1,30),
	(2,3,10);
	
	insert into transaction_categories(plaidTransactionId, categoryId) values
	(123,2),(124,1), (125,3);
	
	
	select * from user;