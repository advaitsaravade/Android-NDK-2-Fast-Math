#include <jni.h>

extern "C" JNIEXPORT jint JNICALL
Java_com_example_ndkhelloworld_MainActivity_fibonacciNative(
        JNIEnv *env,
        jobject,
        jint n
) {
    if (n <= 1) return n;
    int a = 0, b = 1, temp;
    for (int i = 2; i <= n; i++) {
        temp = a + b;
        a = b;
        b = temp;
    }
    return b;
}