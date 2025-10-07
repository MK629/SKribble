'use client'
import { saveToken } from "@/server-actions/cookie-jar"

const page = () => {
  return (
    <div>
      <button onClick={() => {saveToken("token")}}>login</button>
    </div>
  )
}

export default page