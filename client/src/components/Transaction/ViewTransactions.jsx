import { useState, useEffect } from "react";
import { useParams, Link} from "react-router-dom";
import Transaction from "./Transaction";

function ViewTransactions({loggedInUser}){
    const[transactions, setTransactions] = useState([])
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
        }
        doFetch()
    }, [])


    return(
        <>
        <Link to="/create/account" className="btn btn-success m-1">Create Transaction</Link>
        <h2>Transactions: </h2>
        
        <div className="d-flex">
        {transactions.map(transaction => <div key ={transaction.transactionId} className="flex p-5">
        <Transaction transaction={transaction}/>
        <Link className="btn btn-primary" to={`/view/${transaction.transactionId}`}>View</Link>
        </div>)}
        </div>
        
        

        
        </>
    

    );
}

export default ViewTransactions