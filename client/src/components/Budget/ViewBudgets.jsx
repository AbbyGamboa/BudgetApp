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
            <div className="d-flex flex-column justify-content-end rounded background-blue m-4 p-3"> 
                <h1 className="mt-auto mb-0">Manage Your Budgets: </h1>
               
            </div>

             <Link to={"/add/budget"} className="btn btn-primary mt-auto m-4">Add budget</Link>

            <div className="grid-container">
            {budgets.map((budget, i) => <Budget key={i} name={budget.name} budgetId={budget.budgetId} loggedInUser={loggedInUser}></Budget>)}
            </div>
        
            
        </>
    
    );
}

export default ViewBudgets;