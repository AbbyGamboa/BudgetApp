
import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ViewCategoryByUser from "../Category/ViewCategoryByUser"
import { useParams } from "react-router-dom";
import { Link } from "react-router-dom";

function BudgetCategoryForm({loggedInUser,activeModalItem, setActiveModalItem, handleCreateClose}){
    const navigate = useNavigate();
    const {budgetId} = useParams(); 

    const budgetCategoryId = activeModalItem?.budgetCategoryId;
    const[categoryId, setCategoryId] = useState("");

    const initialBudgetCategory = {
        percentage: ""
    }

    const [budgetCategory, setBudgetCategories] = useState(initialBudgetCategory);
    const [errors, setErrors] = useState([]);

    useEffect(()=> {
            if (budgetCategoryId === undefined){
                setBudgetCategories(initialBudgetCategory)
                setCategoryId("")
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
                setCategoryId(payload.category.categoryId);
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
            url += "/edit/" + budgetCategoryId +
            "?budgetId="+budgetId+"&categoryId="+categoryId
            method = "PUT"
        } else{
            url += "?budgetId=" + budgetId + "&categoryId=" + categoryId
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

    const[show, setShow] = useState(false);


    return(
        <>
        <form onSubmit={handleSubmit}>
            <label htmlFor="percentage">Percentage:</label>
            <input type="text" name="percentage" id="percentage" onChange={handleChange} value={budgetCategory.percentage}/>
            
            <div className="d-flex">
                <p>Category: </p>
                <select name="categoryId" id="categoryId" value={categoryId} onChange={(event)=> setCategoryId(event.target.value)} disabled={budgetCategoryId !== undefined}>
                    <ViewCategoryByUser loggedInUser={loggedInUser}></ViewCategoryByUser>
                </select>
                <Link className="btn btn-primary m-1" hidden={budgetCategoryId !== undefined} onClick={()=>setShow(!show)}>+</Link>
                
            </div>
             {show && 
                    <>
                        <label htmlFor="name">Category Name: </label>
                        <input type="text" name="name" id="name"/>
                    </>
                }
            
            <button type="submit" className="btn btn-primary m-1">{budgetCategoryId? "Update": "Add"}</button>
            <button type="button" className="btn btn-secondary" onClick={()=> budgetCategoryId !== undefined? setActiveModalItem(null): handleCreateClose()}>Close </button>
        </form>
        
        </>
        
    );
}

export default BudgetCategoryForm;