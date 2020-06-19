#include <pthread.h>
#include <jni.h>
#include <memory.h>
#include <dlfcn.h>
#include <cstdio>
#include <cstdlib>

#include "Includes/Logger.h"
#include "src/Substrate/CydiaSubstrate.h"
#import "Includes/Utils.h"

bool PlayerUpdateHookInitialized,test2,test3 = false;

extern "C"
JNIEXPORT jobjectArray JNICALL Java_com_frosty_platinmodsmenu_MenuService_getListFT(JNIEnv *env, jobject jobj){
    jobjectArray ret;
    int i;
    int Total_Feature =3;
    const char *features[]={"test 1", "test 2","test 3"};

    ret= (jobjectArray)env->NewObjectArray(Total_Feature,
                                           env->FindClass("java/lang/String"),
                                           env->NewStringUTF(""));

    for(i=0;i<Total_Feature;i++) {
        env->SetObjectArrayElement(
                ret,i,env->NewStringUTF(features[i]));
    }
    return(ret);
}

extern "C"
JNIEXPORT void JNICALL Java_com_frosty_platinmodsmenu_MenuService_changeToggle(JNIEnv *env, jobject thisObj, jint number) {
    int i = (int) number;
    switch (i) {

        case 0:
            PlayerUpdateHookInitialized = !PlayerUpdateHookInitialized;
            break;
        case 1:
            test2 = !test2;
            break;
        case 2:
            test3 =! test3;
            break;

    }
}




const char* libName = "libil2cpp.so";

void(*old_Player_Update)(void *instance);
void Player_Update(void *instance) {
    //Check if instance is NULL to prevent crashes!  If the instance object is NULL, 
    //this is what the call to update would look like in C++: 
    //NULL.Update(); and dat doesnt make sense right?
    if(instance != NULL) {
        if(!PlayerUpdateHookInitialized){
            //Check if this hook initialized. If so log 
            PlayerUpdateHookInitialized = true;
            LOGI("Player_Update hooked");
        }
        //Your code here
    }
    old_Player_Update(instance);
}

// we will run our patches in a new thread so our while loop doesn't block process main thread
void* hack_thread(void*) {
    LOGI("I have been loaded. Mwuahahahaha");
    // loop until our target library is found
    do {
        sleep(1);
    } while (!isLibraryLoaded(libName));
    LOGI("I found the il2cpp lib. Address is: %lu", findLibrary(libName));
    LOGI("Hooking Player_Update");
    MSHookFunction((void*)getAbsoluteAddress(libName, 0xF09F00), (void*)Player_Update, (void**) &old_Player_Update);

    return NULL;
}

__attribute__((constructor))
void libwolve_main() {
    pthread_t ptid;
    pthread_create(&ptid, NULL, hack_thread, NULL);
}
