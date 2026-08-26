import { Link } from "react-router-dom";
function Account({accountId,subtype}){
    return (
        <div>
            <p>Account: {accountId}</p>
            <p>Type: {subtype} </p>
            <Link className="btn btn-primary m-1" to={`/view/account/${accountId}`}> View</Link >
           <Link className="btn btn-warning m-1" to={`/edit/account/${accountId}`}> Edit</Link >
        </div>
    );
}

export default Account;