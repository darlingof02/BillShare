import React, {Component} from "react";
import {useParams, Link} from 'react-router-dom'
import WelcomePageBeanService from '../api/WelcomePageService.js'
class WelcomeComponent extends Component{
    constructor(props){
        super(props)
        this.state ={welcomeMessage:""}
        this.retrieveWelcomeMessage = this.retrieveWelcomeMessage.bind(this)
        this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this)
        this.handleError = this.handleError.bind(this)

    }
    render(){
        let {name} = this.props.params
        return ( 
            <div>
                <h1>Welcome!</h1>
                <div className="container">
                    Welcome! {name}, 
                    you can add new bills here <Link to = "/newbill">here</Link>
                </div>
                <div className="container">
                    Click <button onClick={this.retrieveWelcomeMessage} className="btn btn">here</button> to get a costomized welcome message 
                </div>
                <div className="container">
                    {this.state.welcomeMessage}
                </div>
            </div> // Why? Where‘s name？
        )
    }
    retrieveWelcomeMessage(){
        let {name} = this.props.params
        // console.log(name)
        WelcomePageBeanService.executeWelcomePageBeanService(name)
        .then(data=>this.handleSuccessfulResponse(data))
        .catch(error => this.handleError(error))
        // console.log("retrieved clicked")
    }
    handleSuccessfulResponse(response){
        this.setState({welcomeMessage:response.data.welcomeMessage})
    }
    handleError(error){
        let errorMessage = ''
        if(error.message)
            errorMessage+=error.message
        if(error.response && error.response.data)
            errorMessage+=error.response.data.message
        this.setState({welcomeMessage:errorMessage})
    }
}



// newly added v6
function WithParams(props) {
    let params = useParams()
    return <WelcomeComponent {...props} params={params} />
}
// export default WelcomeComponent
export default WithParams