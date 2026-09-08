import { useState, useEffect} from "react";

function ViewCategoryByUser({loggedInUser, category}){

    const[categories, setCategories] = useState([])
            
        useEffect(()=>{
            const doFetch = async () => {
                const response = await fetch("http://localhost:8080/api/categories", {
                    headers:{
                            "Authorization": `Bearer ${loggedInUser.token}`
                    }
                })
                const payload = await response.json();
                setCategories(payload.payload)
            }
            doFetch()
        }, [category])

    return(
        <>
        {categories.map((category) => <option key= {category.categoryId} value={category.categoryId}>{category.name}</option>)}
        </>
         
    )
}

export default ViewCategoryByUser;