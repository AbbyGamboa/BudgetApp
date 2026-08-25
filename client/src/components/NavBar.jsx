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
              <Link className='btn btn-primary' to="/user/signup">Sign up</Link>
              <Link className='btn btn-primary' to="/user/login">Login</Link>
            </>}
            {loggedInUser &&  <Link className='btn btn-primary' to="/user/signout">Logout</Link>}
            
            
          </Nav>
          
        </Container>
      </Navbar>
    </>
    );
}

export default NavBar;