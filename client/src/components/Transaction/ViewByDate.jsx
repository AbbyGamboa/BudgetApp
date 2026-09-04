import { useParams } from "react-router-dom";
import { useState} from "react";
import Transaction from "./Transaction";
import { Link } from "react-router-dom";
import TransactionCategory from "../TransactionCategory/TransactionCategory";
import ViewCategoryByUser from "../Category/ViewCategoryByUser";

function ViewByDate({loggedInUser}){

    const{accountId} = useParams();
    const[transactions, setTransactions] = useState([])
    const[errors, setErrors] = useState([])
    const[start, setStart] = useState("")
    const[end, setEnd] = useState("")
    const[categoryId, setCategoryId] = useState()

    async function handleOnlyDates(event){
        setErrors([])
        event.preventDefault();

        const response = await fetch(
            `http://localhost:8080/api/transaction/date/${accountId}?start=${start}&end=${end}`,
            {
                headers: {
                    Authorization: `Bearer ${loggedInUser.token}`
                }
            }
        );
        console.log(response);
        const payload = await response.json()

        if (response.status >= 200 && response.status < 300) {
            
            console.log(payload.payload);
            setTransactions(payload.payload);
        } else {
            setErrors(payload)
        }

    }

    async function handleWithCat(event){
        setErrors([])
        event.preventDefault();

        const response = await fetch(
            `http://localhost:8080/api/transactioncategory/category/${categoryId}?start=${start}&end=${end}`,
            {
                headers: {
                    Authorization: `Bearer ${loggedInUser.token}`
                }
            }
        );
        const payload = await response.json()

        if (response.status >= 200 && response.status < 300) {
            setTransactions(payload);
            setShowTrans(true)
        } else {
            setErrors(payload)
        }

    }

    const [showTrans, setShowTrans] = useState(true);
    const hideTrans = () => setShowTrans(false);

    const[withCat, setWithCat] = useState(false)

    return(
        <>
        <form onSubmit={withCat? handleWithCat:handleOnlyDates} className="flex-column m-5" onReset={hideTrans}>
            <h3>View by date:</h3>
            <label htmlFor="start" className="m-1">Start: </label>
            <input type="date" name="start" id="start" onChange={(event) => setStart(event.target.value)} className="m-1" />

            <label htmlFor="end" className="m-1">End: </label>
            <input type="date" name="end" id="end" onChange={(event) => setEnd(event.target.value)} className="m-1"/>

            { withCat &&
                <div>
                <label htmlFor="categoryId">Category:</label>
                <select name="categoryId"  id="categoryId" value={categoryId} onChange={(event)=> {setCategoryId(event.target.value)}}>
                        <option value="">Select Category</option>
                        <ViewCategoryByUser loggedInUser={loggedInUser}></ViewCategoryByUser>
                </select>
            </div>
            }

            <button type="submit" className="btn btn-secondary m-1">Check</button>
            <button type="reset" className="btn btn-danger">Reset</button>

            <label htmlFor="withCat">Check with category</label>
            <input type="checkbox" name="withCat" id="withCat" onChange={(event)=>setWithCat(!withCat)}/>

        </form>
        {!withCat && showTrans && transactions.map(transaction => <div key ={transaction.transactionId} className="flex p-5">
        <Transaction transaction={transaction}/>
        <TransactionCategory loggedInUser={loggedInUser} transactionId={transaction.transactionId}></TransactionCategory>
        <Link className="btn btn-primary" to={`/view/${transaction.transactionId}`}>View</Link>
        </div>)}
    
         { withCat &&
            showTrans && transactions.map(transaction => <div key ={transaction.transaction.transactionId} className="flex p-5">
        <Transaction transaction={transaction.transaction}/>
        <TransactionCategory loggedInUser={loggedInUser} transactionId={transaction.transaction.transactionId}></TransactionCategory>
        <Link className="btn btn-primary" to={`/view/${transaction.transactionId}`}>View</Link>
        </div>)
        }
        {errors.length > 0 ?
                    <ul>{errors.map(error => <li key={error}>{error}</li>)}</ul>
                    : null
                }
    
        </>


    
    );
}

export default ViewByDate;