'use client'

import { LoginTypes } from "@/constants/system-constants";
import { useState } from "react"
import { Button } from "@/components/ui/button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuRadioGroup,
  DropdownMenuRadioItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Feather, MailIcon, UserIcon } from "lucide-react";
import { IconEye, IconEyeClosed, IconLock, IconRefresh } from "@tabler/icons-react";
import { saveToken } from "@/server-actions/cookie-baker";
import { useRouter } from "next/navigation";
import { TokenCarrier } from "@/constants/response-dtos";
import toast from "react-hot-toast";
import { login } from "@/backend/credentials-herald";
import { toastCss } from "@/config/toast-config";

const LoginForm = () => {

  const [loginType, setLoginType] = useState<LoginTypes>(LoginTypes.Username);
  const [seePassword, setSeePassword] = useState<boolean>(false);
  const router = useRouter()

  const handleLoginCompletion = async (response: TokenCarrier) => {
    await saveToken(response?.token || "");
    toast.success(response?.message, {className: toastCss});
    router.push("/application/home");
  }

  return (
    <div>
      <form action={(data) => {
        login(data, loginType)
        .then((response) => {
          toast.dismissAll(); 
          handleLoginCompletion(response);
        })
          .catch(e => {
            toast.dismissAll();
            toast.error(e.response.data, {className: toastCss});
            console.log(e.message)
          });
        }}
        className="w-full max-w-lg space-y-6 bg-white dark:bg-gray-900 p-8 rounded-lg border-[1px] border-black shadow-lg">
        <h2 className="flex gap-1 justify-center text-2xl font-bold text-center text-gray-900">
            Name thyself! <Feather/>
        </h2>
        <div className="flex gap-1">
          <label htmlFor="usernameOrEmail" className="flex font-bold my-auto">
            {loginType === LoginTypes.Username ? 
              <UserIcon/>
              :
              <MailIcon/>
            }:
          </label>
          <input type="text" name="usernameOrEmail" placeholder={`Enter your ${loginType}.`} className="px-4 py-1 border text-sm border-gray-700 rounded-md shadow-sm focus:outline-none bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100"/>
          <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button size={"sm"}><IconRefresh/></Button>
              </DropdownMenuTrigger> 
              <DropdownMenuContent className="w-56 border-[1px] border-black">
                <DropdownMenuLabel>Login options</DropdownMenuLabel>
                <DropdownMenuSeparator/>
                <DropdownMenuRadioGroup value={loginType} onValueChange={(value) => {setLoginType(value as LoginTypes)}}>
                  <DropdownMenuRadioItem value={LoginTypes.Username}>{LoginTypes.Username}</DropdownMenuRadioItem>
                  <DropdownMenuRadioItem value={LoginTypes.Email}>{LoginTypes.Email}</DropdownMenuRadioItem>
                </DropdownMenuRadioGroup>
              </DropdownMenuContent>
          </DropdownMenu>
        </div>

        <div className="flex gap-1">
          <label htmlFor="password" className="flex font-bold my-auto"><IconLock/>:</label>
          <input type={seePassword ? 'text' : 'password'} placeholder={`Enter your password.`} name="password" className="px-4 py-1 border text-sm border-gray-700 rounded-md shadow-sm focus:outline-none bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100"/>
          <Button type="button" onClick={() => setSeePassword(!seePassword)} size={"sm"}>{seePassword ? <IconEye/> : <IconEyeClosed/>}</Button>
        </div>

        <div className="flex">
          <Button type="submit" className="mx-auto" onClick={() => {toast.loading("Authenticating...")}}>Login</Button>
        </div>
      </form>
    </div>
  )
}

export default LoginForm