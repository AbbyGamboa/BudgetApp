import { Outlet } from "react-router-dom";
function UserLayout({loggedInUser}){
    return(
        <>
        <h1>Welcome</h1>
        <Outlet></Outlet>
        </>
        
    );
}

export default UserLayout;