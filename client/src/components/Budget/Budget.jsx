import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
function Budget({loggedInUser, name, budgetId}){
    const [sum, setSum]= useState(0);

    useEffect(()=>{
            const doFetch = async () => {
                const response = await fetch("http://localhost:8080/api/budgetcategory/"+budgetId, {
                    headers:{
                            "Authorization": `Bearer ${loggedInUser.token}`
                    }
                })
                if(response.status >= 200 && response.status <= 300){
                     const payload = await response.json();

                    if(payload != "Budget has no categories"){
                        let total = 0;
                        for (const budCat of payload) {
                            total += Number(budCat.percentage);
                        }

                        setSum(total.toFixed(2));
                    } else{
                        setSum(0)
                    }
                }
               
            }
            
            doFetch()
            
        }, [budgetId])

    return(
        <div className="grid-item m-4 border border-blue rounded p-3">
            <h4>Budget {budgetId}: </h4>
            <h4>Name: {name}</h4>
            <h4>Total: ${sum}</h4>

            <Link className="btn border-black m-1" to={`/view/budget/${budgetId}`}>View</Link>
            <Link className="btn border-black m-1" to={`/edit/budget/${budgetId}`}>Edit</Link>
        </div>
    );
}

export default Budget;