import { useState, useEffect } from "react"
import Budget from "./Budget"
import { Link } from "react-router-dom"

function ViewBudgets({loggedInUser}){
    const[budgets, setBudgets] = useState([])
        
    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch("http://localhost:8080/api/budget/myBudgets", {
                headers:{
                        "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            const payload = await response.json();
            setBudgets(payload)
        }
        doFetch()
    }, [])
    
    return(
        <>
        <h1>Budgets: </h1>
        {budgets.map((budget, i) => <Budget key={i} income={Number(budget.income).toFixed(2)} budgetId={budget.budgetId}></Budget>)}
        <Link to={"/add/budget"} className="btn btn-primary">Add budget</Link>
        </>
    
    );
}

export default ViewBudgets;