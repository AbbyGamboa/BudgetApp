import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

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
            setTransaction(payload)
            
        }
        doFetch()
    }, [transactionId])

    return(
        <>
         <h1>Viewing Transaction:</h1>
        {transaction && (
            <>
                <p>Transaction ID: {transaction.transactionId}</p>
                
                <Link className="btn btn-warning" to="/view/budgets">View all Transactions for this Account</Link>
            </>
        )}
        </>
        
    );
}

export default SingleTransaction;
