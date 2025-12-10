'use client'

import { getCurrentUserInfo, logout } from "@/backend/credentials-herald";
import { UserInfo } from "@/constants/graphql-response-dtos";
import { IconDoorExit, IconSettings } from "@tabler/icons-react";
import { UserCog } from "lucide-react";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react"
import toast from "react-hot-toast";

const MiniProfileCard = () => {

  const [profileInfo, setProfileInfo] = useState<UserInfo>();
  const router = useRouter()

  const updateUserInfo = () => {
    getCurrentUserInfo().then((response) => {setProfileInfo(response.getCurrentUserInfo)}).catch((e) => {toast.error(e.message)});
  }

  const logoutAction = async () => {
    await logout();
    router.push("/");
  }

  useEffect(() => {
    updateUserInfo()
  }, [])

  return (
    <div className="w-56 bg-gray-200 border-[1px] border-black shadow-xl rounded-xl p-4 flex flex-col gap-3">
        <div className="border-b-[1px] border-black pb-3">
          {
            profileInfo ? 
            <div className="flex flex-col gap-1">
              <span className="font-semibold text-black dark:text-white">{profileInfo?.Username}</span>
              <span className="text-sm text-gray-700 dark:text-gray-300">{profileInfo?.Email}</span>
              <button className="flex items-center justify-center gap-1 mt-2 px-2 py-1 text-sm rounded-md border-[1px] border-black hover:bg-gray-100 dark:hover:bg-gray-800 transition">
              <UserCog/>
              View Detail
              </button>
            </div>
            :
            <div>
              Loading user info...
            </div>
          }

        </div>

        <button className="flex items-center justify-center gap-1 px-2 py-1 text-sm rounded-md border-[1px] border-black hover:bg-gray-100 dark:hover:bg-gray-800 transition">
            <IconSettings/>
            Settings
        </button>

        <button onClick={() => {logoutAction()}} className="flex items-center justify-center gap-1 px-2 py-1 text-sm rounded-md border-[1px] border-black hover:bg-red-100 dark:hover:bg-red-900 text-red-600 dark:text-red-400 transition">
            <IconDoorExit/>
            Logout
        </button>
    </div>
  )
}

export default MiniProfileCard