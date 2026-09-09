import { Link } from "react-router-dom";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Headers from "../Styling/Headers";

function UserForm({signup, setLoggedInUser}){
    const navigate = useNavigate();

    const [user, setUser] = useState({email:"", password:""});
    const [errors, setErrors] = useState([]);

    function handleChange(event){
        setUser({...user, [event.target.name]:event.target.value});
    }

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

    async function handleSubmitSignUp(event){
        event.preventDefault();
        const response = await fetch("http://localhost:8080/api/user",{
            method: "POST", 
            headers:{
                "Content-Type": "application/json",
            }, 
            body: JSON.stringify(user)
        })

        const payload = await response.json()
        if (response.status >= 200 && response.status < 300){
            const token = payload.token;
            const loggedInUser = {email: user.email, token:token};

            setLoggedInUser(loggedInUser)
            localStorage.setItem("loggedInUser", JSON.stringify(loggedInUser))
            navigate("/view/accounts")
        } else{
            setErrors(payload);
        }
    }

    async function handleSubmitLogin(event){
        event.preventDefault()
        const response = await fetch("http://localhost:8080/api/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user)
        })

        const payload = await response.json()
        if (response.status >= 200 && response.status < 300) {
            const token = payload.token;
            const loggedInUser = {email: user.email, token:token};

            setLoggedInUser(loggedInUser)
            localStorage.setItem("loggedInUser", JSON.stringify(loggedInUser))
            navigate("/view/accounts")
        } else {
            setErrors(payload)
        }
    }



    return (
        <>
        <Headers title={signup? "User sign up page": "User login page"}></Headers>
       
        <ul>
            {errors.map((error,i) => <li key={i}>{error}</li>)}
        </ul>
    

        <div className=" d-flex justify-content-center">
            <form onSubmit={signup? handleSubmitSignUp: handleSubmitLogin} className="border border-blue w-75 p-2 d-flex flex-column align-items-center rounded">
                <h3 className="text-center m-3">{signup? "Sign Up": "Login"}</h3>

                <input className="w-50 p-3 m-1 rounded border border-blue bg-light" type="email" id="email" name="email" autoComplete="current-email" required placeholder="Email" onChange={handleChange} value={user.email}/>

                <input className="w-50 p-3 m-1 rounded border border-blue bg-light" type="password" id="password" autoComplete="current-password" name="password" required placeholder="Password" onChange={handleChange} value={user.password}/>
                
                <button type="submit" className="btn btn-primary m-3 w-25">{signup? "Sign Up": "Login"}</button>

                <div className="d-flex justify-content-between">
                    <p className="p-3 me-4">{endParagraph} </p>
                    <Link className="ms-4 p-3 textHighlight" to={endNavigate}>{signup? "Login": "Signup"}</Link>
                </div>
                
            </form>
        </div>
       
            
        </>
        
    );
}

export default UserForm;