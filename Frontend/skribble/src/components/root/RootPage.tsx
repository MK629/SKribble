import React from 'react'
import NavigatorButton from '../ui/NavigatorButton'
import Image from 'next/image'
import RootPageImage from '../../../public/images/RootPageImage.jpg'
import Feather from '../../../public/icons/feather.svg'
import { imageAlt, svgAlt } from '@/constants/system-constants'

const RootPage = () => {
  return (
    <div className='relative h-screen w-full overflow-hidden'>
      <Image src={RootPageImage} alt={imageAlt} fill priority quality={75} className='object-cover object-center'/>

      <div className='relative z-10'>
        <div className='flex justify-center mt-10'>
          <h1 className='flex text-[45px] bg-white opacity-90 inset-0 backdrop-blur-3xl font-extrabold px-4 py-2 border-2 rounded-2xl text-center border-black text-black'>
            SKribble
            <Image src={Feather} alt={svgAlt} width={56} height={56}/>
          </h1>
        </div>

        <div className='flex justify-center mt-8'>
          <NavigatorButton text={`Let's make some cool stuff!`} className='' endpoint='\home'/>
        </div>
      </div>
    </div>
  )
}

export default RootPage