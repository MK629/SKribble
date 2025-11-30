import React from 'react'
import NavigatorButton from '../ui/NavigatorButton'
import Image from 'next/image'
import RootPageImage from '../../../public/images/RootPageImage.jpg'
import Feather from '../../../public/icons/feather.svg'
import { imageAlt, svgAlt } from '@/constants/system-constants'
import RootPageDescBanner from '../ui/RootPageDescBanner'
import { BookOpenText, ListMusic, NotepadText, PencilLine} from 'lucide-react'

const RootPage = () => {
  return (
    <div className='relative min-h-screen w-full overflow-hidden'>
      <Image src={RootPageImage} alt={imageAlt} placeholder='blur' fill priority quality={75} sizes="100vw" className='object-cover object-center z-0'/>

      <div className='fixed inset-0 bg-gray-200/15 z-10'></div>

      <div className='relative z-20'>
        <div className='flex justify-center mt-10'>
          <h1 className='flex text-[45px] bg-white/50 inset-0 backdrop-blur-xs font-extrabold px-4 py-2 border-2 rounded-2xl text-center border-black text-black'>
            SKribble
            <Image src={Feather} alt={svgAlt} width={56} height={56}/>
          </h1>
        </div>

        <div className='grid grid-cols-1 lg:grid-cols-2 gap-4 mt-8 lg:w-4/5 md:w-4/6 sm:w-5/6 mx-auto w-full px-4'>
          <RootPageDescBanner endpoint=''>
            <div className='flex-col'>
              <BookOpenText className='mx-auto' size={45}/>
              Stories
              <div className='rounded-4xl border-white border-2 p-2 mt-2'>
                <ul className='list-disc space-y-4 list-inside text-left text-sm font-semibold'>
                  <li className='list-item'>
                    Channel your inner creativity.
                  </li>
                  <li className='list-item'>
                    Bring your worlds to life.
                  </li>
                  <li className='list-item'>
                    Craft tales that captivate.
                  </li>
                </ul>
              </div>
            </div>
          </RootPageDescBanner>
          <RootPageDescBanner endpoint=''>
            <div>
              <ListMusic className='mx-auto' size={45}/>
              Songs
              <div className='rounded-4xl border-white border-2 p-2 mt-2'>
                <ul className='list-disc space-y-4 list-inside text-left text-sm font-semibold'>
                  <li className='list-item'>
                    Put your ideas onto a digital parchment.
                  </li>
                  <li className='list-item'>
                    Shape lyrics that truly resonate.
                  </li>
                  <li className='list-item'>
                    Create songs that ignite emotion.
                  </li>
                </ul>
              </div>
            </div>
          </RootPageDescBanner>
          <RootPageDescBanner endpoint=''>
            <div>
              <PencilLine className='mx-auto' size={45}/>
              Essays
              <div className='rounded-4xl border-white border-2 p-2 mt-2'>
                <ul className='list-disc space-y-4 list-inside text-left text-sm font-semibold'>
                  <li className='list-item'>
                    Clarify your arguments with ease.
                  </li>
                  <li className='list-item'>
                    Shape ideas into compelling prose.
                  </li>
                  <li className='list-item'>
                    Write essays that stand out.
                  </li>
                </ul>
              </div>
            </div>
          </RootPageDescBanner>
            <RootPageDescBanner endpoint=''>
            <div>
              <NotepadText className='mx-auto' size={45}/>
              Research papers
              <div className='rounded-4xl border-white border-2 p-2 mt-2'>
                <ul className='list-disc space-y-4 list-inside text-left text-sm font-semibold'>
                  <li className='list-item'>
                    Organize your insights efficiently.
                  </li>
                  <li className='list-item'>
                    Structure ideas with precision.
                  </li>
                  <li className='list-item'>
                    Turn research into impact.
                  </li>
                </ul>
              </div>
            </div>
          </RootPageDescBanner>
        </div>

        <div className='flex justify-center mt-8 mb-8'>
          <NavigatorButton text={`Let's make some cool stuff!`} className='' endpoint='/application/home'/>
        </div>
      </div>
    </div>
  )
}

export default RootPage