import axios from 'axios'
const { VITE_API_BASE_URL } = import.meta.env

const localAxios = () => { 
    const instance = axios.create({
        baseURL: VITE_API_BASE_URL,
        header: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    })

    instance.defaults.headers.common["Authorization"] = "";
    instance.defaults.headers.post["Content-Type"] = "application/json";
    instance.defaults.headers.put["Content-Type"] = "application/json";

    return instance;
}

export { localAxios }