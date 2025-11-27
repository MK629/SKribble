'use client'

import { getCurrentUserInfo } from "@/backend/credentials-herald"
import { Button } from "../ui/button"
import { UserInfo } from "@/constants/graphql-response-dtos"
import toast from "react-hot-toast"

const userInfo = async () => {
  try{
    const userInfo: UserInfo = await getCurrentUserInfo()
    console.log(userInfo.getCurrentUserInfo)
  }
  catch(e){
    toast.error(e.message)
  }
}

const HomePage = () => {
  return (
    <div>
      <Button onClick={() => {userInfo()}}>User data</Button>
    </div>
  )
}

export default HomePage