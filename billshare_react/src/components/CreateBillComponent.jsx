import React, {Component} from "react";
import {API_URL} from '../constant'


class CreateBillComponent extends Component {
    constructor(props){
        super(props)
        this.state = {
            amount: 0,
            recipt: "",
            StartTime: null,
            debtors:[]
        }
    }

    render(){
        return(
        <div>
            <h1>create new bill</h1>
            <form action={`${API_URL}/create_bill`} 
                method = 'post'
                className = "form" 
                // onSubmit={formik.handleSubmit} 
                encType="multipart/form-data">
                    
                <fieldset>
                    <label>total amount:</label>
                    <input name = "total" type="number"></input>
                </fieldset>

                <fieldset>
                    <label>email:</label>
                    <input name = "email" type="email" placeholder = "email"></input>
                </fieldset>

        
                <fieldset>
                    <label>upload receipt:</label>
                    <input type="file" name="receipt" accept="image/png, image/jpeg, .docx, .pdf"></input>
                </fieldset>
                <button type="submit" className='btn-success'>Submit</button>
            </form>
        </div>
        )
    }


}

export default CreateBillComponent;