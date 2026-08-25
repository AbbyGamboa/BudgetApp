import { Link } from "react-router-dom";
function UserLogout({setLoggedInUser}){
    function handleSubmit(){
        localStorage.removeItem("loggedInUser");
        setLoggedInUser(null)
    }

    return (
        <div>
            <h1>Are you sure you want to sign out? </h1>
            <button type="submit" className="p-2" onClick={handleSubmit}>Sign out</button>
            <Link to="/" className="btn">Cancel</Link>
        </div>
    )
}

export default UserLogout;