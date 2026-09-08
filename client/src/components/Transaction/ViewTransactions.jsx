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
            
        
            <div className="border border-blue rounded">
                <table className="table table-striped table-hover align-middle table-responsive mb-4">
                    <thead className="">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><i className="fa-solid fa-calendar transactionIcon ms-2 me-4"></i>Date</th>
                        <th scope="col"><i className="fa-solid fa-money-bill-1-wave  ms-2 me-4 transactionIcon"></i>Spent</th>
                        <th scope="col"><i className="fa-solid fa-store ms-2 me-4 transactionIcon"></i>Merchant</th>
                        <th scope="col"><i className="fa-solid fa-pen-ruler ms-2 me-4 transactionIcon"></i>Description</th>
                        <th scope="col"><i class="fa-solid fa-table-cells-large ms-2 me-4 transactionIcon"></i>Category</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody className="table-group-divider">
                    {transactions && transactions.map(transaction => 
                    <tr>
                        <Transaction transaction={transaction}/>
                        <td className="px-5">
                            <TransactionCategory loggedInUser = {loggedInUser} transactionId={transaction.transactionId}></TransactionCategory>
                        </td>
                        
                    <td className="mt-auto">
                        <Link onClick={() => setActiveModalItem(transaction)} className=""><i class="fa-solid fa-ellipsis-vertical"></i></Link>
                    </td>
                    </tr>
                )}
                </tbody>
                </table>
                
                

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