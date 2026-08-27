import { Link } from "react-router-dom"
function Transaction({transaction}){
    return (
        <div className="m-1">
            <h3>Transaction: {transaction.transactionId}</h3>
            <h4>Date: {transaction.date}</h4>
            <h4>Spent: ${Number(transaction.amount).toFixed(2)}</h4>
            <h4>Merchant Name: {transaction.merchant_name}</h4>
            <h4>Description: {transaction.description}</h4>
        </div>


    )
}

export default Transaction