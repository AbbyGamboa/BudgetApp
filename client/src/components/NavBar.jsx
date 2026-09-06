import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';
import logo from '../assets/firstStepsLogo.png';

function NavBar({loggedInUser}){
    return (
       <>
      <Navbar bg="light" data-bs-theme="light">
        <Container>

          <Navbar.Brand href={loggedInUser? "/user/landing": "/"}>
            <img src={logo} alt="React Logo" className="logo p-1" width={60}/>
            1st Steps
            </Navbar.Brand>
          <Nav className="ms-0">
            <Nav.Link href="">Articles</Nav.Link>
            
            {!loggedInUser && <>
              <Link className='btn btn-primary m-1' to="/user/signup">Sign up</Link>
              <Link className='btn btn-primary m-1' to="/user/login">Login</Link>
            </>}
            {loggedInUser &&  <>
            <Nav.Link href="/view/budgets">Budgets</Nav.Link>
            <Nav.Link href="/view/accounts">Accounts</Nav.Link>
            <Link className='btn btn-primary m-1' to="/user/signout">Logout</Link>
            </>
            }
            
            
          </Nav>
          
        </Container>
      </Navbar>
    </>
    );
}

export default NavBar;