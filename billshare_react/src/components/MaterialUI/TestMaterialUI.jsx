import React, {Component, useState} from "react";
import {API_URL} from '../../constant'
import { Container, Button, ButtonGroup, Typography, TextField, Radio, RadioGroup, FormControlLabel, FormControl, FormLabel, Grid, Paper } from "@mui/material";
import { makeStyles } from "@mui/styles";
// import { makeStyles } from "@mui/material";
import AdjustIcon from '@mui/icons-material/Adjust';
import AcUnitIcon from '@mui/icons-material/AcUnit';
import notes from './data/db.json'
import Notes from "./Notes";
import NoteCard from "./NoteCard";
import FooterComponent from "../FooterComponent";

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

            {/* <FooterComponent/> */}
        </Container>
        
    )

}


export default TestMaterialUI;