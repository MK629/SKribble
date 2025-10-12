'use server'

export const unauthenticatedFetch = async (endpoint: string | undefined, jsonBody: any) : Promise<Response> => {
    try {
        const response = await fetch(`${process.env.SKRIBBLE_BACKEND_BASE_URL}${endpoint}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonBody),
        });

        if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
        }

        const responseData = await response.json();
        return responseData;
    }
    catch (error) {
        console.error('Login failed:', error);
        throw error;
    }
}