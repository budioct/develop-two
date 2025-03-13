import {urlApi} from "../constants/urlApi.js";
import axios from 'axios';

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
    login,
}