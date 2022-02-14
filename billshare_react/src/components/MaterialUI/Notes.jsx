import React, { useEffect, useState } from "react"
import data from './data/db.json'
import { Container, Grid, Paper } from "@mui/material";
import NoteCard from "./NoteCard";

export default function Notes() {
    const [notes, setNotes] = useState(data.notes)
    return (
        <Container>
        <Grid container spacing={2}>
            {data.notes.map(note => (
                <Grid item md={4} xs={12} sm={6} key = {note.id}>
                    <NoteCard note = {note}></NoteCard>
                </Grid>
            ))}
            
        </Grid>
        </Container>
    
    )
}