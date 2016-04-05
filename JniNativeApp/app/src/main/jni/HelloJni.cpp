#include <jni.h>
#include <android/log.h>
#include "Factorial.h"
#include <string.h>
#define  LOG_TAG    "testjni"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
extern "C"
{
JNIEXPORT jstring JNICALL Java_com_codeshiv_jninativeapp_MainActivity_CreateNativeCall( JNIEnv* env, jobject obj, jstring string);
};

JNIEXPORT jstring JNICALL Java_com_codeshiv_jninativeapp_MainActivity_CreateNativeCall( JNIEnv* env, jobject obj, jstring string )
{
    return (env)->NewStringUTF("Hello");
}