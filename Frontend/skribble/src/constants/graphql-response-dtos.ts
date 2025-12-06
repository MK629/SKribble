export type UserInfoResponse = {
    getCurrentUserInfo: UserInfo
}

export type UserInfo = {
    Id: string,
    Username: string,
    Email: string
}