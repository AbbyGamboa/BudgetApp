
function Article({image, title, text}){
    return (
        <div className="border border-black p-4 rounded-4 text-center hover" style={{width:"350px", height: '320px'}}>
            <div style={{width:"300px", height: '200px'}}>
                <img src={image} alt="" style={{width:"100%", height:'100%'}} className="rounded-4 p-1"/>
            </div>
            

            <h4 className="m-1 ">{title}</h4>
            <p>{text}</p>
        </div>
    )
}

export default Article