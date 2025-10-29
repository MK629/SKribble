import { JsonRequestBody } from "@/constants/request-dtos";
import { JsonResponseBody } from "@/constants/response-dtos";
import { getBaseSKribbleBackendUrl } from "@/backend/dotenv-herald";
import axiosSaddle from "./axios-saddle";

export const RESTFetch = async (endpoint: string, jsonBody: JsonRequestBody) : Promise<JsonResponseBody> => {
    const baseUrl = await getBaseSKribbleBackendUrl();
    return (await axiosSaddle.post(baseUrl + endpoint, jsonBody))?.data;
}