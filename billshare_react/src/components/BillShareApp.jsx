import React, {Component} from "react";
import {BrowserRouter as Router, Route, Routes, Link} from 'react-router-dom';
import SignUpComponent from "./SignUpComponent";
import TestFileUpComponent from "./TestFileUp";

class BillShareApp extends Component {
    render(){
        return (
            <div className="BillShareApp">
                {/* This is todo application<br/> */}
                <Router>
                    <Routes>
                        {/* <Route path="/login" element={<LoginComponent/>}/> */}
                        <Route path="/signup" element={<SignUpComponent/>}/>
                        <Route path="/test" element={<TestFileUpComponent/>}/>
                    </Routes>
                </Router>

            </div>
        )
    }
}


export default BillShareApp
