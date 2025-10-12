'use client'

import { LoginTypes } from "@/constants/system-constants";
import { FormEvent, useState } from "react"
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
import { IconLock, IconRefresh, IconSwitch } from "@tabler/icons-react";
import { loginAction } from "@/server-actions/credentials-actions";

const LoginForm = () => {

  const [loginType, setLoginType] = useState<LoginTypes>(LoginTypes.Username);

  const handleLogin = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const data = new FormData(e.currentTarget);
    loginAction(data, loginType);
  }

  return (
    <div>
      <form onSubmit={(e) => {handleLogin(e)}} className="w-full max-w-md space-y-6 bg-white dark:bg-gray-900 p-8 rounded-lg shadow-lg">
        <h2 className="flex gap-1 justify-center text-2xl font-bold text-center text-gray-900 dark:text-white">
            Welcome <Feather/>
        </h2>
        <div className="flex gap-1">
          <label htmlFor="usernameOrEmail" className="flex font-bold my-auto">
            {loginType === LoginTypes.Username ? 
              <UserIcon/>
              :
              <MailIcon/>
            }:
          </label>
          <input type="text" name="usernameOrEmail" placeholder={`Enter your ${loginType}.`} className="px-4 py-1 border text-sm border-gray-300 rounded-md shadow-sm focus:outline-none bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100"/>
          <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button size={"sm"}><IconRefresh/></Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent className="w-56">
                <DropdownMenuLabel>Login options</DropdownMenuLabel>
                <DropdownMenuSeparator/>
                <DropdownMenuRadioGroup value={loginType} onValueChange={(value) => {setLoginType(value as LoginTypes)}}>
                  <DropdownMenuRadioItem value={LoginTypes.Username}>{`Username`}</DropdownMenuRadioItem>
                  <DropdownMenuRadioItem value={LoginTypes.Email}>{`E-Mail`}</DropdownMenuRadioItem>
                </DropdownMenuRadioGroup>
              </DropdownMenuContent>
          </DropdownMenu>
        </div>

        <div className="flex gap-1">
          <label htmlFor="password" className="flex text-sm font-bold my-auto"><IconLock/>:</label>
          <input type="password" placeholder={`Enter your password.`} name="password" className="px-4 py-1 border text-sm border-gray-300 rounded-md shadow-sm focus:outline-none bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100"/>
        </div>

        <button type="submit">Login</button>
      </form>
    </div>
  )
}

export default LoginForm