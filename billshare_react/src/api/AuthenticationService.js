import axios from 'axios'
import {API_URL} from './constant'
export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
class AuthenticationService {


    /**JWT AUTH */
    executeJWTAuthService(email,password){
        return axios.post(`${API_URL}/authenticate`,{email,password})
    }
    registerSuccessfulLoginForJWT(username,token){
        
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME,username)
        this.setupAxiosIntercetors(this.createJWTHeader(token))
    }
    createJWTHeader(token){
        return 'Bearer ' + token
    }

    /** */
    logout(){
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    }
    isUserLogin(){
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if(user === null) 
            return false
        return true
    }
    getUser(){
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if(user === null)
            return ''
        return user
    }
    setupAxiosIntercetors(AuthHeader){

        axios.interceptors.request.use(
            (config) => {
                if(this.isUserLogin())
                    config.headers.authorization = AuthHeader
                return config
            }
        )
    }

}

export default new AuthenticationService();