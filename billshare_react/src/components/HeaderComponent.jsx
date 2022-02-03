import React, {Component} from "react";
import {Link} from 'react-router-dom'
import AuthenticationService from "../api/AuthenticationService";
import { useNavigate } from 'react-router-dom';

export const withRouter = (Component) => {
    const Wrapper = (props) => {
        const navigate = useNavigate();
        return <Component history={navigate} {...props}/>
    };
    return Wrapper;
};

class HeaderComponent extends Component {
    render(){
        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <a href="https://baidu.com" className="navbar-brand">4_Celery</a>
                    <ul className="navbar-nav">
                        {AuthenticationService.isUserLogin() && <li><Link className="nav-link" to="./welcome/yuninx1">Home</Link></li>}
                        {AuthenticationService.isUserLogin() && <li><Link className="nav-link" to="./todos">Todos</Link></li>}
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                        {!AuthenticationService.isUserLogin() && <li><Link className="nav-link" to="./login">Login</Link></li>}
                        {AuthenticationService.isUserLogin() && <li><Link className="nav-link" to="./logout" onClick={AuthenticationService.logout}>Logout</Link></li>}
                        {!AuthenticationService.isUserLogin() && <li><Link className="nav-link" to="./signup">Sign up</Link></li>}
                    </ul>
                </nav>
            </header>
        )
    }
}
export default withRouter(HeaderComponent);