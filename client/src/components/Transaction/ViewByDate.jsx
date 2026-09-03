import { useParams } from "react-router-dom";
import { useState} from "react";
import Transaction from "./Transaction";
import { Link } from "react-router-dom";
import TransactionCategory from "../TransactionCategory/TransactionCategory";

function ViewByDate({loggedInUser}){

    const{accountId} = useParams();
    const[transactions, setTransactions] = useState([])
    const[errors, setErrors] = useState([])
    const[start, setStart] = useState("")
    const[end, setEnd] = useState("")

    async function handleSubmit(event){
        event.preventDefault();

        const response = await fetch(
            `http://localhost:8080/api/transaction/date/${accountId}?start=${start}&end=${end}`,
            {
                headers: {
                    Authorization: `Bearer ${loggedInUser.token}`
                }
            }
        );
        console.log(response);
        const payload = await response.json()

        if (response.status >= 200 && response.status < 300) {
            
            console.log(payload.payload);
            setTransactions(payload.payload);
        } else {
            setErrors(payload)
        }

    }

    const [showTrans, setShowTrans] = useState(true);
    const hideTrans = () => setShowTrans(false);


    return(
        <>
        <form onSubmit={handleSubmit} className="flex-column m-5" onReset={hideTrans}>
            <h3>View by date:</h3>
            <label htmlFor="start" className="m-1">Start: </label>
            <input type="date" name="start" id="start" onChange={(event) => setStart(event.target.value)} className="m-1" />

            <label htmlFor="end" className="m-1">End: </label>
            <input type="date" name="end" id="end" onChange={(event) => setEnd(event.target.value)} className="m-1"/>
            <button type="submit" className="btn btn-secondary m-1">Check</button>
            <button type="reset" className="btn btn-danger">Reset</button>

        </form>
        {showTrans && transactions.map(transaction => <div key ={transaction.transactionId} className="flex p-5">
        <Transaction transaction={transaction}/>
        <TransactionCategory loggedInUser={loggedInUser} transactionId={transaction.transactionId}></TransactionCategory>
        <Link className="btn btn-primary" to={`/view/${transaction.transactionId}`}>View</Link>
        </div>)}
        {errors.length > 0 ?
                    <ul>{errors.map(error => <li key={error}>{error}</li>)}</ul>
                    : null
                }
    
        </>


    
    );
}

export default ViewByDate;