import { getToken } from "@/server-actions/cookie-baker"
import { getGraphQLEndpoint } from "@/server-actions/dotenv-scholar"
import { InMemoryCache } from "@apollo/client"
import { ApolloClient, HttpLink } from "@apollo/client"

export const getApolloClient = async () => {
    const token = await getToken();
    return new ApolloClient({
        link: new HttpLink({
            uri: await getGraphQLEndpoint(),
            headers: {
                Authorization: token? `Bearer ${token}` : null
            }
        }),
        cache: new InMemoryCache(),
    })
}