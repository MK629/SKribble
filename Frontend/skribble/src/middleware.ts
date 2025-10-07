import { NextResponse, NextRequest } from 'next/server'
import { getToken } from './server-actions/cookie-jar'
 
// This function can be marked `async` if using `await` inside
export const middleware = async (request: NextRequest) : Promise<NextResponse> => {
    if(await getToken()){
        return NextResponse.next()
    }
    else{
        return NextResponse.redirect(new URL('/login',request.url))
    }
}

export const config = {
    matcher: '/home/:path*',
}