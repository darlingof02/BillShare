import { DeleteOutline } from "@mui/icons-material"
import { Card, CardContent, CardHeader, CardMedia, IconButton, Typography } from "@mui/material"
import React from "react"
import Notes from "./Notes"


export default function NoteCard( {note} ){
    return (
        <Card>
            <CardHeader
                action={
                    <IconButton>
                        <DeleteOutline />
                    </IconButton>
                }
                title = {note.title}
                subheader = {note.category}
            ></CardHeader>
        <CardContent>
            <Typography variant="body2" color="textSecondary">{note.details}</Typography>


        </CardContent>
        <CardMedia></CardMedia>
        
        </Card>
    )
}