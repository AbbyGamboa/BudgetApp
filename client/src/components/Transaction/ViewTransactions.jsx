import { useState, useEffect } from "react";
import { useParams, Link} from "react-router-dom";
import Transaction from "./Transaction";
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import TransactionForm from "./TransactionForm";


function ViewTransactions({loggedInUser}){
    const[transactions, setTransactions] = useState([])
    const {accountId} = useParams();
    const [showCreate, setShowCreate] = useState(false);
    const [showEdit, setShowEdit] = useState(false);
    
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

    const handleShowCreate = () => setShowCreate(true);
    const handleCreateClose= () => setShowCreate(false);
   const [activeModalItem, setActiveModalItem] = useState(null);

    return(
        <>
        
        <button onClick={handleShowCreate}>Create Transaction</button>
        <Modal show={showCreate} onHide={handleCreateClose}>
            <Modal.Header closeButton>
                <Modal.Title>Modal heading</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <TransactionForm loggedInUser={loggedInUser} transactionId ={undefined}/>
                <Button variant="secondary" onClick={handleCreateClose}>Close </Button>
            </Modal.Body>
            
           
            
        </Modal>
        <h2>Transactions: </h2>
        
        <div className="d-flex">
        {transactions.map(transaction => 
        <div key ={transaction.transactionId} className="flex p-5">
        <Transaction transaction={transaction}/>
        <Link className="btn btn-primary m-1" to={`/view/${transaction.transactionId}`}>View</Link>
        <button onClick={() => setActiveModalItem(transaction)} className="btn btn-primary m-1" >Edit</button>
        
        </div>)}

        {activeModalItem && 
        <Modal show={true}>
            <Modal.Header closeButton>
                <Modal.Title>Modal heading</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <TransactionForm loggedInUser={loggedInUser} transactionId ={activeModalItem.transactionId} setActiveModalItem={setActiveModalItem}/>
                <Button variant="secondary" onClick={()=>setActiveModalItem(null)}>Close </Button>
            </Modal.Body>
            
        </Modal>}
        </div>
        
        
        </>
    

    );
}

export default ViewTransactions