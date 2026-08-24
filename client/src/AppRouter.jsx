import { createBrowserRouter, RouterProvider} from "react-router-dom";
import Layout from "./components/Layout";
import Landing from "./components/Landing";
import UserLogin from "./components/User/UserLogin";
import UserSignUp from "./components/User/UserSignUp";
import { useState } from "react";
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
                    element: <UserLogin></UserLogin>,
                },
                {
                    path:"/user/signup",
                    element: <UserSignUp></UserSignUp>,
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
