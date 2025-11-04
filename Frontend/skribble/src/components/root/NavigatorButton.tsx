"use client"

import { useRouter } from 'next/navigation'
import React from 'react'
import { Button } from '../ui/button'

const NavigatorButton = ({name, className, endpoint} : {name: string, className: string, endpoint: string}) => {

  const router = useRouter()
    
  return (
    <Button className={`${className}`} onClick={() => {router.push(endpoint)}}>{name}</Button>
  )
}

export default NavigatorButton