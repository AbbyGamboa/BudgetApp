import { Outlet } from "react-router-dom";
import NavBar from "./NavBar";

function Layout({loggedInUser}){
    return (
        <>
        <NavBar loggedInUser={loggedInUser}></NavBar>
        <Outlet></Outlet>
        </>
        
    );
}

export default Layout;