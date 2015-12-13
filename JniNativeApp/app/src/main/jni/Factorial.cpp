#include "Factorial.h"
#include <stdio.h>
#include <android/log.h>
#define  LOG_TAG    "testjni"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
void Factorial::factorial1(){
    int num,factorial=1;
    for(int a=1;a<=10;a++)
    {
        factorial=factorial*a;
    }
    printf("Factorials are = %d",factorial);
    ALOG("Factorials are = %d",factorial);
}