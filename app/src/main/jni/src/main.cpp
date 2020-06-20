/*
 * Credits:
 *
 * Octowolve - Mod menu: https://github.com/z3r0Sec/Substrate-Template-With-Mod-Menu
 * And hooking: https://github.com/z3r0Sec/Substrate-Hooking-Example
 * VanHoevenTR A.K.A Nixi: https://github.com/LGLTeam/VanHoevenTR_Android_Mod_Menu
 * MrIkso - Mod menu: https://github.com/MrIkso/FloatingModMenu
 * Rprop - https://github.com/Rprop/And64InlineHook
 * MJx0 A.K.A Ruit - KittyMemory: https://github.com/MJx0/KittyMemory
 * */

#include <pthread.h>
#include <jni.h>
#include <src/Includes/Utils.h>
#include <src/Includes/Quaternion.hpp>
#include <src/Includes/Vector3.hpp>
#include <src/Includes/Vector2.hpp>
#include <src/Includes/Unity.h>

#if defined(__aarch64__) //Compile for arm64 lib only

#include <src/And64InlineHook/And64InlineHook.hpp>

#else //Compile for armv7 lib only. Do not worry about greyed out highlighting code, it still works
#include <src/Substrate/SubstrateHook.h>
#endif

#include "KittyMemory/MemoryPatch.h"
#include "Includes/Logger.h"


extern "C"
JNIEXPORT jint JNICALL
Java_uk_lgl_modmenu_FloatingModMenuService_IconSize(
        JNIEnv *env,
        jobject activityObject) {
    return 70;
}

extern "C"
JNIEXPORT jobjectArray  JNICALL
Java_com_frosty_platinmodsmenu_MenuService_getFeatureList(
        JNIEnv *env,
        jobject activityObject) {
    jobjectArray ret;

    // Note: Do not translate the first text
    // Usage:
    // Category_(text)
    // Toggle_[feature name]
    // SeekBar_[feature name]_[min value]_[max value]
    // Spinner_[feature name]_[Items e.g. item1_item2_item3]
    // Button_[feature name]
    // Button_OnOff_[feature name]
    // InputValue_[feature name]
    const char *features[] = {
            "Category_Category 1", // 0
            "Toggle_The toggle", // 1
            "Toggle_The toggle 2", // 2
            "Spinner_The spinner_Items 1_Items 2_Items 3", // 3
            "Category_Category 2", // 4
            "SeekBar_The slider hook example_0_100", // 5
            "SeekBar_The slider kittymemory example_0_3", // 6
            "Button_The button"}; // 7

    int Total_Feature = (sizeof features /
                         sizeof features[0]); //Now you dont have to manually update the number everytime;

    ret = (jobjectArray) env->NewObjectArray(Total_Feature, env->FindClass("java/lang/String"),
                                             env->NewStringUTF(""));
    int i;
    for (i = 0; i < Total_Feature; i++)
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));
    return (ret);
}

// fancy struct for patches for kittyMemory
struct My_Patches {
    // let's assume we have patches for these functions for whatever game
    // like show in miniMap boolean function
    MemoryPatch GodMode, SpeedHack, Patch1;
    // etc...
} hexPatches;

bool feature1 = false, feature2 = false, feature3 = false;
int speedHack = 0;
int fovModifier = 60;

const char *libName = "libil2cpp.so";

