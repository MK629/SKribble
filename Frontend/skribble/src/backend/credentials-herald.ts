import { LoginTypes} from "@/constants/system-constants";
import { getELoginEndpoint, getULoginEndpoint } from "@/backend/dotenv-herald";
import { EmailLoginForm, UsernameLoginForm } from "@/constants/request-dtos";
import { RESTFetch } from "./REST-stallion";
import { TokenCarrier } from "@/constants/response-dtos";
import { UserInfoResponse } from "@/constants/graphql-response-dtos";
import { getApolloClient } from "@/config/apolloClient-config";
import { gql } from "@apollo/client";
import { clearToken } from "@/server-actions/cookie-baker";


export const login = async (data: FormData, loginType: LoginTypes) : Promise<TokenCarrier> => {
    let loginForm: UsernameLoginForm | EmailLoginForm;
    let endpoint: string | undefined;

    if(loginType === LoginTypes.Username){
        endpoint = await getULoginEndpoint();
        loginForm = {
            username: data.get("usernameOrEmail")?.toString() || "",
            password: data.get("password")?.toString() || "",
        } as UsernameLoginForm;
    }
    else{
        endpoint = await getELoginEndpoint();
        loginForm = {
            email: data.get("usernameOrEmail")?.toString() || "",
            password: data.get("password")?.toString() || "",
        } as EmailLoginForm;
    }

    return RESTFetch<UsernameLoginForm | EmailLoginForm, TokenCarrier>(endpoint, loginForm);
}

export const getCurrentUserInfo = async () : Promise<UserInfoResponse> => {
    const apollo = await getApolloClient();

    const QUERY = gql`
        query {getCurrentUserInfo {Id, Username, Email} }
    `

    const {data, error} = await apollo.query({query: QUERY})

    if(error){
        throw new Error(error.message)
    }

    return data as UserInfoResponse
}

export const logout = async () => {
    await clearToken();
}