import React, {Component, useState} from "react";
import {API_URL} from '../../api/constant'
import { Container, Button, ButtonGroup, Typography, TextField, Radio, RadioGroup, FormControlLabel, FormControl, FormLabel, Grid, Paper } from "@mui/material";
import { makeStyles } from "@mui/styles";
// import { makeStyles } from "@mui/material";
import AdjustIcon from '@mui/icons-material/Adjust';
import AcUnitIcon from '@mui/icons-material/AcUnit';

import Notes from "./Notes";
import TestWebSocket from "./TestWebSocket";
import SockJS from "sockjs-client";
import Stomp from "stompjs";



const useStyle = makeStyles({
    btn:{
        fontSiz: 50,
        backgroundColor: "#fefefa",
        "&:hover":{
            backgroundColor: "#adadad"
        }
    },
    field:{
        marginTop: 20,
        marginBottom: 20,
        display: "block"
    }
})
/*
import {createMuiTheme, ThemeProvider} from

const theme = {
    pallete:{
        parimary:{

        }
    }
}
<ThemeProvider theme = {theme}>
*/ 

// function connect() {
//     var socket = new SockJS('/gs-guide-websocket');
//     var stompClient = Stomp.over(socket);
//     stompClient.connect({}, function (frame) {
//         // setConnected(true);
//         console.log('Connected: ' + frame);
//         stompClient.subscribe('/topic/greetings', function (greeting) {
//             // showGreeting(JSON.parse(greeting.body).content);
//         });
//     });
// }


const TestMaterialUI = () => {
    const classes = useStyle()
    const [title, setTitle] = useState('')
    const [details, setDetails] = useState('')
    const [titleError, setTitleError] = useState(false)
    const [detailsError, setDetailsError] = useState(false)
    const [category, setCategory] = useState('China')
    const handleSubmit = (e)=>{
        setTitleError(false)
        setDetailsError(false)
        if(title=='')
            setTitleError(true)
        if(details=='')
            setDetailsError(true)  
        console.log(title+details)
    }

    return (
        <Container>
            <Typography variant = "h4" component="h2" gutterBottom color = "secondary"> 
                H1Header
            </Typography>
            <Button type = "submit" variant="contained" color = "primary">Submit</Button>
            <ButtonGroup color = "secondary">
                <Button startIcon ={<AdjustIcon color = "warning"/>}>One</Button>
                <Button variant="contained" disableElevation>Two</Button>
                <Button endIcon ={<AdjustIcon color = "success"/>}>Three</Button>
            </ButtonGroup>
            <br/>
            <AdjustIcon color = "warning"/>
            <AcUnitIcon color = "warning" fontSize="large"/>
            <AdjustIcon color = "success"/>
            <AdjustIcon color = "error"/>
            <AdjustIcon color = "warning"/>
            <br/>
            <TestWebSocket></TestWebSocket>
            <form noValidate autoComplete="off" onSubmit={handleSubmit}>
                <TextField margin="normal" variant = "outlined" label="Note title" color = "primary" required
                    onChange={(e)=>{setTitle(e.target.value)}} error={titleError}/>
                <TextField className={classes.field} variant = "outlined" label="Details" color = "primary" fullWidth
                    multiline rows={4} onChange={(e)=>{setDetails(e.target.value)}} error={detailsError}/>
                <FormControl display = "block">
                    <FormLabel>Country</FormLabel>
                    <RadioGroup value={category} onChange={(e)=>{setCategory(e.target.value)}}>
                        {/* <Radio value="male"></Radio>
                        <Radio value="female"></Radio> */}
                        <FormControlLabel value="China" control={<Radio />} label="中国"/>
                        <FormControlLabel value="Braitan" control={<Radio />} label="Braitan"/>
                    </RadioGroup>
                </FormControl>


                <Button className={classes.btn} type="submit" color="secondary" variant="contained">Sumbit</Button>

            </form>
            <Notes></Notes>

            {/* websocket part */}
            {/* <form class="form-inline">
                <div class="form-group">
                    <label for="connect">WebSocket connection:</label>
                    <Button variant="contained" disableElevation type="submit" onClick={connect}>Connect</Button>
                    {<Button type="submit" disabled="disabled" onClick={connect}>Disconnect
                    </Button>}
                </div>
            </form> */}


           

            
        </Container>
        
    )

}


export default TestMaterialUI;