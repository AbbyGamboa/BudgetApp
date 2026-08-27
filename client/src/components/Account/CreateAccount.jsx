import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Link } from "react-router-dom";

function CreateAccount({loggedInUser}){
    const navigate = useNavigate();

    const{accountId} = useParams();

    const initialAccount = {
        "subtype": "",
    }

    const [account, setAccount] = useState(initialAccount);
    const [errors, setErrors] = useState([]);

    useEffect(()=> {
        if (accountId === undefined){
            setAccount(initialAccount)
            return;
        }

        const prepopulate = async function(){
            const response = await fetch("http://localhost:8080/api/account/" + accountId,{
                headers:{
                    "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
        
            if(!response.ok){
                navigate("/")
                return;
            }

            const payload = await response.json();

            setAccount(payload)
        }
        prepopulate()
    }, [accountId])
    
    function handleChange(event){
        let value = event.target.value;
        setAccount({...account, [event.target.name]:value})
    }

    async function handleSubmit(event) {
        event.preventDefault()
        // could handle frontend validation here
        let url = "http://localhost:8080/api/account"
        let method = "POST"
        if (accountId !== undefined) {
            url += "/edit/" + accountId
            method = "PUT"
        }

        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${loggedInUser.token}`
            },
            body: JSON.stringify(account)
        })
        if (response.status >= 200 && response.status < 300) {
            navigate("/view/accounts")
        } else {
            const payload = await response.json()
            setErrors(payload)
        }
    }

    return(
        <form onSubmit={handleSubmit}>
            <h1>{accountId ? "Update" : "Create"} Account</h1>
             {errors.length > 0 ?
                    <ul>{errors.map(error => <li key={error}>{error}</li>)}</ul>
                    : null
                }
                
                


            <label htmlFor="subtype">Type: </label>
            <input type="text" id="subtype" name="subtype" onChange={handleChange} value={account.subtype}/>

            <label htmlFor="income">Total worth: </label>
            <input type="text" id="income" name="income"/>

            <button className="btn btn-primary m-1" type="submit">{accountId? "Update":"Create"}</button>
            <Link className="btn btn-primary m-1" to="/view/accounts"> Cancel</Link>
        </form>
    );
}

export default CreateAccount;
