import { useState, useEffect } from "react"

function TransactionCategory({loggedInUser, transactionId}){

    const[tranCategory, setTranCategory] = useState([])
    
    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/transactioncategory/get/${transactionId}`, {
                headers:{
                     "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            const payload = await response.json();

            setTranCategory(payload.budgetCategory.category.name)
        }
        doFetch()
    }, [])

    return (
        <>{
            tranCategory && <h2>Category: {tranCategory}</h2>
        }
        
        </>
    )
}

export default TransactionCategory;