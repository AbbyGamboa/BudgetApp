import { Link } from "react-router-dom";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import background from '../../assets/blueBackground.jpg';

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
        <div className="p-4 mb-5 background-blue">
            <h1 className="text-white">{signup? "User sign up page": "User login page"}</h1></div>
        <ul>
            {errors.map((error,i) => <li key={i}>{error}</li>)}
        </ul>
    

        <div className="d-flex justify-content-center">
            <form onSubmit={signup? handleSubmitSignUp: handleSubmitLogin} className="border border-secondary w-75 p-2 d-flex flex-column align-items-center rounded">
                <h3 className="text-center">{signup? "Sign Up Form": "Login Form"}</h3>
                <label htmlFor="email">Email: </label>
                <input type="email" id="email" name="email" required placeholder="ex: 123abc@gmail.com" onChange={handleChange} value={user.email}/>

                <label htmlFor="password">Password: </label>
                <input type="password" id="password" name="password" required placeholder="ex: password123" onChange={handleChange} value={user.password}/>
                
                <button type="submit" className="btn btn-primary m-3 w-25">{signup? "Sign Up": "Login"}</button>

                <p>{endParagraph} <Link to={endNavigate}>Click here</Link></p>
            </form>
        </div>
       
            
        </>
        
    );
}

export default UserForm;