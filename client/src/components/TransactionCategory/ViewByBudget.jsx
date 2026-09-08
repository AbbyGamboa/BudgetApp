import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { PieChart } from '@mui/x-charts/PieChart';

function ViewByBudget({loggedInUser, budgetTotal, budgetcategories}){
    const [TranCates, setTC] = useState([])
    const {budgetId} = useParams()

    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch("http://localhost:8080/api/transactioncategory/"+budgetId, {
                headers:{
                        "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            if (response.status === 401 || response.status === 404){
                return;
            } 
            const payload = await response.json();
            setTC(payload)
            
        }
        doFetch()
    }, [budgetId])

    const categoryTotals = {};

    TranCates.forEach((tranCat) => {
        const categoryName = tranCat.budgetCategory.category.name;
        const amount = Number(tranCat.transaction.amount);

        if (categoryTotals[categoryName]) {
            categoryTotals[categoryName] += amount;
        } else {
            categoryTotals[categoryName] = amount;
        }
    });

    const chartData = Object.entries(categoryTotals).map(
        ([categoryName, total]) => ({
            id: categoryName,
            value: Number(total).toFixed(2),
            label: categoryName
        })
    );

    const sum = TranCates.reduce((total, tranCat) => {
        return total + Number(tranCat.transaction.amount);
    }, 0);

    const categoryBreakdown = budgetcategories.map((budgetCat) => {
        const categoryName = budgetCat.category.name;

        const budgeted = Number(budgetCat.percentage);
        const spent = categoryTotals[categoryName] || 0;
        const difference = budgeted - spent;

        return {
            categoryName,
            budgeted,
            spent,
            difference
        };
    });


    return (
        <>
            {TranCates && <div className="d-flex">
                 <div className="border border-blue p-3 rounded w-50">
                    <h3>Breakdown</h3>
                    <hr />
                    <h4>{(Number(sum-budgetTotal).toFixed(2) < 0)? "You are within budget":`You went over budget by $${Number(sum-budgetTotal).toFixed(2)}`}</h4>
                        {categoryBreakdown.map((category) => (
            <div key={category.categoryName}>
                <h5>{category.categoryName}</h5>
                <p>
                    Spent: ${category.spent.toFixed(2)}
                </p>

                <p>
                    {category.difference >= 0
                        ? `Remaining: $${category.difference.toFixed(2)}`
                        : `Over budget: $${Math.abs(category.difference).toFixed(2)}`
                    }
                </p>

                <hr />
            </div>
))}

                </div>

                <div className="p-3">
                    <h4>Total Spent: ${sum.toFixed(2)}</h4>
                        <PieChart series={[
                        {
                            data: chartData
                        }
                        ]}
                        width={400}
                        height={300}>
                    </PieChart>
                </div>
            </div>}
        </>
    );
}

export default ViewByBudget;