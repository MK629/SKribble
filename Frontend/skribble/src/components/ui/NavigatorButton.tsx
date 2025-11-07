"use client"

import { useRouter } from 'next/navigation'
import React from 'react'
import { Button } from './button'

const NavigatorButton = ({text, className, endpoint} : {text: string, className: string, endpoint: string}) => {

  const router = useRouter()
    
  return (
    <Button className={`${className}`} onClick={() => {router.push(endpoint)}}>{text}</Button>
  )
}

export default NavigatorButton