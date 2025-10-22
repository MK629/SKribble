"use server"

const SKRIBBLE_BACKEND_BASE_URL = process.env.SKRIBBLE_BACKEND_BASE_URL;
const SKRIBBLE_BACKEND_USERNAME_LOGIN_ENDPOINT = process.env.SKRIBBLE_BACKEND_USERNAME_LOGIN_ENDPOINT;
const SKRIBBLE_BACKEND_EMAIL_LOGIN_ENDPOINT = process.env.SKRIBBLE_BACKEND_EMAIL_LOGIN_ENDPOINT;
const SKRIBBLE_BACKEND_USER_REGISTER_ENDPOINT = process.env.SKRIBBLE_BACKEND_USER_REGISTER_ENDPOINT;

export const getBaseSKribbleBackendUrl = async () => {
    return SKRIBBLE_BACKEND_BASE_URL;
}

export const getULoginEndpoint = async () => {
    return SKRIBBLE_BACKEND_USERNAME_LOGIN_ENDPOINT;
}

export const getELoginEndpoint = async () => {
    return SKRIBBLE_BACKEND_EMAIL_LOGIN_ENDPOINT;
}

export const getUserRegEndpoint = async () => {
    return SKRIBBLE_BACKEND_USER_REGISTER_ENDPOINT;
}