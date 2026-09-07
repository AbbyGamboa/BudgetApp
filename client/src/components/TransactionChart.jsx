import { LineChart } from "@mui/x-charts/LineChart";


function TransactionChart({ transactions, withCat }) {

    // Convert your transactions into chart data
    const chartTransactions = transactions.map(item => {
        const transaction = withCat ? item.transaction : item;

        return {
            date: transaction.date,
            amount: Number(transaction.amount), 
        };
    });

    // Sort transactions by date
    chartTransactions.sort((a, b) =>
        new Date(a.date) - new Date(b.date)
    );

    const dates = chartTransactions.map(transaction => transaction.date);
    const amounts = chartTransactions.map(transaction => transaction.amount);

    return (
        <div className="m-4">
            <h3>Transactions Over Time</h3>

            <LineChart
                xAxis={[
                    {
                        scaleType: "point",
                        data: dates
                    }
                ]}
                series={[
                    {
                        data: amounts,
                        label: "Amount",
                        showMark: true
                    }
                ]}
                grid={{ vertical: true, horizontal: true }}
                height={400}
            />
        </div>
    );
}

export default TransactionChart;