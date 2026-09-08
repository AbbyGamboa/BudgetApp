import { Link } from "react-router-dom";

function Account({accountId,subtype}){
    return (
        <div className="border border-blue p-3">
                <h1>Account: {accountId}</h1>
            <p>Type: {subtype} </p>
            <Link className="btn border border-black m-1" to={`/view/account/${accountId}`}> View</Link >
           <Link className="btn border border-black m-1" to={`/edit/account/${accountId}`}> Edit</Link >
        </div>
       
    );
}

export default Account;