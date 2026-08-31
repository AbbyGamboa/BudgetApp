import { useNavigate } from "react-router-dom";
import { useEffect, useState} from "react";

function BudgetCategory({budgetId, loggedInUser}){
    const navigate = useNavigate();

    const[budgetcategories, setBudgetCategories] = useState([])

    useEffect(()=>{
            const doFetch = async () => {
                const response = await fetch("http://localhost:8080/api/budgetcategory/"+budgetId, {
                    headers:{
                            "Authorization": `Bearer ${loggedInUser.token}`
                    }
                })
                const payload = await response.json();
                setBudgetCategories(payload)
            }
            doFetch()
        }, [])

    return (
        <>
        <h1>Categories: </h1>
        {budgetcategories.map(budgetCategory=> <div key={budgetCategory.budgetCategoryId}>
            <h2>{budgetCategory.category.name}: %{budgetCategory.percentage}</h2>
            <h3>Dedicated amount from income: ${budgetCategory.budget.income * (budgetCategory.percentage / 100)}</h3>
        </div>)}
        </>
        
    );
}

export default BudgetCategory;