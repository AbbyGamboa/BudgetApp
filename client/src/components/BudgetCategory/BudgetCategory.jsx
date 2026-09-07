import { useNavigate } from "react-router-dom";
import { useEffect, useState} from "react";
import Modal from 'react-bootstrap/Modal';
import BudgetCategoryForm from "./BudgetCategoryForm";
import DeleteBCConfirm from "./DeleteBCConfirm";
import { useParams } from "react-router-dom";
import BudgetChart from "../BudgetChart";


function BudgetCategory({loggedInUser}){
    const navigate = useNavigate()
    const {budgetId} = useParams()


    const[budgetcategories, setBudgetCategories] = useState([])
    const [activeModalItem, setActiveModalItem] = useState(null);
    const [deleteItem, setdeleteItem] = useState(null);
    const [sum, setSum]= useState(0);
    

    useEffect(()=>{
            const doFetch = async () => {
                const response = await fetch("http://localhost:8080/api/budgetcategory/"+budgetId, {
                    headers:{
                            "Authorization": `Bearer ${loggedInUser.token}`
                    }
                })
                const payload = await response.json();
                    if(response.status>= 200 && response.status <= 300){
                        setBudgetCategories(payload)

                    if(payload != "Budget has no categories"){
                        let total = 0;
                        for (const budCat of payload) {
                            total += Number(budCat.percentage);
                        }

                        setSum(total.toFixed(2));
                    } else{
                        setSum(0)
                    }
                } else{
                    navigate("/view/budgets")
                }
                
            }
            
            doFetch()
            
        }, [budgetId])
    const [showCreate, setShowCreate] = useState(false);
    const handleShowCreate = () => setShowCreate(true);
    const handleCreateClose= () => setShowCreate(false);

    return (
        <>

        
        <Modal show={showCreate} onHide={handleCreateClose}>
            <Modal.Header closeButton>
                <Modal.Title>Add category to budget</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <BudgetCategoryForm loggedInUser={loggedInUser} handleCreateClose={handleCreateClose} activeModalItem={activeModalItem} setActiveModalItem={setActiveModalItem}/>
            </Modal.Body>
            
            
        </Modal>
         <div className="d-flex justify-content-between">
            <h1 className="p-2">Budget breakdown: </h1>
            <button className="btn border-black w-25" onClick={handleShowCreate}>Add category</button>
        </div>
        
    
        <div className="d-flex justify-content-center">
            {budgetcategories[0] === "Budget has no categories"? <div>
            <h4>No categories found</h4></div>: <>
                <div>
                <BudgetChart budgetId={budgetId} loggedInUser={loggedInUser}></BudgetChart>
            
                
                </div>
                <div className=" w-50 p-4"> 
                    <h3>Categories: </h3>
                    {budgetcategories.map(budgetCat => 
                    <div key={budgetCat.budgetCategoryId}>
                        {budgetCat.category.name}
                        <button className="btn btn-primary m-1" onClick={() => setActiveModalItem(budgetCat)}>Edit amount</button>
                        <button className="btn btn-danger" onClick={()=>setdeleteItem(budgetCat)}>Delete</button>
                    </div>
                    )}
                </div>
            </>
            
            }
            
        </div>
        

       {activeModalItem && 
        <Modal show={true}>
            <Modal.Header closeButton>
                <Modal.Title>Modal heading</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <BudgetCategoryForm loggedInUser={loggedInUser} activeModalItem={activeModalItem} setActiveModalItem={setActiveModalItem}/>
            </Modal.Body>
            
        </Modal>}

        {deleteItem && 
        <Modal show={true}>
            <Modal.Header closeButton>
                <Modal.Title>Delete Budget Category: {deleteItem.budgetCategoryId}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <DeleteBCConfirm deleteItem={deleteItem} setdeleteItem={setdeleteItem} loggedInUser={loggedInUser}></DeleteBCConfirm>
                
            </Modal.Body>
            
        </Modal>}
        </>
        
    );
}

export default BudgetCategory;