import Article from "./Landing/Article";
import background from '../assets/blueBackground.jpg';
import { useEffect, useState } from "react";
import LargeSection from "./Landing/LargeSection";

function Landing(){

    return (
        <>
            <img src={background} alt="" className="w-100" style={{ height: "200px", objectFit: "cover" }}/>
            <div className="d-flex justify-content-between m-5">
                <Article></Article>
                <Article></Article>
                <Article></Article>
            </div>

            <LargeSection></LargeSection>
        </>
    );
}

export default Landing;