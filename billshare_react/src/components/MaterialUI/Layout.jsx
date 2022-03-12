import { AddCircleOutlined, SubjectOutlined } from "@mui/icons-material";
import { Drawer, List, ListItem, ListItemIcon, ListItemText, Typography, AppBar, Toolbar, Container } from "@mui/material";
import { makeStyles, createStyles } from "@mui/styles";
import React from "react";
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { useLocation, useNavigate } from "react-router-dom";
import { styled } from '@mui/system';

const drawerWidth = 240

const useStyles = makeStyles((theme) =>
  createStyles(
    {
        page: {
            background: "#f9f9f9",
            width: '100%',
        },
        drawer: {
            width: drawerWidth
        },
        drawerPaper: {
            width: drawerWidth
        },
        root: {
            display: "flex"
        },
        active: {
            backgroundColor: "#f9f9f9",
            background: "#f9f9f9"
        },
        appBar: {
            width: `calc(100% - ${drawerWidth}px)`,
            marginLeft: drawerWidth,

        }
        
    }
  )
);

const theme = createTheme()

export default function Layout( {children} ){
    const Offset = styled('div')(({ theme }) => theme.mixins.toolbar);
    const classes = useStyles()
    const nav = useNavigate()
    const location = useLocation()
    const manuItem = [
        {
            text: "login",
            icon: <SubjectOutlined color="secondary"/>,
            path: "/login"
        },
        {
            text: "signup",
            icon: <AddCircleOutlined color="secondary"/>,
            path: "/signup"
        }
    ]
    
    return (
        
        <ThemeProvider theme = {theme}>
        <div className={classes.root}>
        <Drawer
            className={classes.drawer}
            variant="permanent"
            anchor="left"
            classes={{paper: classes.drawerPaper}}>
                
                <div>
                    <Typography variant="h4">4_Celery</Typography>
                </div>
                <List>

                    {manuItem.map(item=>(
                        <ListItem
                        key = {item.text}
                        button
                        onClick={()=>nav(item.path)}
                        className={location.pathname == item.path ? classes.active : null}
                        >
                            <ListItemIcon>{item.icon}</ListItemIcon>
                            <ListItemText primary={item.text}/>
                        </ListItem>
                        
                    ))}
                </List>

            </Drawer>

            <Container>
            <AppBar 
             
             position="fixed" 
             elevation={0}
             color="primary"
             style={{width:`calc(100% - ${drawerWidth}px)`, marginLeft:drawerWidth}}
            //  style={classes.appBar}
             >
                <Toolbar>
                    <Typography>Welcome to Homepage </Typography>
                </Toolbar>
            </AppBar>
            <Offset/>
            
            <div className={classes.page}>
                {children}
            </div>
            </Container>
        </div>
        </ThemeProvider>

    )
}