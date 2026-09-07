
function Article({title, text}){
    return (
        <div className="border border-black p-3 rounded">
            <img src="https://placehold.co/250x150" alt="" />

            <h4>{title}</h4>
            <p>{text}</p>
        </div>
    )
}

export default Article