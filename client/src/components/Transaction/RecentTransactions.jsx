import { useEffect, useState } from "react";
import Transaction from './Transaction.jsx'
import TransactionCategory from "../TransactionCategory/TransactionCategory.jsx";

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
        <div  className=" rounded p-4 position-relative m-3 border border-blue">
            <h4>Recent Transactions:</h4>
            <table className="table table-striped table-hover align-middle mb-4">
                <thead className="">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><i className="fa-solid fa-calendar transactionIcon ms-2 me-4"></i>Date</th>
                        <th scope="col"><i className="fa-solid fa-money-bill-1-wave  ms-2 me-4 transactionIcon"></i>Spent</th>
                        <th scope="col"><i className="fa-solid fa-store ms-2 me-4 transactionIcon"></i>Merchant</th>
                        <th scope="col"><i className="fa-solid fa-pen-ruler ms-2 me-4 transactionIcon"></i>Description</th>
                        <th scope="col"><i class="fa-solid fa-table-cells-large ms-2 me-4 transactionIcon"></i>Category</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {transactions.map(transaction => 
                    <tr key={transaction.transactionId} className="">
                        <Transaction transaction={transaction}></Transaction>
                        <td className="px-5">
                            <TransactionCategory loggedInUser = {loggedInUser} transactionId={transaction.transactionId}></TransactionCategory>
                        </td>
                        <td></td>
                    </tr>)}
                </tbody>
            </table>
            
        </div>
    );
}

export default RecentTransactions;