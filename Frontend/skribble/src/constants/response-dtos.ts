export type JsonResponseBody = object

export type TokenCarrier = JsonResponseBody & {
    message: string,
    token: string,
}