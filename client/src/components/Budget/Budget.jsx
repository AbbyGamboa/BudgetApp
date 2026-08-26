function Budget({income, budgetId}){
    return(
        <div>
            <h3>Budget {budgetId}: </h3>
            Start total: ${income}
        </div>
    );
}

export default Budget;