
import React from 'react';
import { useFormik, Field, Form } from 'formik';
import SignUpService from '../api/SignUpService';
import TestFileUpComponent from './TestFileUp'
import { API_URL } from '../api/constant';
const SignupForm = () => {
  // Pass the useFormik() hook initial form values and a submit function that will
  // be called when the form is submitted
  const formik = useFormik({
    initialValues: {
        usernamne: '',
        password: '',
        email: '',
        nickname: '',
        avatar: null,

    },
    onSubmit: values => {
        // alert(JSON.stringify(values, null, 2));
        SignUpService.register(values).then(response=>alert(response)).catch(error=>console.log(error))
    },
  });
  return (
    <form action={`${API_URL}/create_user`} 
        method = 'post'
        className = "form" 
        // onSubmit={formik.handleSubmit} 
        encType="multipart/form-data">
            
        <fieldset>
            <label>firstnamne:</label>
            <input name = "firstname" type="text"
            placeholder = "input username" onChange={formik.handleChange}></input>
        </fieldset>

        <fieldset>
            <label>lastnamne:</label>
            <input name = "lastname" type="text"
            placeholder = "input username" onChange={formik.handleChange}></input>
        </fieldset>


        <fieldset>
            <label>password:</label>
            <input name = "password" type="password"
                onChange={formik.handleChange}></input>
        </fieldset>
        <fieldset>
            <label>email:</label>
            <input name = "email" type="email"
            placeholder = "email" onChange={formik.handleChange} value={formik.values.email}></input>
        </fieldset>
        <fieldset>
            <label>nickname:</label>
            <input name = "nickname" type="text"
            placeholder = "nickname" onChange={formik.handleChange}></input>
        </fieldset>
  
        <fieldset>
            <input type="file" name="avatar" accept="image/png, image/jpeg"></input>
        </fieldset>
        <button type="submit" className='btn-success'>Submit</button>
    </form>
    
  );
};


export default SignupForm;
