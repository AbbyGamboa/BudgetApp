import { createBrowserRouter, Navigate, RouterProvider} from "react-router-dom";
import Layout from "./components/Layout";
import Landing from "./components/Landing";
import UserForm from "./components/User/UserForm";
import { useState } from "react";
import UserLanding from "./components/User/UserLanding";
import UserLogout from "./components/User/UserLogout";
import UserLayout from "./components/User/UserLayout";
import ViewAccount from "./components/Account/ViewAccount";
import SingleAccount from "./components/Account/SingleAccount";
import CreateAccount from "./components/Account/CreateAccount";
import SingleBudget from "./components/Budget/SingleBudget";
import ViewBudgets from "./components/Budget/ViewBudgets";
import BudgetForm from "./components/Budget/BudgetForm";
import SingleTransaction from "./components/Transaction/SingleTransaction"

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
                }, 
                {
                    path:"/view/accounts", 
                    element: loggedInUser? <ViewAccount loggedInUser={loggedInUser}></ViewAccount>:<Navigate to="/"></Navigate>,
                }, 
                {
                    path:"/view/account/:accountId", 
                    element: loggedInUser? <SingleAccount loggedInUser={loggedInUser}></SingleAccount>:<Navigate to="/"></Navigate>,
                },
                {
                    path:"/create/account", 
                    element: loggedInUser? <CreateAccount loggedInUser={loggedInUser}></CreateAccount>: <Navigate to="/"></Navigate>
                }, 
                {
                    path:"/edit/account/:accountId", 
                    element: loggedInUser? <CreateAccount loggedInUser={loggedInUser}></CreateAccount>: <Navigate to="/"></Navigate>
                }, 
                {
                    path:"/view/budgets/", 
                    element: loggedInUser? <ViewBudgets loggedInUser={loggedInUser}></ViewBudgets>:<Navigate to="/"></Navigate>,
                },
                {
                    path:"/view/budget/:budgetId", 
                    element: loggedInUser? <SingleBudget loggedInUser={loggedInUser}></SingleBudget>:<Navigate to="/"></Navigate>,
                },
                {
                    path:"/edit/budget/:budgetId", 
                    element: loggedInUser? <BudgetForm loggedInUser={loggedInUser}></BudgetForm>:<Navigate to="/"></Navigate>,
                },
                {
                    path:"/add/budget", 
                    element: loggedInUser? <BudgetForm loggedInUser={loggedInUser}></BudgetForm>:<Navigate to="/"></Navigate>,
                }, 
                {
                    path:"/view/:transactionId",
                    element: loggedInUser? <SingleTransaction loggedInUser={loggedInUser}></SingleTransaction>:<Navigate to="/"></Navigate>,
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
