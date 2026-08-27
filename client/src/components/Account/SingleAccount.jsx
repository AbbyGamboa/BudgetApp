import { useState, useEffect } from "react"
import { useParams,useNavigate} from "react-router-dom"
import ViewBudgets from "../Budget/ViewBudgets";

function SingleAccount({loggedInUser}){
    const {accountId} = useParams();
    const navigate = useNavigate();

    const[account, setAccount] = useState(null)
        
    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch("http://localhost:8080/api/account/"+accountId, {
                headers:{
                        "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            if (response.status === 401 || response.status === 404){
                navigate("/view/accounts")
                return;
            } 
            const payload = await response.json();
            setAccount(payload)
            
        }
        doFetch()
    }, [accountId])
    
    return(
        <>
         <h1>Viewing account:</h1>
        {account && (
            <>
                <p>Account ID: {account.accountId}</p>
                <p>Type: {account.subtype}</p>
                
            </>
        )}
        </>
    

    );
}

export default SingleAccount;