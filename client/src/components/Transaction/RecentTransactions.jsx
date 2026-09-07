import { useEffect, useState } from "react";
import Transaction from './Transaction.jsx'
function RecentTransactions({loggedInUser}){
    const [transactions, setTransactions] = useState([])

    useEffect(()=>{
            const doFetch = async () => {
                const response = await fetch(`http://localhost:8080/api/transaction/recent`, {
                    headers:{
                         "Authorization": `Bearer ${loggedInUser.token}`
                    }
                })
            
                if (response.status >= 200 && response.status < 300) {
                    const payload = await response.json();
                    setTransactions(payload.payload)   
                } 
    
            }
            doFetch()
        }, [loggedInUser])

    return (
        <div  className=" rounded p-4 position-relative m-3">
            <h4>Recent Transactions:</h4>
            {transactions.map(transaction => <Transaction transaction={transaction}></Transaction>)}

            
        </div>
    );
}

export default RecentTransactions;