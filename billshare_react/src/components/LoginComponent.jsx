import { Formik, Field, Form } from "formik";
import React, {Component} from "react";
import { useNavigate } from 'react-router-dom'
import AuthenticationService from "../api/AuthenticationService";
class LoginComponent extends Component {
    constructor(props){
        super(props)
        this.state = {
            email: 'yuninx1',
            password: '',
            loginSuccessful: false,
            loginFailed: false
        }
        this.handleChange = this.handleChange.bind(this)
        this.loginState = this.loginState.bind(this)
        
    }

    handleChange (event){
        this.setState({[event.target.name]:event.target.value})
    }
    loginState(){

        console.log(this.state)
        AuthenticationService.executeJWTAuthService(this.state.email,this.state.password)
            .then(response => {
                AuthenticationService.registerSuccessfulLoginForJWT(this.state.email, response.data.token)
                this.props.navigate(`/welcome/${this.state.email}`) // newly added v6
            }).catch(error => {
                this.setState({loginSuccessful : false,loginFailed : true})
            })
    }

    render(){
        return (
            
            <div className="Login">
                <h1>Login</h1>

                <div className="container">
                    {this.state.loginSuccessful && <div>Login Successful!</div>}
                    {this.state.loginFailed && <div className="alert alert-warning">Login Failed!</div>}
                    <Formik
                    onSubmit={this.loginState}
                    enableReinitialize={true}
                    initialValues={this.state}
                    >
                        <Form>
                            <fieldset>
                                <label>email:&nbsp;&nbsp;&nbsp;</label>
                                <Field className="form" type="text" name="email" placeholder="email" onChange= {this.handleChange}></Field>
                            </fieldset>
                            <fieldset>
                                <label>&nbsp;Password:&nbsp;&nbsp;</label>
                                <Field className="form" type="password" name="password" onChange= {this.handleChange}></Field>
                            </fieldset>
                            <button className="btn btn-success">Login</button>
                        </Form>
                    </Formik>
                </div>
            </div>
        )
    }
}

// newly added v6
function WithNavigate(props) {
    let navigate = useNavigate()
    return <LoginComponent {...props} navigate={navigate} />
}
export default WithNavigate