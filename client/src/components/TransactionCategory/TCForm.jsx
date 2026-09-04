import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

function TCForm({loggedInUser, setActiveModalItem}){
    const[budgetCategories, setBudgetCategories] = useState([])
    const[budgetCategoryId, setBudgetCategoryId] = useState()
    const[budgetId, setBudgetId] = useState()
    const[budgets, setBudgets] = useState([])
    const[transactions, setTransactions] = useState([])
    const [transactionId, setTransactionId] = useState()
    const {accountId} = useParams();


    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/transaction/account/${accountId}`, {
                headers:{
                     "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            const payload = await response.json();

            setTransactions(payload.payload)
            setTransactionId(payload.payload[payload.payload.length -1].transactionId)
        }
        doFetch()
    }, [])

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

     useEffect(()=> {
            if (budgetId === undefined || budgetId === ""){
                setBudgetCategories([])
                return;
            }; 
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
    
        }, [budgetId])

    async function handleTransCate(event){
        event.preventDefault()
        // could handle frontend validation here
        let url = `http://localhost:8080/api/transactioncategory?tId=${transactionId}&bCId=${budgetCategoryId}`
        let method = "POST"

        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${loggedInUser.token}`
            }
        })
        console.log(response)
        if (response.status >= 200 && response.status < 300) {
            setActiveModalItem(null)
           window.location.reload();
            
        } else {
            const payload = await response.json()
            setErrors(payload)
            
        }
    }


    return (
        <form onSubmit={handleTransCate}>
                <h1>Add to a budget</h1>
                <p htmlFor="transactionId">Transaction: {transactionId}</p>

                <label htmlFor="budget">Budget: </label>
                <select name="budget" id="budget" onChange={(event)=> setBudgetId(event.target.value)}>
                    <option value="">Select Budget</option>
                    {budgets.map((budget)=> <option key={budget.budgetId} value={budget.budgetId}>{budget.income}</option>)}
                </select>

                <div>
                <label htmlFor="budgetCategoryId">Category:</label>
                <select name="budgetCategoryId"  id="budgetCategoryId" value={budgetCategoryId} onChange={(event)=> setBudgetCategoryId(event.target.value)}>
                        <option value="">Select Category</option>
                        {budgetCategories.map(budgetCategory => <option key={budgetCategory.budgetCategoryId} value={budgetCategory.budgetCategoryId}>{budgetCategory.category.name}</option>)}
                        
                </select>
                </div>

                <button type="submit" className="btn btn-primary m-1">Add</button>
                <button type="submit" className="btn btn-danger m-1">No thanks</button>
            </form>
    )
}

export default TCForm;