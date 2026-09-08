import { useParams } from "react-router-dom"

function DeleteBCConfirm({loggedInUser, deleteItem, setdeleteItem}){
    const {budgetId} = useParams()
    const budgetCategoryId = deleteItem.budgetCategoryId

    async function handleDelete(){
        const response = await fetch("http://localhost:8080/api/budgetcategory/" + budgetCategoryId, {
            method: "DELETE",
            headers: {
                 "Authorization": `Bearer ${loggedInUser.token}`
            }
        })

        if (response.status >= 200 && response.status <= 300){
            window.location.reload();
        } else{
            navigate(`/view/budget/${budgetId}`)
        }

        navigate("/view/categories")
    }

    return (
        <div>
            <h3>Would you like to delete the category from your budget:</h3>
            <p>Name: {deleteItem.category.name}</p>
            <p>Amount: ${Number(deleteItem.percentage).toFixed(2)}</p>

            <button className="btn btn-primary m-1" onClick={()=>setdeleteItem(null)}>Exit</button>
            <button type="submit" className="btn btn-danger m-1" onClick={handleDelete}>Delete</button>
    
        </div>
        
    );
}

export default DeleteBCConfirm;