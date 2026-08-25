import { createBrowserRouter, Navigate, RouterProvider} from "react-router-dom";
import Layout from "./components/Layout";
import Landing from "./components/Landing";
import UserForm from "./components/User/UserForm";
import { useState } from "react";
import UserLanding from "./components/User/UserLanding";
function AppRouter(){
    const [loggedInUser, setLoggedInUser] = useState(JSON.parse(localStorage.getItem("loggedInUser")));

    const routes = [
        {
            path: "/", 
            element: <Layout></Layout>,
            children:[
                {
                    path: "/", 
                    element: <Landing></Landing>,
                }, 
                {
                    path:"/user/login",
                    element: loggedInUser? <Navigate to="/user/landing"></Navigate>: <UserForm login={false} setLoggedInUser={setLoggedInUser}></UserForm>,
                },
                {
                    path:"/user/signup",
                    element: loggedInUser? <Navigate to="/user/landing"></Navigate>: <UserForm signup={true} setLoggedInUser={setLoggedInUser}></UserForm>,
                },
                {
                    path:"/user/landing",
                    element: <UserLanding></UserLanding>,
                },
            ],
        },
    ]

    const router = createBrowserRouter(routes);
    return (
       <RouterProvider router={router}></RouterProvider>
    );
}

export default AppRouter;
