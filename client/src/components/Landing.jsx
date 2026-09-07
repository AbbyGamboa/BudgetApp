import Article from "./Landing/Article";
import background from '../assets/blueBackground.jpg';
import LargeSection from "./Landing/LargeSection";
import studentLoans from "../assets/studentLoans.jpg";
import creditScore from "../assets/creditScore.avif";
import taxes from "../assets/tax.jpg";

function Landing(){

    return (
        <>
            <img src={background} alt="" className="w-100" style={{ height: "200px", objectFit: "cover" }}/>
            <div className="d-flex justify-content-between m-5">
                <Article image ={studentLoans} title={"Student Loans and Debt"} text={"Types of loans and understanding debt"}></Article>
                <Article image={creditScore} title={"Credit and Credit Scores"} text={"What is credit and how to improve your score"}></Article>
                <Article image={taxes} title={"Income and tax"} text={"Understanding your paycheck"}></Article>
            </div>

            <LargeSection></LargeSection>
        </>
    );
}

export default Landing;