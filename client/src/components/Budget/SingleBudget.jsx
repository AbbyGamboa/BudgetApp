import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import BudgetCategory from "../BudgetCategory/BudgetCategory";

function SingleBudget({loggedInUser}){
    const {budgetId} = useParams()

    const navigate = useNavigate();

    const[budget, setBudget] = useState(null)
        
    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch("http://localhost:8080/api/budget/"+budgetId, {
                headers:{
                        "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            if (response.status === 401 || response.status === 404){
                navigate("/view/budgets")
                return;
            } 
            const payload = await response.json();
            setBudget(payload)
            
        }
        doFetch()
    }, [budgetId])

    return(
        <>
         <h1>Viewing Budget:</h1>
        {budget && (
            <>
                <p>Budget ID: {budget.budgetId}</p>
                <p>Total income: {budget.income}</p>
                <BudgetCategory loggedInUser={loggedInUser} budgetId={budgetId}></BudgetCategory>
                <Link className="btn btn-warning" to="/view/budgets">View all Budgets</Link>
            </>
        )}
        </>
        
    );
}

export default SingleBudget;