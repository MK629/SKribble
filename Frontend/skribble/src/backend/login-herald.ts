import { LoginTypes} from "@/constants/system-constants";
import { getELoginEndpoint, getULoginEndpoint } from "@/backend/dotenv-herald";
import { EmailLoginForm, UsernameLoginForm } from "@/constants/request-dtos";
import { RESTFetch } from "./request-stallion";
import { TokenCarrier } from "@/constants/response-dtos";

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