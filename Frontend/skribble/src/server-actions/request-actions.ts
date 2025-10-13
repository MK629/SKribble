'use server'

import { JsonRequestBody } from "@/constants/request-dtos";
import { JsonResponseBody } from "@/constants/response-dtos";

export const unauthenticatedFetch = async (endpoint: string | undefined, jsonBody: JsonRequestBody) : Promise<JsonResponseBody> => {
    try {
        const response = await fetch(`${process.env.SKRIBBLE_BACKEND_BASE_URL}${endpoint}`, {
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