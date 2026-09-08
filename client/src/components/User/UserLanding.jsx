import ViewBudgets from "../Budget/ViewBudgets";
import { Link } from "react-router-dom";
import { useState, useEffect } from "react"
import BudgetChart from "../BudgetChart";
import RecentTransactions from "../Transaction/RecentTransactions";

function UserLanding({loggedInUser}){
    const[budget, setBudget] = useState()
    const[budgets, setBudgets] = useState([])
    const[budgetId, setBudgetId] = useState()
        const[budgetcategories, setBudgetCategories] = useState([])


    useEffect(()=>{
            const doFetch = async () => {
                const response = await fetch("http://localhost:8080/api/budget/myBudgets", {
                    headers:{
                            "Authorization": `Bearer ${loggedInUser.token}`
                    }
                })

                if(response.status >= 200 && response.status <= 300){
                    const payload = await response.json();
                    setBudgets(payload)
                }
                
            }
            doFetch()
        }, [])


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

    useEffect(()=>{
            const doFetch = async () => {
                const response = await fetch("http://localhost:8080/api/budgetcategory/"+budgetId, {
                    headers:{
                            "Authorization": `Bearer ${loggedInUser.token}`
                    }
                })
                const payload = await response.json();
                if(response.status>= 200 && response.status <= 300){
                    setBudgetCategories(payload)
                } else{
                    navigate("/view/budgets")
                }
            }
            
            doFetch()
            
        }, [budgetId])


     
    return(
        <>
        <div className="d-flex flex-column justify-content-end rounded w-80 m-4 p-3 background-blue">
            <h1>Welcome {loggedInUser.email} !</h1>
        </div>

        <RecentTransactions loggedInUser={loggedInUser}></RecentTransactions>

        <div className="border border-blue rounded p-4 position-relative m-4">
            
            <div className=" d-flex justify-content-between p-2">
                <div>
                     <h4>Preview Budgets:</h4>
                </div>
               
                <div className=" d-flex justify-content-end p-2">
                    <h4 className="m-2">Budget: </h4>
                    <select name="budgetId" id="budgetId" onChange={(event)=>setBudgetId(event.target.value)}>
                        <option value="">Select budget</option>
                        {budgets.map(budget => <option key={budget.budgetId} value={budget.budgetId}>{budget.name}</option>)}
                    </select>
                </div>
                
            </div>

           
            {budget && <div className="m-1  rounded p-3">
                <div>
                    <h1>{budget.name}</h1>
                    <BudgetChart loggedInUser={loggedInUser} budgetId={budgetId}></BudgetChart>
                </div>
            </div>}

            
        </div>
        </>
    );
}

export default UserLanding;