extern "C"
JNIEXPORT void JNICALL
Java_com_frosty_platinmodsmenu_MenuService_Changes(
        JNIEnv *env,
        jobject activityObject,
        jint feature,
        jint value) {

    __android_log_print(ANDROID_LOG_VERBOSE, "Mod Menu", "Feature: = %d", feature);
    __android_log_print(ANDROID_LOG_VERBOSE, "Mod Menu", "Value: = %d", value);

    // You must count your features from 0, not 1
    switch (feature) {
        // The category was 0 so "case 0" is not needed
        case 1: // Switch
            feature1 = !feature1;
            if (feature1) {
                //Remove LOGD before you release the mod
                LOGD("Feature 1 ON");
                // modify & print bytes
                if (hexPatches.GodMode.Modify()) {
                    LOGD("GodMode has been modified successfully");
                    LOGD("Current Bytes: %s", hexPatches.GodMode.get_CurrBytes().c_str());
                }
                //Or
                hexPatches.GodMode.Modify();
            } else {
                LOGD("Feature 1 OFF");
                //restore & print bytes
                if (hexPatches.GodMode.Restore()) {
                    LOGD("canShowInMinimap has been restored successfully");
                    LOGD("Current Bytes: %s", hexPatches.GodMode.get_CurrBytes().c_str());
                }
                //Or
                hexPatches.GodMode.Restore();
            }
            break;
        case 2: // Switch 2
            __android_log_print(ANDROID_LOG_VERBOSE, "Mod Menu", "Value of the switcher 2: = %d",
                                feature2);
            feature2 = !feature2;
            break;
        case 3: // Spinner
            switch (value) {
                case 0:
                    LOGI("Selected item 1");
                    break;
                case 1:
                    LOGI("Selected item 2");
                    break;
                case 2:
                    LOGI("Selected item 3");
                    break;
            }
            break;
            // The another category was 4 so "case 4" is not needed
        case 5: // Slider in hook example
            speedHack = value;
            break;
        case 6: // Slider in KittyMemory example
            if (value == 0) {
                hexPatches.SpeedHack.Restore();
            } else if (value == 1) {
                hexPatches.SpeedHack = MemoryPatch(libName, 0x10000,
                                                   "\x02\x00\xa0\xE3\x1E\xFF\x2F\xE1", 8);
                hexPatches.SpeedHack.Modify();
            } else if (value == 2) {
                hexPatches.SpeedHack = MemoryPatch(libName, 0x10000,
                                                   "\x03\x00\xa0\xE3\x1E\xFF\x2F\xE1", 8);
                hexPatches.SpeedHack.Modify();
            } else if (value == 3) {
                hexPatches.SpeedHack = MemoryPatch(libName, 0x10000,
                                                   "\x04\x00\xa0\xE3\x1E\xFF\x2F\xE1", 8);
                hexPatches.SpeedHack.Modify();
            }
            break;
        case 7: // Button
            LOGI("Button pressed");
            break;
    }
}

bool exampleBooleanForToggle = false;
bool GameManagerLateUpdateHookInitialized = false;

// ---------- Hooking ---------- //


bool (*old_get_IsInvincible)(void *instance);
bool get_IsInvincible(void *instance) {
    if (instance != NULL && feature2) {
        return true;
    }
    //LOGI("get_IsInvincible");
    return old_get_IsInvincible(instance);
}

float (*old_get_fieldOfView)(void *instance);
float get_fieldOfView(void *instance) {
    if (instance != NULL && fovModifier > 1) {
        return (float)fovModifier;
    }
    return old_get_fieldOfView(instance);
}

float (*old_get_MoveSpeedUpRate)(void *instance);
float get_MoveSpeedUpRate(void *instance) {
    if (instance != NULL && speedHack > 1) {
        //LOGI("get_MoveSpeedUpRate hacked");
        return (float) speedHack;
    }
    // LOGI("get_MoveSpeedUpRate");
    return old_get_MoveSpeedUpRate(instance);
}

void (*old_GameManager_LateUpdate)(void *instance);

void GameManager_LateUpdate(void *instance) {
    //Check if instance is NULL to prevent crashes!  If the instance object is NULL,
    //this is what the call to update would look like in C++:
    //NULL.Update(); and dat doesnt make sense right?
    //Also check if our example boolean is true so the hack will work then. if not it just returns the old method
    if (instance != NULL && exampleBooleanForToggle) {
        if (!GameManagerLateUpdateHookInitialized) {
            //Check if this hook initialized. If so log
            GameManagerLateUpdateHookInitialized = true;
            //LOGI("GameManager_LateUpdate hooked");
        }
    }
    old_GameManager_LateUpdate(instance);
}

