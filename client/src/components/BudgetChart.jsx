import { PieChart } from '@mui/x-charts/PieChart';
import { useNavigate } from "react-router-dom";
import { useEffect, useState} from "react";
import { Link } from 'react-router-dom';

function BudgetChart({budgetId, loggedInUser, showButton}){
    const navigate = useNavigate()

    const[budgetcategories, setBudgetCategories] = useState([])
    const [sum, setSum]= useState(0);

     useEffect(()=>{
                const doFetch = async () => {
                    const response = await fetch("http://localhost:8080/api/budgetcategory/"+budgetId, {
                        headers:{
                                "Authorization": `Bearer ${loggedInUser.token}`
                        }
                    })
                    
                        if(response.status>= 200 && response.status <= 300){
                            const payload = await response.json();
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
                    } 
                    
                }
                
                doFetch()
                
            }, [budgetId])

    const chartData = budgetcategories.map((budCat) => ({
        id: budCat.budgetCategoryId,
        value: Number(budCat.percentage).toFixed(2),
        label: budCat.category.name
    }));


    return (
        <>
        {budgetcategories && <>
            <p>Total Budget: ${sum}</p>
            {showButton && <Link className='glow' to={`/view/budget/${budgetId}`}>View Budget</Link>}
            
            <PieChart series={[
                {
                    data: chartData
                }
            ]}
            width={400}
            height={300}>
            
        </PieChart>
        
        </>}</>
    )
}

export default BudgetChart;