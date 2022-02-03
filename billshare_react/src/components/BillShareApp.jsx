import React, {Component} from "react";
import {BrowserRouter as Router, Route, Routes, Link} from 'react-router-dom';
import SignUpComponent from "./SignUpComponent";
import TestFileUpComponent from "./TestFileUp";
import FooterComponent from "./FooterComponent";
import HeaderComponent from "./HeaderComponent";
import LoginComponent from './LoginComponent';
import RequireAuth from "./RequireAuth";
import WelcomeComponent from './WelcomeComponent';
class BillShareApp extends Component {
    render(){
        return (
            <div className="BillShareApp">
                {/* This is todo application<br/> */}
                <Router>
                    <HeaderComponent/>
                    <Routes>
                        {/* <Route path="/login" element={<LoginComponent/>}/> */}
                        <Route path="/" element={<LoginComponent/>}/>
                        <Route path="/signup" element={<SignUpComponent/>}/>
                        <Route path="/login" element={<LoginComponent/>}/>
                        <Route path="/welcome/:name" element={<RequireAuth><WelcomeComponent/></RequireAuth>}/>

                        <Route path="/test" element={<TestFileUpComponent/>}/>
                    </Routes>
                    <FooterComponent/>
                </Router>
                    {/* <Route path="/" element={<LoginComponent/>}/>
                        <Route path="/login" element={<LoginComponent/>}/>
                        <Route path="/signup" element={<SignUpComponent/>}/>
                        <Route path="/welcome/:name" 
                            element={
                                <RequireAuth>
                                    <WelcomeComponent/>
                                </RequireAuth>}/>
                        <Route path="/todos/:id" element={<RequireAuth><TodoComponent /></RequireAuth>}/>
                        <Route path="/todos" element={<RequireAuth><ListTodosComponent /></RequireAuth>}/>
                        <Route path="/logout" element={<RequireAuth><LogoutComponent /></RequireAuth>}/>
                        <Route path="*" element={ <ErrorComponent /> } /> */}
                

            </div>
        )
    }
}


export default BillShareApp
