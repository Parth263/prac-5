#include <jni.h>
#include <stdio.h>

JNIEXPORT jint JNICALL Java_JNI_add
  (JNIEnv *env, jobject thisobj, jint n1, jint n2) {
    jint res;
    res = n1 + n2;  // Fixed: was n2 + n2
    return res;
}