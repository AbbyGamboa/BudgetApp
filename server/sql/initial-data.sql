use budget_app;

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
	
	select * from user;
select * from budget;

select * from transaction t inner join account a on t.accountId = a.accountId;

select * from categories c left outer join user u on c.userId = u.userId
where c.userId is null or c.userId = 1;

select * from categories c 
where categoryId = 1 or userId = 1;

select bc.budgetCategoryId, b.budgetId, c.categoryId, bc.percentage, b.income, c.name, c.userId, u.userId, u.email, u.password
            from budget_category bc
            inner join budget b on bc.budgetId = b.budgetId
            inner join categories c on bc.categoryId = c.categoryId
            inner join user u on b.userId = u.userId;

select
                bc.budgetCategoryId,
                bc.percentage,
                b.budgetId,
                b.income,
                c.name,
                c.categoryId,
                bu.userId AS budgetUserId,
                bu.email AS budgetUserEmail,
                bu.password AS budgetUserPassword,
                cu.userId AS categoryUserId,
                cu.email AS categoryUserEmail,
                cu.password AS categoryUserPassword
            from budget_category bc
                inner  join budget b on bc.budgetId = b.budgetId
                inner join user bu on b.userId = bu.userId
                inner join categories c on bc.categoryId = c.categoryId
                left join user cu on c.userId = cu.userId;
