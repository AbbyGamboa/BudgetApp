import { useState, useEffect } from "react"
import { useParams,useNavigate} from "react-router-dom"
import ViewByDate from "../Transaction/ViewByDate";
import ViewTransactions from "../Transaction/ViewTransactions";
import Headers from "../Styling/Headers";
import Link from "@mui/material/Link";

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
        
        {account && (
            <>    

                <div className = "d-flex justify-content-between rounded background-blue m-4 p-3 border">
                    <div className="mt-auto mb-0">
                        <h1 >Account ID: {account.accountId}</h1>
                        <p >Type: {account.subtype}</p>
                    </div>
                </div>
                <ViewTransactions loggedInUser={loggedInUser}></ViewTransactions>
                <ViewByDate loggedInUser={loggedInUser}></ViewByDate>
            </>
        )}
        </>
    

    );
}

export default SingleAccount;