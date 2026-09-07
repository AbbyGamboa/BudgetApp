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
        
            if (response.status >= 200 && response.status < 300) {
                const payload = await response.json();
                setTranCategory(payload.budgetCategory.category.name)
            } else{
                setTranCategory(null);
            }

        }
        doFetch()
    }, [])

    return (
        <>{
            tranCategory && <h4>Category: {tranCategory}</h4>
        }
        </>
    )
}

export default TransactionCategory;