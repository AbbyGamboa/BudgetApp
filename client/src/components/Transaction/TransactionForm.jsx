import { useParams, useNavigate} from "react-router-dom";
import { useState, useEffect } from "react";

function TransactionForm({loggedInUser, transactionId, setActiveModalItem}){
    const navigate = useNavigate();

    const{accountId} = useParams();
    const[budgets, setBudgets] = useState([])
    const[budgetCategories, setBudgetCategories] = useState([])
    const [addCat, setAddCat] = useState(true);
    const[budgetCategoryId, setBudgetCategoryId] = useState()
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



    const initialTransaction = {
        "amount": "",
        "date":"",
        "merchantName":"", 
        "description":""
    }

    const [transaction, setTransaction] = useState(initialTransaction);
    const [errors, setErrors] = useState([]);

    useEffect(()=> {
        if (transactionId === undefined){
            setTransaction(initialTransaction)
            return;
        }

        const prepopulate = async function(){
            const response = await fetch("http://localhost:8080/api/transaction/" + transactionId,{
                headers:{
                    "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
        
            if(!response.ok){
                navigate("/")
                return;
            }

            const payload = await response.json();

            setTransaction(payload.payload)
        }
        prepopulate()
    }, [transactionId])
    
    function handleChange(event){
        let value = event.target.value;
        setTransaction({...transaction, [event.target.name]:value})
    }

    async function handleSubmit(event) {
        event.preventDefault()
        // could handle frontend validation here
        let url = `http://localhost:8080/api/transaction/${accountId}`
        let method = "POST"
        if (transactionId !== undefined) {
            url += "/edit/" + transactionId
            method = "PUT"
        }

        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${loggedInUser.token}`
            },
            body: JSON.stringify(transaction)
        })
        console.log(response)
        if (response.status >= 200 && response.status < 300) {
            setActiveModalItem(null)
            setAddCat(true);
            
        } else {
            const payload = await response.json()
            setErrors(payload)
            
        }
    }

    return(
        <>
        <form action="" className="flex-column align-content-center" onSubmit={handleSubmit} hidden={addCat}>
            <h1>{transactionId? "Update": "Create"} Transaction: </h1>

            <div>
                <label htmlFor="amount">*Amount: </label>
                <input type="text" name="amount" id="amount" value={transaction.amount} onChange={handleChange} required/>
                <label htmlFor="date">*Date: </label>
                <input type="date" name="date" id="date" value={transaction.date} onChange={handleChange} required/>
            </div>
            
            <div>
                <label htmlFor="merchant_name">Merchant Name: </label>
                <input type="text" name="merchant_name" id="merchant_name" value={transaction.merchant_name? transaction.merchant_name : " "} onChange={handleChange}/>
            </div>
            
            <div>
                <label htmlFor="amount">Description: </label>
                <input type="description" name="description" id="description" value={transaction.description? transaction.description: " "} onChange={handleChange}/>
            </div>


            <button type="submit" className="btn btn-primary">{transactionId? "Update": "Create"}</button>
        </form>

        {
            addCat && budgets && <form>
                <label htmlFor="budget">Budget: </label>
                <select name="budget" id="budget" onChange={(event)=> setBudgetId(event.target.value)}>
                    <option value="">Select Budget</option>
                    {budgets.map((budget)=> <option key={budget.budgetId} value={budget.budgetId}>{budget.income}</option>)}
                </select>

                <label htmlFor="budgetCategoryId">Category:</label>
                <select name="budgetCategoryId"  id="budgetCategoryId" value={budgetCategoryId}>
                        <option value="">Select Category</option>
                        {budgetCategories.map(budgetCategory => <option key={budgetCategory.budgetCategoryId}>{budgetCategory.category.name}</option>)}
                        
                </select>
            </form>
        }
        </>
    );
}

export default TransactionForm;