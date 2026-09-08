import { useState, useEffect } from "react";
import { useParams, Link} from "react-router-dom";
import Transaction from "./Transaction";
import Modal from 'react-bootstrap/Modal';
import TransactionForm from "./TransactionForm";
import TransactionCategory from "../TransactionCategory/TransactionCategory";


function ViewTransactions({loggedInUser}){
    const[transactions, setTransactions] = useState([])
    const {accountId} = useParams();
    const [showCreate, setShowCreate] = useState(false);
    
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
       
        
        <Modal show={showCreate} onHide={handleCreateClose}>
            <Modal.Header closeButton>
            </Modal.Header>
            <Modal.Body>
                <TransactionForm loggedInUser={loggedInUser} transactionId ={undefined} setActiveModalItem={setActiveModalItem} handleCreateClose={handleCreateClose}/>
            </Modal.Body>
            
        </Modal>

        <div className="border border-blue rounded m-4 p-3">
             <div className="d-flex justify-content-between m-3">
                <h2 >Manage Transactions: </h2>
                <button  className="btn border-black m-2" onClick={handleShowCreate}>Create Transaction</button>
            </div>
            
        
            <div className="grid-container">
                {transactions && transactions.map(transaction => 
                <div key ={transaction.transactionId} className="d-flex flex-column grid-item p-3 border border-black rounded">
                    <div className="m-1">
                        <Transaction transaction={transaction}/>
                        <TransactionCategory loggedInUser = {loggedInUser} transactionId={transaction.transactionId}></TransactionCategory>
                    </div>
                

                    <div className="mt-auto">

                        <button onClick={() => setActiveModalItem(transaction)} className="btn btn-primary m-1" >Edit</button>
                    </div>
               
                
                </div>)}

                {activeModalItem && 
                <Modal show={true} onHide={handleCreateClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Update Transaction</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <TransactionForm loggedInUser={loggedInUser} transactionId ={activeModalItem.transactionId} setActiveModalItem={setActiveModalItem} handleCreateClose={handleCreateClose}/>
                    </Modal.Body>
                    
                </Modal>}
            </div>

        </div>
            
        </>
    

    );
}

export default ViewTransactions