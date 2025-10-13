'use server'

import { LoginTypes, tokenKey } from "@/constants/system-constants";
import { cookies } from "next/headers";
import { EmailLoginForm, JsonRequestBody, UsernameLoginForm } from "@/constants/request-dtos";
import { unauthenticatedFetch } from "./request-actions";
import { TokenCarrier } from "@/constants/response-dtos";

export const getToken = async () : Promise<string | undefined> => {
    const cookieJar = await cookies();
    return cookieJar.get(tokenKey)?.value
}

export const saveToken = async (token: string) => {
    const cookieJar = await cookies();
    cookieJar.set(tokenKey, token, {secure: true});
}

export const loginAction = async (data: FormData, loginType: LoginTypes) : Promise<TokenCarrier> => {
    let loginForm : JsonRequestBody;
    let endpoint: string | undefined;

    if(loginType === LoginTypes.Username){
        endpoint = process.env.SKRIBBLE_BACKEND_USERNAME_LOGIN_ENDPOINT;
        loginForm = {
            username: data.get("usernameOrEmail")?.toString() || "",
            password: data.get("password")?.toString() || "",
        } as UsernameLoginForm;
    }
    else{
        endpoint = process.env.SKRIBBLE_BACKEND_EMAIL_LOGIN_ENDPOINT;
        loginForm = {
            email: data.get("usernameOrEmail")?.toString() || "",
            password: data.get("password")?.toString() || "",
        } as EmailLoginForm;
    }

    return unauthenticatedFetch(endpoint, loginForm) as Promise<TokenCarrier>;
}