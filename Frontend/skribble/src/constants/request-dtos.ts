export type JsonRequestBody = object

export type UsernameLoginForm = JsonRequestBody & {
    username: string,
    password: string,
}

export type EmailLoginForm = JsonRequestBody & {
    email: string,
    password: string,
}