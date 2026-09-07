import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import BudgetCategory from "../BudgetCategory/BudgetCategory";
import Headers from "../Styling/Headers";

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
        
        
        {budget && (<>
            <div className = "d-flex justify-content-between rounded background-blue m-4 p-3 border">
            <div className="mt-auto mb-0">
                <h1 >{budget.name}</h1>
                <p >Budget Id: {budget.budgetId}</p>
            </div>
            <div className="mt-auto mb-0 ">
                <Link className="btn btn-warning" to="/view/budgets">View all Budgets</Link>
            </div>
        </div>

        
            <div className="border border-black rounded m-4 p-2">
                <BudgetCategory loggedInUser={loggedInUser}></BudgetCategory>
                
            </div>
        </>
            
        )}
        </>
        
    );
}

export default SingleBudget;