import { Link } from "react-router-dom";
function LargeSection({loggedInUser}){
    return (
        <div className="m-5 d-flex justify-content-between">
            <div className="w-50 p-2 m-5">
                <h3>Join today</h3>

                <p>Start understanding your finances with 1st step.</p>
                {!loggedInUser && <Link className='btn btn-primary m-1' to="/user/signup">Sign up</Link>}
            
            </div>

            <div className="w-50 p-2">
                <div className="d-flex m-1 p-1">
                    <div className="m-1 p-2 text-center rounded" style={{width:'50px', height:'50px', backgroundColor:'rgba(12, 84, 184)'}}>
                        <i className="fa-solid fa-hand-holding-dollar" style={{fontSize:'30px', color:'white'}}></i>
                    </div>
                    <div className="m-1">
                        <h3>Budgets</h3>
                        <p>Plan budgets with custom or preset categories </p>
                    </div>
                    
                </div>

                <div  className="d-flex m-1 p-1">
                    <div className="m-1 p-2 text-center rounded" style={{width:'50px', height:'50px', backgroundColor:'rgba(12, 84, 184)'}}>
                        <i className="fa-solid fa-credit-card" style={{fontSize:'30px', color:'white'}}></i>
                    </div>
                    <div className="m-1">
                        <h3>Transactions</h3>
                        <p>Track your transactions per each of your accounts. </p>
                    </div>
                    
                </div>

                <div className="d-flex m-1 p-1">
                    <div className="m-1 p-2 text-center rounded" style={{width:'50px', height:'50px', backgroundColor:'rgba(12, 84, 184)'}}>
                        <i className="fa-solid fa-newspaper" style={{fontSize:'30px', color:'white'}}></i>
                    </div>
                    <div className="m-1">
                        <h3>Articles</h3>
                        <p>Get informed on financial topics effecting you.</p>
                    </div>
                </div>
            </div>
        </div>
   )
}

export default LargeSection;