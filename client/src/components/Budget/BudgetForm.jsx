import { useNavigate, useParams, Link} from "react-router-dom";
import { useState, useEffect } from "react";

function BudgetForm({loggedInUser}){
    const navigate = useNavigate();

    const{budgetId} = useParams();

    const initialBudget = {
        "income": "",
    }

    const [budget, setBudget] = useState(initialBudget);
    const [errors, setErrors] = useState([]);

    useEffect(()=> {
        if (budgetId === undefined){
            setBudget(initialBudget)
            return;
        }

        const prepopulate = async function(){
            const response = await fetch("http://localhost:8080/api/budget/" + budgetId,{
                headers:{
                    "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
        
            if(!response.ok){
                navigate("/")
                return;
            }

            const payload = await response.json();

            setBudget(payload)
        }
        prepopulate()

        
    }, [budgetId])
    
    function handleChange(event){
        let value = event.target.value;;


        setBudget({...budget, [event.target.name]:value})
    }

    async function handleSubmit(event) {
        event.preventDefault()
        // could handle frontend validation here
        let url = "http://localhost:8080/api/budget"
        let method = "POST"
        if (budgetId !== undefined) {
            url += "/edit/" + budgetId
            method = "PUT"
        }

        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${loggedInUser.token}`
            },
            body: JSON.stringify(budget)
        })
        if (response.status >= 200 && response.status < 300) {
            console.log(budget.income)
            navigate("/view/budgets")
        } else {
            const payload = await response.json()
            console.log(payload)
            setErrors(payload)
        }
    }
    return(
        <form onSubmit={handleSubmit}>
            <h1>{budgetId? "Update": "Create"} Budget</h1>
             {errors.length > 0 ?
                    <ul>{errors.map(error => <li key={error}>{error}</li>)}</ul>
                    : null
                }
                
                
            <label htmlFor="income">Total budget:  </label>
            <input type="text" id="income" name="income" onChange={handleChange} value={budget.income} inputMode="decimal"/>


            <button className="btn btn-primary m-1" type="submit">{budgetId? "Update":"Create"}</button>
            <Link className="btn btn-primary m-1" to="/view/budgets"> Cancel</Link>
        </form>
    );
}

export default BudgetForm