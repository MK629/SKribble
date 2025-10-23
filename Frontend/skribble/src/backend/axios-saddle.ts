import { getToken } from "@/server-actions/cookie-baker";
import axios from "axios";

const axiosSaddle = axios.create();

axiosSaddle.interceptors.request.use(async (config) => {
    // Do something before request is sent
    config.headers.Authorization = await getToken();
    return config;
  }, (error) => {
    // Do something with request error
    return Promise.reject(error);
  }
);

export default axiosSaddle;