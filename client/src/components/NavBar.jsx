import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
function NavBar(){
    return (
       <>
      <Navbar bg="light" data-bs-theme="light">
        <Container>
          <Navbar.Brand href="#home">Navbar</Navbar.Brand>
          <Nav className="ms-0">
            <Nav.Link href="">Loans</Nav.Link>
            <Nav.Link href="">Student Discounts</Nav.Link>
            <Nav.Link href="">Savings</Nav.Link>
            <Button variant="primary" >Sign up</Button>
            <Button variant="primary" >Login</Button>
          </Nav>
          
        </Container>
      </Navbar>
    </>
    );
}

export default NavBar;