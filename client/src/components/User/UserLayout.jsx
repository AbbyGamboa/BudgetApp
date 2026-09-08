import { Outlet } from "react-router-dom";
function UserLayout({loggedInUser}){
    return(
        <>
        <Outlet></Outlet>
        </>
        
    );
}

export default UserLayout;