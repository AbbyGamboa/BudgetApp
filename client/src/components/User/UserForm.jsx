import { Link } from "react-router-dom";
function UserForm({signup}){

    let endParagraph = "";
    let endNavigate = "";

    

    function endStatement(){
        if (signup){
            endParagraph = "Already have an account? ";
            endNavigate = "/user/login";
        } else{
            endParagraph = "Don't have an account? ";
            endNavigate = "/user/signup"
        }
    }

    endStatement();

    return (
        <>
        <div className="bg-success p-4 mb-5"><h1>{signup? "User sign up page": "User login page"}</h1></div>
        
        <div className="d-flex justify-content-center">
            <form action="" className="border border-secondary w-75 p-2 d-flex flex-column align-items-center">
                <h3 className="text-center">{signup? "Sign Up Form": "Login Form"}</h3>
                <label htmlFor="email">Email: </label>
                <input type="text" id="email" required placeholder="ex: 123abc@gmail.com"/>

                <label htmlFor="password">Password: </label>
                <input type="password" id="password" required />
                
                <button type="submit" className="m-3">{signup? "Sign Up": "Login"}</button>

                <p>{endParagraph} <Link to={endNavigate}>Click here</Link></p>
            </form>
        </div>
       
            
        </>
        
    );
}

export default UserForm;