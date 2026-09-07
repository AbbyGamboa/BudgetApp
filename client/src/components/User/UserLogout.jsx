import { Link } from "react-router-dom";
import endImage from "../../assets/endingImage.avif"
function UserLogout({setLoggedInUser}){
    function handleSubmit(){
        localStorage.removeItem("loggedInUser");
        setLoggedInUser(null)
    }

    return (
        <div className="border border-black m-5 p-5 rounded d-flex position-relative">
            <div className="border rounded endingHeight">
                
            </div>
            <div className="m-5 p-2 w-50">
                <h1>Are you logging out?</h1>
                <hr />

                <p className="text-center">You are about to log out. You can always log back in at any time.</p>
                <div className="d-flex justify-content-center">
                    <Link to="/" className="btn border m-1">Cancel</Link>
                    <button type="submit" className="btn btn-primary m-1" onClick={handleSubmit}>Sign out</button>
                </div>
                
            </div>
        </div>
    )
}

export default UserLogout;