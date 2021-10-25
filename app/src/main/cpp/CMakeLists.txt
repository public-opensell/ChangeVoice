
include_directories("inc")

cmake_minimum_required(VERSION 3.4.2)

set(mydirs D:/BianshengboyGirl/app/libs/armeabi-v7a)
project("bianshengboygirl")
add_library( # Sets the name of the library.
            changeVoice

             # Sets the library as a shared library.
            SHARED

             # Provides a relative path to your source file(s).
        changeVoice.cpp)

#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/../jniLibs/${CMAKE_ANDROID_ARCH_ABI}")
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

target_link_libraries( # Specifies the target library.
                        changeVoice
                        fmod
                        fmodL

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
 add_library(fmod SHARED IMPORTED)
 set_target_properties(fmod PROPERTIES IMPORTED_LOCATION
         ${mydirs}/libfmod.so)
add_library(fmodL SHARED IMPORTED)
set_target_properties(fmodL PROPERTIES IMPORTED_LOCATION
        ${mydirs}/libfmodL.so)


