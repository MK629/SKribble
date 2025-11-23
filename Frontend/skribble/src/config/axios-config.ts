import { getToken } from "@/server-actions/cookie-baker";
import axios from "axios";

//Nullifying baseUrl is required as Axios prepends NextJs api route baseUrl to an external url call, when used in server actions(RPC).
const axiosSaddle = axios.create({baseURL: undefined});

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