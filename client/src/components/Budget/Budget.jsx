import { Link } from "react-router-dom";
function Budget({income, budgetId}){
    return(
        <div>
            <h3>Budget {budgetId}: </h3>
            Start total: ${income}
            <Link className="btn btn-primary m-1" to={`/view/budget/${budgetId}`}>View</Link>
        </div>
    );
}

export default Budget;