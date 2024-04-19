// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("classification");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("classification")
//      }
//    }

#include <jni.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <cstdio>
#include <cerrno>
#include <cstring>
#include <memory>

//extern "C"
//JNIEXPORT jobject JNICALL
//Java_com_yogender_classification_APICLASS_loadModelFile(JNIEnv *env, jobject thiz,
//                                                        jobject asset_manager,
//                                                        jstring model_file_name) {
//    // TODO: implement loadModelFile()
//}
extern "C" {

JNIEXPORT jobject JNICALL
    Java_com_yogender_classification_APICLASS_loadModelFile(JNIEnv* env, jobject obj, jobject assetManager, jstring modelFileName) {
        // Convert jstring to const char*
        const char* modelFile = env->GetStringUTFChars(modelFileName, nullptr);
        if (modelFile == nullptr) {
            // Error handling, return null or throw exception
            return nullptr;
        }

        // Open the model file using AssetManager
        AAssetManager* manager = AAssetManager_fromJava(env, assetManager);
        if (manager == nullptr) {
            // Error handling, return null or throw exception
            return nullptr;
        }

        AAsset* asset = AAssetManager_open(manager, modelFile, AASSET_MODE_UNKNOWN);
        if (asset == nullptr) {
            // Error handling, return null or throw exception
            return nullptr;
        }

        // Get the length of the asset file
        off_t fileLength = AAsset_getLength(asset);

        // Create a ByteBuffer to hold the file data
        jclass byteBufferClass = env->FindClass("java/nio/ByteBuffer");
        jmethodID allocateDirectMethod = env->GetStaticMethodID(byteBufferClass, "allocateDirect", "(I)Ljava/nio/ByteBuffer;");
        jobject byteBuffer = env->CallStaticObjectMethod(byteBufferClass, allocateDirectMethod, fileLength);
        if (byteBuffer == nullptr) {
            // Error handling, return null or throw exception
            return nullptr;
        }

        // Get the address of the ByteBuffer
        void* bufferAddress = env->GetDirectBufferAddress(byteBuffer);
        if (bufferAddress == nullptr) {
            // Error handling, return null or throw exception
            return nullptr;
        }

        // Read the model data into the ByteBuffer
        int bytesRead = AAsset_read(asset, bufferAddress, fileLength);
        if (bytesRead < 0 || bytesRead != fileLength) {
            // Error handling, return null or throw exception
            return nullptr;
        }

        // Close the asset
        AAsset_close(asset);

        // Release the model file string
        env->ReleaseStringUTFChars(modelFileName, modelFile);

        // Return the ByteBuffer
        return byteBuffer;
    }

}