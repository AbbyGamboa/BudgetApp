import { createBrowserRouter, Navigate, RouterProvider} from "react-router-dom";
import Layout from "./components/Layout";
import Landing from "./components/Landing";
import UserForm from "./components/User/UserForm";
import { useState } from "react";
import UserLanding from "./components/User/UserLanding";
import UserLogout from "./components/User/UserLogout";
import UserLayout from "./components/User/UserLayout";

function AppRouter(){
    const [loggedInUser, setLoggedInUser] = useState(JSON.parse(localStorage.getItem("loggedInUser")));

    const routes = [
        {
            path: "/", 
            element: <Layout loggedInUser={loggedInUser}></Layout>,
            children:[
                {
                    path: "/", 
                    element: <Landing></Landing>,
                }, 
                {
                    path:"/user",
                    element:<UserLayout loggedInUser={loggedInUser}/>,
                    children:[{
                        path:"login",
                        element: loggedInUser? <Navigate to="/user/landing"></Navigate>: <UserForm login={false} setLoggedInUser={setLoggedInUser}></UserForm>,
                    },
                    {
                        path:"signup",
                        element: loggedInUser? <Navigate to="/user/landing"></Navigate>: <UserForm signup={true} setLoggedInUser={setLoggedInUser}></UserForm>,
                    },
                    {
                        path:"landing",
                        element: <UserLanding></UserLanding>,
                    },
                    {
                        path: "signout",
                        element: loggedInUser? <UserLogout setLoggedInUser={setLoggedInUser}></UserLogout>: <Navigate to="/"></Navigate>,
                    }]
                }
                
            ],
        },
    ]

    const router = createBrowserRouter(routes);
    return (
       <RouterProvider router={router}></RouterProvider>
    );
}

export default AppRouter;
