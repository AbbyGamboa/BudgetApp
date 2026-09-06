import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

function TCForm({loggedInUser, setActiveModalItem, firstTId, handleCreateClose}){
    const[budgetCategories, setBudgetCategories] = useState([])
    const[budgetCategoryId, setBudgetCategoryId] = useState("")
    const[budgetId, setBudgetId] = useState()
    const[budgets, setBudgets] = useState([])
    const [transactionId, setTransactionId] = useState(null)
    const {accountId} = useParams();


    //We set the transactionId to the the last transaction Id made
    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/transaction/account/${accountId}`, {
                headers:{
                     "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            const payload = await response.json();

            setTransactionId(payload.payload[payload.payload.length -1].transactionId)
        }
        doFetch()
    }, [])

    //Gets the budgets created
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

    //gets budgetCategory by the budgetId
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

    //creates a transaction category with the transactionid and budgetCategoryId 
    //we want to be able to update a transaction category
    //how do we differentiate between creating and updating, if we get by transactionId and it exists in 
    const [existing, setExisting] = useState(false)

    useEffect(() => {
            if (!firstTId) {
                return;
            }

            const seeIfExists = async () => {
                const response = await fetch(
                    `http://localhost:8080/api/transactioncategory/get/${firstTId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${loggedInUser.token}`
                        }
                    }
                );

                if (response.status >= 200 && response.status < 300) {
                    const payload = await response.json();
                    console.log(payload.budgetCategory.budget.budgetId)
                    setBudgetId(payload.budgetCategory.budget.budgetId)
                    setBudgetCategoryId(payload.budgetCategory.budgetCategoryId)
                    setExisting(true);
                } else if (response.status === 404) {
                    setExisting(false);
                }
            };

            seeIfExists();
        }, [transactionId, loggedInUser.token]);

    async function handleTransCate(event){
        event.preventDefault()
        // could handle frontend validation here
        let url;
        let method;

        if (existing) {
            url = `http://localhost:8080/api/transactioncategory/update?tId=${firstTId}&bCId=${budgetCategoryId}`;
            method = "PUT";
        } else {
            url = `http://localhost:8080/api/transactioncategory?tId=${transactionId}&bCId=${budgetCategoryId}`;
            method = "POST";
        }

        const response = await fetch(url, {
            method: method,
            headers: {
                Authorization: `Bearer ${loggedInUser.token}`
            }
        })
        if (response.status >= 200 && response.status < 300) {
            setActiveModalItem(null)
            window.location.reload();
            
        } else {
            const payload = await response.json()
            setErrors(payload)
            
        }
    }
    //work on delete transaction category:
    async function handleDelete(){
    }

    function handleNoCategory(){
        if (existing){
            setActiveModalItem(null)
        } else{
            handleCreateClose()
        }
        window.location.reload();
    }

    return (
        <form onSubmit={handleTransCate}>
                <h1>{existing? "Edit": "Add"} a category to transaction</h1>
                <p htmlFor="transactionId">Transaction: {firstTId? firstTId: transactionId}</p>

                <label htmlFor="budgetId">Budget: </label>
                <select name="budgetId" id="budgetId" onChange={(event)=> setBudgetId(event.target.value)} value={budgetId}>
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

                <button type="submit" className="btn btn-primary m-1">{existing? "Edit": "Add"}</button>
                {existing && <button  className="btn btn-danger m-1">Delete</button>}
                <button type="button" className="btn btn-warning m-1" onClick={() => handleNoCategory()}>No thanks</button>
            
            </form>
    )
}

export default TCForm;