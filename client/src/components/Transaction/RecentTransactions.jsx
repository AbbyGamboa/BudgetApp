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
        }, [])

    return (
        <div  className=" rounded p-4 position-relative m-3">
            <h4>Recent Transactions:</h4>
            <div className="grid-container">
                {transactions.map(transaction => 
                <div key={transaction.transactionId} className="d-flex justify-content-center p-5 border border-blue rounded" style={{width:'300px', height: '300px'}}>
                    <Transaction transaction={transaction}></Transaction>
                </div>)}

            </div>
            
        </div>
    );
}

export default RecentTransactions;