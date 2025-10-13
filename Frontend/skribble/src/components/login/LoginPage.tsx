'use client'

import { imageAlt } from "@/constants/system-constants"
import Image from "next/image"
import LoginForm from "./LoginForm"
import LoginPageImage from "../../../public/images/LoginPageImage.jpg"

const LoginPage = () => {
  return (
    <div className="flex h-screen">
      <div className="relative w-1/2 lg:block hidden">
        <Image alt={imageAlt} src={LoginPageImage} placeholder="blur" fill priority quality={75} sizes="100vw" className="object-cover"/>
      </div>
      <div className="flex flex-1 justify-center items-center bg-gradient-to-br from-amber-400/50 to-gray-500/50">
        <LoginForm/>
      </div>
    </div>
  )
}

export default LoginPage