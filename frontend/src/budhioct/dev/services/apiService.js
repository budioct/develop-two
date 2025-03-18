import {urlApi} from "../constants/urlApi.js";
import axios from 'axios';
import {useAuthStore} from "../stores/authStore.js";

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

const logout = async () => {
    try {
        return await axios.post(urlApi.auth.logout, {}, {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${useAuthStore().token}`,
            },
        });
    } catch (error) {
        console.error(error);
        throw error
    }
};

export {
    register,
    login,
    logout,
}