import { useParams, useNavigate} from "react-router-dom";
import { useState, useEffect } from "react";

function TransactionForm({loggedInUser, transactionId}){
    const navigate = useNavigate();

    const{accountId} = useParams();

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
            const response = await fetch("http://localhost:8080/api/transaction/" + accountId,{
                headers:{
                    "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
        
            if(!response.ok){
                navigate("/")
                return;
            }

            const payload = await response.json();

            setTransaction(payload)
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
        console.log(response);
        if (response.status >= 200 && response.status < 300) {
            navigate(`/view/account/${accountId}`)
        } else {
            const payload = await response.json()
            setErrors(payload)
            
        }
    }

    return(
        <form action="" className="flex-column align-content-center" onSubmit={handleSubmit}>
            <h1>{transactionId? "Update": "Create"} Transaction: </h1>

            
            <label htmlFor="amount">*Amount: </label>
            <input type="text" name="amount" id="amount" value={transaction.amount} onChange={handleChange} required/>

            <label htmlFor="date">*Date: </label>
            <input type="date" name="date" id="date" value={transaction.date} onChange={handleChange} required/>

            <label htmlFor="merchantName">Merchant Name: </label>
            <input type="text" name="merchantName" id="merchantName" value={transaction.merchantName} onChange={handleChange}/>

            <label htmlFor="amount">Description: </label>
            <input type="description" name="description" id="description" value={transaction.description} onChange={handleChange}/>

            <button type="submit" className="btn btn-primary">{transactionId? "Update": "Create"}</button>
        </form>
        
    );
}

export default TransactionForm;