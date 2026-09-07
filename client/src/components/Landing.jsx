import Article from "./Landing/Article";
import background from '../assets/blueBackground.jpg';
import LargeSection from "./Landing/LargeSection";
import studentLoans from "../assets/studentLoans.jpg";
import creditScore from "../assets/creditScore.avif";
import taxes from "../assets/tax.jpg";

function Landing({loggedInUser}){

    return (
        <>
            <div className="background-blue w-100 d-flex" style={{ height: "200px", objectFit: "cover" }}>
                <h1 className="mt-auto mb-1 ms-5">Take your first steps today...</h1>
            </div>
            
            <div className="d-flex justify-content-between m-5">
                <Article image ={studentLoans} title={"Student Loans and Debt"} text={"Types of loans and understanding debt"}></Article>
                <Article image={creditScore} title={"Credit and Credit Scores"} text={"What is credit and how to improve your score"}></Article>
                <Article image={taxes} title={"Income and tax"} text={"Understanding your paycheck"}></Article>
            </div>

            <LargeSection loggedInUser={loggedInUser}></LargeSection>
        </>
    );
}

export default Landing;