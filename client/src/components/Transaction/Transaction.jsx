
function Transaction({transaction}){
    return (
        <div>
            <h3 className="text-center">Transaction: {transaction.transactionId}</h3>
            <hr className="border border-black"/>
            <h4><i className="fa-solid fa-calendar transactionIcon ms-2 me-4"></i>{transaction.date}</h4>
            <h4><i className="fa-solid fa-money-bill-1-wave  ms-2 me-4 transactionIcon"></i>Spent: ${Number(transaction.amount).toFixed(2)}</h4>
            <h4>{transaction.merchant_name && <><i className="fa-solid fa-store ms-2 me-4 transactionIcon"></i>{`${transaction.merchant_name}`}</>}</h4>
            <h4>{transaction.description && <><i className="fa-solid fa-pen-ruler ms-2 me-4 transactionIcon"></i>{`${transaction.description}`}</>}</h4>
        </div>
    )
}

export default Transaction