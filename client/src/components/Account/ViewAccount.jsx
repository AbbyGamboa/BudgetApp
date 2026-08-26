import { useEffect } from "react";
import { useState } from "react";
import Account from "./Account";
import { Link } from "react-router-dom";

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
        <h1>Accounts: </h1>
        {accounts.map((account,i) => <Account key ={i} accountId={account.accountId} subtype={account.subtype}/>)}
        <Link to="/create/account" className="btn btn-success m-1">Create Account</Link>
        </>
    

    );
}

export default ViewAccount;