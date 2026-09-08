import { PieChart } from '@mui/x-charts/PieChart';
import { useNavigate } from "react-router-dom";
import { useEffect, useState} from "react";

function BudgetChart({budgetId, loggedInUser}){
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
                    const payload = await response.json();
                        if(response.status>= 200 && response.status <= 300){
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
                    } else{
                        navigate("/view/budgets")
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