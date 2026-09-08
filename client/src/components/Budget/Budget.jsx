import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
function Budget({loggedInUser, name, budgetId}){
    

    return(
        <div className="grid-item m-4 border border-blue rounded p-3">
            <h4>Budget {budgetId}: </h4>
            <h4>Name: {name}</h4>

            <Link className="btn border-black m-1" to={`/view/budget/${budgetId}`}>View</Link>
            <Link className="btn border-black m-1" to={`/edit/budget/${budgetId}`}>Edit</Link>
        </div>
    );
}

export default Budget;