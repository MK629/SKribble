'use client'

import { uDevMsg } from '@/constants/system-constants'
import { useRouter } from 'next/navigation'
import React from 'react'
import toast from 'react-hot-toast'

const RootPageDescBanner = ({children, endpoint} : {children: React.ReactNode, endpoint: string}) => {
  
  const router = useRouter();

  return (
    <div onClick={() => {endpoint ? router.push(endpoint) : toast(uDevMsg)}} className='bg-black/85 hover:bg-black/90 text-white w-full mx-auto text-center p-2 rounded-4xl transition duration-300'>
      {children}
    </div>
  )
}

export default RootPageDescBanner