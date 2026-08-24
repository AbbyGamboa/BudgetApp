import { createBrowserRouter, RouterProvider} from "react-router-dom";
import Layout from "./components/Layout";
import Landing from "./components/Landing";
import UserForm from "./components/User/UserForm";
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
                    element: <UserForm signup={false} ></UserForm>,
                },
                {
                    path:"/user/signup",
                    element: <UserForm signup={true}></UserForm>,
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
