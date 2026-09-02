
import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ViewCategoryByUser from "../Category/ViewCategoryByUser"
import { useParams } from "react-router-dom";

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
    
                setBudgetCategories(payload)
                setCategoryId(payload.category.categoryId);
            }
            prepopulate()
     
        }, [budgetCategoryId])
    
    function handleChange(event){
        let value = event.target.value;
        setBudgetCategories({...budgetCategory, [event.target.name]:value})
        
    }

    async function handleSubmitBC(event) {
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
    const initialCategory = {
        "name": ""
    }
    const[category, setCategory] = useState(initialCategory);

    function handleCategoryChange(event){
        let value = event.target.value;
        setCategory({...category, [event.target.name]:value})
        console.log(category)
    }
    async function handleSubmitCategory(event){
        event.preventDefault()

        const response = await fetch("http://localhost:8080/api/categories", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${loggedInUser.token}`
            },
            body: JSON.stringify(category)
        })
        console.log(response);

        if (response.status >= 200 && response.status < 300) {
            setCategoryRefresh(prev=> prev +1);
        } else {
            const payload = await response.json()
            setErrors(payload)
            
        }
    }

    const[showCustom, setShowCreate] = useState(true)
    const [categoryRefresh, setCategoryRefresh] = useState(0);

    return(
        <>
       <form onSubmit={handleSubmitCategory} hidden={activeModalItem? true:false}>
            <label htmlFor="showCustom">Create Custom Category</label>
                <input type="checkbox" name="showCustom" id="showCustom" onChange={()=>setShowCreate(!showCustom)}/>
            <div>
        
            <label htmlFor="name" hidden={showCustom}>Name: </label>

            <input type="text" name="name" id="name" onChange={handleCategoryChange} hidden={showCustom}/>
            <button type="submit" className="btn btn-primary m-1" hidden={showCustom}>Add</button>
            </div>
           

        </form>
       
        <form onSubmit={handleSubmitBC} >

            <div className="d-flex flex-column align-content-center">
                <label htmlFor="percentage">Amount:</label>
            <input type="text" name="percentage" id="percentage" onChange={handleChange} value={budgetCategory.percentage}/>
            
            <label htmlFor="categoryId">Category:</label>
            <select name="categoryId"  id="categoryId" value={categoryId} onChange={(event)=> {setCategoryId(event.target.value)
                console.log(categoryId)}} disabled={budgetCategoryId !== undefined}>
                    <option value="">Select Category</option>
                    <ViewCategoryByUser loggedInUser={loggedInUser} category={categoryRefresh}></ViewCategoryByUser>
            </select>
            </div>
            
            <button type="submit" className="btn btn-primary m-1" >{budgetCategoryId? "Update": "Add"} </button>
            <button type="button" className="btn btn-secondary" onClick={()=> budgetCategoryId !== undefined? setActiveModalItem(null): handleCreateClose()}>Close </button>
        </form>
        
        </>
        
    );
}

export default BudgetCategoryForm;