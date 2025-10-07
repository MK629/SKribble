'use server'

import { tokenKey } from "@/constants/system-constants";
import { cookies } from "next/headers";

export const getToken = async () : Promise<string | undefined> => {
    const cookieJar = await cookies();
    return cookieJar.get(tokenKey)?.value
}

export const saveToken = async (token: string) => {
    const cookieJar = await cookies();
    cookieJar.set(tokenKey, token, {secure: true});
}