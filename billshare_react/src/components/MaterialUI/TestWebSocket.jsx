
import React from "react"
import '../../api/TestWebSocket/app.js'
import { Container, TextField, Button, ButtonGroup } from "@mui/material"




export default function TestWebSocket() {
    return (
        <div id="main-content" className="container">
            <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>

    <div className="row">
        <div className="col-md-6">
            <form className="form-inline">
                <div className="form-group">
                    <label for="connect">WebSocket connection:</label>
                    <button id="connect" className="btn btn-default" type="submit">Connect</button>
                    <button id="disconnect" className="btn btn-default" type="submit" disabled="disabled">Disconnect
                    </button>
                </div>
            </form>
        </div>
        <br></br>
        <br></br>
        <Container>
        <div className="col-md-6">
            <form >
                <div className="form-group">
                    <label for="name">What is your name?</label>
                    <TextField type="text" id="name" className="form-control" placeholder="Your name here..."/>
                </div>
                <Button id="send" variant="contained" className="btn btn-default" type="submit" color="primary">Send</Button>
            </form>
        </div>
        </Container>
        <br></br>
        <Container>
        <div className="col-md-6">
            <form >
                <div className="form-group">
                    <label for="private-name">What is your private name?</label>
                    <TextField type="text" id="private-name" className="form-control" placeholder="Your name here..."/>
                </div>
                <Button id="send-private" variant="contained" className="btn btn-default" type="submit" color="secondary">Send private name</Button>
            </form>
        </div>

        </Container>

        
    </div>
    <Container>
    <div className="row">
        <div className="col-md-12">
            <table id="conversation" className="table table-striped">
                <thead>
                <tr>
                    <th>Greetings</th>
                </tr>
                </thead>
                <tbody id="greetings">
                </tbody>
            </table>
        </div>
    </div>
    </Container>
</div>
    )
}