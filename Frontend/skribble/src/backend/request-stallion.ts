import { JsonRequestBody } from "@/constants/request-dtos";
import { JsonResponseBody } from "@/constants/response-dtos";
import { getBaseSKribbleBackendUrl } from "@/backend/dotenv-herald";

export const unauthenticatedFetch = async (endpoint: string | undefined, jsonBody: JsonRequestBody) : Promise<JsonResponseBody> => {
    try {
        const response = await fetch(`${await getBaseSKribbleBackendUrl()}${endpoint}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonBody),
        });

        if (!response.ok) {
            const errorStatus = response.status;
            const errorMessage = await response.text()
            console.log((`HTTP error! status: ${response.status}`))
            throw new Error(errorMessage || `HTTP status: ${errorStatus}`)
        }

        const responseData = await response.json();
        return responseData;
    }
    catch (error) {
        console.error('Request failed:', error);
        throw error;
    }
}