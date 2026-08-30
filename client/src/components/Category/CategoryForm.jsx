import { useState} from "react";
import { useNavigate } from "react-router-dom";

function CategoryForm({loggedInUser}){
    const navigate = useNavigate();

    const initialCategory = {
        "name": "",
    }
    const[category, setCategory] = useState(initialCategory);
    const[errors, setErrors] = useState([])

    async function handleSubmit(event){
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
            navigate(`/view/categories`)
        } else {
            const payload = await response.json()
            setErrors(payload)
            
        }
    }

    function handleChange(event){
        let value = event.target.value;
        setCategory({...category, [event.target.name]:value})

    }
    return(
        <form onSubmit={handleSubmit}>
            <h1>Add Category: </h1>

            {errors.length > 0 ?
                    <ul>{errors.map(error => <li key={error}>{error}</li>)}</ul>
                    : null
                }

            <label htmlFor="name">Name: </label>
            <input type="text" name="name" id="name" onChange={handleChange}/>
            <button type="submit" className="btn btn-primary m-1">Add</button>
        </form>
    );
}

export default CategoryForm;