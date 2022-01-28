import React, {Component} from "react";
import {BrowserRouter as Router, Route, Routes, Link} from 'react-router-dom';
import SignUpComponent from "./SignUpComponent";
import axios from "axios"
import {API_URL} from "../constant"
import {Field, Form} from "formik"
class TestFileUpComponent extends Component {

    handleSubmit = (values)=>{
        console.log(`${API_URL}/test/test_up`)
        axios.post(`${API_URL}/test/test_up`,values)
        .then(response=>console.log(response))
        .catch(error=>alert(error))
    }

    render(){
        return (
        // <form method="post" onSubmit={this.handleSubmit} encType="multipart/form-data">
        //     选择要上传的文件：<input type="file" name="file"/>
        //     <input type="submit" value="submit"/>
        // </form>
        <form method="post" encType="multipart/form-data" action="http://localhost:8080/test/test_up">
            
            <input type="text" name="username"/>
            {/* <input type="password" name="password"/> */}
            <input type="file" name="avatar"/>

            <input type="submit" value="submit"/>
        </form>
        )
    }
}
export default TestFileUpComponent