import { useNavigate } from "react-router-dom";
import { useEffect, useState} from "react";
import Modal from 'react-bootstrap/Modal';
import BudgetCategoryForm from "./BudgetCategoryForm";
import DeleteBCConfirm from "./DeleteBCConfirm";
import { useParams } from "react-router-dom";
import BudgetChart from "../BudgetChart";


function BudgetCategory({loggedInUser, setBudgetTotal, budgetcategories, setBudgetCategories}){
    const navigate = useNavigate()
    const {budgetId} = useParams()

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

                        setBudgetTotal(total.toFixed(2));
                    } else{
                        setBudgetTotal(0)
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
                <div className=" w-50 p-4 border border-blue rounded m-3"> 
                    <h3>Categories: </h3>
                    {budgetcategories.map(budgetCat => 
                    <div key={budgetCat.budgetCategoryId} className="d-flex justify-content-between border border-blue rounded p-2 m-3">
                        <h4 className="text-center p-1">{budgetCat.category.name}</h4>
                        
                        <div className="p-1">
                            <i className="fa-solid fa-pen-to-square m-2 iconHover" onClick={() => setActiveModalItem(budgetCat)}></i>
                            <i className="fa-solid fa-circle-minus m-2 deleteHover" onClick={()=>setdeleteItem(budgetCat)}></i>
                        </div>
                       
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