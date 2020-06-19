LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := Frosty
LOCAL_SRC_FILES := src/main.cpp \
		   src/Substrate/hde64.c \
                   src/Substrate/SubstrateDebug.cpp \
                   src/Substrate/SubstrateHook.cpp \
                   src/Substrate/SubstratePosixMemory.cpp

include $(BUILD_SHARED_LIBRARY)
