#include <jni.h>
#include <android/log.h>
#include "Factorial.h"
#define  LOG_TAG    "testjni"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
extern "C"
{
JNIEXPORT void Java_com_codeshiv_jninativeapp_MainActivity_CreateNativeCall( JNIEnv* env, jobject obj );
};

JNIEXPORT void Java_com_codeshiv_jninativeapp_MainActivity_CreateNativeCall( JNIEnv* env, jobject obj )
{
    ALOG("Hello Baby I love you!!!! %d.", __LINE__);
    Factorial fact1;
    fact1.factorial1();
}