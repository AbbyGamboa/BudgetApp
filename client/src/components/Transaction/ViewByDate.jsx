import { useParams } from "react-router-dom";
import { useState} from "react";
import ViewCategoryByUser from "../Category/ViewCategoryByUser";
import TransactionChart from "../TransactionChart";

function ViewByDate({loggedInUser}){

    const{accountId} = useParams();
    const[transactions, setTransactions] = useState([])
    const[errors, setErrors] = useState([])
    const[start, setStart] = useState("")
    const[end, setEnd] = useState("")
    const[categoryId, setCategoryId] = useState("")

    async function handleOnlyDates(){
        setErrors([])

        const response = await fetch(
            `http://localhost:8080/api/transaction/date/${accountId}?start=${start}&end=${end}`,
            {
                headers: {
                    Authorization: `Bearer ${loggedInUser.token}`
                }
            }
        );
    
        const payload = await response.json()
        if (response.status >= 200 && response.status < 300) {
            console.log(response)
            
            console.log(payload.payload);
            setTransactions(payload.payload);
        } else{
            setErrors(payload)
        }

    }

    async function handleWithCat(){
        setErrors([])

        const response = await fetch(
            `http://localhost:8080/api/transactioncategory/category/${categoryId}?start=${start}&end=${end}`,
            {
                headers: {
                    Authorization: `Bearer ${loggedInUser.token}`
                }
            }
        );

        if (response.status >= 200 && response.status < 300) {
            const payload = await response.json()
            const transactionData = payload.map(item => item.transaction);

            setTransactions(transactionData);
        }

    }

    const [showTrans, setShowTrans] = useState(true);
    function hideTrans(){
        setShowTrans(false)
        setErrors([])
    }

    const[withCat, setWithCat] = useState(false)

    function submit(event){
        event.preventDefault();

        setTransactions([]);
        setShowTrans(true)
        if(withCat){
            handleWithCat()
        } else{
            handleOnlyDates()
        }
    }

    return(
        <>
        <form onSubmit={submit} className="flex-column" onReset={hideTrans}>
            <div className="border border-blue p-3 m-4 rounded">
                <div className="d-flex justify-content-between">
                    <h3>View by date:</h3>
                    <button type="button" name="withCat" id="withCat" className="btn border border-black"onClick={()=>setWithCat(!withCat)}>Check with{withCat?"out":""} category</button>    
                </div>

                <div className="d-flex justify-content-between m-3">
                    <>
                        <div className="border border-blue rounded p-2">
                            <label htmlFor="start" className="m-1">Start: </label>
                            <input type="date" name="start" id="start" onChange={(event) => setStart(event.target.value)} className="m-1" />
                        </div>
                        
                        <div className="border border-blue rounded p-2">
                            <label htmlFor="end" className="m-1">End: </label>
                            <input type="date" name="end" id="end" onChange={(event) => setEnd(event.target.value)} className="m-1"/>
                        </div>
                        
                        {withCat &&
                        <div className="border border-blue rounded p-2">
                        <label htmlFor="categoryId" className="m-1">Category:</label>
                        <select name="categoryId"  id="categoryId" value={categoryId} onChange={(event)=> {setCategoryId(event.target.value)}}>
                                <option value="">Select Category</option>
                                <ViewCategoryByUser loggedInUser={loggedInUser}></ViewCategoryByUser>
                        </select>
                        </div>}
                    </>
                    
                    <div >
                        <button type="submit" className="btn btn-primary m-2">Check</button>
                        <button type="reset" className="btn btn-danger m-2">Reset</button>
                    </div>

                </div>

            </div>
            
            
        </form>

        {showTrans && transactions.length > 0 && (
            <TransactionChart
                transactions={transactions}
            />
        )}
        

        {errors.length > 0 ?
        
                    <div className="border border-blue rounded p-3 m-4"> 
                        {errors.map(error => <div className="d-flex justify-content-center">
                            <i className="fa-solid fa-triangle-exclamation text-danger p-1 larger"></i>
                            <h3 key={error} className="text-danger">{error}</h3></div>
                        )}
                    </div>
                    
                    : null
                }
    
        </>


    
    );
}

export default ViewByDate;