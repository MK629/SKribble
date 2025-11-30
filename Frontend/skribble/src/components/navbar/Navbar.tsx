'use client'

import Image from "next/image";
import Feather from '../../../public/icons/feather.svg'
import { svgAlt } from "@/constants/system-constants";
import {IconSearch, IconUser } from "@tabler/icons-react";

export default function Navbar() {
  return (
    <nav className="sticky top-0 z-50 w-full bg-black/90 text-white px-2 py-2 shadow-md">
      <div className="px-1 mx-auto flex items-center justify-between">
        <h1 className='flex text-3xl bg-gray-200 font-extrabold px-4 py-2 border-2 rounded-2xl text-center border-black text-black'>
            SKribble
            <Image src={Feather} alt={svgAlt} width={38} height={38}/>
        </h1>
        
        <div className="hidden lg:flex min-w-lg justify-center items-center gap-2">
            <input
            name="searchbar"
            type="text"
            placeholder="Search..."
            className="w-full px-4 py-2 rounded-xl bg-gray-200 text-black border-2 border-black focus:outline-none"
            />
            <label htmlFor="searchbar" className="bg-black transition duration-400 text-gray-200 rounded-2xl p-1 border-2 border-gray-200 hover:bg-gray-200 hover:text-black hover:border-black">
                <IconSearch size={28}/>
            </label>
        </div>


        <div className="bg-black transition duration-400 text-gray-200 rounded-full p-1 border-2 border-gray-200 hover:bg-gray-200 hover:text-black hover:border-black">
            <IconUser size={34}/>
        </div>
      </div>
    </nav>
  );
}
