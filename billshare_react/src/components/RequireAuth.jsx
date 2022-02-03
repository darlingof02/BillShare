import React, {Component} from "react";
import {Route, Navigate} from 'react-router-dom';
import AuthenticationService from "../api/AuthenticationService";

function RequireAuth({children}){
    if(AuthenticationService.isUserLogin())
        return children;
    return <Navigate to="/login"/>;
}

export default RequireAuth;