// we will run our patches in a new thread so our while loop doesn't block process main thread
// Don't forget to remove or comment out logs before you compile it.
void *hack_thread(void *) {
    // loop until our target library is found
    ProcMap il2cppMap;
    do {
        il2cppMap = KittyMemory::getLibraryMap(libName);
        sleep(1);
    } while (!il2cppMap.isValid());

    // now here we do our stuff
    // let's say our patches are meant for an arm library
    // http://shell-storm.org/online/Online-Assembler-and-Disassembler/
    /* Armv7:
     * mov r0, #0
     * bx lr
     * Arm64:
     * mov x0, #0x0
     * ret
    */
    // bytes len = 8

    // This is to tell the compiler to compile that code for arm64 only if compiling arm64 lib.
    // And else, compile for armv7 lib only
    // You may wonder why there is no target for x86.
    // x86 is not our high priority and it is being deprecated

    // by default MemoryPatch will cache library map for faster lookup when use getAbsoluteAddress
    // You can disable this by passing false for last argument
    //gPatches.canShowInMinimap = MemoryPatch("libil2cpp.so", 0x6A6144, "\x01\x00\xA0\xE3\x1E\xFF\x2F\xE1", 8, false);

#if defined(__aarch64__) //Compile for arm64 lib only
    hexPatches.GodMode = MemoryPatch(libName, 0xA672EE,
                                     "\x00\x00\x80\xD2\xC0\x03\x5F\xD6", 8);
    // also possible with hex & no need to specify len
    hexPatches.GodMode = MemoryPatch::createWithHex("libil2cpp.so", 0xA672EE, "000080D2C0035FD6");

    // spaces are fine too
    hexPatches.GodMode = MemoryPatch::createWithHex("libil2cpp.so", 0xA672EE,
                                                    "00 00 80 D2 C0 03 5F D6");

    // ---------- Hook ---------- //
    LOGI("I found the il2cpp lib. Address is: %p", (void *) findLibrary(libName));
    A64HookFunction((void *) getAbsoluteAddress(libName, 0x2BA06F2),
                    (void *) GameManager_LateUpdate, (void **) &old_GameManager_LateUpdate);
    A64HookFunction((void *) getAbsoluteAddress(libName, 0x1B44263), (void *) get_IsInvincible,
                    (void **) &old_get_IsInvincible);

#else //Compile for armv7 lib only. Do not worry about greyed out highlighting code, it still works
    hexPatches.GodMode = MemoryPatch(libName, 0xFCAA6C,
                                          "\x00\x00\xa0\xE3\x1E\xFF\x2F\xE1", 8);

    // also possible with hex & no need to specify len
    hexPatches.GodMode = MemoryPatch::createWithHex("libil2cpp.so", 0xA672EE, "0000A0E31EFF2FE1");

    // spaces are fine too
    hexPatches.GodMode = MemoryPatch::createWithHex("libil2cpp.so", 0xA672EE, "00 00 A0 E3 1E FF 2F E1");

    // ---------- Hook ---------- //
    MSHookFunction((void *) getAbsoluteAddress(libName, 0x70DCCD0),
                   (void *) GameManager_LateUpdate, (void **) &old_GameManager_LateUpdate);
    MSHookFunction((void *) getAbsoluteAddress(libName, 0xA62284), (void *) get_IsInvincible,
                   (void **) &old_get_IsInvincible);

#endif
    LOGI("I found the il2cpp lib. Address is: %p", (void *) findLibrary(libName));

    LOGD("===== New KittyMemory Patch Entry =====");
    LOGD("Patch Address: %p", (void *) hexPatches.GodMode.get_TargetAddress());
    LOGD("Patch Size: %zu", hexPatches.GodMode.get_PatchSize());
    LOGD("Current Bytes: %s", hexPatches.GodMode.get_CurrBytes().c_str());

    LOGD("Loaded...");
    return NULL;
}

JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *globalEnv;
    vm->GetEnv((void **) &globalEnv, JNI_VERSION_1_6);

    // Create a new thread so it does not block the main thread, means the game would not freeze
    pthread_t ptid;
    pthread_create(&ptid, NULL, hack_thread, NULL);

    return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL
JNI_OnUnload(JavaVM *vm, void *reserved) {}
