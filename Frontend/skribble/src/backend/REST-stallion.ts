import { getBaseSKribbleBackendUrl } from "@/backend/dotenv-herald";
import axiosSaddle from "../config/axios-config";

export const RESTFetch = async <RequestBodyType, ResponeBodyType> (endpoint: string, jsonBody: RequestBodyType) : Promise<ResponeBodyType> => {
    const baseUrl = await getBaseSKribbleBackendUrl();
    return (await axiosSaddle.post(baseUrl + endpoint, jsonBody))?.data;
}
