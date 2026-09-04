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

            if (response.status >= 200 && response.status < 300) {
                setTranCategory(payload.budgetCategory.category.name)
            } else{
                setTranCategory(null);
            }

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