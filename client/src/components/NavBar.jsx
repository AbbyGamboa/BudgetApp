import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';
function NavBar({loggedInUser}){
    return (
       <>
      <Navbar bg="light" data-bs-theme="light">
        <Container>
          <Navbar.Brand href="#home">Navbar</Navbar.Brand>
          <Nav className="ms-0">
            <Nav.Link href="">Loans</Nav.Link>
            <Nav.Link href="">Student Discounts</Nav.Link>
            <Nav.Link href="">Savings</Nav.Link>
            {!loggedInUser && <>
              <Link className='btn btn-primary m-1' to="/user/signup">Sign up</Link>
              <Link className='btn btn-primary m-1' to="/user/login">Login</Link>
            </>}
            {loggedInUser &&  <>
            <Nav.Link href="/view/accounts">Account</Nav.Link>
            <Link className='btn btn-primary m-1' to="/user/signout">Logout</Link>
            <Link className='btn btn-primary m-1' to="/user/connect/bank">Connect Bank</Link>
            </>
            }
            
            
          </Nav>
          
        </Container>
      </Navbar>
    </>
    );
}

export default NavBar;