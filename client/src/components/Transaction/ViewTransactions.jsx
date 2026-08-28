import { useState, useEffect } from "react";
import { useParams, Link} from "react-router-dom";
import Transaction from "./Transaction";
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import TransactionForm from "./TransactionForm";


function ViewTransactions({loggedInUser}){
    const[transactions, setTransactions] = useState([])
    const {accountId} = useParams();
    const [show, setShow] = useState(false);
    
    useEffect(()=>{
        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/transaction/account/${accountId}`, {
                headers:{
                     "Authorization": `Bearer ${loggedInUser.token}`
                }
            })
            const payload = await response.json();

            setTransactions(payload.payload)
        }
        doFetch()
    }, [])

    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);


    return(
        <>
        
        <button onClick={handleShow}>Create Transaction</button>
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Modal heading</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <TransactionForm loggedInUser={loggedInUser} transactionId ={undefined}/>
                <Button variant="secondary" onClick={handleClose}>Close </Button>
            </Modal.Body>
            
           
            
        </Modal>
        <h2>Transactions: </h2>
        
        <div className="d-flex">
        {transactions.map(transaction => <div key ={transaction.transactionId} className="flex p-5">
        <Transaction transaction={transaction}/>
        <Link className="btn btn-primary" to={`/view/${transaction.transactionId}`}>View</Link>
        </div>)}
        </div>
        
        
        </>
    

    );
}

export default ViewTransactions