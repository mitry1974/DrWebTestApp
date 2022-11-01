#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_calcshalib_CalcShaLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_calcshalib_CalcShaLib_calculate(
        JNIEnv* env,
        jobject /* this */,
        jstring packageName) {
    return env->NewStringUTF("sha1");
}
