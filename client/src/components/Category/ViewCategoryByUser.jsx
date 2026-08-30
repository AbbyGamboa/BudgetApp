import { useState, useEffect} from "react";
import { Link } from "react-router-dom";
import Category from "./Category";

function ViewCategoryByUser({loggedInUser}){

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
        }, [])

    return(
         <>
        <h1>Categories: </h1>
        {categories.map((category, i) => <Category key={i} category={category}></Category>)}
        <Link to={"/add/category"} className="btn btn-primary">Add Category</Link>
        </>
    )
}

export default ViewCategoryByUser;