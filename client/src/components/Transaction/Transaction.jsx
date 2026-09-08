
function Transaction({transaction}){
    return (
        <>
            <th className="text-center">{transaction.transactionId}</th>
            <td className="px-5">{transaction.date}</td>
            <td className="px-5">${Number(transaction.amount).toFixed(2)}</td>
            <td className="px-5">{transaction.merchant_name && <>{`${transaction.merchant_name}`}</>}</td>
            <td className="px-5">{transaction.description && <>{`${transaction.description}`}</>}</td>
        </>
    )
}

export default Transaction