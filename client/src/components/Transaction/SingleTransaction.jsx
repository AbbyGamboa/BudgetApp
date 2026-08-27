import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Transaction from "./Transaction";

function SingleTransaction({loggedInUser}){
    const {transactionId} = useParams()

    const navigate = useNavigate();

    const[transaction, setTransaction] = useState(null)
        
    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch("http://localhost:8080/api/transaction/"+transactionId, {
                headers:{
                        "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            if (response.status === 401 || response.status === 404){
                navigate("/view/accounts")
                return;
            } 
            const payload = await response.json();
            setTransaction(payload.payload)
            
        }
        doFetch()
    }, [transactionId])

    return(
        <>
         <h1>Viewing Transaction:</h1>
        {transaction && (
            <>
                <Transaction transaction={transaction}></Transaction>
                
                <Link className="btn btn-warning m-1" to={`/view/account/${transaction.account.accountId}`}>View all Transactions for this Account</Link>
                <Link className="btn btn-primary m-1" to="/edit/transaction">Edit</Link>
            </>
        )}
        </>
        
    );
}

export default SingleTransaction;
