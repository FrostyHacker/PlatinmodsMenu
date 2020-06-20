LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := Frosty
LOCAL_SRC_FILES := src/main.cpp \
	src/Substrate/hde64.c \
	                   src/Includes/Unity/Vector2.hpp \
                   src/Includes/Unity/Vector3.hpp \
                   src/Includes/Unity/Quaternion.hpp \
	src/Substrate/SubstrateDebug.cpp \
	src/Substrate/SubstrateHook.cpp \
	src/Substrate/SubstratePosixMemory.cpp \
	src/KittyMemory/KittyMemory.cpp \
	src/KittyMemory/MemoryPatch.cpp \
    src/KittyMemory/MemoryBackup.cpp \
    src/KittyMemory/KittyUtils.cpp \
	src/And64InlineHook/And64InlineHook.cpp

include $(BUILD_SHARED_LIBRARY)
