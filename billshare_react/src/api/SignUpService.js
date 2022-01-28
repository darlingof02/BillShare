import axios from "axios"

class SignUpService{

    register(userInfo){
        console.log(userInfo)
        return axios.post("/test/test_up",userInfo)
    }


    testupload(picture){
        return axios.post("/test/test_up",picture)
    }
}
export default new SignUpService();