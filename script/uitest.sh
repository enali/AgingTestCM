#!/bin/bash

# usage: ./uitest.sh run
#        ./uitest.sh init

SDK_PATH=$HOME/adt/sdk
ADB_PATH=$SDK_PATH/playform-tools
ANDROID_PATH=$SDK_PATH/tools
PROJ_NAME=AgingTestCM


####    run test   ####
if [ "$1" = "run" ]; then
    $ANDROID_PATH/android create uitest-project -n $PROJ_NAME -t 1 -p ../
    cd ../
    ant build
    $ADB_PATH/adb push ../bin/${PROJ_NAME}.jar /data/local/tmp/
    $ADB_PATH/adb shell uiautomator runtest ${PROJ_NAME}.jar
fi
####    end test   ####

####    init test   ####
UITEST_PHONE_PATH=/sdcard/uitest
UITEST_HOST_PATH=../uitest
if [ "$1" = "init" ]; then
    $ADB_PATH/adb shell rm -rf $UITEST_PHONE_PATH
    $ADB_PATH/adb push $UITEST_HOST_PATH $UITEST_PHONE_PATH
fi
####    end init    ####
