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
            <div className="d-flex justify-content-between rounded background-blue m-4 p-3 border"> 
                <div className="mt-auto mb-0">
                    <h1 >Manage Budgets:</h1>
                </div>
                <div className="mt-auto mb-0 ">
                    <Link to={"/add/budget"} className="btn btn-warning m-1">Create Budget</Link>
                </div>
        
            </div>

            <div className="grid-container">
                {budgets.map((budget, i) => <Budget key={i} name={budget.name} budgetId={budget.budgetId} loggedInUser={loggedInUser}></Budget>)}
            </div>
        
            
        </>
    
    );
}

export default ViewBudgets;