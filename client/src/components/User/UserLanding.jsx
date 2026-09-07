import ViewBudgets from "../Budget/ViewBudgets";
import { Link } from "react-router-dom";
import { useState, useEffect } from "react"
import BudgetChart from "../BudgetChart";

function UserLanding({loggedInUser}){
    const[budget, setBudget] = useState()
    const[budgets, setBudgets] = useState([])
    const[budgetId, setBudgetId] = useState()


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

        const [sum, setSum]= useState(0);

    useEffect(()=>{
        if(budgetId === undefined || budgetId === ""){return;}
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
        <div className="d-flex flex-column justify-content-end rounded m-3 w-80 p-3 background-blue">
            <h1>Welcome user {loggedInUser.email}</h1>
        </div>

        <div className="border border-blue rounded p-4 position-relative m-3">
            <div className="position-absolute top-1 end-0 p-2">
                <label htmlFor="budgetId">Budget: </label>
                <select name="budgetId" id="budgetId" onChange={(event)=>setBudgetId(event.target.value)}>
                    <option value="">Select budget</option>
                    {budgets.map(budget => <option key={budget.budgetId} value={budget.budgetId}>{budget.name}</option>)}
                </select>
            </div>

            <div className="m-1 d-flex justify-content-between rounded p-3">
                {budget && <div className="">
                    <h1>{budget.name}</h1>
                    <BudgetChart loggedInUser={loggedInUser} budgetId={budgetId}></BudgetChart>
                </div>}

            </div>
            
        </div>
        </>
    );
}

export default UserLanding;