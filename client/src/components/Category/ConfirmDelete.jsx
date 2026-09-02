import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect} from "react";
import { Link } from "react-router-dom";

function ConfirmDelete({loggedInUser}){
    const { categoryId } = useParams()

    const navigate = useNavigate()

    const initialCategory = {
        "name" :"",
    }

    const [category, setCategory] = useState({});

    useEffect(() => {
        if (categoryId === undefined) {
            setCategory(initialCategory)
            return
        }

        const prepopulate = async function() {
            const response = await fetch("http://localhost:8080/api/categories/" + categoryId, {
                headers:{
                            "Authorization": `Bearer ${loggedInUser.token}`
                    }
            })
            const payload = await response.json()

            // TODO: this would be better handled in a loader function in AppRouter
            if (response.status >= 200 && response.status <= 300){
                setCategory(payload.payload)
            } else{
                navigate("/view/categories")
            }
        }
        prepopulate()
    }, [categoryId])

    async function handleDelete() {
        const response = await fetch("http://localhost:8080/api/categories/" + categoryId, {
            method: "DELETE",
            headers: {
                 "Authorization": `Bearer ${loggedInUser.token}`
            }
        })



        navigate("/view/categories")
    }


    return(
        <div>
             <h1>Confirm delete</h1>

             <p>Category Id: {category.categoryId}</p>
             <p>Name: {category.name}</p>

            <Link className="btn btn-secondary" to="/view/categories">Cancel</Link>
            <button className='btn btn-danger' onClick={handleDelete}>Delete</button>

        </div>

    );
}

export default ConfirmDelete;