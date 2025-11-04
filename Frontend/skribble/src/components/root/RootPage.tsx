import React from 'react'
import NavigatorButton from './NavigatorButton'

const RootPage = () => {
  return (
    <div>
      <div className='flex justify-center'>
        <h1 className='text-4xl font-extrabold'>SKribble</h1>
      </div>

      <div className='flex justify-center'>
        <NavigatorButton name={`Let's make some cool stuff!`} className='' endpoint='\home'/>
      </div>
    </div>
  )
}

export default RootPage