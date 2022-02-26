import React, {Component} from "react";
import {API_URL} from '../api/constant'
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";


function CreateBillComponent() {
    const [state, setState] = useState({
        totalAmount: 0,
        debtors: [],
        type: '',
        comment: '',
        receipt: null
    });
    const navigate = useNavigate();
    const handleSubmit = (event) => {
        event.preventDefault();
        const form = document.forms[0];
        // console.log(form.children)
        const billFormData = new FormData(form);
        console.log(billFormData);

        // billFormData.append()
        // axios.post(`${API_URL}/create_bill`,billFormData,{headers:{'content-type': 'multipart/form-data'}})
        //     .then(navigate(-1))
    };


    return (
        <div>
            <h1>create new bill</h1>

            <form
                // action={`${API_URL}/create_bill`} 
                // method = 'post'
                // className = "form" 
                // encType="multipart/form-data"
                onSubmit={handleSubmit}
            >

                <fieldset>
                    <label>total amount:</label>
                    <input name="total" type="number"></input>
                </fieldset>

                <fieldset>
                    <label>debtor1 email:</label>
                    <input name="debtor[0][email]" type="email" placeholder="debtor1email"></input>

                    <label>debtor1 amount:</label>
                    <input name="debtor[0][amount]" type="number" placeholder="amount"></input>
                </fieldset>

                <fieldset>
                    <label>type:</label>
                    <input name="type" type="text" placeholder="type"></input>
                </fieldset>

                <fieldset>
                    <label>comment:</label>
                    <input name="comment" type="text" placeholder="comment"></input>
                </fieldset>


                <fieldset>
                    <label>upload receipt:</label>
                    <input type="file" name="receipt" accept="image/png, image/jpeg, .docx, .pdf"></input>
                </fieldset>
                <button type="submit" className='btn-success'>Submit</button>
            </form>
        </div>
    );
}

export default CreateBillComponent;