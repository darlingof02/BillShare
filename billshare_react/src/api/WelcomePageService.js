import axios from "axios";
import AuthenticationService from './AuthenticationService'
import {API_URL} from './constant'


class WelcomePageBeanService{

    executeWelcomePageBeanService(name){

        return axios.get(`${API_URL}/hello-world-bean/${name}`)
    }
}

export default new WelcomePageBeanService();