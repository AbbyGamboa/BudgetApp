
import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function BudgetCategoryForm({loggedInUser,activeModalItem, setActiveModalItem}){
    const navigate = useNavigate();
    const budgetId = activeModalItem.budget.budgetId
    const categoryId = activeModalItem.category.categoryId
    const budgetCategoryId = activeModalItem.budgetCategoryId


    const initialBudgetCategory = {
        budgetCategoryId: "",
        percentage: ""
    }

    const [budgetCategory, setBudgetCategories] = useState(initialBudgetCategory);
    const [errors, setErrors] = useState([]);

    useEffect(()=> {
            if (budgetCategoryId === undefined){
                setBudgetCategories(initialBudgetCategory)
                return;
            }
    
            const prepopulate = async function(){
                const response = await fetch("http://localhost:8080/api/budgetcategory/mybc/" + budgetCategoryId,{
                    headers:{
                        "Authorization": `Bearer ${loggedInUser.token}`
                    }
                })
            
                if(!response.ok){
                    navigate("/")
                    return;
                }
    
                const payload = await response.json();
                console.log(payload)
    
                setBudgetCategories(payload)
            }
            prepopulate()
     
        }, [budgetCategoryId])
    
    function handleChange(event){
        let value = event.target.value;
        setBudgetCategories({...budgetCategory, [event.target.name]:value})
    }

    async function handleSubmit(event) {
        event.preventDefault()
        // could handle frontend validation here
        let url = "http://localhost:8080/api/budgetcategory"
        let method = "POST"
        if (budgetCategoryId !== undefined) {
            url += "/edit/" + budgetCategoryId +"?budgetId="+budgetId+"&categoryId="+categoryId
            method = "PUT"
        }
        
        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${loggedInUser.token}`
            },
            body: JSON.stringify(budgetCategory)
        })
    
        if (response.status >= 200 && response.status < 300) {
            setActiveModalItem(null);
            navigate(`/view/budget/${budgetId}`)
            window.location.reload();
        } else {
            const payload = await response.json()
            console.log(payload)
            setErrors(payload)
        }
    }

    return(
        <form onSubmit={handleSubmit}>
            <label htmlFor="percentage">Percentage:</label>
            <input type="text" name="percentage" id="percentage" onChange={handleChange} value={budgetCategory.percentage}/>
            <button type="submit" className="btn btn-primary">Update</button>
        </form>
    );
}

export default BudgetCategoryForm;