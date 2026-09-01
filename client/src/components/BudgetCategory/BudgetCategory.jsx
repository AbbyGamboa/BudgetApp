import { useNavigate } from "react-router-dom";
import { useEffect, useState} from "react";
import { Link } from "react-router-dom";
import Modal from 'react-bootstrap/Modal';
import BudgetCategoryForm from "./BudgetCategoryForm";
import Button from "react-bootstrap/esm/Button";

function BudgetCategory({budgetId, loggedInUser}){

    const[budgetcategories, setBudgetCategories] = useState([])
    const [activeModalItem, setActiveModalItem] = useState(null);
    

    useEffect(()=>{
            const doFetch = async () => {
                const response = await fetch("http://localhost:8080/api/budgetcategory/"+budgetId, {
                    headers:{
                            "Authorization": `Bearer ${loggedInUser.token}`
                    }
                })
                const payload = await response.json();
                setBudgetCategories(payload)
            }
            doFetch()
        }, [])
    const [showCreate, setShowCreate] = useState(false);
    const handleShowCreate = () => setShowCreate(true);
    const handleCreateClose= () => setShowCreate(false);

    return (
        <>
        <button onClick={handleShowCreate}>Add category to budget</button>
        <Modal show={showCreate} onHide={handleCreateClose}>
            <Modal.Header closeButton>
                <Modal.Title>Add category to budget</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <BudgetCategoryForm loggedInUser={loggedInUser} activeModalItem={activeModalItem} setActiveModalItem={setActiveModalItem}/>
                <Button variant="secondary" onClick={handleCreateClose}>Close </Button>
            </Modal.Body>
            
            
        </Modal>
        <h1>Categories: </h1>
        {budgetcategories.map(budgetCategory=> <div key={budgetCategory.budgetCategoryId}>
            <h2>{budgetCategory.category.name}:</h2>
            <h3>Dedicated amount from income: ${Number(budgetCategory.percentage).toFixed(2)}</h3>
            <button className="btn btn-primary m-1" onClick={() => setActiveModalItem(budgetCategory)}>Edit amount</button>
        </div>)}

       {activeModalItem && 
        <Modal show={true}>
            <Modal.Header closeButton>
                <Modal.Title>Modal heading</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <BudgetCategoryForm loggedInUser={loggedInUser} activeModalItem={activeModalItem} setActiveModalItem={setActiveModalItem}/>
                <Button variant="secondary" onClick={()=>setActiveModalItem(null)}>Close </Button>
            </Modal.Body>
            
        </Modal>}
        </>
        
    );
}

export default BudgetCategory;