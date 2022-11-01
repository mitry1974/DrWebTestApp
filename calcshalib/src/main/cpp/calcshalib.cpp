#include <jni.h>
#include <cstdlib>
#include <string>
#include <__locale>


using namespace std;

string exec(char *command);

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_calcshalib_CalcShaLib_calculate(
        JNIEnv* env,
        jobject /* this */,
        jstring fileName) {
    const char *nativeString = env->GetStringUTFChars(fileName, nullptr);
    int size = snprintf(nullptr, 0, "sha1sum %s", nativeString);
    char *command = new char[size + 1];
    snprintf(command, size + 1, "sha1sum %s", nativeString);
    env->ReleaseStringUTFChars(fileName, nativeString);
    string result = exec(command);
    delete[] command;
    return env->NewStringUTF(result.c_str());
}

string exec(char *command) {
    char buffer[128];
    string result;
    FILE* pipe = popen(command, "r");
    if (!pipe)
        throw runtime_error("popen() failed!");
    try {
        while (fgets(buffer, sizeof buffer, pipe) != nullptr) {
            result += buffer;
        }
    } catch (...) {
    }
    pclose(pipe);

    if(result.empty()) {
        return "";
    }

    const string delimiter = " ";
    return result.substr(0, result.find(delimiter));
}
