cmake_minimum_required(VERSION 3.22.1)

# Set the project name
project("classification")

# Add the native library
add_library(${PROJECT_NAME} SHARED
        classification.cpp)

# Specify directories which should be included in the build
include_directories(${CMAKE_SOURCE_DIR}/include)

# Specify the libraries CMake should link to your target library
target_link_libraries(${PROJECT_NAME}
        android
        log)

# Set the path to the JNI directory
set(JNI_DIR ${CMAKE_SOURCE_DIR}/app/src/main/jni)

# Specify the location of the Java Native Interface (JNI) headers
include_directories(${JNI_DIR}/include)
include_directories(${JNI_DIR}/include/android)

# Specify the location of the asset manager headers
include_directories(${ANDROID_NDK}/sources/android/native_app_glue)

# Specify the location of the asset manager source files
add_library(native_app_glue STATIC
        ${ANDROID_NDK}/sources/android/native_app_glue/android_native_app_glue.c)

# Link the native_app_glue library
target_link_libraries(${PROJECT_NAME}
        native_app_glue)
