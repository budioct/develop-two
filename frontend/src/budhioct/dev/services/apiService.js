import {urlApi} from "../constants/urlApi.js";
import axios from 'axios';

const register = async (dataRegister) => {
    try {
        return await axios.post(urlApi.auth.register, dataRegister, {
            withCredentials: true
        });
    } catch (error) {
        console.error(error);
        throw error
    }
};

const login = async (dataLogin) => {
    try {
        return await axios.post(urlApi.auth.login, dataLogin, {
            withCredentials: true
        });
    } catch (error) {
        console.error(error);
        throw error
    }
};

export {
    register,
    login,
}