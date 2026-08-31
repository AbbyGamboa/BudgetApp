import { Link } from "react-router-dom";
function Category({category}){
    return(
        <>
         <h1>{category.name}</h1>
         <Link className="btn btn-danger" to={`/delete/${category.categoryId}`}>Delete</Link>
        </>
       
        
    );

}

export default Category;