import { useEffect } from "react";
import { useState } from "react";
import Account from "./Account";
import { Link } from "react-router-dom";
import Headers from "../Styling/Headers";

function ViewAccount({loggedInUser}){
    const[accounts, setAccounts] = useState([])
    
    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch("http://localhost:8080/api/account/myAccounts", {
                headers:{
                     "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            const payload = await response.json();
            setAccounts(payload)
        }
        doFetch()
    }, [])


    return(
        <>
        <div className = "d-flex justify-content-between rounded background-blue m-4 p-3 border">
            <div className="mt-auto mb-0">
                <h1 >Manage Accounts:</h1>
            </div>
            <div className="mt-auto mb-0 ">
                <Link to="/create/account" className="btn btn-warning m-1">Create Account</Link>
            </div>
        </div>

        <div className="grid-container m-4 ">
            {accounts.map((account,i) => <Account key ={i} accountId={account.accountId} subtype={account.subtype}/>)}
        </div>
        
        
        </>
    

    );
}

export default ViewAccount;