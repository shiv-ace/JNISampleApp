#ifndef JNINATIVEAPP_HELLOJNI_H
#define JNINATIVEAPP_HELLOJNI_H

#include <jni.h>

JNIEXPORT jstring Java_com_codeshiv_jninativeapp_MainActivity_CreateNativeCall( JNIEnv* env, jobject obj, jstring string);

#endif //JNINATIVEAPP_HELLOJNI_H